package com.example.animationtest1;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Matrix;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;

public class AnimationTest1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_test1);
		ImageView imageView =(ImageView)findViewById(R.id.imageView1);
		
		CustomAnimation customAnimation = new CustomAnimation(imageView);
		
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate1);
		
		imageView.startAnimation(customAnimation);  //カスタムアニメーションから
		//imageView.startAnimation(anim); //xmlファイルから
	}


}
