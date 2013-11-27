package com.example.buscaminas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class TableroCompleto extends TableLayout {
	private BarraDeMenu Barra;
	private Tablero tablero;
	private TableRow fila1;
	private Jugador jugador;
	private Nivel nivel;
	private TopManager Top;
	private Context C;
	
	public TableroCompleto(Context context, int fila, int columna,int Nminas,Observer O) {
		super(context);
		C=context;
		Barra=new BarraDeMenu(context);
		Barra.setObserver(O);
		tablero=new Tablero(context,fila,columna,Nminas);
		//el reloj observa al tablero para saber cuando reiniciarse , detenerce , o encerarse
		tablero.setObserver(Barra.getObserverRelor());
		tablero.setObserverCara(Barra.getCaraObserver());
		tablero.setObserverTableroCompleto(TabCompObserver);
		fila1= new TableRow(context);
		Top= new TopManager(context);
		fila1.setGravity(Gravity.CENTER);
		this.setBackgroundResource(R.drawable.fondo);
		
		ArmarTablero();	
	}
	
	public void ArmarTablero(){
		fila1.addView(Barra);
		this.addView(fila1);
		this.addView(tablero.getLayout());
	}
	
	public void reiniciarJuego(){
		tablero.reiniciar();
	}
	
	Observer TabCompObserver= new Observer(){
		@Override
		public void update() {
			long time=Barra.getTiempo();
			boolean entraTop=Top.validarTiempo(time);
			Toast toast1 = Toast.makeText(C,""+time, Toast.LENGTH_SHORT);
			toast1.show();
			if (entraTop){
			Toast toast = Toast.makeText(C,"Entraste al Top"+time, Toast.LENGTH_SHORT);
			toast.show();
			}
		}

		@Override
		public void update(Object o) {
		}
		
	};
	
	
	public Nivel getNivel(int j){
		Nivel N;
		
		if (j==9){
			return Nivel.PRINCIPIANTE;
		}
		else if(j==16){
			return Nivel.INTERMEDIO;
		}
		else{
			return Nivel.EXPERTO;
			}
		
	}
	
	
}