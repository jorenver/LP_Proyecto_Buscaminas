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
		
		public Tablero(Context context,int f,int c) {
			super(context);
			celdas=new HashMap<Point,Celda>();
			filas=new ArrayList<TableRow>();
			generarTablero(context);
			n_filas=f;
			n_columnas=c;
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
					celda.setText(i+" "+j);
					f.addView(celda);
				}
			layout.addView(f);
		}
}

	}
