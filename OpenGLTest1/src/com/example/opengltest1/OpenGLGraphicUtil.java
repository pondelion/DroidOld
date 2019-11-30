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
	
	// バッファオブジェクトを保持する
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
		 
		int vertexId = 0;//頂点配列の要素の番号を記憶しておくための変数
		for (int i = 0; i < divides; i++) {
			//円周上のi番目の頂点の角度(ラジアン)を計算します
			float theta1 = 2.0f / (float)divides * (float)i * (float)Math.PI;
		 
			//円周上の(i + 1)番目の頂点の角度(ラジアン)を計算します
			float theta2 = 2.0f / (float)divides * (float)(i+1) * (float)Math.PI;
		 
			//i番目の三角形の0番目の頂点情報をセットします
			vertices[vertexId++] = x;
			vertices[vertexId++] = y;
		 
			//i番目の三角形の1番目の頂点の情報をセットします (円周上のi番目の頂点)
			vertices[vertexId++] = (float)Math.cos((double)theta1) * radius + x;//x座標
			vertices[vertexId++] = (float)Math.sin((double)theta1) * radius + y;//y座標
		 
			//i番目の三角形の2番目の頂点の情報をセットします (円周上のi+1番目の頂点)
			vertices[vertexId++] = (float)Math.cos((double)theta2) * radius + x;//x座標
			vertices[vertexId++] = (float)Math.sin((double)theta2) * radius + y;//y座標
		}
		FloatBuffer polygonVertices = makeVerticesBuffer(vertices);
		
		//ポリゴンの色を指定します
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
