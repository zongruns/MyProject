package com.zihao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button listview =(Button)findViewById(R.id.listview);
		Button srollview =(Button)findViewById(R.id.scrollview);
		
		listview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(MainActivity.this,ListActivity.class);
				startActivity(intent);
				
			}
		});
		srollview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(MainActivity.this,ScrollViewActivity.class);
				startActivity(intent);
			}
		});
	}

}