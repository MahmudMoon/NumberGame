package com.example.bou.numbergame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Gaming extends AppCompatActivity {

    Button[] btnCollection ;
    Button btn1,btn2,btn3,exit_;
    Button btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16;
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
   Button btnSelect[];
    ArrayList<Integer> Numbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming);

        init_views();
        init_variables();
        init_functions();
    }


    private void init_views() {
        btn1 = (Button)findViewById(R.id.btn_one);
        btn2 = (Button)findViewById(R.id.btn_two);
        btn3 = (Button)findViewById(R.id.btn_three);
        btn4 = (Button)findViewById(R.id.btn_four);
        btn5 = (Button)findViewById(R.id.btn_five);
        btn6 = (Button)findViewById(R.id.btn_six);
        btn7 = (Button)findViewById(R.id.btn_seven);
        btn8 = (Button)findViewById(R.id.btn_eight);
        btn9 = (Button)findViewById(R.id.btn_nine);
        btn10 = (Button)findViewById(R.id.btn_ten);
        btn11 = (Button)findViewById(R.id.btn_eleven);
        btn12 = (Button)findViewById(R.id.btn_twelve);
        btn13 = (Button)findViewById(R.id.btn_thirteen);
        btn14 = (Button)findViewById(R.id.btn_fourteen);
        btn15 = (Button)findViewById(R.id.btn_fifteen);
        btn16 = (Button)findViewById(R.id.btn_sixteen);



        current_Score = (TextView)findViewById(R.id.tv_Score);
        exit_ = (Button)findViewById(R.id.btn_close_game);
        highScore = (TextView)findViewById(R.id.tv_highestScore);
        Attempt = (TextView)findViewById(R.id.tv_attemp);
        restart = (ImageButton)findViewById(R.id.btn_restart);

    }

    private void init_variables() {
        restart.setVisibility(View.INVISIBLE);

        intent = getIntent();
        play_music = intent.getBooleanExtra("music",false);
        vibrate = intent.getBooleanExtra("vibration",false);
        Numbers = new ArrayList<>();

       btnCollection = new Button[16];
        btnSelect = new Button[4];

       btnCollection[0] = btn1;
       btnCollection[1] = btn2;
       btnCollection[2] = btn3;
        btnCollection[3] = btn4;
        btnCollection[4] = btn5;
        btnCollection[5] = btn6;
        btnCollection[6] = btn7;
        btnCollection[7] = btn8;
        btnCollection[8] = btn9;
        btnCollection[9] = btn10;
        btnCollection[10] = btn11;
        btnCollection[11] = btn12;
        btnCollection[12] = btn13;
        btnCollection[13] = btn14;
        btnCollection[14] = btn15;
        btnCollection[15] = btn16;




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
        showALLButtons();
        hideALLButtons();
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
                    if(Numbers.size()>0){
                        Numbers.clear();
                    }

                    Random random = new Random();
                    for (int i = 0; i < 4; i++) {
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
                          showButtons();
                          Attempt.setText(Integer.toString(attemptLeft));
                          for(int j =0;j<4;j++){
                              btnSelect[j].setText(Integer.toString(Numbers.get(j)));
                          }

                          //hideVisibleButtons();
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
                         hideVisibleButtons();
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

    private void hideVisibleButtons() {
        for(int i= 0;i<btnSelect.length;i++){
            btnSelect[i].setVisibility(View.INVISIBLE);
        }
    }

    private void hideALLButtons() {
        for(int i= 0;i<btnCollection.length;i++){
            if(btnCollection[i]!=null)
                btnCollection[i].setVisibility(View.INVISIBLE);
        }
    }

    private void showALLButtons() {
        for(int i= 0;i<btnCollection.length;i++){
            if(btnCollection[i]!=null)
                btnCollection[i].setVisibility(View.VISIBLE);
        }
    }

    public void  showButtons(){
       int fst = 0 + (int)(Math.random()*((3-0)+1));
       int sec = 4 + (int)(Math.random() * ((7 - 4) + 1));
       int thd = 8 + (int)(Math.random() * ((11 - 8) + 1));;
       int forth = 12 + (int)(Math.random() * ((15 - 12) + 1));

        Log.i("NumberOfBtnCollection",btnCollection.length+"");
        Log.i("NumberOfBtnCollection",fst+"");
        Log.i("NumberOfBtnCollection",sec+"");
        Log.i("NumberOfBtnCollection",thd+"");
        Log.i("NumberOfBtnCollection",forth+"");

        hideALLButtons();

       btnSelect[0] = btnCollection[fst];
       btnSelect[1] = btnCollection[sec];
       btnSelect[2] = btnCollection[thd];
       btnSelect[3] = btnCollection[forth];

       for(int i=0;i<4;i++){
           btnSelect[i].setVisibility(View.VISIBLE);
       }


    }



    private void closeGame() {
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        Toast.makeText(getApplicationContext(),Integer.toString(Score),Toast.LENGTH_SHORT).show();
    }

    public void btn_clicked(View view) {

        Button button = (Button)view;
        int number = Integer.parseInt(button.getText().toString());

        if(LowestValue==number){
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            Score+=1;
            current_Score.setText(Integer.toString(Score));
            button.setText("");
            button.setBackgroundResource(R.drawable.success);
        }else {
            Score-=1;
            if(Score<0){
                Score = 0;
            }
            current_Score.setText(Integer.toString(Score));
            setVibration();
        }
        hideVisibleButtons();


    }

    private void hideButtons() {
        for(int i=0;i<btnSelect.length;i++){
            btnSelect[i].setVisibility(View.INVISIBLE);
        }
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
