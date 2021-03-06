package com.example.buscaminas;


public class Jugador implements Comparable<Jugador> {
	private String nombre;
	private int tiempo;
	
	public Jugador(String Nombre, long tiempo) {
		// TODO Auto-generated constructor stub
		nombre=Nombre;
		this.tiempo=aproximarTime(tiempo);
	}
	
	public Jugador(String Nombre, int tiempo){
		nombre=Nombre;
		this.tiempo=tiempo;
	}
	
	public int getTiempo(){
		return tiempo;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String N){
		nombre=N;
	}

	@Override
	public int compareTo(Jugador another) {
		
		if (this.tiempo==another.getTiempo()){
			return 0;
		}
		else if(this.tiempo>another.getTiempo())
			return 1;
		else{
			return -1;
		}
	}
	
	public static int aproximarTime(long tiempoMiliseg){
		int tiempo;
		long tiempoSeg;
		tiempoSeg=tiempoMiliseg/1000;
		tiempo=(int)Math.floor( (Long.valueOf(tiempoSeg)).doubleValue());
		return tiempo;
	}
	
	@Override
	public String toString(){
		String player;
		player="\t"+nombre+"\t\t\t"+Integer.toString(tiempo)+"\n";
		return player;
	}
	
	public String JugadorFormatoDeArchivo(){
		return tiempo+";"+nombre+"\n";
	}

}
