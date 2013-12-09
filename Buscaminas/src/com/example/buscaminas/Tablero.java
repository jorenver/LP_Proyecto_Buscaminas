package com.example.buscaminas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Random;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.Toast;



public class Tablero extends View implements Observer{
	private HashMap<Point,Celda>celdas;
	private ArrayList<TableRow>tablero;
	private TableLayout layout;
	int n_filas,n_columnas,cantidad_de_minas;
	public static boolean Inicio;
	private Context contexto;
	private Observer relog;
	private Observer cara;
	private Observer TabCompleto;
	private MediaPlayer player;
	
	public Tablero(Context context,int i,int j,int minas) {
		super(context);
		Inicio=true;
		contexto=context;
		n_filas=i;
		n_columnas=j;
		cantidad_de_minas=minas;
		celdas=new HashMap<Point,Celda>();
		tablero=new ArrayList<TableRow>();
		
		
		generarTablero(context);
		registrarCeldasAdyacentes();
		observarCeldas();
		layout.setGravity(Gravity.CENTER);
	}

	public void generarTablero(Context context){
		layout = new TableLayout(context);
	//	layout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT , LayoutParams.FILL_PARENT ));
		for(int i=0;i<n_filas;i++){
			TableRow f = new TableRow(context);
			//f.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT ));
			f.setGravity(Gravity.CENTER);
			tablero.add(f);
		}
		for(int i=0;i<n_filas;i++)
		{
			TableRow f=tablero.get(i);
			for(int j=0;j<n_columnas;j++){
				Celda celda= new Celda(context);
				celdas.put(new Point(i,j),celda);
				celda.setOnClickListener(ClickCelda);
				celda.setText("  ");
				celda.setBackgroundResource(R.drawable.boton);
				//celda.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT ));
				f.addView(celda);				
				layout.setColumnShrinkable(j,true);
			}
			layout.addView(f);
		}		
	}

	public Celda obtenerCelda(int i,int j){
		Point punto=new Point (i,j);
		Celda celda=celdas.get(punto);
		if(celda!=null){
			return celda;
		}
		return null;
	}
	
