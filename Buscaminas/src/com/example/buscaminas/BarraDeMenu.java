package com.example.buscaminas;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class BarraDeMenu {


	private ImageButton Cara;
	private TableLayout layout;
	private TableRow fila;
	private Observer Inicio;
	Context C;
	public BarraDeMenu(Context C){
		Cara=new ImageButton(C);
		layout=new TableLayout(C);
		fila=new TableRow(C);
		Cara= new ImageButton(C);
		Cara.setBackgroundResource(R.drawable.cara);
		Cara.setOnClickListener(Reiniciar);
		fila.addView(Cara);
		layout.addView(fila);
		this.C=C;
		
	}
	
	public TableLayout getLayout(){
		return layout;
	}
	

	OnClickListener Reiniciar =new  OnClickListener(){
		public void onClick(View arg0) {
				Inicio.update();
		}
	};
	

	
	public void setObserver(Observer O){
		Inicio=O;
	}
	
}
