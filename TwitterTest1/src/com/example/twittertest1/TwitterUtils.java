package com.example.twittertest1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Spannable.Factory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterUtils {
	private static final String TOKEN = "ADFDEHJWFOIREHFIOUWJREHFOUIFHRE";
	private static final String TOKEN_SECRET = "akshdjiuHIUdHIUHAIDAEIDEHUFIKEUH";
	private static final String PREF_NAME = "twitter_access_token";
	
	public static Twitter getTwitterInstance(Context context) {
		String consumerKey = context.getString(R.string.twitter_consumer_key);
		String consumerSecret = context.getString(R.string.twitter_consumer_secret);
		
		TwitterFactory factroy = new TwitterFactory();
		Twitter twitter = (Twitter) Factory.getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		
		if (hasAccessToken(context)) {
			twitter.setOAuthAccessToken(loadAccessToken(context));
		}
		return twitter;
	}
	
	public static void storeAccessToken(Context context, AccessToken accessToken) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(TOKEN, accessToken.getToken());
		editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());
		editor.commit();
	}
	
	public static AccessToken loadAccessToken(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		String token = preferences.getString(TOKEN, null);
		String tokenSecret = preferences.getString(TOKEN_SECRET, null);
		if (token != null && tokenSecret != null) {
			return new AccessToken(token, tokenSecret);
		} else {
			return null;
		}
	}
	
	public static boolean hasAccessToken(Context context) {
		return loadAccessToken(context) != null;
	}
	
}
