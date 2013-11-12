package com.example.buscaminas;

import java.util.ArrayList;
import android.content.Context;
import android.widget.Button;

public class Celda extends Button implements Observer{
	private boolean Mina;
	private EstadoCelda estado;
	private int n_minas_cercanas;
	private ArrayList<Observer> adjacentes;
	
	public Celda(Context context,int i,int j) {
		super(context);
		esMina=false;
		estado=EstadoCelda.CUBIERTA;
		n_minas_cercanas=0;
	}
	
	//Metodos sets and gets
	public EstadoCelda getEstado(){
		return estado;
	}
	
	
	
	
	public void descubrir(){
		estado=EstadoCelda.DESCUBIERTA;
	}
	
	
	public void setObserver(ArrayList<Observer> observador ){
			adjacentes=observador;
	}
	
	
	public void SetBombasCercanas(){ 
		int CuentaBombas=0;
		for(Observer Celda: adyacentes){
			if(Celda.getMina())
				CuentaBombas++;
		}
		this.setCantMinasCercanas(CuentaBombas);
	}
		
	}
	
	public void update(){
	
	}
}


public void setCantMinasCercanas(int NumerodeMinas){
	n_minas_cercanas=NumerodeMinas;
}


public boolean getMina(){
	return Mina;
}
	
	/*
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

	
*/

