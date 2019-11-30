/**
 * 文字列を数式に読み替えて計算するためのクラス
 *
 * @version 1.00 2011/7/22
 * @author aki ( URL http://akihiro-i.net/~akihiro-i/ )
 *
 * Copyright aki All Rights Reserved.
 */

package net.akihiroi.MyStringParser;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * 文字列を数式に読み替えて計算するためのクラス.
 *
 * <pre>
 * Usage:
 *
 *	MyStringParser str_parser = new MyStringParser("1+2=");
 *	if( str_parser.check_str() ){
 *		double calc_result = str_parser.calculate();
 *		if( str_parser.error_flag != true ){
 *			System.out.println("result = " + String.valueOf(calc_result));
 *		}else{
 *			System.out.println(str_parser.error_message);
 *			if(str_parser.error_index != -1)
 *				System.out.println( str_parser.error_index );
 *		}
 *	}else{
 *		System.out.println(str_parser.error_message);
 *		if(str_parser.error_index != -1)
 *			System.out.println( str_parser.error_index );
 *	}
 * </pre>
 * @version 1.00 2011/7/22
 * @author aki ( URL http://akihiro-i.net/~akihiro-i/ )
 *
 * Copyright aki All Rights Reserved.

 */
public class MyStringParser {
	/** 各ステップごとの計算結果：最終的に式全体の解となる */
	private double x;
	/** 現在の文字列の読み取り位置 */
	private int index;
	/** 解析・計算する文字列 */
	private String str;
	/** エラーメッセージ */
	public String error_message;
	/** エラーが生じた地点 */
	public int error_index;
	/** 解析・計算の際にエラーが生じたか：生じていればtrue */
	public boolean error_flag;

	/** リソースバンドル */
	ResourceBundle resource = ResourceBundle.getBundle("net.akihiroi.MyStringParser.Application", Locale.JAPANESE);

	/**
	 * コンストラクタ<br>
	 * index、str、error_flagを各変数を初期化
	 * @param s 解析・計算したい文字列
	 */
	public MyStringParser( String s)
	{
//		String menuFile = resource.getString("menu.file");
//		System.out.println("menu.file" + menuFile);
		index = 0;
		str = s;
		error_flag = false;
		error_index = -1;
	}

	/**
	 * 関数を判別し、その結果を返す
	 * @return 判別した関数の名前
	 * （sin、asin、cos、acos、tan、atan、log、ln、sqrt）
	 */
	private String check_func( )
	{
		String sub_str;
		if(index == 0){
			return " ";
		} else if(index < 5){
	    	sub_str = str.substring(0, index-1); //括弧の前方の4文字を取得
	//    	result.setText( result.getText().toString() + " " + sub_str + "index<5 ");
		} else{
			sub_str = str.substring(index-5, index-1); //括弧の前方の4文字を取得
		}

	//	result.setText(result.getText().toString() +"sub_str=" + sub_str);
		if( sub_str.indexOf("sin") != -1 )
		{
			if( sub_str.indexOf("asin") != -1 )
	    		return "asin";
			else
				return "sin";
		}
		else if( sub_str.indexOf("cos") != -1 )
		{
			if( sub_str.indexOf("acos") != -1 )
	    		return "acos";
			else
				return "cos";
		}
		else if( sub_str.indexOf("tan") != -1 )
		{
			if( sub_str.indexOf("atan") != -1 )
	    		return "atan";
			else
				return "tan";
		}
		else if( sub_str.indexOf("log") != -1 )
			return "log";
		else if( sub_str.indexOf("ln") != -1 )
			return "ln";
		else if( sub_str.indexOf("sqrt") != -1 )
			return "sqrt";
		else
			return " ";

	}


	/**
	 * index番目の文字をcharとして返し、indexを+1する
	 * @return index番目の文字（char）
	 */
	private char getchar( )
	{
		char c = str.charAt(index);
		index++;
		return c;
	}

	/**
	 * indexを-1する
	 */
	private void ungetchar(  )
	{
		index--;
	}


	/**
	 * 数式の因子を取得
	 */
	private void Factor( )
	{
	  double num = 0.0;
	  int flag = 1, c = 0;
	  boolean dot = false;
	  int under_dot = 0;

	  c = getchar( );

	  if( c == '-' || c == '+' )
	  {
	      c = getchar( );
	      flag = (c == '+' ) ? 1 : -1;
	  }

	  if( Character.isDigit(c) || c == '.')
	  {
		  long n = 0;
		  while( Character.isDigit(c) || c == '.' )
		  {
			  if(dot == true)
				  under_dot++;
			  if(c == '.')
				  dot = true;
			  if(Character.isDigit(c))
				  n = n * 10 + ( c - '0' );
			  if(index == str.length())
				  break;
			  c = getchar( );
		  }
		  num = (double)(n * flag)*Math.pow(10,-under_dot);
	  }
	  else if( c == 'P')
	  {
		  c = getchar( );
	  	  if( c == 'I')
	  		  num = Math.PI;
	  	  c = getchar( );
	  }
	  else if( c == 'e')
	  {
	      num = Math.E;
	  	  c = getchar( );
	  }
	  else
	  {
		  if( Character.isLetter(c) && c != 'E' )
		  {
			  while( Character.isLetter(c) && c != 'E'  )
			  {
	        	  c = getchar( );
			  }
		  }
		  if( c == '(' )
		  {
			  String func_name = check_func();
			  num = calc();
			  if( func_name.equals("sin") )
				  num = Math.sin(num);
			  else if( func_name.equals("cos") )
				  num = Math.cos(num);
			  else if( func_name.equals("tan") )
				  num = Math.tan(num);
			  else if( func_name.equals("asin") )
			  {
				  if(num < -1.0)
				  {
					  error_flag = true;
					  error_message = resource.getString("error.asin.lower");
				  }
				  else if(num > 1.0)
				  {
					  error_flag = true;
					  error_message = resource.getString("error.asin.upper");;
				  }else
					  num = Math.asin(num);
			  }
			  else if( func_name.equals("acos") )
			  {
				  if(num < -1.0)
				  {
					  error_flag = true;
					  error_message = resource.getString("error.acos.lower");
				  }
				  else if(num > 1.0)
				  {
					  error_flag = true;
					  error_message = resource.getString("error.acos.upper");
				  }
				  else
					  num = Math.acos(num);
			  }
			  else if( func_name.equals("atan") )
			  {
				  if(num < -Math.PI/2)
				  {
					  error_flag = true;
					  error_message = resource.getString("error.atan.lower");
				  }
				  else if(num > Math.PI/2)
				  {
					  error_flag = true;
					  error_message = resource.getString("error.atan.upper");
				  }
				  else
					  num = Math.atan(num);
			  }
			  else if( func_name.equals("log") )
			  {
				  if(num <= 0.0)
				  {
					  error_flag = true;
					  error_message = resource.getString("error.log.limit");
				  }
				  else
					  num = Math.log10(num);
			  }
			  else if( func_name.equals("ln") )
			  {
				  if(num <= 0.0)
				  {
					  error_flag = true;
					  error_message = resource.getString("error.ln.limit");
				  }
				  else
    				  num = Math.log(num);
			  }
			  else if( func_name.equals("sqrt") )
			  {
				  if(num < 0.0)
				  {
					  error_flag = true;
					  error_message = resource.getString("error.root.limit");
				  }
				  else
					  num = Math.sqrt(num);
			  }
			  else{}

			  if( getchar( ) != ')' ){}
			  c = 0x0100;
		  }
	  }
	  if( c != 0x0100 )
		  ungetchar( );
	  x = num;
	}

	/**
	 * 乗除算に対する処理
	 */
	private void MulDiv(  )
	{
		double num = 0.0;
		char c = 0;

		Factor(  );
		num = x;

		c = getchar( );

		while( c == '*' || c == '/' || c == '^' || c == 'E' )
		{
			switch( c )
			{
			case '*':
				Factor( );
				num = num * x;
				break;

			case '/':
				Factor( );
				num = num / x;
				if(x == 0)
				{
					error_flag = true;
					error_message = resource.getString("error.div.0");
				}
				break;

			case '^':
				Factor( );
				num = Math.pow(num, x);
				break;

			case 'E':
				Factor( );
				num = num * Math.pow(10, x);
				break;
			}
			c = getchar( );
		}
		ungetchar( );
		x = num;
	}

	/**
	 * 加減算に対する処理
	 */
	private void AddSub( )
	{
		double num = 0.0;
		char c = 0;

		MulDiv(  );
		num = x;

		c = getchar( );

		while( c == '+' || c == '-' )
		{
			switch( c )
			{
			case '+':
				MulDiv( );
				num = num + x;
				break;

			case '-':
				MulDiv( );
				num = num - x;
				break;
			}
			c = getchar( );
		}
		ungetchar( );
		x = num;
	}

	/**
	 * 計算のメインルーチン<br>
	 * これを呼ぶことで、再帰的に文字列を数式に読み替えて計算する
	 */
	private double calc()
	{
		x = 0;
		AddSub( );
		return x;
	}

	/**
	 * calc()呼び出し用関数<br>
	 * indexを0で初期化し、calc()を呼ぶ
	 */
	public double calculate()
	{
		index =0;
		return calc();
	}


	/**
 	 * 文字列が数式として成り立つかのチェック
	 * 内部で check_unknown()、check_parentheses()、check_num()、check_operator()を呼ぶ
	 * @return 数式として成り立てばtrue、成り立たなければfalseを返す
	 */
	public boolean check_str()
	{
		index =0;
		if( check_unknown() && check_parentheses() && check_num() && check_operator())
			return true;
		else
			return false;
	}

	/**
	 * 括弧に関するの表記チェック（括弧の数、括弧の前後の文字を確認）
	 * @return エラーがなければtrue、エラーがあればfalseを返す
	 */
	private boolean check_parentheses()
	{
		int idx = 0;
		int cnt[] = new int[2];
		while( idx !=-1 )
		{
			if(cnt[0] ==0)
    			idx = str.indexOf( "(");
			else
				idx = str.indexOf( "(" ,  idx+1);
			/* 括弧の前に演算子がない場合 */
			/*数字の方のチェックとかぶってる？*/
			if( idx > 0)
			{
				if( Character.isDigit( str.charAt(idx-1)))
	            {
					error_flag = true;
					error_message = resource.getString("error.prn.pre.op");
		            error_index = idx;
					index =0;
		    		return false;
        		}
			}

			if( idx < str.length() && idx != -1)
			{
    			/*括弧の中身がない場合*/
				if( str.charAt(idx+1) == ')' )
	            {
					error_flag = true;
					error_message = resource.getString("error.prn.in.num");
					error_index = idx+1;
					index =0;
		    		return false;
        		}
    			/*括弧の直後に「-」以外の演算子がある場合*/
				else if( str.charAt(idx+1) == '+' || str.charAt(idx+1) == '*' || str.charAt(idx+1) == '/' || str.charAt(idx+1) == 'E' || str.charAt(idx+1) == '^')
	            {
					error_flag = true;
					error_message = resource.getString("error.prn.pre.num");
					error_index = idx+1;
					index =0;
		    		return false;
        		}

			}
			cnt[0]++;
		}
		idx = 0;
		while( idx !=-1 )
		{
			if(cnt[1] ==0)
    			idx = str.indexOf( ")");
			else
				idx = str.indexOf( ")" ,  idx+1);
			/*括弧の直後に数字*/
			if( idx !=-1 && idx < str.length() )
			{
				if( Character.isDigit( str.charAt(idx+1)))
	            {
					error_flag = true;
					error_message = resource.getString("error.prn.aft.op");
		            error_index = idx+1;
					index =0;
		    		return false;
        		}
			}
			cnt[1]++;
		}
		if( cnt[0] != cnt[1] )
		{
			error_flag = true;
			if(cnt[0] > cnt[1])
				error_message = resource.getString("error.prn.less1") + (cnt[0]-cnt[1]) + resource.getString("error.prn.less2");
			else
				error_message = resource.getString("error.prn.more1") + (cnt[0]-cnt[1]) + resource.getString("error.prn.more2");
    	    return false;
		}
		/* )の後に(が来る */
		if( str.indexOf("(") > str.indexOf(")") )
		{
			error_flag = true;
			error_message = resource.getString("error.prn.opened");
    	    return false;
		}

		index =0;
	    return true;
	}


	/*  */
	/**
	 * 数字の表記をチェック（何度も小数点が出てくる、桁が大きすぎる（18桁まで許容）、数字の直後に文字（演算子の不足） ）
	 * @return エラーがなければtrue、エラーがあればfalseを返す
	 */
	private boolean check_num()
	{
		index =0;
		char c = 0;
		boolean dot = false;
		boolean read_num = false;
		while( index != str.length() )
		{
			c = getchar( );
    		int digits =0;
			if( Character.isDigit(c) || c == '.')
			{
				/* 数字の直前に記号が来ている場合 */
				if( index > 1 )
				{
            		if( Character.isLetter(str.charAt(index-2)) && str.charAt( index-2 ) != 'E' ) //数字の直前に文字
            		{
            			error_flag = true;
            			if( ( index - str.lastIndexOf("PI", index)) == 2+1 && str.lastIndexOf("PI", index) != -1 )
            				error_message = resource.getString("error.pi.aft.op");
            			else if( (  index - str.lastIndexOf("e", index)) == 1+1  && str.lastIndexOf("e", index) != -1)
            				error_message = resource.getString("error.e.aft.op");
            			else
            				error_message = resource.getString("error.fnc.aft.prn");
    		            error_index = index-1;
    					index =0;
    		    		return false;
            		}
				}

				while( Character.isDigit(c) || c == '.' )
				{
					digits++;
					/*小数点のチェック*/
					if(dot == true && c == '.')
					{
						error_flag = true;
        				error_message = resource.getString("error.digit.more");
			            error_index = index;
						index =0;
			    		return false;
					}
					if(c == '.')
						dot = true;

					/*桁数のチェック*/
					if( digits > 18)
					{
						error_flag = true;
        				error_message = resource.getString("error.num.huge");
			            error_index = index;
						index =0;
			    		return false;
					}
					c = getchar( );
				}
				dot = false;
				read_num = true;
			}
    		if( read_num == true && ( Character.isLetter(c) || c == '(' ) && c != 'E' ) //数字の直後に文字または括弧
    		{
    			error_flag = true;
				error_message = resource.getString("error.op.less");
	            error_index = index-1;
				index =0;
	    		return false;
    		}
    		read_num = false;
		}
		index =0;
		return true;
	}

	/**
	 * 演算子の表記をチェック（演算子が連続する、式の最後が演算子、式の途中で等号）
	 * @return エラーがなければtrue、エラーがあればfalseを返す
	 */
	private boolean check_operator()
	{
		index =0;
		char c = 0;
		c = getchar( );
		while( index != str.length() )
		{
			if( c == '=' && index != str.length()-1 )
			{
				error_flag = true;
				error_message = resource.getString("error.equal.not.end");
				error_index = index;
				index =0;
				return false;
			}

			if( c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == 'E')
			{
    			if(index == 1)
    			{
    				if( !(c == '-') )
    				{
    					error_flag = true;
    					error_message = resource.getString("error.op.top") + String.valueOf((char)c);
						error_index = index;
						index =0;
						return false;
    				}
    				else
    				{
        				c = getchar( );
        				if( (c == '-') )
        				{
        					error_flag = true;
        					error_message = resource.getString("error.op.continued") + String.valueOf((char)c);
							error_index = index;
							index =0;
							return false;
        				}
        				ungetchar();
    				}
    			}
    			if(index == str.length()-1)
    			{
    				error_flag = true;
					error_message = resource.getString("error.op.end") + String.valueOf((char)c);
					error_index = index;
					index =0;
					return false;
    			}
				c = getchar( );
    			if( c == '+' || c == '*' || c == '/' || c == '^' || c == 'E' || c == '=') //6/20 マイナスは除外
    			{
    				error_flag = true;
					error_message = resource.getString("error.op.continued") + String.valueOf((char)c);
		            error_index = index;
					index =0;
    	    		return false;
    			}
    			else if( c == ')')
    			{
    				error_flag = true;
					error_message = resource.getString("error.op.aft.num");
		            error_index = index-1;
					index =0;
    	    		return false;
    			}
			}
			else
				c = getchar( );
		}
		index =0;
		return true;
	}

	/**
	 * 関数と記号のチェック（未知の関数と記号を探す）
	 * @return エラーがなければtrue、エラーがあればfalseを返す
	 */
	private boolean check_unknown()
	{
		index =0;
		int start=0,end=0;
		char c = 0;
		String sub_str;
		while( index != str.length() )
		{
			start=index;
    		c = getchar( );
			if( Character.isLetter(c) )
			{
				while( Character.isLetter(c) ) //英字の場合
				{
					end = index;
					c = getchar( );
				}
				sub_str = str.substring(start, end);
				if( !sub_str.equals("sin") &&
						!sub_str.equals("cos") &&
						!sub_str.equals("tan") &&
						!sub_str.equals("asin") &&
						!sub_str.equals("acos") &&
						!sub_str.equals("atan") &&
						!sub_str.equals("log") &&
						!sub_str.equals("ln") &&
						!sub_str.equals("sqrt") &&
						!sub_str.equals("e") &&
						!sub_str.equals("PI") &&
						!sub_str.equals("E"))
				{
					error_flag = true;
					error_message = resource.getString("error.unknown.fuc") + sub_str;
		            error_index = end;
					index =0;
    	    		return false;
				}
			} else if( !Character.isDigit(c) && !( c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '=' || c == '.' ) ) //英字でなく、左のいずれでもない場合
			{
				error_flag = true;
				error_message = resource.getString("error.unknown.var") + String.valueOf((char)c);
	            error_index = index;
				index =0;
	    		return false;
			}
		}
		index =0;
		return true;
	}
}
