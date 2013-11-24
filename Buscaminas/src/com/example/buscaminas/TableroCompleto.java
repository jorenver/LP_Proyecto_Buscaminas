package com.example.buscaminas;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
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
		//el reloj observa al tablero para saber cuando reiniciarse , detenerce , o encerarse
		tablero.setObserver(Barra.getObserverRelor());
		tablero.setObserverCara(Barra.getCaraObserver());
		fila1= new TableRow(context);
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
	@SuppressLint("NewApi")
	OnTouchListener ListenerTocar = new OnTouchListener(){

		@Override
		public boolean onTouch(View view, MotionEvent event) {
	
			if (MotionEvent.ACTION_DOWN==event.getAction()){
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				return true;
			}
			return false;
		}

	};
	
	
	@SuppressLint("NewApi")
	OnDragListener ListenerArrastar = new OnDragListener(){

		@Override
		public boolean onDrag(View v, DragEvent event) {
			
			switch (event.getAction()) {
		    case DragEvent.ACTION_DRAG_STARTED:
		        //no se define
		        break;
		    case DragEvent.ACTION_DRAG_ENTERED:
		        //no se define
		        break;
		    case DragEvent.ACTION_DRAG_EXITED:
		        //no se define
		        break;
		    case DragEvent.ACTION_DROP:
		        //cuando se suelta la vista
		    	Object O;
		    	O=event.getLocalState();
		    	if(O instanceof Celda  )
		    	{
		    		Celda c=(Celda) O;
		    		c.setText("B");
		    	}
		        break;
		    case DragEvent.ACTION_DRAG_ENDED:
		        //no se define
		        break;
		    default:
		        break;
		}
			return false;
		}

		
	};
}