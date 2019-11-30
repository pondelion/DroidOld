package com.example.elementscalculator;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class ElementsCalculator extends Activity {

	int numElems=0;
	LinearLayout layout3;
	ScrollView scrollView;
	final double[] weights = {
			1.007940,
			4.002602,
			6.941000,
			9.012182,
			10.811000,
			12.010700,
			14.006700,
			15.999400,
			18.998403,
			20.179700,
			22.989769,
			24.305000,
			26.981539,
			28.085500,
			30.973762,
			32.065000,
			35.453000,
			39.948000,
			39.098300,
			40.078000,
			44.955912,
			47.867000,
			50.941500,
			51.996100,
			54.938045,
			55.845000,
			58.933195,
			58.693400,
			63.546000,
			65.380000,
			69.723000,
			72.640000,
			74.921600,
			78.960000,
			79.904000,
			83.798000,
			85.467800,
			87.620000,
			88.905850,
			91.224000,
			92.906380,
			95.960000,
			99.000000,
			101.070000,
			102.905500,
			106.420000,
			107.868200,
			112.411000,
			114.818000,
			118.710000,
			121.760000,
			127.600000,
			126.904470,
			131.292000,
			132.905452,
			137.327000,
			138.905470,
			140.116000,
			144.242000,
			145.000000,
			150.360000,
			151.964000,
			157.250000,
			158.925350,
			158.925350,
			162.500000,
			164.930320,
			167.259000,
			168.934210,
			173.054000,
			174.966800,
			178.490000,
			180.947880,
			183.840000,
			186.207000,
			190.230000,
			192.217000,
			195.084000,
			196.966569,
			200.590000,
			204.383300,
			207.200000,
			208.980400,
			210.000000,
			210.000000,
			222.000000,
			223.000000,
			226.000000,
			227.000000,
			232.038060,
			231.035880,
			238.028910,
			237.000000,
			239.000000,
			243.000000,
			247.000000,
			247.000000,
			252.000000,
			252.000000,
			257.000000,
			258.000000,
			259.000000,
			262.000000,
			267.000000,
			268.000000,
			271.000000,
			272.000000,
			277.000000,
			276.000000
	};
			
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_elements_calculator);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundColor(Color.rgb(255,255,255));
		setContentView(layout);
		
		LinearLayout layout2 = new LinearLayout(this);
		layout2.setOrientation(LinearLayout.HORIZONTAL);
		layout2.setBackgroundColor(Color.rgb(50,50,50));
		
		TextView text1 = new TextView(this);
		text1.setText("�g�p���錳�f��:");
		text1.setTextSize(16);
		text1.setTextColor(Color.rgb(240,240,240));
		layout2.addView(text1);
		
		EditText editText1 = new EditText(this);
		editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
		editText1.addTextChangedListener(
				new MyTextWatcher());
		layout2.addView(editText1);
		
		layout.addView(layout2);
		
		scrollView = new ScrollView(this);
		
		layout3 = new LinearLayout(this);
		layout3.setOrientation(LinearLayout.VERTICAL);
		
		scrollView.addView(layout3);
		
		layout.addView(scrollView);
	}
	
	class MyTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			try
			{
				numElems = Integer.parseInt(arg0.toString());
				if(numElems > 10)
				{
					numElems = 10;
				}
				setField(numElems);
			}
			catch(Exception e)
			{
			}
		}

		
		
	}
	
	private void setField(int numElem) {
		// TODO Auto-generated method stub
		layout3.removeAllViews();
		
		for(int i=0; i<numElem; i++)
		{
			TextView textView = new TextView(this);
			textView.setText("************"+(i+1)+"�Ԗڂ̌��f**************");
			textView.setTextColor(Color.rgb(255, 0, 0));
			textView.setGravity(Gravity.CENTER_HORIZONTAL);
			layout3.addView(textView);
			
			LinearLayout layout4 = new LinearLayout(this);
			layout4.setOrientation(LinearLayout.HORIZONTAL);
			
			TextView textView2 = new TextView(this);
			textView2.setText("���f�I���F");
			layout4.addView(textView2);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					this, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);
			String[] str = {
					"1.H(���f)",
					"2.He(�w���E��)",
					"3.Li(���`�E��)",
					"4.Be(�x�����E��)",
					"5.B(�z�E�f)",
					"6.C(�Y�f)",
					"7.N(���f)",
					"8.O(�_�f)",
					"9.F(�t�b�f)",
					"10.Ne(�l�I��)",
					"11.Na(�i�g���E��)",
					"12.Mg(�}�O�l�V�E��)",
					"13.Al(�A���~�j�E��)",
					"14.Si(�P�C�f)",
					"15.P(����(��))",
					"16.S(����(�Ε�))",
					"17.Cl(���f)",
					"18.Ar(�A���S��)",
					"19.K(�J���E��)",
					"20.Ca(�J���V�E��)",
					"21.Sc(�X�J���W�E��)",
					"22.Ti(�`�^��)",
					"23.V(�o�i�W�E��)",
					"24.Cr(�N����)",
					"25.Mn(�}���K��)",
					"26.Fe(�S)",
					"27.Co(�R�o���g)",
					"28.Ni(�j�b�P��)",
					"29.Cu(��)",
					"30.Zn(����)",
					"31.Ga(�K���E��)",
					"32.Ge(�Q���}�j�E��)",
					"33.As(�q�f(�Z��))",
					"34.Se(�Z����)",
					"35.Br(�L�f)",
					"36.Kr(�N���v�g��)",
					"37.Rb(���r�W�E��)",
					"38.Sr(�X�g�����`�E��)",
					"39.Y(�C�b�g���E��)",
					"40.Zr(�W���R�j�E��)",
					"41.Nb(�j�I�u)",
					"42.Mo(�����u�f��)",
					"43.Tc(�e�N�l�`�E��)",
					"44.Ru(���e�j�E��)",
					"45.Rh(���W�E��)",
					"46.Pb(�p���W�E��)",
					"47.Ag(��)",
					"48.Cd(�J�h�~�E��)",
					"49.In(�C���W�E��)",
					"50.Sn(�X�Y)",
					"51.Sb(�A���`����)",
					"52.Te(�e����)",
					"53.I(���E�f)",
					"54.Xe(�L�m�Z��)",
					"55.Cs(�Z�V�E��)",
					"56.Ba(�o���E��)",
					"57.La(�����^��)",
					"58.Ce(�Z���E��)",
					"59.Pr(�v���Z�I�W��)",
					"60.Nd(�l�I�W��)",
					"61.Pm(�v�����`�E��)",
					"62.Sm(�T�}���E��)",
					"63.Eu(���E���s�E��)",
					"64.Gd(�K�h���j�E��)",
					"65.Tb(�e���r�E��)",
					"66.Dy(�W�X�v���V�E��)",
					"67.Ho(�z���~�E��)",
					"68.Er(�G���r�E��)",
					"69.Tm(�c���E��)",
					"70.Yb(�C�b�e���r�E��)",
					"71.Lu(���e�`�E��)",
					"72.Hf(�n�t�j�E��)",
					"73.Ta(�^���^��)",
					"74.W(�^���O�X�e��)",
					"75.Re(���j�E��)",
					"76.Os(�I�X�~�E��)",
					"77.Ir(�C���W�E��)",
					"78.Pt(����)",
					"79.Au(��)",
					"80.Hg(����)",
					"81.Tl(�^���E��)",
					"82.Pb(��)",
					"83.Bi(�r�X�}�X)",
					"84.Po(�|���j�E��)",
					"85.At(�A�X�^�`��)",
					"86.Rn(���h��)",
					"87.Fr(�t�����V�E��)",
					"88.Ra(���W�E��)",
					"89.Ac(�A�N�`�j�E��)",
					"90.Th(�g���E��)",
					"91.Pa(�v���g�A�N�`�j�E��)",
					"92.U(�E����)",
					"93.Np(�l�v�c�j�E��)",
					"94.Pu(�v���g�j�E��)",
					"95.Am(�A�����V�E��)",
					"96.Cm(�L�����E��)",
					"97.Bk(�o�[�N���E��)",
					"98.Cf(�J���z���j�E��)",
					"99.Es(�A�C���X�^�C�j�E��)",
					"100.Fm(�t�F���j�E��)",
					"101.Md(�����f���r�E��)",
					"102.No(�m�[�x���E��)",
					"103.Lr(���[�����V�E��)",
					"104.Rf(���U�z�[�W�E��)",
					"105.Db(�h�u�j�E��)",
					"106.Sg(�V�[�{�[�M�E��)",
					"107.Bh(�{�[���E��)",
					"108.Hs(�n�b�V�E��)",
					"109.Mt(�}�C�g�l���E��)"
			};
			for(int i2=0; i2<str.length; i2++)
			{
				adapter.add(str[i2]);
			}
			Spinner spinner = new Spinner(this);
			
			spinner.setAdapter(adapter);
			
			spinner.setSelection(0);
			spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
			spinner.setId(2000+i);
			layout4.addView(spinner);
			
			layout3.addView(layout4);
			
			
			LinearLayout layout5 = new LinearLayout(this);
			layout5.setOrientation(LinearLayout.HORIZONTAL);
			
			TextView textView3 = new TextView(this);
			textView3.setText("������   �F");
			layout5.addView(textView3);
			
			EditText editText1 = new EditText(this);
			editText1.setId(1000+i);
			editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			editText1.addTextChangedListener(
					new MyTextWatcher3(editText1));
			layout5.addView(editText1);
			
			layout3.addView(layout5);
			
			
			LinearLayout layout6 = new LinearLayout(this);
			layout6.setOrientation(LinearLayout.HORIZONTAL);
			
			TextView textView4 = new TextView(this);
			textView4.setText("���ʔ��ʒl(g)�F");
			layout6.addView(textView4);
			
			EditText editText2 = new EditText(this);
			editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
			editText2.setId(i);
			//editText2.setFocusable(false);
			editText2.addTextChangedListener(
					new MyTextWatcher2(editText2));
			
			layout6.addView(editText2);
			
			layout3.addView(layout6);
			
			TextView textView5 = new TextView(this);
			textView5.setText("���ʌv�Z�l(g) : ");
			textView5.setTextColor(Color.BLUE);
			textView5.setTextSize(16);
			textView5.setId(3000 + i);
			
			layout3.addView(textView5);
			
			
		}
		
		TextView textView6 = new TextView(this);
		textView6.setText("*************************");
		textView6.setTextColor(Color.RED);
		textView6.setTextSize(16);
		textView6.setGravity(Gravity.CENTER_HORIZONTAL);
		
		layout3.addView(textView6);
		
		TextView textView7 = new TextView(this);
		textView7.setText("���v(g) : ");
		textView7.setTextColor(Color.rgb(0, 255, 0));
		textView7.setTextSize(18);
		textView7.setId(4000);
		
		layout3.addView(textView7);
	}
	
	class MyTextWatcher2 implements TextWatcher {

		private View view;
		
		private MyTextWatcher2(View view)
		{
			this.view = view;
		}
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			try
			{
				double mass;
				mass = Double.parseDouble(arg0.toString());
				if(isMolerRatiosSet())
				{
					calcWeights(mass, view.getId());
				}
			}
				
			catch(Exception e)
			{
				
			}
		}

	}
	
	class MyTextWatcher3 implements TextWatcher {

		private View view;
		
		private MyTextWatcher3(View view)
		{
			this.view = view;
		}
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			TextView textView;
			if(isMolerRatiosSet())
			{
				for(int i=0; i<numElems; i++)
				{
					textView = (TextView)findViewById(1000+i);
					textView.setFocusable(true);
				}
			}
			else
			{
				for(int i=0; i<numElems; i++)
				{
					textView = (TextView)findViewById(1000+i);
					textView.setFocusable(false);
				}
			}
		}

	}
	
	private void calcWeights(double mass, int id)
	{
		EditText molEditText,massEditText;
		TextView calcMassTextView, totalMassTextView;
		Spinner elemSpinner;
		molEditText = (EditText)findViewById(1000+id);
		elemSpinner = (Spinner)findViewById(2000+id);
		double totalMass = 0.0f;
		double molid = Double.parseDouble(molEditText.getText().toString());
		double atomicWeightOfId = weights[(int)elemSpinner.getSelectedItemId()];
		Log.v("massOfId", String.valueOf(mass));
		Log.v("molid", molEditText.getText().toString());
		Log.v("atomicWeightOfId", String.valueOf(atomicWeightOfId));
		
		totalMassTextView = (TextView)findViewById(4000);
		
		for(int i=0; i<numElems; i++)
		{
			calcMassTextView = (TextView)findViewById(3000+i);
		
			if(i==id)
			{
				calcMassTextView.setText("���ʊ�l(g) : "+String.valueOf(mass));
				totalMass += mass;
				continue;
			}
			
			molEditText = (EditText)findViewById(1000+i);
			massEditText = (EditText)findViewById(i);
			elemSpinner = (Spinner)findViewById(2000+i);
			
			double moli = Double.parseDouble(molEditText.getText().toString());
			//massEditText.setText(String.valueOf((moli*mass*atomicWeightOfId)/(molid*weights[(int)elemSpinner.getSelectedItemId()])));
			String log;
			log = "i="+ i + ":"+ String.valueOf(moli);
			Log.v("moli",log );
			log = "i="+ i + ":"+ String.valueOf(weights[(int)elemSpinner.getSelectedItemId()]);
			Log.v("weights[i]", log);
			log = "i="+ i + ":"+ String.valueOf((moli*mass*weights[(int)elemSpinner.getSelectedItemId()])/(molid*atomicWeightOfId));
			Log.v("x", log);
			calcMassTextView.setText("���ʌv�Z�l(g) : " + String.valueOf((moli*mass*weights[(int)elemSpinner.getSelectedItemId()])/(molid*atomicWeightOfId)));
			totalMass += (moli*mass*weights[(int)elemSpinner.getSelectedItemId()])/(molid*atomicWeightOfId);
		}
		totalMassTextView.setText("���v����(g)�@�F " + String.valueOf(totalMass));
	}

	private boolean isMolerRatiosSet()
	{
		boolean b = true;
		/*
		TextView textView;
		for(int i=0; i<numElems; i++)
		{
			textView = (TextView)findViewById(i+1000);
			if(textView.getText() == "")
			{
				b = false;
			}
		}
		*/
		return b;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.elements_calculator, menu);
		return true;
	}

}
