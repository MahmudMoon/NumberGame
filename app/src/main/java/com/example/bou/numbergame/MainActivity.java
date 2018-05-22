package com.example.bou.numbergame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import java.nio.Buffer;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button start;
    CardView cardView;
    Button setting;
    Button save;
    Button cancel;
    CheckBox music_play,setVibreation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init_views();
        init_variabes();
        init_functions();
    }

    private void init_functions() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Gaming.class);
                startActivity(intent);
            }
        });
        cardView.setVisibility(View.INVISIBLE);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);

            }
        });
    }

    private void init_variabes() {


    }

    private void init_views() {
        start = (Button) findViewById(R.id.btn_start);
        cardView = (CardView) findViewById(R.id.cardView);
        setting = (Button) findViewById(R.id.btn_settings);
        save = (Button) findViewById(R.id.btn_save);
        cancel = (Button)findViewById(R.id.btn_cancel);
        music_play = (CheckBox)findViewById(R.id.chb_music);
        setVibreation = (CheckBox)findViewById(R.id.chb_vibration);
    }
}
