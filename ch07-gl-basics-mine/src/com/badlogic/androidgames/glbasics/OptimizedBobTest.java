package com.badlogic.androidgames.glbasics;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.Vertices;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.badlogic.androidgames.framework.gl.FPSCounter;

public class OptimizedBobTest extends GLGame 
{
	public Screen getStartScreen()
	{
		return new BobScreen(this);
	}
	
    class BobScreen extends Screen
    {
        static final int NUM_BOBS = 100;
        GLGraphics glGraphics;
        Texture bobTexture;
        Vertices bobModel;
        Bob[] bobs;
		FPSCounter fps = new FPSCounter();
        
        public BobScreen(Game game) {
            super(game);
            glGraphics = ((GLGame)game).getGLGraphics();
			
			bobTexture = new Texture((GLGame)game, "bobrgb888-32x32.png");
			
			bobModel = new Vertices(glGraphics, 4, 12, false, true);
			bobModel.setVertices(new float[] { 
												-16, -16, 0, 1,
												 16, -16, 1, 1,
												 16,  16, 1, 0,
												-16,  16, 0, 0
			
											 }, 0, 16);
			bobModel.setIndices(new short[] { 0, 1, 2, 2, 3, 0 }, 0, 6);
			
			bobs = new Bob[100];
			for(int i = 0; i < 100; i++)
			{
				bobs[i] = new Bob();
			}
		}
	
	
		@Override
		public void update(float deltaTime)
		{
			game.getInput().getTouchEvents();
			game.getInput().getKeyEvents();
			
			for(int i = 0; i < NUM_BOBS; i++)
			{
				bobs[i].update(deltaTime);
			}
		}
		
		@Override
		public void present(float deltaTime)
		{
			GL10 gl = glGraphics.getGL();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			bobModel.bind();
			for(int i = 0; i < NUM_BOBS; i++)
			{
				gl.glLoadIdentity();
				gl.glTranslatef(bobs[i].x, bobs[i].y, 0);
				gl.glRotatef(45, 0, 0, 1);
				gl.glScalef(2, 0.5f, 1);
				bobModel.drawOptimal(GL10.GL_TRIANGLES, 0, 6);
			}
			bobModel.unbind();
			
			fps.logFrame();
		}


		@Override
		public void pause() {
			
		}


		@Override
		public void resume() {
			GL10 gl = glGraphics.getGL();
			gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
			gl.glClearColor(1, 0, 0, 1);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, 320, 0, 480, 1, -1);
			bobTexture.reload();
			gl.glEnable(GL10.GL_TEXTURE_2D);
			bobTexture.bind();
			gl.glMatrixMode(GL10.GL_MODELVIEW);
		}


		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
		
}
}
