package com.example.buscaminas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


public class TopJugadores extends Activity{

	private static String ARCHIVO_PRINC = "principiante.txt";
	private static String ARCHIVO_INTER = "intermedio.txt";
	private static String ARCHIVO_EXPER = "experto.txt";
	private InputStreamReader is;
	private BufferedReader fin;
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
		ListaPrincipiante.setMovementMethod(new ScrollingMovementMethod());//agrego un scrollbar
		ListaPrincipiante.setText(llenarLista(ARCHIVO_PRINC));//lleno la Lista
		ListaPrincipiante.setBackgroundResource(R.drawable.fondo);
		ListaIntermedio.setMovementMethod(new ScrollingMovementMethod()); //agrego un scrollbar
		ListaIntermedio.setText(llenarLista(ARCHIVO_INTER));//lleno la Lista
		ListaIntermedio.setBackgroundResource(R.drawable.fondo);
		ListaExperto.setMovementMethod(new ScrollingMovementMethod());//agrego un scrollbar
		ListaExperto.setText(llenarLista(ARCHIVO_EXPER));//lleno la Lista
		ListaExperto.setBackgroundResource(R.drawable.fondo);
		
		Top= (TabHost)findViewById(R.id.TablaTop);
		Top.setup();
		/* Agrego la primera pestaña*/
		TabSpec spec = Top.newTabSpec("tag1");
		spec.setIndicator("Principiante");
		spec.setContent(R.id.tab1);
		Top.addTab(spec);
		
		/* Agrego la segunda pestaña*/
		spec = Top.newTabSpec("tag2");
		spec.setIndicator("Intermedio");
		spec.setContent(R.id.tab2);
		Top.addTab(spec);
		
		/* Agrego la tercera pestaña*/
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
	
	
	/*Metodo readJugadoresfromFile(String Archivo) 
	* Lee linea por linea el archivo y obtiene jugadores para
	agregarlo al LinkedList*/
	public LinkedList<Jugador> readJugadoresfromFile(String Archivo) {
		Jugador jugador;
		String entrada;
		LinkedList<Jugador> Jugadores= new LinkedList<Jugador>();
		try {
			is = new InputStreamReader(openFileInput(Archivo));
			fin = new BufferedReader(is);
			while ((entrada = fin.readLine()) != null) {
				jugador = obtenerJugadorDeString(entrada); //creo un jugador a partir de un String
				Jugadores.add(jugador);
			}
			fin.close();

		} catch (IOException e) {
			Log.e("readJugadorFormFile", "IO error", e);
		}
		if(!Jugadores.isEmpty()){
			Collections.sort(Jugadores);//ordeno los jugadores
		}
		return Jugadores;
	}
	

	/* Metodo llenarLista:
	 * Recibe un String para abrir un archivo, y retorna un String con su contenido en el formato 
	 * adecuado*/
	public String llenarLista(String Archivo){
		LinkedList<Jugador> Jugadores;
		String Lista="";
		Jugadores=readJugadoresfromFile(Archivo);
		if(!Jugadores.isEmpty()){
			for(int i=0; i<Jugadores.size();i++){
				String agregacion;
				agregacion=(Jugadores.get(i)).toString();
				Lista=Lista+agregacion+"\n";
			}
		}
		else{
			return "\n\tNo existen elementos para mostrar.";
		}
		return Lista;
		
	}
	
	/*Metodo obtenerJugadorDeString
	 * Dado un String devuelve un objeto de tipo jugador  */
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
				 tiempo=tiempo +iterador; 
			} 
			 else{
				 Nombre=Nombre+iterador;
			 }
		} 
		 return new Jugador(Nombre,Integer.parseInt(tiempo));
		 
	}
	
	
}
