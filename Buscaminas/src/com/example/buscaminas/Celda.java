package com.example.buscaminas;

import java.util.Observable;
import java.util.Observer;


import android.content.Context;
import android.widget.Button;

public class Celda extends Button implements Observer{
	boolean esMina;
	EstadoCelda estado;
	int n_minas_cercanas;
	int pos_x,pos_y;
	private CeldaObservable observable;
	
	public Celda(Context context,int i,int j) {
		super(context);
		esMina=false;
		estado=EstadoCelda.CUBIERTA;
		n_minas_cercanas=0;
		pos_x=i;
		pos_y=j;
	}
	
	//Metodos sets and gets
	public EstadoCelda getEstado(){
		return estado;
	}
	
	
	
	
	public void descubrir(){
		
		estado=EstadoCelda.DESCUBIERTA;
	
	}
	
	public CeldaObservable getObservable() {
		return observable;
	}

	
	class CeldaObservable extends Observable{
		void cambioCelda(){
			setChanged();
		}
	}
	
	

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	

}
