package com.example.buscaminas;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TableLayout;

public class Celda extends Button implements Observer{
	private boolean vacio;
	private boolean Mina;
	private int colores[]= {Color.BLUE,Color.GREEN,Color.RED};
	private EstadoCelda estado;
	private int CantMinasCercanas;
	private int pos_x,pos_y;
	private ArrayList<Observer> adyacentes;
	private Observer TableroObservador;
		
	
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
					this.setText(String.valueOf(CantMinasCercanas));
					Random aleatorio=new Random();
					int indice_color=aleatorio.nextInt(3);
					this.setTextColor(colores[indice_color]);
				}
				TableroObservador.update(this);
			}else{
				TableroObservador.update(this);
				this.setText("*");
			}
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
	
	public void setTableroObservador(Observer tableroObservador) {
		TableroObservador = tableroObservador;
	}

	public ArrayList<Observer> getAdyacentes() {
		return adyacentes;
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
