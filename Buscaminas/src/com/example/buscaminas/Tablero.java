package com.example.buscaminas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;

public class Tablero extends View implements Observer{
	private HashMap<Point,Celda>celdas;
	private ArrayList<TableRow>tablero;
	private TableLayout layout;
	int n_filas,n_columnas;
	public static boolean Inicio=true;
	
	public Tablero(Context context,int i,int j) {
		super(context);
		n_filas=i;
		n_columnas=j;
		celdas=new HashMap<Point,Celda>();
		tablero=new ArrayList<TableRow>();
		generarTablero(context);
		registrarCeldasAdyacentes();
		observarCeldas();
	}

	public void generarTablero(Context context){
		layout = new TableLayout(context);
		//Cambios mios
		layout.setLayoutParams(new TableLayout.LayoutParams(
			       LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT ));
		//fin cambios mios
		for(int i=0;i<n_filas;i++){
			TableRow f = new TableRow(context);
			tablero.add(f);
		}
		for(int i=0;i<n_filas;i++)
		{
			TableRow f=tablero.get(i);
			for(int j=0;j<n_columnas;j++){
				Celda celda= new Celda(context,i,j);
				celdas.put(new Point(i,j),celda);
				celda.setOnClickListener(ClickCelda);
				celda.setText("  ");
				f.addView(celda);
			}
			layout.addView(f);
		}
	}

	public Celda obtenerCelda(int i,int j){
		Point punto=new Point (i,j);
		Celda celda=celdas.get(punto);
		if(celda!=null){
			return celda;
		}
		return null;
	}
	
	public void registrarCeldasAdyacentes(){
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_columnas;j++){
				Celda celda_actual=obtenerCelda(i,j);
				if(celda_actual!=null){
					for(int k=-1;k<=1;k++){
						for(int l=-1;l<=1;l++){
							if(k==0&&j==0){
								continue;
							}
							Celda celda_adj=obtenerCelda(i+k,j+l);
							if(celda_adj!=null){
								celda_actual.getAdyacentes().add(celda_adj);
							}
						}
					}
				}
			}
		}
	}
	
	
	public void generarMinas(Celda celdaInicio,int minas){ //falta mejorar este algoritmo
		int aleatorio_x,aleatorio_y;
		Random random=new Random();
		Celda celda=null;
		while(minas!=0){
			aleatorio_x=random.nextInt(n_filas);
			aleatorio_y=random.nextInt(n_columnas);
			celda=obtenerCelda(aleatorio_x,aleatorio_y);
			if(celda!=null&&celda!=celdaInicio){
				if(celda.getMina()){
					continue;
				}else{
					celda.setMina(true);
					minas--;
				}
			}
		}
	}
	
	
	OnClickListener ClickCelda =new  OnClickListener(){
		@Override
		public void onClick(View arg0) {
			for(int i=0;i<n_filas;i++){
				for(int j=0;j<n_columnas;j++){
					Celda c;
					c=celdas.get(new Point(i,j));
					//si la vista que genero el evento es una celda del tablero
					if(arg0==c)
					{
						//si el juego recien inicia se generan las bombas
						if(Inicio){//generar Bombas
							Inicio=false;	
							Celda celdaInicio=c;//referencia a la celda que se presiono primero en el juego			
							generarMinas(celdaInicio,cantidad_de_minas);
							setMinasCercanas();
							Inicio=false;					
						}
						//si la celda aun no ha sido descubierta se la descubre
						if(c.getEstado()!=EstadoCelda.DESCUBIERTA)	
							c.descubrir();	
					}
				}
			}
		}
	};
	
	

	public TableLayout getLayout() {
		return layout;
	}

	public void setLayout(TableLayout layout) {
		this.layout = layout;
	}

	public void update(){
		
	}
	
	public void update(Object o){
		//determina si el jugador , sigue jugando, perdio o gano
		Celda celda=(Celda)o;
		if(celda.getMina()){
			//jugador pierde
			for(int i=0;i<n_filas;i++){
				for(int j=0;j<n_columnas;j++){
					Celda c=obtenerCelda(i,j);
					c.setEnabled(false); //desactivar todas las celdas
				}
			}
		}
	}
	
	public void setMinasCercanas(){//llama al metodo setBombasCercanas 
		int i,j;
		Celda celda;
		for(i=0;i<n_filas;i++){
			for(j=0;j<n_columnas;j++){
				celda=obtenerCelda(i,j);
				if(celda!=null){
					celda.SetBombasCercanas();
				}
			}
		}
	}	
	
	public void observarCeldas(){
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_columnas;j++){
				Celda celda=obtenerCelda(i,j);
				celda.setTableroObservador(this);//hacer que el tablero observe a las celdas
			}
		}
	}	


}
