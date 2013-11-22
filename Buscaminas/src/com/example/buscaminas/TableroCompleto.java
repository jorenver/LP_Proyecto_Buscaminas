package com.example.buscaminas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TableroCompleto extends TableLayout {

	
	private BarraDeMenu Barra;
	private Tablero tablero;
	private TableRow fila1;
	
	public TableroCompleto(Context context, int fila, int columna,int Nminas,Observer O) {
		super(context);
		Barra=new BarraDeMenu(context);
		Barra.setObserver(O);
		tablero=new Tablero(context,fila,columna,Nminas);
		fila1= new TableRow(context);
		ArmarTablero();
		
	}
	
	public void ArmarTablero(){
		fila1.addView(Barra.getLayout());
		this.addView(fila1);
		this.addView(tablero.getLayout());
	}
	
	public void reiniciarJuego(){
		tablero.reiniciar();
	}
	
	
}