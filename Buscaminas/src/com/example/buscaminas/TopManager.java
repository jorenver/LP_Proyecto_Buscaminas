package com.example.buscaminas;

import java.util.ArrayList;

public class TopManager {
	private ArrayList<String> ListaJugadores;
	
	public TopManager() {
		ListaJugadores=new ArrayList<String>();
		ListaJugadores=readListfromFile();
	}
	
	
	
	//lee un archivo y coloca sus datos en el arraylist
	public ArrayList<String> readListfromFile(){
		return null;
	}
	
	
	//recibe un tiempo y compara si es menor a los tiempos de la lista de jugadores
	//devuelve true si es que ingresa al top y false caso contrario. 
	public boolean validarTiempo(long tiempo){
		return false;
	}
	
	
	
	//es llamado seguido de validadTiempo, si el tiempo fue valido
	//agrega el jugador a la lista;
	public void agregarJugador(String Nombre, long tiempo){
		
	}
	
	
	
	
}
