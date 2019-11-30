package com.example.twittertest2;





import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Twitter;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterListener;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TwitterTest2 extends Activity implements OnClickListener {

	private final String API_KEY = "ALKJDKHJEKHFEKDFUJHEWFH";
	private final String API_SECRET = "ASKDEJKFUIEKFUJFWHHHEIEUHSDHUedHEFCEIWFHE";
	private final String ACCESS_TOKEN = "ADIJSFOKWIEJFEIUFHEOKIURJFHRIUFGHEWIUFJHEIUWF";
	private final String ACCESS_TOKEN_SECRET = "KAJHFKDEUJHFKIWUFEHKIEJWFKEFHJEKUWFHE";
	private AsyncTwitter mTwitter;
	private RequestToken mReqToken;

	private final TwitterAdapter mListener = new TwitterAdapter() {
		@Override
		public void gotOAuthRequestToken(RequestToken token) {
			mReqToken = token;
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mReqToken.getAuthorizationURL()));
			startActivity(intent);
		}

		@Override
		public void gotOAuthAccessToken(AccessToken token) {
			//token.getToken()��token.getTokenSecret()��ۑ�����
		}

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter_test2);
		
		mTwitter = new AsyncTwitterFactory().getInstance();
		mTwitter.addListener(mListener);
		mTwitter.setOAuthConsumer(API_KEY, API_SECRET);
		mTwitter.getOAuthRequestTokenAsync("twittercallback://callback");
		
		//AccessToken accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		//mTwitter.setOAuthAccessToken(accessToken);
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		//�u���E�U����̃R�[���o�b�N�ŌĂ΂��
		final Uri uri = intent.getData();		
		final String verifier = uri.getQueryParameter("oauth_verifier");
		if (verifier != null) {
			mTwitter.getOAuthAccessTokenAsync(mReqToken, verifier);
		}
		
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mTwitter.updateStatus("test");
	}

}
