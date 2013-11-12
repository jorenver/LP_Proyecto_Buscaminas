package com.example.buscaminas;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Tablero extends View {
		HashMap<Point,Celda>celdas;
		ArrayList<TableRow>filas;
		TableLayout layout;
		int n_filas,n_columnas;
		//varible para saber cuando agregar las bombas en el tablero
		public static boolean Inicio;
		
		public Tablero(Context context,int f,int c) {
			super(context);
			Inicio=true;
			celdas=new HashMap<Point,Celda>();
			filas=new ArrayList<TableRow>();
			n_filas=f;
			n_columnas=c;
			generarTablero(context);
			llenarCeldasAdyacentes();
			
		}
		
		public void generarTablero(Context context){
			layout = new TableLayout(context);
			// se crean las filas del tablero
			for(int i=0;i<n_filas;i++){
				TableRow f = new TableRow(context);
				filas.add(f);
			}
			
			//se agregan las celdas al tablero
			for(int i=0;i<n_filas;i++){
				TableRow f=filas.get(i);
				for(int j=0;j<n_columnas;j++){
					Celda celda=new Celda(context ,i,j);
					celdas.put(new Point(i,j),celda);
					celda.setOnClickListener(ClickCelda);
					f.addView(celda);
				}
			layout.addView(f);
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
							if(Inicio){
								//generar Bombas
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
		
		
		public void llenarCeldasAdyacentes(){
			for(int i=0;i<n_filas;i++){
				for(int j=0;j<n_columnas;j++){
					Celda c,c2;
					ArrayList<Observer> adyacentes=new ArrayList<Observer>();
					c=celdas.get(new Point(i,j));
					//recorro todas las celdas adjacentes y las agrego en un array
					for(int k=-1;k<=1;k++){
						for(int l=-1;l<=1;l++){
							if(k==0&&j==0)
								continue;
							c2=celdas.get(new Point(i+k,j+l));
							if(c2!=null)
								adyacentes.add(c2);
						}
					}
					//seteo todos los observadores a la celda
					c.setObserver(adyacentes);
				}
			}
		}
				
}

