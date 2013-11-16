package com.example.buscaminas;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Inicio extends Activity {
	private Tablero tablero;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tablero = new Tablero(this, 10,12);
		
		setContentView(tablero);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}

}
