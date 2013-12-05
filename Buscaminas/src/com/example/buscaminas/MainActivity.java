package com.example.buscaminas;


import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button inicio,acercaDe,top;
	private ManejadorClick manejadorClickBotones;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		manejadorClickBotones=new ManejadorClick();
		inicio=(Button)findViewById(R.id.PartidaNueva);
		acercaDe=(Button)findViewById(R.id.AcercaDe);
		top=(Button)findViewById(R.id.Top);
		
		inicio.setOnClickListener(manejadorClickBotones);
		acercaDe.setOnClickListener(manejadorClickBotones);
		top.setOnClickListener(manejadorClickBotones);
		
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
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

	class ManejadorClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			if(v instanceof Button){
				Button b=(Button)v;
				switch(b.getId()){
					case R.id.PartidaNueva:
						lanzarInicio();
						break;
					case R.id.AcercaDe:
						lanzarAcercaDe();
						break;
					case R.id.Top:
						lanzarTopJugadores();
						break;
				}
			}	
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}