package com.badlogic.androidgames.framework.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.impl.GLGraphics;

public class Vertices {
	final GLGraphics glGraphics;
	final boolean hasColor;
	final boolean hasTexCoords;
	final int vertexSize;
	final FloatBuffer vertices;
	final ShortBuffer indices;
	
	public Vertices(GLGraphics glGraphics, int maxVertices, int maxIndices, boolean hasColor, boolean hasTexCoords)
	{
		this.glGraphics = glGraphics;
		this.hasColor = hasColor;
		this.hasTexCoords = hasTexCoords;
		this.vertexSize = (2 + (hasColor?4:0) + (hasTexCoords?2:0)) * 4;
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(maxVertices * vertexSize);
		buffer.order(ByteOrder.nativeOrder());
		vertices = buffer.asFloatBuffer();
		
		if(maxIndices > 0)
		{
			buffer = ByteBuffer.allocateDirect(maxIndices * Short.SIZE / 8);
			buffer.order(ByteOrder.nativeOrder());
			indices = buffer.asShortBuffer();
		}
		else
		{
			indices = null;
		}
	}
	
	public void setVertices(float[] vertices, int offset, int length)
	{
		this.vertices.clear();
		this.vertices.put(vertices, offset, length);
		this.vertices.flip();
	}
	
	public void setIndices(short[] indices, int offset, int length)
	{
		this.indices.clear();
		this.indices.put(indices, offset, length);
		this.indices.flip();
	}
	
	public void createSprite(float x, float y, float width, float height, boolean blend)
	{
		float[] points;
		short[] indices;
		int vertOffset;
		int indOffset;
		int vertLength;
		int indLength;
		
		if(blend)
		{
			points = new float[] { 
					 x,   	    y, 			1, 1, 1, 1,	0, 1,
					 x + width, y, 			1, 1, 1, 1, 1, 1,
					 x + width, y + height, 1, 1, 1, 1, 1, 0,
					 x,   	    y + height, 1, 1, 1, 1, 0, 0
	
				 };
			vertOffset = 0;
			vertLength = points.length;
			
			indices = new short[] { 0, 1, 2, 2, 3, 0 };
			indOffset = 0;
			indLength = indices.length;
		}
		else
		{
			points = new float[] { 
					 x,   	    y,   		0, 1,
					 x + width, y,   		1, 1,
					 x + width, y + height, 1, 0,
					 x,   	    y + height, 0, 0
	
				 };
			vertOffset = 0;
			vertLength = points.length;
			
			indices = new short[] { 0, 1, 2, 2, 3, 0 };
			indOffset = 0;
			indLength = indices.length;
		}
		this.vertices.clear();
		this.vertices.put(points, vertOffset, vertLength);
		this.vertices.flip();
		
		this.indices.clear();
		this.indices.put(indices, indOffset, indLength);
		this.indices.flip();
	}
	
	public void bind()
	{
		GL10 gl = glGraphics.getGL();
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertices.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		
		if(hasColor)
		{
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			vertices.position(2);
			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
		}
		if(hasTexCoords)
		{
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			vertices.position(hasColor?6:2);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		}
	}
	
	public void unbind()
	{
		GL10 gl = glGraphics.getGL();
		if(hasTexCoords)
		{
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		
		if(hasColor)
		{
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}
	}
	
	public void draw(int primativeType, int offset, int numVertices)
	{
		GL10 gl = glGraphics.getGL();
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertices.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		
		if(hasColor)
		{
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			vertices.position(2);
			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
		}
		if(hasTexCoords)
		{
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			vertices.position(hasColor?6:2);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		}
		
		if(indices != null)
		{
			indices.position(offset);
			gl.glDrawElements(primativeType, numVertices,  GL10.GL_UNSIGNED_SHORT,  indices);
		}
		else
		{
			gl.glDrawArrays(primativeType, offset, numVertices);
		}
		
		if(hasTexCoords)
		{
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		
		if(hasColor)
		{
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}
	}
	
	public void drawOptimal(int primativeType, int offset, int numVertices)
	{
		GL10 gl = glGraphics.getGL();
				
		if(indices != null)
		{
			indices.position(offset);
			gl.glDrawElements(primativeType, numVertices,  GL10.GL_UNSIGNED_SHORT,  indices);
		}
		else
		{
			gl.glDrawArrays(primativeType, offset, numVertices);
		}
	}
}
