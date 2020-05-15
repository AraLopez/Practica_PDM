package com.example.practicas_pdm;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    CountDownTimer a;
    boolean contando = false;
    MediaPlayer mp;
    boolean second = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        final Button inicio = findViewById(R.id.botonIniciar);
        final Button fin = findViewById(R.id.botonTerminar);
        final TextView msjPantalla = (TextView) findViewById(R.id.msjInicio);
        final TextView contador = (TextView) findViewById(R.id.numero);

        inicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (contando){
                    return;
                }
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                contando = true;
                mp = MediaPlayer.create(getApplicationContext(), R.raw.sonido);
                contador("Ejercicio", 1, 50000, "Descanso", 15000, msjPantalla, contador, mp);
            }
        });

        fin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (contando){
                    a.cancel();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    contando = false;
                    second = false;
                    mp.release();
                }
                msjPantalla.setText("Iniciar");
                contador.setText("00:00");
            }
        });
    }

    private void contador(final String name, final int num, final int t1, final String name2, final int t2, final TextView label1, final TextView label2, final MediaPlayer mp){
        label1.setText(name + " " + num);
        // Suena al inicio
        mp.start();
        a = new CountDownTimer(t1, 10) {
            public void onTick(long millisUntilFinished) {
                // Para que suene dos veces en el descanso
                if (name.equals("Descanso") && second){
                    if (!mp.isPlaying()){
                        mp.start();
                        second = false;
                    }
                }
                int r = (int) (millisUntilFinished % 1000);
                String cad = Integer.toString(r);
                String num = "";
                if (cad.length() >= 2){
                    num = cad.substring(0,2);
                }else{
                    num = "0" + cad.substring(0,1);
                }
                label2.setText(""+ millisUntilFinished / 1000 + ":" + num);
            }

            public void onFinish() {
                if (num < 10){
                    int b = num;
                    if (name.equals("Descanso")){
                        b = b + 1;
                    }else{
                        second = true;
                    }
                    contador(name2, b, t2, name, t1, label1, label2, mp);
                }else{
                    mp.start();
                    label1.setText("Iniciar");
                    label2.setText("00:00");
                    contando = false;
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    while(mp.isPlaying());
                    mp.release();
                }

            }
        };
        a.start();
    }
}