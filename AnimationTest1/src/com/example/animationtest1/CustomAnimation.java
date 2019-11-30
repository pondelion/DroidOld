package com.example.animationtest1;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

public class CustomAnimation extends Animation {

	private Camera mCamera;
	private ImageView mImageView;
	
	public CustomAnimation(ImageView imageView) {
		
		this.setDuration(2000);
		this.setRepeatCount(-1);
		mImageView = imageView;
		
	}
	
	@Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		float theta = 2.0f *  interpolatedTime * 180.0f; //interpolatedTime��0(�A�j���[�V�����J�n��)�`1(�A�j���[�V�����I����)�͈̔͂ŗ^������̂ŁA�I������theta��360�x�ɂȂ�悤��theta���v�Z
		
		Matrix mat = t.getMatrix();
		
		mCamera.save();
		mCamera.rotateX(70.0f);	//x�����S��70�x��]������
		mCamera.rotateZ(theta); //z�����S��theta��]������
		
		mCamera.getMatrix(mat);
		mCamera.restore();
		
		mat.preTranslate(-mImageView.getWidth()/2.0f, -mImageView.getHeight()/2.0f);
		mat.postTranslate(mImageView.getWidth()/2.0f, mImageView.getHeight()/2.0f);
	}
}
