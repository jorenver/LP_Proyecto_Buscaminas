package com.example.buscaminas;

public class Jugador {
	private String nombre;
	private Nivel nivel;
	private long tiempo;
	
	public Jugador(long tiempo) {
		// TODO Auto-generated constructor stub
		this.tiempo=tiempo;
	}
	
	public void setNombre(String N){
		nombre=N;
	}
	
	public void setNivel(Nivel N){
		nivel=N;
	}

}
