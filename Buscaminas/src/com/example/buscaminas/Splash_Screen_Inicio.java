package com.example.buscaminas;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Splash_Screen_Inicio extends Activity {
	private ImageView imagen_inicio;
	private AnimationDrawable animacion;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_intro);   
        imagen_inicio=(ImageView)findViewById(R.id.Imagen_Animacion);
        inicio();
	}
	
	public void inicio(){
		Animation animacion_iluminacion=AnimationUtils.loadAnimation(this,R.drawable.animacion_inicio);
		animacion_iluminacion.reset();
		imagen_inicio.startAnimation(animacion_iluminacion);
		animacion_iluminacion.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				empezarAnimacion();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	public void lanzarActividadPrincipal(){
		Intent mainIntent = new Intent().setClass(Splash_Screen_Inicio.this, MainActivity.class);
        startActivity(mainIntent);
        this.finish();
	}
	
	
	public void empezarAnimacion(){
		imagen_inicio.setBackgroundResource(R.drawable.animacion_intro);
        animacion=(AnimationDrawable)imagen_inicio.getBackground();
        animacion.start();
        lanzarActividadPrincipal();
	}
	
}
