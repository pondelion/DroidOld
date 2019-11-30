package com.example.opengltest1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class MyRenderer implements Renderer {

	
	private int mWidth;
	private int mHeight;
	private List<Circle> mCircleList;
	
	public MyRenderer() {
		mCircleList = new ArrayList<Circle>();
		Circle circle = new MyCircle(0.1f, 0.0f, 0.0f, 0.1f, 0.1f, 0.5f, 0.0f, 0.0f, 1.0f);
		mCircleList.add(circle);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub

		mWidth = width;
		mHeight = height;
		
		OpenGLManager.gl = gl;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub

		gl.glViewport(0, 0, mWidth, mHeight);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		
		gl.glLoadIdentity();
		gl.glOrthof(-1.0f, 1.0f, -1.0f, 1.0f, 0.5f, -0.5f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glClearColor(0.5f,  0.5f, 0.5f, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		for (Circle c : mCircleList) {
			c.update(0.01f);
			Circle.draw(c);
		}
		/*
		float[] vertices = {
					-0.5f, -0.5f,
					0.5f, -0.5f,
					-0.5f, 0.5f,
					0.5f, 0.5f,
		};
		
		float[] colors = {
				1.0f, 1.0f, 1.0f, 1.0f,
				0.0f, 1.0f, 1.0f, 1.0f,
				0.0f, 0.0f, 0.0f, 0.0f,
				1.0f, 0.0f, 0.0f, 1.0f,
		};
		
		FloatBuffer polygonVertices = makeFloatBuffer(vertices);
		FloatBuffer polygonColors = makeFloatBuffer(colors);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, polygonColors);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		*/
		
	}
	/*
	public static final FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	*/

}
