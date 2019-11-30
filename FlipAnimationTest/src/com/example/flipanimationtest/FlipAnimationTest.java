package com.example.flipanimationtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FlipAnimationTest extends Activity implements OnClickListener {

	private RelativeLayout relativeLayout;
	private View frontView;
	private View backView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flip_animation_test);
		
		relativeLayout = (RelativeLayout) findViewById(R.id.layout);
		frontView = (ImageView)findViewById(R.id.imageViewFront);
    	backView = (ImageView)findViewById(R.id.imageViewBack);
    	
    	frontView.setOnClickListener(this);
    	backView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getApplicationContext(), "testetes", Toast.LENGTH_LONG).show();
		switchFrontBackView(false);  // ï\Ç©ÇÁó†Ç…Ç–Ç¡Ç≠ÇËï‘Ç∑èÍçá
//		switchFrontBackView(true);   // ó†Ç©ÇÁï\Ç…Ç–Ç¡Ç≠ÇËï‘Ç∑èÍçá
		
	}
	
	private void switchFrontBackView(boolean isReverse) {
		

    		if(frontView == null || backView == null) return;
    	
    		FrontBackSwitchAnimator animator = 
			new FrontBackSwitchAnimator(frontView, backView, backView.getWidth() /2 , backView.getHeight() / 2);
    		if(isReverse) animator.reverse();
    		relativeLayout.startAnimation(animator);
    		
	}

}
