package com.example.buscaminas;

import android.content.Context;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class BarraDeMenu extends TableLayout {

	private ImageButton Cara;
	private TableRow fila;
	private Observer Inicio;
	private ImageView bandera;
	private Chronometer relog;
	private TextView DispContador;
	private int Contador;
	private long tiempo;
	private TableRow.LayoutParams params1;   
	    
	public BarraDeMenu(Context C){
		super(C);
		Contador=0;
		tiempo=0;
		Cara=new ImageButton(C);
		bandera= new ImageView(C);
		relog = new Chronometer(C);
		fila=new TableRow(C);
		Cara= new ImageButton(C);
		params1= new TableRow.LayoutParams();
		params1.span = 6; 
		DispContador = new TextView (C);
		DispContador.setText(String.valueOf(Contador));
		bandera.setBackgroundResource(R.drawable.bandera);
		Cara.setBackgroundResource(R.drawable.cara2);
		Cara.setOnClickListener(Reiniciar);
		fila.setGravity (Gravity.CENTER); 
		fila.addView(relog);
		fila.addView(Cara);
		fila.addView(bandera);
		fila.addView(DispContador);
		this.addView(fila);
		
	}
	
	
	OnClickListener Reiniciar =new  OnClickListener(){
		public void onClick(View arg0) {
				Inicio.update();
		}
	};
	
	Observer RelogObserver= new Observer(){

		@Override
		public void update() {
			// no se define
		}

		@Override
		public void update(Object o) {
			AccionesRelog accion;
			accion =(AccionesRelog) o;
			if(accion == AccionesRelog.INICIAR)
				relog.setBase(SystemClock.elapsedRealtime());
				relog.start();
			if(accion==AccionesRelog.ENCERAR)
				relog.setText("00:00");
			if(accion==AccionesRelog.DETENER)
				relog.stop();
				tiempo= SystemClock.elapsedRealtime() - relog.getBase();
		}
		
	};
	
	public long getTiempo(){
		return tiempo;
	}
	
	public void setObserver(Observer O){
		Inicio=O;
	}
	
	public Observer getObserverRelor(){
		return RelogObserver;
	}

	Observer CaraObserver=new Observer(){
		@Override
		public void update() {
			
		}

		@Override
		public void update(Object o) {
			EstadoCara estado=(EstadoCara)o;
			switch(estado){
				case ganar:
					//se setea la cara para cuando se gane el juego
					break;
				case perder:
					Cara.setBackgroundResource(R.drawable.terminator);
					break;
				case en_juego:
					Cara.setBackgroundResource(R.drawable.cara2);
					break;
			}
		}
	};

	public Observer getCaraObserver() {
		return CaraObserver;
	}
	
}

