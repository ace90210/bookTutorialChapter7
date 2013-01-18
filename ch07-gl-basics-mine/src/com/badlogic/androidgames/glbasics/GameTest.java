package com.badlogic.androidgames.glbasics;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.Vertices;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.badlogic.androidgames.items.Player;
import com.badlogic.androidgames.items.Ball;

public class GameTest extends GLGame {
	public Screen getStartScreen()
	{
		return new GameTestScreen(this);
	}
	
	public class GameTestScreen extends Screen implements OnKeyListener {

		GLGraphics glGraphics;

		List<Player> pls= new ArrayList<Player>();
		List<Ball> balls= new ArrayList<Ball>();
		
		String[] spriteFiles = { "bobargb8888.png", "bobrgb888.png"};
		int screenHeight;
		int screenWidth;
		final int KEY_PAD_DOWN = 20;
		final int KEY_PAD_UP = 19;
		final int KEY_PAD_LEFT = 21;
		final int KEY_PAD_RIGHT = 22;
		final int KEY_PAD_X = 23;
		final int KEY_PAD_CIRCLE = 103;
		final int KEY_PAD_TRIANGLE = 100;
		final int KEY_PAD_SQUARE = 99;
		final int BALL_SPEED_X = 50;
		final int BALL_SPEED_Y = 50;
		final float PLAYER_SPEED = 15.0f;
		
		public GameTestScreen(Game game)
		{
			super(game);		
			glGraphics = ((GLGame)game).getGLGraphics();
			screenHeight = glGraphics.getHeight();
			screenWidth = glGraphics.getWidth();
			
			makePlayer("bat1.png", 18, (screenHeight / 2) + 64, 32, 128, true);		
			makePlayer("bat2.png", screenWidth - 50, (screenHeight / 2) + 64, 32, 128, true);
			makeBall("ball.png", screenWidth / 2, (screenHeight / 2) + 16, 50, 50, screenWidth, screenHeight,  true);
		}

		@Override
		public void update(float deltaTime) 
		{       
	        //update ball
	        balls.get(0).updatePosition(deltaTime);	
	        float ballX = balls.get(0).getX(), ballY = balls.get(0).getY(), ballWidth = balls.get(0).getBallWidth(), ballHeight = balls.get(0).getBallHeight();
	        
			if(ballX < 0 || ballX > (screenWidth - ballWidth))
			{				
				balls.get(0).invertX(deltaTime);
			}
			if(ballY < 0 || ballY > (screenHeight - ballHeight))
			{
				balls.get(0).invertY(deltaTime);
			}
			if(pls.get(0).collision(ballX, ballY, ballWidth, ballHeight))
			{
				balls.get(0).invertX(deltaTime);
			}
			else if(pls.get(1).collision(ballX, ballY, ballWidth, ballHeight))
			{
				balls.get(0).invertX(deltaTime);
			}		
		}

		@Override
		public void present(float deltaTime)
		{
			GL10 gl = glGraphics.getGL();
			gl.glClearColor(0, 0, 1, 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		 	gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, screenWidth, 0, screenHeight, 1, -1);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			
			for(Player p : pls)
			{
				if(p.getBlend())
				{
					gl.glEnable(GL10.GL_BLEND);
					gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				}
				else
				{
					gl.glDisable(GL10.GL_BLEND);
				}
				p.getTexture().bind();
				gl.glMatrixMode(GL10.GL_MODELVIEW);
		    	gl.glLoadIdentity();
				gl.glTranslatef(p.getX(), p.getY(), 0);
				p.getVertices().draw(GL10.GL_TRIANGLES, 0, 6);
			}
			for(Ball b : balls)
			{
				if(b.getBlend())
				{
					gl.glEnable(GL10.GL_BLEND);
					gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				}
				else
				{
					gl.glDisable(GL10.GL_BLEND);
				}
				b.getTexture().bind();
				gl.glMatrixMode(GL10.GL_MODELVIEW);
		    	gl.glLoadIdentity();
				gl.glTranslatef(b.getX(), b.getY(), 0);
				b.getVertices().draw(GL10.GL_TRIANGLES, 0, 6);
			}
		}
		
