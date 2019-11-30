package com.example.opengltest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class OpenGLBaseRenderer implements Renderer {

	
	private GL10	m_gl;
	private int		m_iWidth;	// �T�[�t�F�[�X�T�C�Y
	private int		m_iHeight;	// �T�[�t�F�[�X�T�C�Y
	private boolean				m_bViewingFrustumValid;			// ����p����ݒ�̗L����
	private boolean				m_bViewingTransformValid;			// ���_���W�ϊ��ݒ�̗L����

	public int getWidth()
	{
		return m_iWidth;
	}

	public int getHeight()
	{
		return m_iHeight;
	}
	
	public GL10 getGL()
	{
		return m_gl;
	}
	public boolean isViewingFrustumValid()
	{
		return m_bViewingFrustumValid;
	}

	public boolean isViewingTransformValid()
	{
		return m_bViewingTransformValid;
	}

	public void setViewingFrustumValid( boolean arg )
	{
		m_bViewingFrustumValid = arg;
	}

	public void setViewingTransformValid( boolean arg )
	{
		m_bViewingTransformValid = arg;
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );

		if( false == isViewingFrustumValid() )
		{
			setupViewingFrustum();
		}

		if( false == isViewingTransformValid() )
		{
			setupViewingTransform();
		}

		preRenderScene();

		gl.glPushMatrix();
		renderStockScene();
		gl.glPopMatrix();

		gl.glPushMatrix();
		renderScene();
		gl.glPopMatrix();

		postRenderScene();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		m_iWidth = width;
		m_iHeight = height;

		// �r���[�|�[�g�ݒ�
		setupViewport();

		// ����p����ݒ�̖������i�`�揈�����ɍĐݒ肳��A�L���������j
		setViewingFrustumValid( false );

		// ���_���W�ϊ��ݒ�̖������i�`�揈�����ɍĐݒ肳��A�L���������j
		setViewingTransformValid( false );
	}
	
	private void setupViewport()
	{
		m_gl.glViewport( 0, 0, m_iWidth, m_iHeight );
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		m_gl = gl;
		
		gl.glClearColor(0.0f,  0.0f,  0.0f,  1.0f);
		gl.glClearDepthf(1.0f);
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glDepthMask(true);
		
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_NORMALIZE);
		gl.glDisable(GL10.GL_POLYGON_OFFSET_FILL);
		gl.glPolygonOffset(1.0f,  1.0f);
		
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_POINT_SMOOTH);
		
		
	}

}
