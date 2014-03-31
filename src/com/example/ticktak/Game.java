package com.example.ticktak;

import java.util.LinkedList;
import java.util.Random;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Game extends Activity
{
	class Layout
	{
		public Layout()
		{
			solutionLayout = (LinearLayout)findViewById(R.id.SolutionLayout);
			numbersLayout = (LinearLayout)findViewById(R.id.NumbersLayout);
			
			btnNumber1 = (Button)findViewById(R.id.Number1);
			btnNumber2 = (Button)findViewById(R.id.Number2);
			btnNumber3 = (Button)findViewById(R.id.Number3);
			btnNumber4 = (Button)findViewById(R.id.Number4);
			btnNumber5 = (Button)findViewById(R.id.Number5);
			btnNumber6 = (Button)findViewById(R.id.Number6);
			
			btnPlus = (Button)findViewById(R.id.OperatorPlus);
			btnMinus = (Button)findViewById(R.id.OperatorMinus);
			
			targetNum = (Button)findViewById(R.id.targetNum);
			btnSendSolution = (Button)findViewById(R.id.sendSolution);
			btnStartNewGame = (Button)findViewById(R.id.startNewGame);
			btnBackToMenu = (Button)findViewById(R.id.backToMenu);
			attemptedSolutionResult = (Button)findViewById(R.id.attemptedSolutionResult);
			
			SolutionButtonList = new LinkedList<Button>();
		}
		
		LinearLayout solutionLayout;
		LinearLayout numbersLayout;
		
		Button btnNumber1;
		Button btnNumber2;
		Button btnNumber3;
		Button btnNumber4;
		Button btnNumber5;
		Button btnNumber6;
		Button btnPlus;
		Button btnMinus;
		Button targetNum;
		Button btnSendSolution;
		Button btnStartNewGame;
		Button btnBackToMenu;
		Button attemptedSolutionResult;
		
		LinkedList<Button> SolutionButtonList;
	}
	
	Layout layout;
	boolean operator;
	int index;
	String solutionString;
	
	class Events
	{
		public Events()
		{
			layout.btnNumber1.setOnClickListener(new btnNumberClick());
			layout.btnNumber2.setOnClickListener(new btnNumberClick());
			layout.btnNumber3.setOnClickListener(new btnNumberClick());
			layout.btnNumber4.setOnClickListener(new btnNumberClick());
			layout.btnNumber5.setOnClickListener(new btnNumberClick());
			layout.btnNumber6.setOnClickListener(new btnNumberClick());
			
			layout.btnPlus.setOnClickListener(new btnOperatorClick());
			layout.btnMinus.setOnClickListener(new btnOperatorClick());
			
			layout.btnSendSolution.setOnClickListener(new btnSendSolutionClick());
			layout.btnStartNewGame.setOnClickListener(new btnStartNewGameClick());
			layout.btnBackToMenu.setOnClickListener(new btnBackToMenuClick());
		}
		
		class btnNumberClick implements OnClickListener
		{
			@Override
			public void onClick(View view) { Click_btnNumber((Button)view); }
		}
		
		class btnOperatorClick implements OnClickListener
		{
			@Override
			public void onClick(View view) { Click_btnOperator((Button)view); }
		}
		
		class btnSendSolutionClick implements OnClickListener
		{
			@Override
			public void onClick(View view) { Click_btnSendSolution(); }
		}
		
		class btnStartNewGameClick implements OnClickListener
		{
			@Override
			public void onClick(View view) { Click_btnStartNewGame(); }
		}
		
		class btnBackToMenuClick implements OnClickListener
		{
			@Override
			public void onClick(View view) { Click_btnBackToMenu(); }
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		layout = new Layout();
        Events events = new Events();
        
        index = 0;
        solutionString = "";
        layout.btnStartNewGame.setBackgroundColor(Color.MAGENTA);
        layout.btnBackToMenu.setBackgroundColor(Color.CYAN);
        
        generateRandomTargetNumber();
	}

	void Click_btnNumber(final Button parent)
    {
		if (operator) return;
		
		solutionString += parent.getText().toString();
		
		parent.setVisibility(View.INVISIBLE);
		Button btn = new Button(this);
		btn.setBackgroundColor(Color.GREEN);
		btn.setText(parent.getText());
		btn.setTag(index);
		parent.setTag(index);
		operatorNext();
		
		btn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				int currentTag = (Integer)(v.getTag());
				
				if (currentTag == index - 1) 
				{
					solutionString = solutionString.substring(0, solutionString.length() - 1);
					
					layout.solutionLayout.removeView(v);
					layout.SolutionButtonList.remove();
					
					if (index - 2 >= 0)
					{
						Toast.makeText(getApplicationContext(), String.valueOf(index - 2), Toast.LENGTH_LONG).show();
						
						layout.SolutionButtonList.get(index - 2).setBackgroundColor(Color.GREEN);
					}
					
					index--;
					
					revealParentButton(layout.btnNumber1, currentTag);
					revealParentButton(layout.btnNumber2, currentTag);
					revealParentButton(layout.btnNumber3, currentTag);
					revealParentButton(layout.btnNumber4, currentTag);
					revealParentButton(layout.btnNumber5, currentTag);
					revealParentButton(layout.btnNumber6, currentTag);
					
					operator = false;
					
					numNext();
				}
			}
		});
		
		layout.SolutionButtonList.add(btn);
		layout.solutionLayout.addView(btn, 50, 80);
		
		if (index - 1 >= 0)
		{
			layout.SolutionButtonList.get(index - 1).setBackgroundColor(Color.RED);
		}
		
		operator = true; index++;
    }
	
	void Click_btnOperator(Button parent)
	{
		if (!operator) return;
		
		solutionString += parent.getText().toString();
		
		Button btn = new Button(this);
		btn.setBackgroundColor(Color.GREEN);
		btn.setText(parent.getText());
		btn.setTag(index);
		numNext();
		
		btn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				int currentTag = (Integer)(v.getTag());

                Toast.makeText(getApplicationContext(), "Current tag is: "+currentTag, Toast.LENGTH_LONG).show();
				if (currentTag == index - 1) 
				{
					solutionString = solutionString.substring(0, solutionString.length() - 1);
					
					layout.solutionLayout.removeView(v);
					layout.SolutionButtonList.remove();
					
					layout.solutionLayout.post(new Runnable() {
						
						@Override
						public void run() 
						{
							if (index - 2 >= 0)
							{

								layout.SolutionButtonList.get(index - 2).setBackgroundColor(Color.GREEN);
							}
							
						}
					});

					index--;
					operator = true;
					
					operatorNext();
				}
			}
		});
		
		layout.SolutionButtonList.add(btn);
		layout.solutionLayout.addView(btn, 50, 80);
		
		if (index - 1 >= 0)
		{
			layout.SolutionButtonList.get(index - 1).setBackgroundColor(Color.RED);
		}

		operator = false; index++;
	}
	
	void Click_btnSendSolution()
	{
		if(solutionString.length() < 11)
		{
			Toast.makeText(getApplicationContext(), "Please use all the numbers", Toast.LENGTH_SHORT).show();
		}
		else
		{
			int index = 0;
			int result = Integer.parseInt(solutionString.substring(index, index + 1));
			
			for (index = 0; index <= 8; index += 2) 
			{
				result = basicCalc(result, Integer.parseInt(solutionString.substring(index + 2, index + 3)), solutionString.substring(index + 1, index + 2));
			}
			
			layout.attemptedSolutionResult.setText(String.valueOf(result));
			
			if (result == Integer.parseInt(layout.targetNum.getText().toString())) 
			{
				Toast.makeText(getApplicationContext(), "Correct Solution! :)", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Wrong Solution :(", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	void Click_btnStartNewGame()
	{
		startActivity (new Intent(Game.this, Game.class));
	}
	
	void Click_btnBackToMenu()
	{
		startActivity (new Intent(Game.this, MainMenu.class));
	}
	
	void revealParentButton(View view, int currentTag)
	{
		Object value = view.getTag();
		if (value == null) return;
		
		int parentTag = (Integer)value;
		
		if (parentTag == currentTag)
		{
			view.setVisibility(View.VISIBLE);
		}
	}
	
	void generateRandomNumber(Button btn)
	{
		int randomNum;
		
		double randomizerNum = Math.random() * 9 + 1;
		
		randomNum = (int) randomizerNum;
		btn.setText(String.valueOf(randomNum));
	}
	
	void generateRandomTargetNumber()
	{
		int targetNum = 0;
		
		generateRandomNumber(layout.btnNumber1);
		generateRandomNumber(layout.btnNumber2);
		generateRandomNumber(layout.btnNumber3);
		generateRandomNumber(layout.btnNumber4);
		generateRandomNumber(layout.btnNumber5);
		generateRandomNumber(layout.btnNumber6);
		
		if (randomizeOperator())
		{
			targetNum += Integer.parseInt(layout.btnNumber1.getText().toString());
		}
		else
		{
			targetNum -= Integer.parseInt(layout.btnNumber1.getText().toString());
		}
		
		if (randomizeOperator())
		{
			targetNum += Integer.parseInt(layout.btnNumber2.getText().toString());
		}
		else
		{
			targetNum -= Integer.parseInt(layout.btnNumber2.getText().toString());
		}
		
		if (randomizeOperator())
		{
			targetNum += Integer.parseInt(layout.btnNumber3.getText().toString());
		}
		else
		{
			targetNum -= Integer.parseInt(layout.btnNumber3.getText().toString());
		}
		
		if (randomizeOperator())
		{
			targetNum += Integer.parseInt(layout.btnNumber4.getText().toString());
		}
		else
		{
			targetNum -= Integer.parseInt(layout.btnNumber4.getText().toString());
		}
		
		if (randomizeOperator())
		{
			targetNum += Integer.parseInt(layout.btnNumber5.getText().toString());
		}
		else
		{
			targetNum -= Integer.parseInt(layout.btnNumber5.getText().toString());
		}
		
		if (randomizeOperator())
		{
			targetNum += Integer.parseInt(layout.btnNumber6.getText().toString());
		}
		else
		{
			targetNum -= Integer.parseInt(layout.btnNumber6.getText().toString());
		}
		
		targetNum = Math.abs(targetNum);
		
		layout.targetNum.setText(String.valueOf(targetNum));
	}
	
	Boolean randomizeOperator()
	{
		if (Math.random() > 0.5) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	int basicCalc(int num1, int num2, String operator)
	{
		if(operator.equals("+"))
		{
			return (num1 + num2);
		}
		else
		{
			if (operator.equals("-")) 
			{
				return (num1 - num2);
			}
			
			return 0;
		}
	}
	
	void numNext()
	{
		layout.btnNumber1.setBackgroundColor(Color.GREEN);
		layout.btnNumber2.setBackgroundColor(Color.GREEN);
		layout.btnNumber3.setBackgroundColor(Color.GREEN);
		layout.btnNumber4.setBackgroundColor(Color.GREEN);
		layout.btnNumber5.setBackgroundColor(Color.GREEN);
		layout.btnNumber6.setBackgroundColor(Color.GREEN);
		
		layout.btnPlus.setBackgroundColor(Color.RED);
		layout.btnMinus.setBackgroundColor(Color.RED);
	}
	
	void operatorNext()
	{
		layout.btnNumber1.setBackgroundColor(Color.RED);
		layout.btnNumber2.setBackgroundColor(Color.RED);
		layout.btnNumber3.setBackgroundColor(Color.RED);
		layout.btnNumber4.setBackgroundColor(Color.RED);
		layout.btnNumber5.setBackgroundColor(Color.RED);
		layout.btnNumber6.setBackgroundColor(Color.RED);
		
		layout.btnPlus.setBackgroundColor(Color.GREEN);
		layout.btnMinus.setBackgroundColor(Color.GREEN);
	}
	
//	void extraOptionsOnSolutionSent(boolean isWon)
//	{
//		if (!doesNewGameButtonExist) 
//		{
//			Button startNewGame = new Button(this);
//		
//			if (isWon) 
//			{
//				startNewGame.setText("Start A New Game");
//			}
//			else
//			{
//				startNewGame.setText("Gave Up? Start A New Game");
//			}
//		
//			startNewGame.setBackgroundColor(Color.MAGENTA);
//		
//			startNewGame.setOnClickListener(new OnClickListener() 
//			{
//				@Override
//				public void onClick(View arg0) 
//				{
//					startActivity (new Intent(Game.this, Game.class));
//				}
//			});
//		
//			layout.mainLayout.addView(startNewGame);
//			doesNewGameButtonExist = true;
//		}
//	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}