package com.example.buscaminas;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
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
	
	public TableroCompleto(Context context, int fila, int columna,int Nminas,Observer Reinicio) {
		super(context);
		C=context;
		Barra=new BarraDeMenu(context);
		Barra.setObserver(Reinicio);
		Barra.getBandera().setOnTouchListener(ListenerTocar);
		tablero=new Tablero(context,fila,columna,Nminas);
		//el reloj observa al tablero para saber cuando reiniciarse , detenerce , o encerarse
		
		tablero.setObserver(Barra.getObserverRelor());//reloj
		tablero.setObserverCara(Barra.getCaraObserver());//cara
		tablero.setObserverTableroCompleto(TabCompObserver);//tab
		tablero.setOnDrag(ListenerArrastar);
		
		
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
	

	OnTouchListener ListenerTocar = new OnTouchListener(){
	
		@SuppressLint("NewApi")
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
	OnDragListener ListenerArrastar = new OnDragListener(){//manejador del evento para las celdas
	 
		@SuppressLint("NewApi")
		public boolean onDrag(View view, DragEvent event) {		
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
	             Celda c=(Celda) view;
	             if(c.getEstado()==EstadoCelda.CUBIERTA){
	            	 c.SetEstado(EstadoCelda.BANDERA);
	            	 c.setBackgroundResource(R.drawable.bandera);
	            	 if(event.getLocalState() instanceof Celda){//si la vista del drag fue una celda
	            		Celda celda_origen=(Celda)event.getLocalState();
	            		actualizarCelda(celda_origen);//actualiza el estado de la celda_origen
	            	 }
	            	 listenerQuitarBandera();
	            	 tablero.update(c);	             
	             }
	             break;
	         case DragEvent.ACTION_DRAG_ENDED:
	             //no se define
	             break;
	         default:
	             break;
			}
			return true;
		}
	   };
	   
	
	@SuppressLint("NewApi")
	public void listenerQuitarBandera(){
		this.setOnDragListener(new OnDragListener(){
			@SuppressLint("NewApi")
			@Override
			public boolean onDrag(View view, DragEvent event) {
				Celda celda_actual=null;
				switch (event.getAction()) {
			         case DragEvent.ACTION_DRAG_STARTED:
			             break;
			         case DragEvent.ACTION_DRAG_ENTERED:
			             break;
			         case DragEvent.ACTION_DRAG_EXITED:
			             break;
			         case DragEvent.ACTION_DROP:
			        	 if(event.getLocalState() instanceof Celda){
				        	 celda_actual=(Celda)event.getLocalState();//origen del drag event
				        	 if(celda_actual!=null){
				        		 actualizarCelda(celda_actual);//actualiza el nuevo estado de la celda
				        	 }
			        	 }
			        	 break;
			         case DragEvent.ACTION_DRAG_ENDED:
			             break;
			         default:
			             break;
				}
				return true;
			}
		});
		
	}   
	   
	public void actualizarCelda(Celda celda_actual){
		 celda_actual.setEnabled(true);//setea background
	   	 celda_actual.SetEstado(EstadoCelda.CUBIERTA);// ya no tiene bandera
	   	 tablero.setCeldaOnTouchListener(celda_actual);//celda ya no sea arrastrable
	}
	
}