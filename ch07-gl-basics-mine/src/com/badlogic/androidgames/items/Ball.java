package com.badlogic.androidgames.items;

import com.badlogic.androidgames.framework.gl.Vertices;
import com.badlogic.androidgames.framework.gl.Texture;

public class Ball {
	boolean blendEnabled;
	Vertices vertices;
	Texture texture;
	int screenHeight;
	int screenWidth;
	float textureHeight;
	float textureWidth;
	float x, y;
	float momentumX, momentumY;
	
	public Ball(Vertices vertices, Texture texture, float x, float y, float speedX, float speedY, int screenWidth, int screenHeight, float textureWidth, float textureHeight)
	{
		this.vertices = vertices;
		this.texture = texture;
		this.blendEnabled = false;
		this.momentumX = speedX;
		this.momentumY = speedY;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.x = x;
		this.y = y;
	}
	
	public Ball(Vertices vertices, Texture texture, float x, float y, float speedX, float speedY, int screenWidth, int screenHeight, float textureWidth, float textureHeight, boolean blendEnabled)
	{
		this.vertices = vertices;
		this.texture = texture;
		this.blendEnabled = blendEnabled;
		this.momentumX = speedX;
		this.momentumY = speedY;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.x = x;
		this.y = y;
	}
	
	public void updatePosition(float deltaTime)
	{
		this.x += (deltaTime * this.momentumX);
		this.y += (deltaTime * this.momentumY);
		
	}
	
	public void invertX(float deltaTime)
	{
		x -=(deltaTime * this.momentumX);
		momentumX = -momentumX;
	}
	
	public void invertY(float deltaTime)
	{
		y -=(deltaTime * this.momentumY);
		momentumY = -momentumY;
	}
	
	public Vertices getVertices()
	{
		return vertices;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getBallHeight()
	{
		return textureHeight;
	}
	
	public float getBallWidth()
	{
		return textureWidth;
	}
	
	public void addMomentumX(float x)
	{
		this.momentumX *= x;
	}
	
	public void addMomentumY(float y)
	{
		this.momentumY *= y;
	}
	
	public boolean getBlend()
	{
		return blendEnabled;
	}

}
