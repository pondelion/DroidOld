package com.ollyolly.cameratest1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.Toast;

public class CameraView extends SurfaceView implements Callback, PictureCallback {

	private Camera mCamera;
	private CameraTest1 mContext;
	private long mTime = 0;
	private Handler uiHandler = null;
	private boolean isSending = false;
	
	public CameraView(Context context, Handler handler) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = (CameraTest1)context;
		this.uiHandler = handler;
		SurfaceHolder holder = this.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(this);
	}
	/*
	public CameraView(Context context, AttributeSet attr) {
		super(context, attr);
	}
	*/

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

		this.mCamera.stopPreview();
        
        Parameters params = this.mCamera.getParameters();
         
        List<Size> supportedSizes = params.getSupportedPreviewSizes();
        Collections.sort(supportedSizes, new Comparator<Size>() {
        	@Override
        	public int compare(Size size1, Size size2) {
        		return size1.width - size2.width;
        	}
        });
        Size previewSize = supportedSizes.get(0);
        
        for(Size s : supportedSizes) {
        	if(s.width > width) {
        		break;
        	}
        	previewSize = s;
        }
       
       
        //params.setPictureSize(480, 320);
        
        params.setPreviewSize(previewSize.width, previewSize.height);
        
        
        
        
        this.mCamera.setParameters(params);
        this.mCamera.setDisplayOrientation(90);
        this.mCamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

		this.mCamera = Camera.open();
		
		if (this.mCamera != null) {
			
			try {
					this.mCamera.setPreviewDisplay(holder);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			Toast.makeText(null, "Camera.open() failed", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		/*
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			//Toast.makeText(mContext, String.valueOf(mTime), Toast.LENGTH_LONG).show();
			if (mCamera != null) {
				mCamera.takePicture(null, null, this);
			}
		}
		*/
		
		return true;
	}
		

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (this.mCamera != null) {
			this.mCamera.stopPreview();
			this.mCamera.release();
			this.mCamera = null;
		}
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
		File path = new File(Environment.getExternalStorageDirectory(), "CameraTest1");
		String filename = "test.jpg";
		File file = new File(path, filename);
		
		
		try {
			if(!path.exists()) {
				path.mkdirs();
			}
			/*
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data);
			fos.close();
			*/
			
			
			
			//BitmapFactory.Options options = new BitmapFactory.Options();
			//options.inJustDecodeBounds = true;
			//BitmapFactory.decodeFile(file.getAbsolutePath(), options);
			//int scaleW = options.outWidth / 300;
			//int scaleH = options.outHeight / 200;
			//int scaleW = bmp.getWidth() / 300;
			//int scaleH = bmp.getHeight() / 200;
			//int scale = Math.max(scaleW, scaleH);
			//options.inJustDecodeBounds = false;
			//options.
			FileOutputStream fos = new FileOutputStream(file);
			
			Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
			
			//Toast.makeText(mContext, String.valueOf(bmp.getWidth()) + " �~ " + String.valueOf(bmp.getHeight()), Toast.LENGTH_LONG).show();
			Matrix matrix = new Matrix();
			//matrix.postScale(300/bmp.getWidth(), 200/bmp.getHeight());
			matrix.postScale(0.2f, 0.2f);
			matrix.postRotate(90);
			bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
			
			
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bmp.compress(CompressFormat.JPEG, 20, fos);
			
			//fos.write(baos.toByteArray());
			fos.close();
			
			Toast.makeText(mContext, "�摜��ۑ����܂���", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			
			Toast.makeText(mContext, "�t�@�C���������݂Ɏ��s���܂���", Toast.LENGTH_LONG).show();
			mCamera.startPreview();
			return;
		}
		
		
		if(!isSending)
		{
			ImagePostThread ipt = new ImagePostThread(file);
			ipt.start();
		}
		/*
		try {
			ipt.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		//progressDialog.dismiss();
		
		
		
		mCamera.startPreview();
	}
	
	public void takePicture() {
		if (mCamera != null) {
			mCamera.takePicture(null, null, this);
		}
	}
	
	
	
	class ImagePostThread extends Thread {
		
		File mFile;
		ProgressDialog progressDialog = new ProgressDialog(mContext);
		
		public ImagePostThread(File file)
		{
			super();
			
			mFile = file;
		}
		
		@Override
		public void run() {
			sendImageToServer(mFile);
		}
		
		private void sendImageToServer(File imgPath) {
			
			String url = "http://192.168.0.4/PHP/uploadtest.php";
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			UsernamePasswordCredentials credential = new UsernamePasswordCredentials("android", "android");
			AuthScope authScope = new AuthScope(httpPost.getURI().getHost(), httpPost.getURI().getPort());
			((AbstractHttpClient)httpClient).getCredentialsProvider().setCredentials(authScope, credential);
			MultipartEntity multipartEntity = new MultipartEntity();
			try {
				multipartEntity.addPart("image", new FileBody(imgPath.getAbsoluteFile()));
				
				httpPost.setEntity(multipartEntity);
				
				long startTime = System.currentTimeMillis();
				
				isSending = true;
				uiHandler.post(new Runnable() {
					
					//progressDialog.setCancelable(true);
					@Override
					public void run() {
						
						progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						progressDialog.setMessage("���ް�ɉ摜��]�����Ă��܂�");
						progressDialog.show();
					}
					
				});
				
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				mTime = (System.currentTimeMillis() - startTime) /1000;
				//Toast.makeText(mContext, "�摜�]�����܂���", Toast.LENGTH_LONG).show();
				
				
				
			} catch (Exception e) {
				Toast.makeText(mContext, "�摜�]���Ɏ��s���܂���", Toast.LENGTH_LONG).show();
			} finally {
				isSending = false;
				uiHandler.post(new Runnable() {
					
					//progressDialog.setCancelable(true);
					@Override
					public void run() {
						
						progressDialog.dismiss();
					}
					
				});
			}
		}
		
		
		
	}
	

}
