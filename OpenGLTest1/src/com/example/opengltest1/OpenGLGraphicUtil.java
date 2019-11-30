package com.example.opengltest1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Hashtable;

import javax.microedition.khronos.opengles.GL10;

public class OpenGLGraphicUtil {
	
	private static Hashtable<Integer, float[]> verticesPool = new Hashtable<Integer, float[]>();
	private static Hashtable<Integer, float[]> colorsPool = new Hashtable<Integer, float[]>();
	private static Hashtable<Integer, float[]> coordsPool = new Hashtable<Integer, float[]>();
	
	public static float[] getVertices(int n) {
		if (verticesPool.containsKey(n)) {
			return verticesPool.get(n);
		}
		float[] vertices = new float[n];
		verticesPool.put(n, vertices);
		return vertices;
	}
 
	public static float[] getColors(int n) {
		if (colorsPool.containsKey(n)) {
			return colorsPool.get(n);
		}
		float[] colors = new float[n];
		colorsPool.put(n, colors);
		return colors;
	}
 
	public static float[] getCoords(int n) {
		if (coordsPool.containsKey(n)) {
			return coordsPool.get(n);
		}
		float[] coords = new float[n];
		coordsPool.put(n, coords);
		return coords;
	}
	
	// �o�b�t�@�I�u�W�F�N�g��ێ�����
	private static Hashtable<Integer, FloatBuffer> polygonVerticesPool = new Hashtable<Integer, FloatBuffer>();
	private static Hashtable<Integer, FloatBuffer> polygonColorsPool = new Hashtable<Integer, FloatBuffer>();
	private static Hashtable<Integer, FloatBuffer> texCoordsPool = new Hashtable<Integer, FloatBuffer>();
	
	public static final FloatBuffer makeVerticesBuffer(float[] arr) {
		FloatBuffer fb = null;
		if (polygonVerticesPool.containsKey(arr.length)) {
			fb = polygonVerticesPool.get(arr.length);
			fb.clear();
			fb.put(arr);
			fb.position(0);
			return fb;
		}
		fb = makeFloatBuffer(arr);
		polygonVerticesPool.put(arr.length, fb);
		return fb;
	}
 
	public static final FloatBuffer makeColorsBuffer(float[] arr) {
		FloatBuffer fb = null;
		if (polygonColorsPool.containsKey(arr.length)) {
			fb = polygonColorsPool.get(arr.length);
			fb.clear();
			fb.put(arr);
			fb.position(0);
			return fb;
		}
		fb = makeFloatBuffer(arr);
		polygonColorsPool.put(arr.length, fb);
		return fb;
	}
 
	public static final FloatBuffer makeTexCoordsBuffer(float[] arr) {
		FloatBuffer fb = null;
		if (texCoordsPool.containsKey(arr.length)) {
			fb = texCoordsPool.get(arr.length);
			fb.clear();
			fb.put(arr);
			fb.position(0);
			return fb;
		}
		fb = makeFloatBuffer(arr);
		texCoordsPool.put(arr.length, fb);
		return fb;
	}
	
	public static final void drawCircle(GL10 gl, float x, float y, int divides, float radius, float r, float g, float b, float a) {
		float[] vertices = getVertices(divides * 3 * 2);
		 
		int vertexId = 0;//���_�z��̗v�f�̔ԍ����L�����Ă������߂̕ϐ�
		for (int i = 0; i < divides; i++) {
			//�~�����i�Ԗڂ̒��_�̊p�x(���W�A��)���v�Z���܂�
			float theta1 = 2.0f / (float)divides * (float)i * (float)Math.PI;
		 
			//�~�����(i + 1)�Ԗڂ̒��_�̊p�x(���W�A��)���v�Z���܂�
			float theta2 = 2.0f / (float)divides * (float)(i+1) * (float)Math.PI;
		 
			//i�Ԗڂ̎O�p�`��0�Ԗڂ̒��_�����Z�b�g���܂�
			vertices[vertexId++] = x;
			vertices[vertexId++] = y;
		 
			//i�Ԗڂ̎O�p�`��1�Ԗڂ̒��_�̏����Z�b�g���܂� (�~�����i�Ԗڂ̒��_)
			vertices[vertexId++] = (float)Math.cos((double)theta1) * radius + x;//x���W
			vertices[vertexId++] = (float)Math.sin((double)theta1) * radius + y;//y���W
		 
			//i�Ԗڂ̎O�p�`��2�Ԗڂ̒��_�̏����Z�b�g���܂� (�~�����i+1�Ԗڂ̒��_)
			vertices[vertexId++] = (float)Math.cos((double)theta2) * radius + x;//x���W
			vertices[vertexId++] = (float)Math.sin((double)theta2) * radius + y;//y���W
		}
		FloatBuffer polygonVertices = makeVerticesBuffer(vertices);
		
		//�|���S���̐F���w�肵�܂�
		gl.glColor4f(r, g, b, a);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	 
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	 
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, divides * 3);
	}
	
	public static final FloatBuffer makeFloatBuffer(float[] buf) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(buf.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		floatBuffer.put(buf);
		floatBuffer.position(0);
		return floatBuffer;
	}
}
