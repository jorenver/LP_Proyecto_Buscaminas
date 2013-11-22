package com.example.buscaminas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TableroCompleto extends View {

	
	private BarraDeMenu Barra;
	private Tablero tablero;
	private TableLayout layout;
	private TableRow fila1;
	
	public TableroCompleto(Context context, int fila, int columna,int Nminas,Observer O) {
		super(context);
		Barra=new BarraDeMenu(context);
		Barra.setObserver(O);
		tablero=new Tablero(context,fila,columna,Nminas);
		layout=new TableLayout(context);
		fila1= new TableRow(context);
		ArmarTablero();
		
	}
	
	public void ArmarTablero(){
		fila1.addView(Barra.getLayout());
		layout.addView(fila1);
		layout.addView(tablero.getLayout());
	}
	
	public TableLayout getLayout(){
		return layout;
	}
	
	
}