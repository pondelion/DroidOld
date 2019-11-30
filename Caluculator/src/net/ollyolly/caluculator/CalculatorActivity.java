package net.ollyolly.caluculator;



import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import net.akihiroi.MyStringParser.MyStringParser;



public class CalculatorActivity extends Activity implements OnClickListener {

	private ImageButton btnNumbers[] = new ImageButton[10];
	private ImageButton btnPlus, btnMinus, btnMultiple, btnDivide, btnEqual;
	private ImageButton btnErase;
	private ImageButton btnTax;
	private ImageButton btnPower, btnSqrt;
	private ImageButton btnDecimalP;
	private ImageButton btnLKakko, btnRKakko;
	private ImageButton btnBack;
	private TextView txtExpression;
	private TextView txtResults;
	private MyStringParser mExpParser;
	private Animation rotationAnim;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_calculator);
		
		btnNumbers[0] 	= (ImageButton)findViewById(R.id.numberZeroBtn);
		btnNumbers[1]	= (ImageButton)findViewById(R.id.numberOneBtn);
		btnNumbers[2]	= (ImageButton)findViewById(R.id.numberTwoBtn);
		btnNumbers[3]	= (ImageButton)findViewById(R.id.numberThreeBtn);
		btnNumbers[4]	= (ImageButton)findViewById(R.id.numberFourBtn);
		btnNumbers[5]	= (ImageButton)findViewById(R.id.numberFiveBtn);
		btnNumbers[6]	= (ImageButton)findViewById(R.id.numberSixBtn);
		btnNumbers[7]	= (ImageButton)findViewById(R.id.numberSevenBtn);
		btnNumbers[8]	= (ImageButton)findViewById(R.id.numberEightBtn);
		btnNumbers[9]	= (ImageButton)findViewById(R.id.numberNineBtn);
		
		btnPlus		= (ImageButton)findViewById(R.id.plusBtn);
		btnMinus	= (ImageButton)findViewById(R.id.minusBtn);
		btnMultiple	= (ImageButton)findViewById(R.id.multipleBtn);
		btnDivide	= (ImageButton)findViewById(R.id.divideBtn);
		btnEqual	= (ImageButton)findViewById(R.id.equalBtn);
		btnErase	= (ImageButton)findViewById(R.id.eraseBtn);
		btnTax		= (ImageButton)findViewById(R.id.taxBtn);
		btnPower	= (ImageButton)findViewById(R.id.powerBtn);
		btnSqrt		= (ImageButton)findViewById(R.id.sqrtBtn);
		btnDecimalP	= (ImageButton)findViewById(R.id.decimalBtn);
		btnLKakko	= (ImageButton)findViewById(R.id.lkakkoBtn);
		btnRKakko	= (ImageButton)findViewById(R.id.rkakkoBtn);
		btnBack		= (ImageButton)findViewById(R.id.backBtn);
		
		for(int i = 0; i < 10; i++) {
			btnNumbers[i].setOnClickListener(this);
		}
		
		btnPlus.setOnClickListener(this);
		btnMinus.setOnClickListener(this);
		btnMultiple.setOnClickListener(this);
		btnDivide.setOnClickListener(this);
		btnEqual.setOnClickListener(this);
		btnErase.setOnClickListener(this);
		btnTax.setOnClickListener(this);
		btnPower.setOnClickListener(this);
		btnSqrt.setOnClickListener(this);
		btnDecimalP.setOnClickListener(this);
		btnLKakko.setOnClickListener(this);
		btnRKakko.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		
		txtExpression	= (TextView)findViewById(R.id.textView1);
		txtResults		= (TextView)findViewById(R.id.textView2);
		
		
		rotationAnim = AnimationUtils.loadAnimation(this, R.anim.rotate1);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 10; i++) {
			if (v == btnNumbers[i]) {

				txtExpression.append(String.valueOf(i));
				
				return;
			}
		}
		
		if (v == btnPlus) {
			txtExpression.append("+");
			btnPlus.startAnimation(rotationAnim);
		} else if (v == btnMinus) {
			txtExpression.append("-");
			btnMinus.startAnimation(rotationAnim);
		} else if (v == btnMultiple) {
			txtExpression.append("*");
			btnMultiple.startAnimation(rotationAnim);
		} else if (v == btnDivide) {
			txtExpression.append("/");
			btnDivide.startAnimation(rotationAnim);
		} else if (v == btnEqual) {
			txtExpression.append("=");
			try {
				
				mExpParser = new MyStringParser(txtExpression.getText().toString());
				txtExpression.append(String.valueOf(mExpParser.calculate()));
				txtResults.setText(txtExpression.getText().toString() + "\n" + txtResults.getText().toString());
				txtExpression.setText(String.valueOf(mExpParser.calculate()));
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
			btnEqual.startAnimation(rotationAnim);
		} else if (v == btnErase) {
			txtExpression.setText("");
			btnErase.startAnimation(rotationAnim);
		} else if (v == btnBack) {
			
			txtExpression.setText(txtExpression.getText().toString().substring(0, (txtExpression.getText().toString().length()-1 > 0) ? txtExpression.getText().toString().length()-1 : 0));
		} else if (v == btnTax) {
			
			try {
				txtResults.setText(txtExpression.getText().toString() + "*1.08 =" + Double.parseDouble(txtExpression.getText().toString())*1.08 + "\n" + txtResults.getText().toString());
				txtExpression.setText(String.valueOf(Double.parseDouble(txtExpression.getText().toString())*1.08));
			} catch(Exception e) {
				
			}
			btnTax.startAnimation(rotationAnim);
		} else if (v == btnPower) {
			btnPower.startAnimation(rotationAnim);
			txtExpression.append("^");
		} else if (v == btnSqrt) {
			btnSqrt.startAnimation(rotationAnim);
			txtExpression.append("sqrt(");
		} else if (v == btnDecimalP) {
			txtExpression.append(".");
		} else if (v == btnLKakko) {
			btnLKakko.startAnimation(rotationAnim);
			txtExpression.append("(");
		} else if (v == btnRKakko) {
			btnRKakko.startAnimation(rotationAnim);
			txtExpression.append(")");
		}
		
	}

}
