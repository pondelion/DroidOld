package com.example.graphicloudactivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LoudSurFaceView extends SurfaceView implements SurfaceHolder.Callback {

	private int mCircleX = 0;
	private int mCircleY = 0;
	private double mRatio = 0;
	
	private SurfaceHolder mHolder;
	private Canvas mCanvas = null;
	private Paint mPaint = null;
	
	public LoudSurFaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setFixedSize(getWidth(), getHeight());
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCanvas  = holder.lockCanvas();
		mCanvas.drawColor(Color.BLACK);
		holder.unlockCanvasAndPost(mCanvas);
		
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);
		
		mCircleX = getWidth() /2 ;
		mCircleY = getHeight() / 2;
		mRatio = Math.min(getWidth(), getHeight()) / 20000.0;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	public void drawCircle(short vol) {
		try {
		
		mCanvas = getHolder().lockCanvas();
		
		mCanvas.drawCircle(mCircleX, mCircleY, (float)(vol/2*mRatio/2), mPaint);
		
		getHolder().unlockCanvasAndPost(mCanvas);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
