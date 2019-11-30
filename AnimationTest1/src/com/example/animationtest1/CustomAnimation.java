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
		float theta = 2.0f *  interpolatedTime * 180.0f; //interpolatedTimeが0(アニメーション開始時)～1(アニメーション終了時)の範囲で与えられるので、終了時にthetaが360度になるようにthetaを計算
		
		Matrix mat = t.getMatrix();
		
		mCamera.save();
		mCamera.rotateX(70.0f);	//x軸中心に70度回転させる
		mCamera.rotateZ(theta); //z軸中心にtheta回転させる
		
		mCamera.getMatrix(mat);
		mCamera.restore();
		
		mat.preTranslate(-mImageView.getWidth()/2.0f, -mImageView.getHeight()/2.0f);
		mat.postTranslate(mImageView.getWidth()/2.0f, mImageView.getHeight()/2.0f);
	}
}