		private void makePlayer(String textureLocation, float x, float y, float width, float height, boolean blend)
		{
			Texture playerTex = new Texture((GLGame)game, textureLocation);
			Vertices playerVert = new Vertices(glGraphics, 4, 12, blend, true);
			playerVert.createSprite(0, 0, width, height, blend);
			
			pls.add(new Player(playerVert, playerTex, x, y, width, height, blend));
		}
		
		private void makeBall(String textureLocation, float x, float y, float width, float height, int screenWidth,int screenHeight, boolean blend)
		{
			Texture playerTex = new Texture((GLGame)game, textureLocation);
			Vertices playerVert = new Vertices(glGraphics, 4, 12, blend, true);
			playerVert.createSprite(0, 0, width, height, blend);
			
			balls.add(new Ball(playerVert, playerTex, x, y, BALL_SPEED_X, BALL_SPEED_Y, screenWidth, screenHeight, width, height, blend));
		}
		

		@Override
		public void pause() {
			
		}

		@Override
		public void resume() {
			
		}

		@Override
		public void dispose() {
			
		}
		
		public boolean onKey(View v, int keyCode, KeyEvent event) 
		{
				boolean validKey = true;
				switch(event.getKeyCode())
				{
					case KeyEvent.KEYCODE_DPAD_CENTER: {
															if(pls.get(0).getY() > 0)
															{
																pls.get(0).moveY(-PLAYER_SPEED);
															}	
													   }																						
													break;
					case KeyEvent.KEYCODE_BACK:  {
													if(event.isAltPressed())
													{							
				        								balls.get(0).addMomentumX(1.05f);
				        								balls.get(0).addMomentumY(1.05f);				        					
													}
													else
													{
														validKey = false;
													}
												}
													break;
					case KeyEvent.KEYCODE_BUTTON_X:	{
														balls.get(0).addMomentumX(0.95f);
														balls.get(0).addMomentumY(0.95f);
													}
													break;
					case KeyEvent.KEYCODE_BUTTON_Y: {
								        				if(pls.get(0).getY() + pls.get(0).getHeight() < screenHeight )
														{
								        					pls.get(0).moveY(PLAYER_SPEED);
														}
													}													
													break;
					case KeyEvent.KEYCODE_DPAD_UP:  {
								        				if(pls.get(0).getY() + pls.get(0).getHeight() < screenHeight )
														{
								        					pls.get(0).moveY(PLAYER_SPEED);
														}
													}
													break;
					case KeyEvent.KEYCODE_DPAD_DOWN: {
															if(pls.get(0).getY() > 0)
															{
																pls.get(0).moveY(-PLAYER_SPEED);
															}	
													 }
													
													break;
					case KeyEvent.KEYCODE_DPAD_RIGHT: 	{
															balls.get(0).addMomentumX(1.05f);
															balls.get(0).addMomentumY(1.05f);
														}
														
													break;
					case KeyEvent.KEYCODE_DPAD_LEFT:  	{
															balls.get(0).addMomentumX(0.95f);
															balls.get(0).addMomentumY(0.95f);
														}
													break;
					case KeyEvent.KEYCODE_BUTTON_SELECT: 
													//builder.append("\"Select\" key was pressed");
													break;
					case KeyEvent.KEYCODE_BUTTON_START: 
													//builder.append("\"Start\" key was pressed");
													break;
					case KeyEvent.KEYCODE_BUTTON_L1: 
													//builder.append("\"L1\" key was pressed");
													break;
					case KeyEvent.KEYCODE_BUTTON_R1: 
													//builder.append("\"R1\" key was pressed");
													break;
				}
				
				return validKey;
			}
	}

}