	public void registrarCeldasAdyacentes(){
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_columnas;j++){
				Celda celda_actual=obtenerCelda(i,j);
				if(celda_actual!=null){
					for(int k=-1;k<=1;k++){
						for(int l=-1;l<=1;l++){
							if(k==0&&j==0){
								continue;
							}
							Celda celda_adj=obtenerCelda(i+k,j+l);
							if(celda_adj!=null){
								celda_actual.getAdyacentes().add(celda_adj);
							}
						}
					}
				}
			}
		}
	}
	
	public void generarMinas(Celda celdaInicio,int minas){ //falta mejorar este algoritmo
		int aleatorio_x,aleatorio_y;
		Random random=new Random();
		Celda celda=null;
		while(minas!=0){
			aleatorio_x=random.nextInt(n_filas);
			aleatorio_y=random.nextInt(n_columnas);
			celda=obtenerCelda(aleatorio_x,aleatorio_y);
			if(celda!=null&&celda!=celdaInicio){
				if(celda.getMina()){
					continue;
				}else{
					celda.setMina(true);
					celda.setText("*");
					minas--;
				}
			}
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
					{//si el juego recien inicia se generan las bombas
						if(Inicio){//generar Bombas
							Celda celdaInicio=c;//referencia a la celda que se presiono primero en el juego			
							generarMinas(celdaInicio,cantidad_de_minas);
							setMinasCercanas();
							encerarRelog();
							iniciarRelog();
							Inicio=false;
						}
						//si la celda aun no ha sido descubierta se la descubre
							c.descubrir();	
					}
				}
			}
		}
	};
	
	
	public TableLayout getLayout() {
		return layout;
	}

	public void setLayout(TableLayout layout) {
		this.layout = layout;
	}

	public void update(){
		
	}
	
	public void update(Object o){
		//determina si el jugador , sigue jugando, perdio o gano
		if(o!=null){//si el objeto no es nulo se dio click en una celda
			Celda celda=(Celda)o;
			if(celda.getMina() && celda.getEstado() != EstadoCelda.BANDERA){ //si es verdadero tiene una mina
				//jugador pierde
				detenerRelog();//reloj se detiene
				cara.update(EstadoCara.perder);
				reproducirMusica();
				for(int i=0;i<n_filas;i++){ //recorro las celdas
					for(int j=0;j<n_columnas;j++){
						Celda c=obtenerCelda(i,j);
						c.destapar(false); //informo a la celda que ha perdido el juego
					}
				}
				Toast toast = Toast.makeText(contexto, "BOOM!!!", Toast.LENGTH_SHORT);
				toast.show();
			}
			else{
				if(celdasDescubiertas()){ //si el gana
					detenerRelog();//reloj se detiene
					//recorro las celdas
					for(int i=0;i<n_filas;i++){
						for(int j=0;j<n_columnas;j++){
							Celda c=obtenerCelda(i,j);
							c.setBackgroundResource(R.drawable.boton);
							c.destapar(true);//informo que gano
							c.setEnabled(false);//desactivar todas las celdas
						}
					}
					TabCompleto.update(); // informa al top que ha ganado
				}
			}
		}else{//si es nulo vino de alguna bandera
			if(this.allMinasBandera() && this.cantBanderasTablero()==cantidad_de_minas){
				detenerRelog();//reloj se detiene
				//recorro las celdas
				for(int i=0;i<n_filas;i++){
					for(int j=0;j<n_columnas;j++){
						Celda c=obtenerCelda(i,j);
						c.setBackgroundResource(R.drawable.boton);
						c.destapar(true);//informo que gano
						c.setEnabled(false);//desactivar todas las celdas
					}
				}
				
				Toast toast = Toast.makeText(contexto, "Ganaste!!!", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
	
	
	public void observarCeldas(){
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_columnas;j++){
				Celda celda=obtenerCelda(i,j);
				celda.setTableroObservador(this);//hacer que el tablero observe a las celdas
			}
		}
	}
		
	public void setMinasCercanas(){//llama al metodo setBombasCercanas 
		int i,j;
		Celda celda;
		for(i=0;i<n_filas;i++){
			for(j=0;j<n_columnas;j++){
				celda=obtenerCelda(i,j);
				if(celda!=null){
					celda.SetBombasCercanas();
				}
			}
		}
	}
	
	public boolean celdasDescubiertas(){//retorna true si todas las celdas del tablero que no son minas esta descubiertas y false si esto no es cierto
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_filas;j++){
				Celda celda=obtenerCelda(i,j);
				if(celda.getMina()==false && (celda.getEstado()==EstadoCelda.CUBIERTA ||celda.getEstado()==EstadoCelda.BANDERA)) {
					return false;
				}
			}
		} 
		return true;
	}

	//inicializa el tablero y el reloj
	public void reiniciar(){
		Inicio=true;
		encerarRelog();
		detenerRelog();
		
		if(player!=null&&player.isPlaying()){
			player.stop();
		}
		
		cara.update(EstadoCara.en_juego);
		
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_columnas;j++){
				Celda c=obtenerCelda(i,j);
				c.setText(" ");
				c.setTextColor(Color.BLACK);
				c.setMina(false);
				c.setEnabled(true);
				c.SetEstado(EstadoCelda.CUBIERTA);
			}
		}
	}
	
	public void setObserver(Observer o){
		relog =o;
	}

	public void iniciarRelog(){
		relog.update(AccionesRelog.INICIAR);
	}
	
	public void detenerRelog(){
		relog.update(AccionesRelog.DETENER);
	}
	
	public void encerarRelog(){
		relog.update(AccionesRelog.ENCERAR);
	}
	
	public void setObserverCara(Observer observer){
		this.cara=observer;
	}

	public void  setObserverTableroCompleto(Observer T){
		TabCompleto=T;
	}
	
	public int getCantMinas(){
		return cantidad_de_minas;
	}
	

	public void reproducirMusica(){
		AssetManager manejador=this.getContext().getAssets();
		player=new MediaPlayer();
		player.setOnCompletionListener(new OnCompletionListener(){
				@Override
				public void onCompletion(MediaPlayer arg0) {
					player.stop();
				}
			}
		);
		try {
			AssetFileDescriptor des=manejador.openFd("terminator_final.wav");
			player.setDataSource(des.getFileDescriptor(),des.getStartOffset(),des.getLength());
			player.prepare();			
			player.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//setea el drag para que las celdas pueden escuchar cuando cae una bandera
	@SuppressLint("NewApi")
	public void setOnDrag(OnDragListener listenerTocar){
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_columnas;j++){
				Celda c=obtenerCelda(i,j);
				c.setOnDragListener(listenerTocar);
			}
		}
	}
	
	
	//setea en ontouch para que las celdas que tengan banderas sean arrastables
	@SuppressLint("NewApi")
	public void setOnTouch(OnTouchListener listenerTocar){
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_columnas;j++){
				Celda c=obtenerCelda(i,j);
				c.setOnTouchListener(listenerTocar);
			}
		}
	}
	
	
	int cantBanderasTablero(){
		int cont=0;
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_columnas;j++){
				Celda c=obtenerCelda(i,j);
				if(c.getEstado()==EstadoCelda.BANDERA)
					cont++;
			}
		}
		return cont;
	}
	
	boolean allMinasBandera(){
		for(int i=0;i<n_filas;i++){
			for(int j=0;j<n_columnas;j++){
				Celda c=obtenerCelda(i,j);
				//si la mina esta cubierta y tiene mina
				if(c.getEstado()==EstadoCelda.CUBIERTA && c.getMina())
					return false;
			}
		}
		return true;
	}
	
	
}
		