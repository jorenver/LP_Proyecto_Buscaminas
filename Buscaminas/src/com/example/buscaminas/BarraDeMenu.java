package com.example.buscaminas;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class BarraDeMenu extends View {


	private ImageButton Cara;
	private TableLayout layout;
	private TableRow fila;
	
	public BarraDeMenu(Context C){
		super(C);
		Cara=new ImageButton(C);
		layout=new TableLayout(C);
		fila=new TableRow(C);
		fila.addView(Cara);
		layout.addView(fila);
		Cara= new ImageButton(C);
		Cara.setBackgroundResource(R.drawable.ic_launcher);

	}
	
	public TableLayout getLayout(){
		return layout;
	}
	
}
