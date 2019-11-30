package com.example.takeapicture;

import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.hardware.Camera;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PictureCallback {

	private SurfaceHolder mHolder;
	private Camera mCamera;
	
	public CameraView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void onPictureTaken(byte[] arg0, Camera arg1) {
		// TODO Auto-generated method stub
		try {
			String path = Environment.getExternalStorageDirectory() + "/data/" + System.currentTimeMillis() + ".jpg";
			data2file(arg0, path);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void data2file(byte[] data, String fileName) throws Exception {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			out.write(data);
			out.close();
		} catch(Exception e) {
			if(out != null) {
				out.close();
			}
			throw e;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		mCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		try {
			mCamera = Camera.open();
			mCamera.setPreviewDisplay(mHolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}
	
	public void takeAPicture() {
		try {
			mCamera.takePicture(null, null, null, this);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
