package com.example.buscaminas;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomDialog extends Dialog implements OnClickListener {
	private Button Aceptar;
	private TextView Felicitacion;
	private EditText Nombre;
	private long time;
	private String nombreJug;
	private Observer Top;

	
	public CustomDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.custom_dialog_jugador);
		setTitle("Tiempo Record");
		Aceptar=(Button)findViewById(R.id.Aceptar);
		Felicitacion=(TextView)findViewById(R.id.Felicitacion);
		Nombre=(EditText)findViewById(R.id.NombreJug);
		Aceptar.setText("Aceptar");
		Aceptar.setOnClickListener(this);
		Felicitacion.setText("Felicidades, gracias a ti Terminator no piso una mina y no murió, ingresas al top");
	}

	public void mostrar(long time){
		setTime(time);
		show();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		getNombre();
		Jugador J= crearJugador();
		Top.update(J);
		dismiss();
		Nombre.setText("");
	}

	public Jugador crearJugador(){
		return new Jugador(nombreJug,time);
		
	}
	
	public void getNombre(){
		nombreJug=(Nombre.getText()).toString();
		Log.i("getNombre",nombreJug);
	}
	
	public void setTime(long time){
		this.time=time;
	}
	
	public void setObserver(Observer O){
		Top=O;
	}

}
