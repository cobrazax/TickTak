package com.example.ticktak;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Instructions extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions);
		
		TextView title = (TextView)findViewById(R.id.title);
		TextView instructionsText = (TextView)findViewById(R.id.instructionsText);
		
		title.setTextColor(Color.BLUE);
		instructionsText.setTextColor(Color.BLUE);
		
		Button btnBackToMenu = (Button)findViewById(R.id.backToMenu);
		btnBackToMenu.setBackgroundColor(Color.CYAN);
		
		btnBackToMenu.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				startActivity (new Intent(Instructions.this, MainMenu.class));
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
