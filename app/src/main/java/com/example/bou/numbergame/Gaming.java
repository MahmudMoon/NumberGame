package com.example.bou.numbergame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Gaming extends AppCompatActivity {

    Button[] btnCollection ;
    Button btn1,btn2,btn3,exit_;
    int LowestValue;
    int Score = 0;
    TextView current_Score,highScore;
    SharedPreferences sharedPreferences;
    int highestScore;
    TextView Attempt;
    MediaPlayer mediaPlayer;
    ImageButton restart;
    boolean play_music,vibrate;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming);

        init_views();
        init_variables();
        init_functions();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_gaming);
    }

    private void init_views() {
        btn1 = (Button)findViewById(R.id.button);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);
        current_Score = (TextView)findViewById(R.id.tv_Score);
        exit_ = (Button)findViewById(R.id.btn_close_game);
        highScore = (TextView)findViewById(R.id.tv_highestScore);
        Attempt = (TextView)findViewById(R.id.tv_attemp);
        restart = (ImageButton)findViewById(R.id.btn_restart);

    }

    private void init_variables() {
        intent = getIntent();
        play_music = intent.getBooleanExtra("music",false);
        vibrate = intent.getBooleanExtra("vibration",false);

       btnCollection = new Button[3];
       btnCollection[0] = btn1;
       btnCollection[1] = btn2;
       btnCollection[2] = btn3;
       sharedPreferences = getSharedPreferences("Highest Score",MODE_PRIVATE);

    }

    private void init_functions() {

        prepareingGame();

        if(play_music)
            playBackMusic();

        getHighestScore();

        exit_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighestScore(Score);
                if(play_music && mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                finishAffinity();
            }
        });
        startGame();

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init_functions();
            }
        });
    }

    private void prepareingGame() {
        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);
        Score = 0;
        current_Score.setText(Integer.toString(Score));
    }

    private void playBackMusic() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                  mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.back);
                  mediaPlayer.start();

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void getHighestScore() {
       highestScore =  sharedPreferences.getInt("score",0);
       highScore.setText(Integer.toString(highestScore));
    }

    private void startGame() {
             restart.setVisibility(View.INVISIBLE);
           Runnable r = new Runnable() {
               @Override
               public void run() {
                for(int gameLoop = 1; gameLoop<=10;gameLoop++) {

                    final int attemptLeft = 10 - gameLoop;

                    int previousNum = 11;
                    final ArrayList<Integer> Numbers = new ArrayList<>();
                    Random random = new Random();
                    for (int i = 0; i < 3; i++) {
                        int number = random.nextInt(10) + 1;
                        Integer integer = new Integer(number);

                        while (Numbers.indexOf(integer)>=0){
                             number = random.nextInt(10) + 1;
                             integer = new Integer(number);
                        }

                        if (number < previousNum) {
                            previousNum = number;
                        }
                       Numbers.add(number);
                    }
                    LowestValue = previousNum;

                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          Attempt.setText(Integer.toString(attemptLeft));
                             btn1.setVisibility(View.VISIBLE);
                             btn2.setVisibility(View.VISIBLE);
                             btn3.setVisibility(View.VISIBLE);
                          for(int j =0;j<3;j++){
                              btnCollection[j].setText(Integer.toString(Numbers.get(j)));

                          }
                      }
                  });
                    SystemClock.sleep(2000);
                }

                    if(play_music)
                        mediaPlayer.stop();

                 //  closeGame();
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         btn1.setVisibility(View.INVISIBLE);
                         btn2.setVisibility(View.INVISIBLE);
                         btn3.setVisibility(View.INVISIBLE);
                         restart.setVisibility(View.VISIBLE);
                         if(highestScore<Score) {
                             setHighestScore(Score);
                             highScore.setText(Integer.toString(Score));
                         }

                         Toast.makeText(getApplicationContext(),Integer.toString(Score),Toast.LENGTH_SHORT).show();
                     }
                 });

               }

           };

           Thread thread = new Thread(r);
           thread.start();



    }



    private void closeGame() {
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        Toast.makeText(getApplicationContext(),Integer.toString(Score),Toast.LENGTH_SHORT).show();
    }

    public void btn_clicked(View view) {

        int id = view.getId();
        switch (id){
            case R.id.button:
                int valueOftheButton1 = Integer.parseInt(btn1.getText().toString());
                if(LowestValue==valueOftheButton1){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    Score+=1;
                    current_Score.setText(Integer.toString(Score));
                }else {
                    Score-=1;
                    if(Score<0){
                        Score = 0;
                    }
                    current_Score.setText(Integer.toString(Score));
                    setVibration();
                }
                hideButtons();

                break;
            case R.id.button2:
                int valueOftheButton2 = Integer.parseInt(btn2.getText().toString());
                if(LowestValue==valueOftheButton2){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    Score+=1;
                    current_Score.setText(Integer.toString(Score));
                }else {
                    Score-=1;
                    if(Score<0){
                        Score = 0;
                    }
                    current_Score.setText(Integer.toString(Score));
                    setVibration();

                }

                hideButtons();
                break;
            case R.id.button3:
                int valueOftheButton3 = Integer.parseInt(btn3.getText().toString());
                if(LowestValue==valueOftheButton3){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    Score+=1;
                    current_Score.setText(Integer.toString(Score));
                }else {
                    Score-=1;
                    if(Score<0){
                        Score = 0;
                    }
                    current_Score.setText(Integer.toString(Score));
                    setVibration();

                }

                hideButtons();
                break;
        }

    }

    private void hideButtons() {
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
    }

    private void setVibration() {
        if(vibrate) {
            Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }else {
            Toast.makeText(getApplicationContext(),"Vibration Off",Toast.LENGTH_SHORT).show();
        }
    }

    public void setHighestScore(int score) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("score",score);
        editor.commit();
    }
}
