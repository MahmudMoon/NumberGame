package com.example.bou.numbergame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.nio.Buffer;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button start;
    CardView cardView;
    Button setting;
    Button save;
    Button cancel;
    CheckBox music_play,setVibreation;
    boolean play_music,vibrate;
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
                intent.putExtra("music",play_music);
                intent.putExtra("vibration",vibrate);
                startActivity(intent);
            }
        });
        cardView.setVisibility(View.INVISIBLE);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE );
                getValuesFromSharedPref();
            }
        });

        music_play.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    play_music = true;
                }else
                {
                    play_music = false;
                }
            }
        });

        setVibreation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    vibrate = true;
                }else
                    vibrate = false;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValueToSharedPref();
                cardView.setVisibility(View.INVISIBLE);
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                getValuesFromSharedPref();
                music_play.setChecked(play_music);
                setVibreation.setChecked(vibrate);
                cardView.setVisibility(View.INVISIBLE);
            }
        });
    }



    private void init_variabes() {
        getValuesFromSharedPref();
    }


    private void setValueToSharedPref(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("Settings",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("music",play_music);
        editor.putBoolean("vibration",vibrate);
        editor.commit();
    }

    private void getValuesFromSharedPref() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Settings",MODE_PRIVATE);
        play_music = sharedPreferences.getBoolean("music",false);
        vibrate =  sharedPreferences.getBoolean("vibration",false);
        music_play.setChecked(play_music);
        setVibreation.setChecked(vibrate);
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
