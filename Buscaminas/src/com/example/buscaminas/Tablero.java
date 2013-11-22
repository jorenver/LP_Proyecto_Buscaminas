package com.example.buscaminas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.Toast;

public class Tablero extends View implements Observer{
	private HashMap<Point,Celda>celdas;
	private ArrayList<TableRow>tablero;
	private TableLayout layout;
	int n_filas,n_columnas,cantidad_de_minas;
	public static boolean Inicio;
	private Context contexto;
	
	
	public Tablero(Context context,int i,int j,int minas) {
		super(context);
		Inicio=true;
		contexto=context;
		n_filas=i;
		n_columnas=j;
		cantidad_de_minas=minas;
		celdas=new HashMap<Point,Celda>();
		tablero=new ArrayList<TableRow>();
		generarTablero(context);
		registrarCeldasAdyacentes();
		observarCeldas();
	}

	public void generarTablero(Context context){
		layout = new TableLayout(context);
		layout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT , LayoutParams.WRAP_CONTENT ));
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
				layout.setColumnShrinkable(j,true);
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
					celda.setText("*");
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
					{//si el juego recien inicia se generan las bombas
						if(Inicio){//generar Bombas
							Celda celdaInicio=c;//referencia a la celda que se presiono primero en el juego			
							generarMinas(celdaInicio,cantidad_de_minas);
							setMinasCercanas();
							Inicio=false;
						}
						//si la celda aun no ha sido descubierta se la descubre
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
					c.destapar();
					c.setEnabled(false);//desactivar todas las celdas
				}
			}
			Toast toast = Toast.makeText(contexto, "BOOM!!!", Toast.LENGTH_SHORT);
			toast.show();
		
		}else{
			
			if(celdasDescubiertas()){
				Toast toast = Toast.makeText(contexto, "Ganaste!!!", Toast.LENGTH_SHORT);
				toast.show();
			}else{
				//continua jugando
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
	
	public boolean celdasDescubiertas(){//retorna true si todas las celdas del tablero que no son minas esta descubiertas y false si esto no es cierto
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_filas;j++){
				Celda celda=obtenerCelda(i,j);
				if(celda.getMina()==false && celda.getEstado()==EstadoCelda.CUBIERTA){
					return false;
				}
			}
		} 
		return true;
	}
}
