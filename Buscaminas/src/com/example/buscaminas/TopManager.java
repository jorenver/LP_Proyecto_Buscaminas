package com.example.buscaminas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import android.content.Context;
import android.util.Log;

public class TopManager implements Observer {
	private static String ARCHIVO_PRINC = "principiante.txt";
	private static String ARCHIVO_INTER = "intermedio.txt";
	private static String ARCHIVO_EXPER = "experto.txt";
	private Nivel nivel;
	private Context context;
	private InputStreamReader is;
	private BufferedReader fin;
	private CustomDialog Dialogo;

	public TopManager(Context C) {
		context = C;
		is = null;
		fin = null;
		Dialogo=new CustomDialog(C);
		Dialogo.setObserver(this);
	}
	/*Metodo readTimesfromFile 
	 * Recibe un string para abrir un archivo
	 * Lee linea por linea el archivo y separar el tiempo para agregarlo al LinkedList
	 * Sirve para obtener todos los tiempos existentes y comparar si un nuevo jugador
	 * ingresa el top*/
	public  LinkedList<Integer> readTimesfromFile(String Archivo) {
		
		LinkedList<Integer> ListaTiempos=new LinkedList<Integer>();
		String entrada;
		int tiempo;
			try {
	            // instancio mi InputStreamReader para leer el archivo de texto.
					is = new InputStreamReader(context.openFileInput(Archivo));
	            // instancio mi buffer.
					fin = new BufferedReader(is);
					//recorro, obtengo el tiempo y lo guardo en el LinkedList
					while ((entrada = fin.readLine()) != null) {
						Log.i(entrada,"ReadTimesfromFile" );//imprime en consola
						tiempo = obtenerTiempoDeString(entrada);
						ListaTiempos.add(Integer.valueOf(tiempo));
					}
	            fin.close(); //cierro el archivo
	 
	        } catch (Exception e) {
	        	Log.i("ListaReadTiempos","Error");
	        }
		return ListaTiempos;
	}
	
	
	
	
	public void setNivel(Nivel nivel){
		this.nivel = nivel;
	}
	
	

	
	
	/* Metodo validaTiempo
	 * Recibe un tiempo y revisa los tiempos de la lista de
	 jugadores; si cumple las condiciones 
	  devuelve true si es que ingresa al top y false caso contrario.
	*/
	public boolean validarTiempo(int tiempo) {
		LinkedList<Integer> ListaTiempos=new LinkedList<Integer>();
		long tiempoActual;
		
		// lee el archivo adecuado de acuerdo al nivel
		if (nivel == Nivel.PRINCIPIANTE){
			ListaTiempos=readTimesfromFile(ARCHIVO_PRINC);
		}
		else if(nivel==Nivel.INTERMEDIO){
			ListaTiempos=readTimesfromFile(ARCHIVO_INTER);
		}
		else if(nivel==Nivel.EXPERTO){
			ListaTiempos=readTimesfromFile(ARCHIVO_EXPER);
		}
		
		if (!ListaTiempos.isEmpty()) {
			
			if (ListaTiempos.size()>=10){ //si la lista es mayor o igual que  10, para ingresar debe ser menor que alguno de ellos
				for (int i = 0; i < ListaTiempos.size(); i++) {
					tiempoActual = (ListaTiempos.get(i)).intValue();
					if (tiempo < tiempoActual) {
						return true; //encuentra que es menor a in tiempo existente
					}
				}
				return false; //no fue menor a ningun tiempo, no ingresa
			}
			else{ //si la lista es menor que 10, entra al top
				return true;
			}
			
		}

		return true;//si la lista esta vacia ingresa al top
	}

	/*Metodo agregarJugador
	 *Recibe un tiempo y abre un cuadro de dialogo para pedir datos al jugador
	 * es llamado seguido de validadTiempo, si el tiempo fue valido
	 agrega el jugador a la lista;*/
	public void agregarJugador(long tiempo) {
		Dialogo.mostrar(tiempo);
	}

	//analiza una cdena de caracteres y devuelve e tiempo
	public int obtenerTiempoDeString(String elementoDeArchivo) {
		String tiempo;
		char iterador;		
		tiempo=String.valueOf(elementoDeArchivo.charAt(0));
		
		 for (int i=1; i<elementoDeArchivo.length();i++){
			 iterador=elementoDeArchivo.charAt(i); 
			 if (iterador!=';'){// siencuentra un ';' significa que se leyo el tiempo 
				 Log.i(tiempo, "obteniendo Tiempo");
				 tiempo=tiempo +iterador; 
				 } else{ i=elementoDeArchivo.length(); } 
			 } return Integer.parseInt(tiempo);
		 
	}
	
	
	
	
	public void guardarJugador(Jugador J){
		String JugadorString=J.JugadorFormatoDeArchivo();
		Log.i("guardarJugador",JugadorString);
		if (nivel == Nivel.PRINCIPIANTE){
			escribirJugadorEnArchivo(ARCHIVO_PRINC,JugadorString);
		}
		else if(nivel==Nivel.INTERMEDIO){
			escribirJugadorEnArchivo(ARCHIVO_INTER,JugadorString);
		}
		else if(nivel==Nivel.EXPERTO){
			escribirJugadorEnArchivo(ARCHIVO_EXPER,JugadorString);
		}
		
	}
	
	public void escribirJugadorEnArchivo(String Archivo, String JugadorString){
		try {
            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput(Archivo, Context.MODE_APPEND));
            out.write(JugadorString);
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.i("EscribirJugadroEnArchivo", 	"NO SE PUDO");
        }
		
	}
	

	@Override
	public void update() {
		
	}

	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		Jugador J=(Jugador)o;
		guardarJugador(J);
		
	}
		

	
}





