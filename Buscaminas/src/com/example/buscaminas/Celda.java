package com.example.buscaminas;

import java.util.ArrayList;
import java.util.Observable;

import android.content.Context;
import android.widget.Button;

public class Celda extends Button implements Observer{
	private boolean vacio;
	private boolean Mina;
	private EstadoCelda estado;
	private int CantMinasCercanas;
	private int pos_x,pos_y;
	private CeldaObservable observable;
	private ArrayList<Observer> adyacentes;
	private Observer TableroObservador;
		
	public ArrayList<Observer> getAdyacentes() {
		return adyacentes;
	}
	
	public Celda(Context context,int i,int j) {
		super(context);
		vacio=false;
		Mina=false;
		estado=EstadoCelda.CUBIERTA;
		CantMinasCercanas=0;
		pos_x=i;
		pos_y=j;
		adyacentes=new ArrayList<Observer>();
	}
	
	public void descubrir(){
		if(this.getEstado()==EstadoCelda.CUBIERTA){			
			if(!Mina){	
				estado=EstadoCelda.DESCUBIERTA;
				if(this.CantMinasCercanas==0){
					for(Observer o:adyacentes){
						o.update();
					}
					this.setEnabled(false);
				}else{
					this.setText(" "+String.valueOf(CantMinasCercanas)+" ");
				}
			}else{
				//envia el boleano de la Mina para ver si el juego continua o no
				//TableroObservador.update(Mina);
				this.setText("*");
			}
		}
	}
	
	public CeldaObservable getObservable() {
		return observable;
	}

	class CeldaObservable extends Observable{
		void cambioCelda(){
			setChanged();
		}
	}
	
	public void SetBombasCercanas(){ //este metodo debe llamarse en la clase tablero despues que se generen las minas
		int CuentaBombas=0;
		for(Observer Celda: adyacentes){
			if(((Celda)Celda).getMina())
				CuentaBombas++;
		}
		this.setCantMinasCercanas(CuentaBombas);
	}

	public void setCantMinasCercanas(int cuentaBombas) {
		this.CantMinasCercanas=cuentaBombas;
	}


	public EstadoCelda getEstado() {
		return estado;
	}

	public boolean getMina(){
		return Mina;
	}

	
	
	public void setMina(boolean mina) {
		Mina = mina;
	}

	@Override
	public void update() {
		this.descubrir();
	}

	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		
	}
	
	

}
