package com.example.buscaminas;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button inicio,acercaDe,top;

	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inicio=(Button)findViewById(R.id.PartidaNueva);
		acercaDe=(Button)findViewById(R.id.AcercaDe);
		top=(Button)findViewById(R.id.Top);
		
		inicio.setOnClickListener(ClickInicio);
		acercaDe.setOnClickListener(ClickAcercaDe);
		top.setOnClickListener(ClickTop);
	}

	public void lanzarInicio(){
		Intent i=new Intent(this,Inicio.class);
		startActivity(i);
	}
	
	public void lanzarAcercaDe(){
		Intent i=new Intent(this,AcercaDe.class);
		startActivity(i);
	}
	
	public void lanzarTopJugadores(){
		Intent i=new Intent(this,TopJugadores.class);
		startActivity(i);
	}
	
	OnClickListener ClickInicio = new  OnClickListener(){
		@Override
		public void onClick(View arg0) {
			lanzarInicio();
		}
		
	};

	OnClickListener ClickAcercaDe = new  OnClickListener(){
		@Override
		public void onClick(View arg0) {
			lanzarAcercaDe();
		}
		
	};
	
	OnClickListener ClickTop = new  OnClickListener(){
		@Override
		public void onClick(View arg0) {
			lanzarTopJugadores();
		}
		
	};

	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}