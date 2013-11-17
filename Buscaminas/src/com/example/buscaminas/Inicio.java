package com.example.buscaminas;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Inicio extends Activity {
	private Button botonFacil,botonIntermedio,botonExperto;
	private int filas,columnas,minas;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_dificultad_del_juego);
		botonFacil=(Button)findViewById(R.id.Facil);
		botonIntermedio=(Button)findViewById(R.id.Intermedio);
		botonExperto=(Button)findViewById(R.id.Experto);
		registrarBotones();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}
	
	public void registrarBotones(){
		
	//setea layout dependiendo del boton que se escoja
		botonFacil.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				filas=9;columnas=9;minas=10;
				crearTablero();
			}
		});
	
		botonIntermedio.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				filas=16;columnas=16;minas=40;
				crearTablero();
			}
			});
	
		botonExperto.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				filas=16;columnas=30;minas=99;
				crearTablero();
			} 
			});
	}
	
	
	public void crearTablero(){
		Tablero t=new Tablero(this,filas,columnas,minas);
		this.setContentView(t.getLayout());
	}
}
