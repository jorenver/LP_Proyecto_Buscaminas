package com.example.buscaminas;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class TopManager {
	private ArrayList<Long> ListaTiempos;
	private static String NOMBRE_ARCHIVO="top.txt";
	private Context context;
	private AssetManager am; 
	private InputStream is;
	private BufferedReader fin;
	
	public TopManager(Context C) {
		context=C;
		am = C.getResources().getAssets();
		is=null;
		fin= null;
		ListaTiempos=new ArrayList<Long>();
		ListaTiempos=readListfromFile();
	}
	
	
	
	//lee un archivo y coloca sus datos en el ArrayList
	public ArrayList<Long> readListfromFile(){
			//debe leer linea por linea el archivo y separar el tiempo para agregarlo
			//al ArrayList
		String entrada;
		long tiempo;
		ArrayList<Long> Tiempos= new ArrayList<Long>();
		try {
			fin=new BufferedReader(new InputStreamReader(context.openFileInput(NOMBRE_ARCHIVO)));
			do{
			entrada=fin.readLine();
			tiempo=obtenerTiempoDeString(entrada);
			Tiempos.add(new Long(tiempo));
			}while(entrada!=null);
		}catch(IOException e) {
				   Log.e("TopManager", "IO error", e);
				   
			}
		return null;
	}
	
	
	//recibe un tiempo y compara si es menor a los tiempos de la lista de jugadores
	//devuelve true si es que ingresa al top y false caso contrario. 
	
	public boolean validarTiempo(long tiempo){
		long tiempoActual;
		for (int i=0;i<20;i++){
			tiempoActual=(ListaTiempos.get(i)).longValue();
			if (tiempo<tiempoActual){
				return true;
			}
		}
		
		return false;
	}
	
	
	
	//es llamado seguido de validadTiempo, si el tiempo fue valido
	//agrega el jugador a la lista;
	public void agregarJugador(String Nombre, long tiempo){
		String ingreso=Nombre+";"+ tiempo+ "\n";
		
	}
	
	public long obtenerTiempoDeString(String elementoDeArchivo){
		String tiempo;
		char iterador;
		//obtengo la primera letra
		tiempo=String.valueOf(elementoDeArchivo.charAt(0));
		
		for (int i=1; i<elementoDeArchivo.length();i++){
			iterador=elementoDeArchivo.charAt(i);
				if (iterador!=';'){// si encuentra un ';' significa que se leyo el tiempo
					tiempo=tiempo + iterador;
				}
		}
		return Long.parseLong(tiempo);
	}
	
}
