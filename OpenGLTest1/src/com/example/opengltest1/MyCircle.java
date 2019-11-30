package com.example.opengltest1;

public class MyCircle extends Circle {

	

	public MyCircle(float radius, float centerX, float centerY,
			float velocityX, float velocityY, float colorR, float colorG,
			float colorB, float colorA) {
		super(radius, centerX, centerY, velocityX, velocityY, colorR, colorG, colorB,
				colorA);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		mCenterX	+= mVelocityX * dt;
		mCenterY	+= mVelocityY * dt;
		
		if ((mCenterX + mRadius) >= 1.0f) {
			rightWallHit();
		} else if ((mCenterX - mRadius) <= -1.0f) {
			leftWallHit();
		}
		
		if ((mCenterY + mRadius) >= 1.0f) {
			topWallHit();
		} else if ((mCenterY - mRadius) <= -1.0f) {
			bottomWallHit();
		}
	}

	private void topWallHit() {
		// TODO Auto-generated method stub
		mVelocityY = -mVelocityY;
	}

	private void leftWallHit() {
		// TODO Auto-generated method stub
		mVelocityX = -mVelocityX;
	}

	private void bottomWallHit() {
		// TODO Auto-generated method stub
		mVelocityY = -mVelocityY;
	}

	private void rightWallHit() {
		// TODO Auto-generated method stub
		mVelocityX = -mVelocityX;
	}

}
