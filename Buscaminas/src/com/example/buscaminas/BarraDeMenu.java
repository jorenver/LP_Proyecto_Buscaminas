package com.example.buscaminas;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class BarraDeMenu extends TableLayout {

	private ImageButton Cara;
	private TableRow fila;
	private Observer Inicio;
	private ImageView bandera;
	private Chronometer relog;
	public BarraDeMenu(Context C){
		super(C);
		Cara=new ImageButton(C);
		bandera= new ImageView(C);
		relog = new Chronometer(C);
		fila=new TableRow(C);
		Cara= new ImageButton(C);
		bandera.setBackgroundResource(R.drawable.bandera);
		Cara.setBackgroundResource(R.drawable.cara);
		Cara.setOnClickListener(Reiniciar);
		fila.addView(relog);
		fila.addView(Cara);
		fila.addView(bandera);
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
				relog.start();
			if(accion==AccionesRelog.ENCERAR)
				relog.setText("00:00");
			if(accion==AccionesRelog.DETENER)
				relog.stop();
		}
		
	};
	
	public void setObserver(Observer O){
		Inicio=O;
	}
	
	public Observer getObserverRelor(){
		return RelogObserver;
	}

}

