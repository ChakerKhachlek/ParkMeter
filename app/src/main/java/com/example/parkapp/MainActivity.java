package com.example.parkapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkapp.sqllite.DatabaseHandler;

import java.io.IOException;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

    final MediaPlayer mp = new MediaPlayer();
    TextView toHorlogeTime,textDisplay,countDown;
    ImageButton buttonConfig,buttonTicketOut;
    ImageView m100,m200,m500,m1000,moneyEntry;
    int selectedMontant;
    float montant;
    TextView montantTotalView;

    ImageButton buttonCashOut;

    public static final String STATUS_PREFERENCE = "bobinePreference";
    public static final String BOBINE_FIELD = "BOBINE";
    SharedPreferences sharedPreferences;

    CountDownTimer countDownTimer;

    @Override
    protected void onResume() {
        super.onResume();
        resetInput();
        checkBobine();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m100=(ImageView) findViewById(R.id.image100M);
        m200=(ImageView) findViewById(R.id.image200M);
        m500=(ImageView) findViewById(R.id.image500M);
        m1000=(ImageView) findViewById(R.id.image1000M);


        moneyEntry=(ImageView) findViewById(R.id.moneyEntry);
        montantTotalView =(TextView) findViewById(R.id.montant);
        toHorlogeTime =(TextView) findViewById(R.id.toHorlogeTime);
        textDisplay =(TextView) findViewById(R.id.textDisplay);
        countDown =(TextView) findViewById(R.id.countDown);


        buttonConfig=(ImageButton) findViewById(R.id.buttonConfig);
        buttonCashOut=(ImageButton) findViewById(R.id.buttonCashOut);
        buttonTicketOut=(ImageButton) findViewById(R.id.buttonTicketOut);



        moneyEntry.setOnDragListener(new ChoiceDragListner());

        m100.setOnTouchListener(new ChoiceTouchListener(100));
        m200.setOnTouchListener(new ChoiceTouchListener(200));
        m500.setOnTouchListener(new ChoiceTouchListener(500));
        m1000.setOnTouchListener(new ChoiceTouchListener(1000));


        buttonConfig.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(countDownTimer != null){
                    countDownTimer.cancel();
                }

                countDown.setVisibility(View.INVISIBLE);

                Intent intentConfig=new Intent(MainActivity.this,AdminLogin.class);
                startActivity(intentConfig);

            }
        });

        buttonCashOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(montant != 0){
                    resetInput();
                    textDisplay.setText("Cash Out");
                    if(countDownTimer != null){
                        countDownTimer.cancel();
                    }
                    countDown.setVisibility(View.INVISIBLE);
                    returnMoney();
                }else{
                    textDisplay.setText("Box Empty");
                    playSoundEffect("wrong_effect.mp3");
                }

            }
        });

        buttonTicketOut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(montant < 1000){
                    textDisplay.setText("Minimaum 1000 TND");
                    playSoundEffect("wrong_effect.mp3");

                }else{
                    if(countDownTimer != null){
                        countDownTimer.cancel();
                    }
                    textDisplay.setText("Park App");
                    countDown.setVisibility(View.INVISIBLE);

                    bobineTicketOut();

                    Intent ticketIntent=new Intent(MainActivity.this,TicketPrint.class);
                    ticketIntent.putExtra("montant",String.format("%.3f", montant/1000)+" TND ");



                    //passing dates to ticket activity

                    LocalDateTime now = LocalDateTime.now();

                    String month=now.getMonthValue()+"";
                    if (month.length() ==1)
                    {
                        month="0"+now.getMonthValue();
                    }else{

                    }

                    String day=now.getDayOfMonth()+"";
                    if (day.length() ==1)
                    {
                        day="0"+now.getDayOfMonth();
                    }else{

                    }

                    String year=now.getYear()+"";

                    String hour=now.getHour()+"";
                    if (hour.length() ==1)
                    {
                       hour="0"+now.getHour();
                    }else{

                    }

                    String minute=now.getMinute()+"";
                    if (minute.length() ==1)
                    {
                        minute ="0"+now.getMinute();
                    }else{

                    }

                    String second=now.getSecond()+"" ;
                    if (second.length() ==1)
                    {
                        second  ="0"+now.getSecond();
                    }else{

                    }

                    String nowTime=month+"/"+day+"/"+year
                            +"   "+hour+":"+minute+":"+second;
                    ticketIntent.putExtra("nowTime",nowTime
                    );

                    //passing to date
                    int montantTime =(int) montant/10;
                    LocalDateTime to=now.plusMinutes(montantTime);

                    String tmonth=to.getMonthValue()+"";
                    if (tmonth.length() ==1)
                    {
                        tmonth="0"+to.getMonthValue();
                    }

                    String tday=now.getDayOfMonth()+"";
                    if (tday.length() ==1)
                    {
                        tday="0"+to.getDayOfMonth();
                    }

                    String tyear=to.getYear()+"";


                    String thour=to.getHour()+"";
                    if (thour.length() ==1)
                    {
                        thour="0"+to.getHour();
                    }

                    String tminute=to.getMinute()+"";
                    if (tminute.length() == 1)
                    {
                        tminute ="0"+to.getMinute();
                    }

                    String tsecond =to.getSecond()+"" ;
                    if (tsecond.length() == 1)
                    {
                       tsecond  ="0"+to.getSecond();
                    }
                    String toTime=tmonth+"/"+tday+"/"+tyear
                            +"   "+thour+":"+tminute+":"+tsecond;

                    ticketIntent.putExtra("toTime",toTime);

                    //playing ticket printing sound effect
                    if(mp.isPlaying())
                    {
                        mp.stop();
                    }

                    playSoundEffect("ticketprintingsound.mp3");

                    DatabaseHandler db = new
                            DatabaseHandler(getApplicationContext());


                    db.insert(nowTime,toTime,montant);

                    startActivity(ticketIntent);
                }
            }
        });

    }

    public void bobineTicketOut(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(BOBINE_FIELD,sharedPreferences.getInt(BOBINE_FIELD, 0)-500);
        editor.commit();
    }

    public void bobineEmptyBlock(){
        resetInput();
        buttonTicketOut.setEnabled(false);
        buttonCashOut.setEnabled(false);
        m100.setEnabled(false);
        m200.setEnabled(false);
        m500.setEnabled(false);
        m1000.setEnabled(false);
        playSoundEffect("wrong_effect.mp3");

    }


    public void checkBobine(){

        sharedPreferences = getSharedPreferences(STATUS_PREFERENCE, Context.MODE_PRIVATE);


        if(sharedPreferences.getInt(BOBINE_FIELD, 0) == 0) {
            textDisplay.setText("Paper Roll Empty");

            bobineEmptyBlock();

        }
        else
         {
            resetInput();
             textDisplay.setText("Park App");
             buttonTicketOut.setEnabled(true);
             buttonCashOut.setEnabled(true);
             m100.setEnabled(true);
             m200.setEnabled(true);
             m500.setEnabled(true);
             m1000.setEnabled(true);

        }
    }
    public void playSoundEffect(String effectUrl){
        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = getAssets().openFd(effectUrl);
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnMoney(){
        textDisplay.setText("Cash Out");

        //playing drop sound effect
        if(mp.isPlaying())
        {
            mp.stop();
        }
        playSoundEffect("moneyreturns.mp3");

    }

         public void resetInput(){
                 montant=0;
                 toHorlogeTime.setText("+ 0H 0M");
                 montantTotalView.setText("0");

         }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        int montant;
        public ChoiceTouchListener(int montant) {
            this.montant=montant;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ClipData data = ClipData.newPlainText("","");
                    View.DragShadowBuilder shadowBuilder=new View.DragShadowBuilder(v);
                    v.setVisibility(View.INVISIBLE);
                    selectedMontant=montant;

                    v.startDrag(data,shadowBuilder,v,0);

                    return true;

                case MotionEvent.ACTION_UP:

                    return true;

                default:

                    return false;


            }


        }
    }

    private class ChoiceDragListner implements View.OnDragListener{

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch(event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    Log.d("test","Dropped");

                    //playing drop sound effect
                    if(mp.isPlaying())
                    {
                        mp.stop();
                    }
                    playSoundEffect("bagofcoins.mp3");

                    montant=montant+selectedMontant;
                    String montantEnDinar=String.format("%.3f", montant/1000);
                    montantTotalView.setText(montantEnDinar);
                    textDisplay.setText(selectedMontant+" TND Coin Inserted");


                    countDown.setVisibility(View.VISIBLE);
                    if(countDownTimer != null){
                        countDownTimer.cancel();
                    }
                    countDownTimer = new CountDownTimer(10000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            countDown.setText("" + millisUntilFinished / 1000);
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            countDown.setVisibility(View.INVISIBLE);

                            resetInput();
                            returnMoney();
                        }
                    }.start();


                    break;


                case DragEvent.ACTION_DRAG_ENDED:
                    View view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);
                    if(montant>=1000){
                        int montantTime =(int) montant/10;
                        toHorlogeTime.setText("+ "+montantTime/60+"H "+ montantTime%60 + "M");
                    }




                    break;


            }
            return true;
        }
    }


}