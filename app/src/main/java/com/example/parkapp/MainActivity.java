package com.example.parkapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final MediaPlayer mp = new MediaPlayer();
    TextView toHorlogeTime,textDisplay;
    ImageButton buttonConfig,buttonTicketOut;
    ImageView m100,m200,m500,m1000,moneyEntry;
    int selectedMontant;
    float montant;
    TextView montantTotalView;

    ImageButton buttonCashOut;


    @Override
    protected void onResume() {
        super.onResume();
        resetInput();
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
                Intent intentConfig=new Intent(MainActivity.this,AdminLogin.class);
                startActivity(intentConfig);
                finish();
            }
        });

        buttonCashOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(montant != 0){
                    resetInput();
                    textDisplay.setText("Cash Out");

                    //playing drop sound effect
                    if(mp.isPlaying())
                    {
                        mp.stop();
                    }

                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("moneyreturns.mp3");
                        mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    textDisplay.setText("Box Empty");
                }

            }
        });

        buttonTicketOut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(montant < 1000){
                    textDisplay.setText("Minimaum 1000 TND");

                }else{
                    Intent ticketIntent=new Intent(MainActivity.this,TicketPrint.class);
                    ticketIntent.putExtra("montant",String.format("%.3f", montant/1000)+" TND ");


                    //passing dates to ticket activity
                    LocalDateTime now = LocalDateTime.now();
                    String hour;
                    if (now.getHour() <10)
                    {
                       hour="0"+now.getHour();
                    }else{
                        hour=now.getHour()+"";
                    }
                    String minute;
                    if (now.getMinute() <10)
                    {
                        minute ="0"+now.getMinute();
                    }else{
                        minute =now.getHour()+"";
                    }

                    String second ;
                    if (now.getSecond() <10)
                    {
                        second  ="0"+now.getSecond();
                    }else{
                        second  =now.getSecond()+"";
                    }


                    ticketIntent.putExtra("nowTime",now.getMonthValue()+"/"+now.getDayOfMonth()+"/"+now.getYear()
                    +"   "+hour+":"+minute+":"+second
                    );

                    //passing to date
                    int montantTime =(int) montant/10;
                    LocalDateTime to=now.plusMinutes(montantTime);

                    String thour;
                    if (to.getHour() <10)
                    {
                        thour="0"+to.getHour();
                    }else{
                        thour=to.getHour()+"";
                    }
                    String tminute;
                    if (to.getMinute() <10)
                    {
                        tminute ="0"+to.getMinute();
                    }else{
                        tminute =to.getHour()+"";
                    }

                    String tsecond ;
                    if (to.getSecond() <10)
                    {
                       tsecond  ="0"+to.getSecond();
                    }else{
                        tsecond  =to.getSecond()+"";
                    }

                    ticketIntent.putExtra("toTime",to.getMonthValue()+"/"+to.getDayOfMonth()+"/"+to.getYear()
                            +"   "+thour+":"+tminute+":"+tsecond);

                    //playing ticket printing sound effect
                    if(mp.isPlaying())
                    {
                        mp.stop();
                    }

                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("ticketprintingsound.mp3");
                        mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startActivity(ticketIntent);
                }
            }
        });




    }

         public void resetInput(){
                 montant=0;
                 toHorlogeTime.setText("+ 0H 0M");
                 montantTotalView.setText("0");
                 textDisplay.setText("Display");
         }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        int montant;
        public ChoiceTouchListener(int montant) {
            this.montant=montant;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float xCoord = event.getX();
            float yCoord = event.getY();

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

                    try {
                        mp.reset();
                        AssetFileDescriptor afd;
                        afd = getAssets().openFd("bagofcoins.mp3");
                        mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    montant=montant+selectedMontant;
                    String montantEnDinar=String.format("%.3f", montant/1000);
                    montantTotalView.setText(montantEnDinar);
                    textDisplay.setText(selectedMontant+" TND Coin Inserted");



                    break;


                case DragEvent.ACTION_DRAG_ENDED:
                    View view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);
                    int montantTime =(int) montant/10;
                    toHorlogeTime.setText("+ "+montantTime/60+"H "+ montantTime%60 + "M");
                    break;


            }
            return true;
        }
    }


}