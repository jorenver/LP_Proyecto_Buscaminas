package com.example.buscaminas;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.Button;
import android.widget.TableLayout;

public class Celda extends Button implements Observer{
	private boolean Mina;
	private int colores[]= {Color.BLUE,Color.rgb(16,124,10),Color.RED,Color.BLACK,Color.MAGENTA,Color.CYAN,Color.YELLOW,Color.GRAY};
	private EstadoCelda estado;
	private int CantMinasCercanas;
	private ArrayList<Observer> adyacentes;
	private Observer TableroObservador;
		
	public Celda(Context context) {
		super(context);
		Mina=false;
		estado=EstadoCelda.CUBIERTA;
		CantMinasCercanas=0;
		adyacentes=new ArrayList<Observer>();
	}
	
	public void descubrir(){
		if(this.getEstado()==EstadoCelda.CUBIERTA){			
			if(!Mina){	
				estado=EstadoCelda.DESCUBIERTA;
				this.setEnabled(false);
				if(this.CantMinasCercanas==0){
					for(Observer o:adyacentes){
						o.update();
					}
				}else{
					this.setTextColor(colores[CantMinasCercanas-1]);
					this.setText(String.valueOf(CantMinasCercanas));
				}
				TableroObservador.update(this);
			}else{
				TableroObservador.update(this);
				this.setBackgroundResource(R.drawable.boton_bomba_pisada);
			}
		}
	}	
	
	public void destapar(boolean cond){
	   if(cond){ // sin cond es verdadera, gano el juego
			if (this.getEstado()==EstadoCelda.CUBIERTA){
				estado=EstadoCelda.DESCUBIERTA;
				if(!Mina){ //si no tiene mina
					if(this.CantMinasCercanas==0){// si no tiene minas cercanas solo se desbloquea
						this.setEnabled(false);
					}
					else{ //si tiene minas cercanas muestra el numero de minas
						this.setTextColor(colores[CantMinasCercanas-1]);
						this.setText(String.valueOf(CantMinasCercanas));
					}
				}//Si es una mina
				else{
					if(estado==EstadoCelda.BANDERA){
						this.setText("!!!");
					}else{
						this.setText("X");
					}
				}
			}
	 }else{ //si cond es falsa perdio el juego
			if (this.getEstado()==EstadoCelda.CUBIERTA){
				estado=EstadoCelda.DESCUBIERTA;
				if(!Mina){
					if(this.CantMinasCercanas==0){
						this.setEnabled(false);
					}
					else{
						this.setEnabled(false);
						this.setTextColor(colores[CantMinasCercanas-1]);
						this.setText(String.valueOf(CantMinasCercanas));
					}
				}else{ // si es una mina imprime una imagen
					this.setBackgroundResource(R.drawable.boton_bomba_destapado);
				}
			}
		}
	}
	
	
	public void SetBombasCercanas(){
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
	
	public void SetEstado(EstadoCelda Estado){
		estado=Estado;
		
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
		
	}
	
	//sobreescritura del metodo setEnabled
	public void setEnabled(boolean flag){
		if(flag){
			this.setBackgroundResource(R.drawable.boton);	
		}else{
			this.setBackgroundResource(R.drawable.boton_destapado);
		}
	}
	

}
