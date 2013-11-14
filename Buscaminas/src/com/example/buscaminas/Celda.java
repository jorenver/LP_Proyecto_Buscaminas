package com.example.buscaminas;

import java.util.ArrayList;
import android.content.Context;
import android.widget.Button;

public class Celda extends Button implements Observer{
	private boolean Mina;
	private EstadoCelda estado;
	private int n_minas_cercanas;
	private ArrayList<Observer> adyacentes;
	private Observer TableroObservador;
	
	public Celda(Context context,int i,int j) {
		super(context);
		Mina=false;
		estado=EstadoCelda.CUBIERTA;
		n_minas_cercanas=0;
	}
	
	//Metodos sets and gets
	public EstadoCelda getEstado(){
		return estado;
	}
	
	
	
	
	public void descubrir(){
		if(!Mina){		
			estado=EstadoCelda.DESCUBIERTA;
			if(this.n_minas_cercanas==0){
				for(Observer o : adyacentes)
					o.update();//notifica a todas sus celdas adyacentes
			}else{
				this.setText(n_minas_cercanas);
			}
		}else
			//envia el boleano de la Mina para ver si el juego continua o no
			TableroObservador.update(Mina);
	}
	
	
	public void setObserver(ArrayList<Observer> observador ){
			adyacentes=observador;
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
		this.descubrir();
	}
	
	public void update(Object o){
		//no se define
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

