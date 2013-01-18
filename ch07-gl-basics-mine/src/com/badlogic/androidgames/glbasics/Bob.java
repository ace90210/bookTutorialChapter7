package com.badlogic.androidgames.glbasics;

import java.util.Random;

public class Bob {
	static final Random rand = new Random();
	public float x, y;
	float dirX, dirY;

	public Bob()
	{
		x = rand.nextFloat() * 320;
		y = rand.nextFloat() * 480;
		dirX = 50;
		dirY = 50;
	}
	
	public void update(float deltaTime)
	{
		x = x + dirX * deltaTime;
		y = y + dirY * deltaTime;
		
		if(x < 0)
		{
			dirX = -dirX;
		}
		if(x > 320)
		{
			dirX = -dirX;
		}
		if( y < 0)
		{
			dirY = -dirY;
		}
		if( y > 480)
		{
			dirY = -dirY;
		}
	}
}
