package com.example.paint1;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class PaintSurfaceView extends SurfaceView implements Callback {

	int mStrokeWidth = 4;
	Bitmap bmp;
	Path path;
	float prevX, prevY;
	Paint paint;
	
	public PaintSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		
		path = new Path();
		paint = new Paint();
		 this.paint = new Paint();
	        this.paint.setStrokeWidth(5);
	        this.paint.setAntiAlias(true);
	        this.paint.setStrokeJoin(Paint.Join.ROUND); // 繋ぎ目
	        this.paint.setStrokeCap(Paint.Cap.ROUND); // 線の両端形状
	        this.paint.setStyle(Paint.Style.STROKE); // 強さ
	        //this.paint.setMaskFilter(mastFilter); // フィルター
	}

	/*
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.test13), 0, 0, new Paint());
	}
	*/
	

	@Override
	public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		/*
		Canvas canvas = getHolder().lockCanvas();
		
		if (!bmp.isMutable()) {
			bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
		}
		canvas.drawBitmap(bmp, new Rect(0,0,bmp.getWidth(),bmp.getHeight()), new Rect(0,0,canvas.getWidth(), canvas.getHeight()), new Paint());
		
		getHolder().unlockCanvasAndPost(canvas);
		*/
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*
		Canvas canvas = getHolder().lockCanvas();
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		if (!bmp.isMutable()) {
			bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
		}
		canvas.drawBitmap(bmp, new Rect(0,0,bmp.getWidth(),bmp.getHeight()), new Rect(0,0,canvas.getWidth(), canvas.getHeight()), new Paint());
		
		getHolder().unlockCanvasAndPost(canvas);
		*/
		
		float x = event.getX();
		float y = event.getY();
		
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			paintStart(x,y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			paintEnd(x,y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			paintMove(x,y);
			invalidate();
			break;
		}
		
		return true;
	}
	
	private void paintEnd(float x, float y) {
		// TODO Auto-generated method stub
		path.lineTo(x, y);
		//path.reset();
		doDraw();
	}

	private void paintMove(float x, float y) {
		// TODO Auto-generated method stub
		if ((x-prevX)*(x-prevX) + (y-prevY)*(y-prevY) > 1.0) {
			path.lineTo(x, y);
			prevX = x;
			prevY = y;
		}
		
	}

	private void paintStart(float x, float y) {
		// TODO Auto-generated method stub
		path.reset();
		path.moveTo(x, y);
		prevX = x;
		prevY = y;
	}

	
	public void doDraw() {
		Canvas canvas = getHolder().lockCanvas();
		
		if (!bmp.isMutable()) {
			bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
		}
		canvas.drawBitmap(bmp, new Rect(0,0,bmp.getWidth(),bmp.getHeight()), new Rect(0,0,canvas.getWidth(), canvas.getHeight()), new Paint());
		
		canvas.drawPath(path, this.paint);
		getHolder().unlockCanvasAndPost(canvas);
		
		
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		doDraw();
	}

}
