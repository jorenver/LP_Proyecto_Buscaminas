package com.example.buscaminas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TopJugadores extends Activity{
	private static String ARCHIVO_PRINC = "principiante.txt";
	private static String ARCHIVO_INTER = "intermedio.txt";
	private static String ARCHIVO_EXPER = "experto.txt";
	private InputStream is;//para leer archivos
	private BufferedReader fin;//para leer archivos
	private TabHost Top;
	private TextView ListaPrincipiante;
	private TextView ListaIntermedio;
	private TextView ListaExperto;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top_jugadores);
		is = null;
		fin = null;
		ListaPrincipiante=(TextView)findViewById(R.id.lista_princ);
		ListaIntermedio=(TextView)findViewById(R.id.lista_inter);
		ListaExperto=(TextView)findViewById(R.id.lista_exper);
		ListaPrincipiante.setText(llenarLista(ARCHIVO_PRINC));
		ListaIntermedio.setText(llenarLista(ARCHIVO_INTER));
		ListaExperto.setText(llenarLista(ARCHIVO_EXPER));
		
		Top= (TabHost)findViewById(R.id.TablaTop);
		Top.setup();
		TabSpec spec = Top.newTabSpec("tag1");
		spec.setIndicator("Principiante");
		spec.setContent(R.id.tab1);
		Top.addTab(spec);
		
		spec = Top.newTabSpec("tag2");
		spec.setIndicator("Intermedio");
		spec.setContent(R.id.tab2);
		Top.addTab(spec);
		
		
		spec = Top.newTabSpec("tag3");
		spec.setIndicator("Experto");
		spec.setContent(R.id.tab3);
		Top.addTab(spec);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.top_jugadores, menu);
		return true;
	}
	
	
	public LinkedList<Jugador> readJugadoresfromFile(String Archivo) {
		// debe leer linea por linea el archivo y separar el tiempo para
		// agregarlo
		// al ArrayList
		String entrada;
		Jugador jugador;
		LinkedList<Jugador> Jugadores= new LinkedList<Jugador>();
		try {
			is = this.getAssets().open(Archivo);
			fin = new BufferedReader(new InputStreamReader(is));
			while ((entrada = fin.readLine()) != null) {
				Log.i(entrada,"Exito" );//imprime en consola
				jugador = obtenerJugadorDeString(entrada);
				Jugadores.add(jugador);
				
			}

		} catch (IOException e) {
			Log.e("TopManager", "IO error", e);
			Log.i("TopManager", "IO error");

		}
		Collections.sort(Jugadores); //ordeno los jugadores
		return Jugadores;
	}
	
	
	public Jugador obtenerJugadorDeString(String elementoDeArchivo) {
		String tiempo;
		String Nombre="";
		char iterador;	
		boolean separar=false;
		tiempo=String.valueOf(elementoDeArchivo.charAt(0));
		
		 for (int i=1; i<elementoDeArchivo.length();i++){
			 iterador=elementoDeArchivo.charAt(i);
			 if (iterador==';'){
				 separar=true;
				 i++;
				 iterador=elementoDeArchivo.charAt(i);//avanzo me salto el ';' y leo el siguiente caracter 
			 }
			 if (!separar){// se lee el tiempo 
				 Log.i(tiempo, ""+iterador);
				 tiempo=tiempo +iterador; 
			} 
			 else{
				 Nombre=Nombre+iterador;
			 }
		} 
		 
		 return new Jugador(Nombre,Long.parseLong(tiempo));
		 
	}

	
	public String llenarLista(String Archivo){
		LinkedList<Jugador> Jugadores;
		String Lista="";
		Jugadores=readJugadoresfromFile(Archivo);
		
		for(int i=0; i<Jugadores.size();i++){
			String agregacion;
			agregacion=(Jugadores.get(i)).toString();
			Log.i(agregacion,"hola");
			Lista=Lista+agregacion;
			Log.i(Lista,"lista");
		}
		
		return Lista;
		
	}
	
	
	
	
}
