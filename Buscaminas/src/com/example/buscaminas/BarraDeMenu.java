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
	private long tiempo;
	private TableRow.LayoutParams params1;   
	 
	public BarraDeMenu(Context C){
		super(C);
		LayoutParams parametros=new TableLayout.LayoutParams();
		tiempo=0;
		Cara=new ImageButton(C);
		bandera= new ImageView(C);
		relog = new Chronometer(C);
		fila=new TableRow(C);
		Cara= new ImageButton(C);
		params1= new TableRow.LayoutParams();
		DispContador = new TextView (C);
		bandera.setBackgroundResource(R.drawable.bandera);
		Cara.setBackgroundResource(R.drawable.terminator_face);
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
				case GANAR:
					Cara.setBackgroundResource(R.drawable.terminator_winner);
					break;
				case PERDER:
					Cara.setBackgroundResource(R.drawable.terminator);
					break;
				case EN_JUEGO:
					Cara.setBackgroundResource(R.drawable.terminator_face);
					break;
			}
		}
	};

	public Observer getCaraObserver() {
		return CaraObserver;
	}

	public View getBandera() {
		return this.bandera;
	}
	
	public TextView getContbandera(){
		return DispContador;
	}
	
}

