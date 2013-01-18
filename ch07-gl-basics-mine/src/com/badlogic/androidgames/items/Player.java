package com.badlogic.androidgames.items;

import com.badlogic.androidgames.framework.gl.Vertices;
import com.badlogic.androidgames.framework.gl.Texture;

public class Player {
	boolean blendEnabled;
	Vertices vertices;
	Texture texture;
	float x, y;
	float textureWidth, textureHeight;
	
	public Player(Vertices vertices, Texture texture, float x, float y, float textureWidth, float textureHeight)
	{
		this.vertices = vertices;
		this.texture = texture;
		this.blendEnabled = false;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.x = x;
		this.y = y;
	}
	
	public Player(Vertices vertices, Texture texture, float x, float y,  float textureWidth, float textureHeight, boolean blendEnabled)
	{
		this.vertices = vertices;
		this.texture = texture;
		this.blendEnabled = blendEnabled;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.x = x;
		this.y = y;
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
	
	public float getHeight()
	{
		return textureHeight;
	}
	
	public float getWidth()
	{
		return textureWidth;
	}
	
	public void moveX(float x)
	{
		this.x += x;
	}
	
	public void moveY(float y)
	{
		this.y += y;
	}
	
	public boolean getBlend()
	{
		return blendEnabled;
	}
	
	public boolean collision(float x, float y, float width, float height)
	{
		if(   (x + width) > this.x  && x <= (this.x + this.textureWidth) &&
			  (y + height) > this.y && y <= (this.y + this.textureHeight)				
		  )
		{
			return true;
		}
		return false;
	}
}
