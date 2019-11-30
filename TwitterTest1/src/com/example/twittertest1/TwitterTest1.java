package com.example.twittertest1;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TwitterTest1 extends Activity implements OnClickListener {

	private Button btnTwitter;
	private String mCallbackURL;
	private Twitter mTwitter;
	private RequestToken mRequestToken;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter_test1);
		
		btnTwitter = (Button)findViewById(R.id.button1);
		btnTwitter.setOnClickListener(this);
		
		mCallbackURL = "gabu://twitter";
		mTwitter = TwitterUtils.getTwitterInstance(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(!TwitterUtils.hasAccessToken(this)) {
			
		
		startAuthorize();
		}
	}

	private void startAuthorize() {
		
			AsyncTask<Void, Void, String> task = new AsyncTask<Void,Void, String>() {
				@Override
				protected String doInBackground(Void... params) {
					try {
						mRequestToken = mTwitter.getOAuthRequestToken(mCallbackURL);
						return mRequestToken.getAuthorizationURL();
					} catch (TwitterException e) {
						e.printStackTrace();
					}
					return null;
				}
				
				@Override
				protected void onPostExecute(String url) {
					if (url != null) {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
						startActivity(intent);
					} else {
						
					}
				}
			};
			task.execute();
	}
	
	
	@Override
	public void onNewIntent(Intent intent) {
		if (intent == null || intent.getData() == null || !intent.getData().toString().startsWith(mCallbackURL)) {
			return;
		}
		
		String verifier = intent.getData().getQueryParameter("oauth_parameter");
		
		AsyncTask<String, Void, AccessToken> task = new AsyncTask<String, Void, AccessToken> () {
			@Override
			protected AccessToken doInBackground(String... params) {
				try {
					return mTwitter.getOAuthAccessToken(mRequestToken, params[0]);
					
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(AccessToken accessToken) {
				if (accessToken != null) {
					
					showToast("認証成功！");
					successOAuth(accessToken);
				} else {
					showToast("認証失敗");
				}
			}
		};
		
		task.execute(verifier);
	}
	
	
	private void successOAuth(AccessToken accessToken) {
		TwitterUtils.storeAccessToken(this,  accessToken);
		//Intent intent = new Intent(this, MainActivity.class);
		
	}
	
	private void showToast(String text) {
		Toast.makeText(this,  text, Toast.LENGTH_LONG).show();
	}

}
