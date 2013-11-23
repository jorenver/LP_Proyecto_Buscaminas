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
	private int verde=Color.rgb(16,124,10);
	private int colores[]= {Color.BLUE,verde,Color.RED,Color.BLACK,Color.MAGENTA,Color.CYAN,Color.YELLOW,Color.GRAY};
	private EstadoCelda estado;
	private int CantMinasCercanas;
	private ArrayList<Observer> adyacentes;
	private Observer TableroObservador;
		
	
	public Celda(Context context) {
		super(context);
		vacio=false;
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
					this.setText(String.valueOf(CantMinasCercanas));
					this.setTextColor(colores[CantMinasCercanas-1]);
				}
				TableroObservador.update(this);
			}else{
				TableroObservador.update(this);
				this.setBackgroundResource(R.drawable.boton_bomba_pisada);
			}
		}
	}	
	
	public void destapar(boolean cond){
		if(cond){
			if (this.getEstado()==EstadoCelda.CUBIERTA){
				if(!Mina){
					if(this.CantMinasCercanas==0){
						this.setEnabled(false);
					}
					else{
						this.setText(String.valueOf(CantMinasCercanas));
						this.setTextColor(colores[CantMinasCercanas-1]);
						}
				}
				else{
					this.setText("X");
				}
			}
	 }else{
			if (this.getEstado()==EstadoCelda.CUBIERTA){
				if(!Mina){
					if(this.CantMinasCercanas==0){
						this.setEnabled(false);
					}
					else{
						this.setText(String.valueOf(CantMinasCercanas));
						this.setTextColor(colores[CantMinasCercanas-1]);
						}
				}
				else{
					this.setText("*");
					//this.setBackgroundResource(R.drawable.boton_bomba_pisada);
				}
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
