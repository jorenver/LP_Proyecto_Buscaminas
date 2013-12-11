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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class TableroCompleto extends TableLayout {
	private BarraDeMenu Barra;
	private Tablero tablero;
	private TableRow fila1;
	private Nivel nivel;
	private TopManager Top;
	private Context C;
	private ScrollView scroll;
	
	public TableroCompleto(Context context, int fila, int columna,int Nminas,Observer Reinicio) {
		super(context);
		C=context;
		Barra=new BarraDeMenu(context);
		Barra.setObserver(Reinicio);
		Barra.getBandera().setOnTouchListener(ListenerTocar);
		tablero=new Tablero(context,fila,columna,Nminas);
		nivel=getNivel(Nminas);
		//el reloj observa al tablero para saber cuando reiniciarse , detenerce , o encerarse
		scroll=new ScrollView(C);
		tablero.setObserver(Barra.getObserverRelor());//reloj
		tablero.setObserverCara(Barra.getCaraObserver());//cara
		tablero.setObserverTableroCompleto(TabCompObserver);//tab
		tablero.setOnDrag(ListenerArrastar);
		tablero.setOnTouch(ListenerTocar);
		fila1= new TableRow(context);
		Top= new TopManager(context);
		fila1.setGravity(Gravity.CENTER);
		this.setBackgroundResource(R.drawable.fondo);	
		int num= TableroCompleto.this.tablero.getCantMinas(); 
		TableroCompleto.this.Barra.getContbandera().setText(String.valueOf(num));
		ArmarTablero();	
	}

	public void ArmarTablero(){ 
		fila1.addView(Barra);
		this.addView(fila1);
		scroll.addView(tablero.getLayout());
		this.addView(scroll);
		//this.addView(tablero.getLayout());
	}
	
	public void reiniciarJuego(){
		tablero.reiniciar();
	}
	
	Observer TabCompObserver= new Observer(){
		@Override
		public void update() {
			long time=Barra.getTiempo();
			Top.setNivel(nivel);
			boolean entraTop=Top.validarTiempo(Jugador.aproximarTime(time));
			Toast toast = Toast.makeText(C, "Ganaste!!!", Toast.LENGTH_SHORT);
			toast.show();
			if (entraTop){
				Top.agregarJugador(time);
			}
		}

		@Override
		public void update(Object o) {
		}
		
	};
	
	 public Nivel getNivel(int Nminas){
		 if (Nminas==10){
		            return Nivel.PRINCIPIANTE;
		 }else if(Nminas==40){
		            return Nivel.INTERMEDIO;
		 }else if (Nminas==99){
		            return Nivel.EXPERTO;
		 }
		 return null;
	 }

	OnTouchListener ListenerTocar = new OnTouchListener(){
	
		@SuppressLint("NewApi")
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// para el caso que sea una celda el que genera el evento
			if((view instanceof Celda ) ){
				Celda a;
				a=(Celda)view;
				//solo la deja arrastar si tiene bandera
				if(a.getEstado()==EstadoCelda.BANDERA){
					if (MotionEvent.ACTION_DOWN==event.getAction()){
				         ClipData data = ClipData.newPlainText("", "");
				         view.setBackgroundResource(R.drawable.bandera); 
				         DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
				         view.startDrag(data, shadowBuilder, view, 0);
				         a.setEnabled(true);//setea background
				         a.SetEstado(EstadoCelda.CUBIERTA);
				         int num;
				         num=TableroCompleto.this.tablero.getCantMinas()-TableroCompleto.this.tablero.cantBanderasTablero();
				         TableroCompleto.this.Barra.getContbandera().setText(String.valueOf(num));
				         tablero.update(null);//informar el tablero de la celda que se le quito la bandera
				         return true;
				       }
				}
			}else{//es una bandera
				if (MotionEvent.ACTION_DOWN==event.getAction()){
					ClipData data = ClipData.newPlainText("", "");
			        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			        view.startDrag(data, shadowBuilder, view, 0);
			        return true;
			    }
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
	             break;
	         case DragEvent.ACTION_DRAG_ENTERED:
	             break;
	         case DragEvent.ACTION_DRAG_EXITED:
	             //no se define
	             break; 
	         case DragEvent.ACTION_DROP:
	             //cuando se suelta la vista
	             Celda c=(Celda) view;
	             if(c.getEstado()==EstadoCelda.CUBIERTA){
	            	 c.SetEstado(EstadoCelda.BANDERA);
	            	 c.setBackgroundResource(R.drawable.boton_bandera);
	            	 if(event.getLocalState() instanceof Celda){//si la vista del drag fue una celda
	            		Celda celda_origen=(Celda)event.getLocalState();
	            		actualizarCelda(celda_origen);//actualiza el estado de la celda_origen
	            	 }
	            	 int num;
			         num=TableroCompleto.this.tablero.getCantMinas()-TableroCompleto.this.tablero.cantBanderasTablero();
			         TableroCompleto.this.Barra.getContbandera().setText(String.valueOf(num));
	            	 tablero.update(null);//informa al tablero que se coloco una bandera	             
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
	   
	   
	public void actualizarCelda(Celda celda_actual){
		 celda_actual.setEnabled(true);//setea background
	   	 celda_actual.SetEstado(EstadoCelda.CUBIERTA);// ya no tiene bandera
	   	 
	}
	
	public  BarraDeMenu getBarra(){
		return Barra;
	}
	
		

	
}