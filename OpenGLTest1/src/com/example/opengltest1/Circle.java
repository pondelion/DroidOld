package com.example.opengltest1;

abstract public class Circle {

	protected float mRadius;
	protected float mCenterX, mCenterY;
	protected float mVelocityX, mVelocityY;
	protected float mColorR, mColorG, mColorB, mColorA;
	
	public Circle(float radius, float centerX, float centerY, float velocityX, float velocityY, float colorR, float colorG, float colorB, float colorA) {
		this.mRadius	= radius;
		this.mCenterX	= centerX;
		this.mCenterY	= centerY;
		this.mVelocityX	= velocityX;
		this.mVelocityY	= velocityY;
		this.mColorR	= colorR;
		this.mColorG	= colorG;
		this.mColorB	= colorB;
		this.mColorA	= colorA;
	}
	
	abstract public void update(float dx);
	
	public static void draw(Circle circle) {
		OpenGLGraphicUtil.drawCircle(OpenGLManager.gl, circle.mCenterX,circle.mCenterY, 36,  circle.mRadius, circle.mColorR, circle.mColorG, circle.mColorB, circle.mColorA);
	}
	
	public static void moveTo(Circle circle, float xdest, float ydest) {
		circle.mCenterX	= xdest;
		circle.mCenterY	= ydest;
	}
	
	public static void moveBy(Circle circle, float dx, float dy) {
		circle.mCenterX	+= dx;
		circle.mCenterY	+= dy;
	}
}
