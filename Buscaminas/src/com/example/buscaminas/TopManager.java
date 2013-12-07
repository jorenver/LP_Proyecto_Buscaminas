package com.example.buscaminas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class TopManager {
	private LinkedList<Long> ListaTiempos;
	private static String ARCHIVO_PRINC = "principiante.txt";
	private static String ARCHIVO_INTER = "intermedio.txt";
	private static String ARCHIVO_EXPER = "experto.txt";
	private Nivel nivel;
	private Context context;
	private InputStream is;
	private BufferedReader fin;

	public TopManager(Context C) {
		context = C;
		is = null;
		fin = null;
		ListaTiempos = new LinkedList<Long>();
		
	}

	// lee un archivo y coloca los tiempos en el ArrayList
	public void readTimesfromFile(String Archivo) {
		// debe leer linea por linea el archivo y separar el tiempo para
		// agregarlo
		// al ArrayList
		String entrada;
		long tiempo;
		try {
			is = context.getAssets().open(Archivo);
			fin = new BufferedReader(new InputStreamReader(is));
			while ((entrada = fin.readLine()) != null) {
				Log.i(entrada,"Exito" );//imprime en consola
				tiempo = obtenerTiempoDeString(entrada);
				ListaTiempos.add(Long.valueOf(tiempo));
				
			}

		} catch (IOException e) {
			Log.e("TopManager", "IO error", e);
			Log.i("TopManager", "IO error");

		}
	}
	
	
	
	
	public void setNivel(Nivel nivel){
		this.nivel = nivel;
	
	}

	
	
	/* recibe un tiempo y compara si es menor a los tiempos de la lista de
	 jugadores; devuelve true si es que ingresa al top y false caso contrario.
	*/
	public boolean validarTiempo(long tiempo) {
		long tiempoActual;
		
		// lee el archivo adecuado de acuerdo al nivel
		if (nivel == Nivel.PRINCIPIANTE){
			readTimesfromFile(ARCHIVO_PRINC);
		}
		else if(nivel==Nivel.INTERMEDIO){
			readTimesfromFile(ARCHIVO_INTER);
		}
		else if(nivel==Nivel.EXPERTO){
			readTimesfromFile(ARCHIVO_EXPER);
		}
		
		if (!ListaTiempos.isEmpty()) {
			
			if (ListaTiempos.size()>=20){ //si la lista es mayor o igual que  20, para ingresar debe ser menor que alguno de ellos
				for (int i = 0; i < ListaTiempos.size(); i++) {
					tiempoActual = (ListaTiempos.get(i)).longValue();
					if (tiempo < tiempoActual) {
						return true;
					}
				}
			}
			else{ //si la lista es menor que 20, entra al top
				return true;
			}
			
		}

		return false;
	}

	// es llamado seguido de validadTiempo, si el tiempo fue valido
	// agrega el jugador a la lista;
	public void agregarJugador(String Nombre, long tiempo) {
		String ingreso = Nombre + ";" + tiempo + "\n";
		//pendiente
	}

	public long obtenerTiempoDeString(String elementoDeArchivo) {
		String tiempo;
		char iterador;		
		tiempo=String.valueOf(elementoDeArchivo.charAt(0));
		
		 for (int i=1; i<elementoDeArchivo.length();i++){
			 iterador=elementoDeArchivo.charAt(i); 
			 if (iterador!=';'){// siencuentra un ';' significa que se leyo el tiempo 
				 Log.i(tiempo, ""+iterador);
				 tiempo=tiempo +iterador; 
				 } else{ i=elementoDeArchivo.length(); } 
			 } return
			  Long.parseLong(tiempo);
		 
	}

	
	
	public void imprimeArraY(ArrayList<Long> Array) {
		if (Array.isEmpty()) {
			Log.i("Esta vacio", "hgyg");
		} else {
			for (int i = 0; i < Array.size(); i++) {
				Log.i((Array.get(i)).toString(), "hey");
			}
		}

	}
}