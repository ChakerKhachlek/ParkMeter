package com.example.parkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.res.AssetFileDescriptor;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final MediaPlayer mp = new MediaPlayer();
    ImageView m100,m200,m500,m1000,moneyEntry;
    int selectedMontant;
    float montant;
    TextView montantTotalView;


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

        moneyEntry.setOnDragListener(new ChoiceDragListner());

        m100.setOnTouchListener(new ChoiceTouchListener(100));
        m200.setOnTouchListener(new ChoiceTouchListener(200));
        m500.setOnTouchListener(new ChoiceTouchListener(500));
        m1000.setOnTouchListener(new ChoiceTouchListener(1000));




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

                    break;


                case DragEvent.ACTION_DRAG_ENDED:
                    View view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);
                    break;


            }
            return true;
        }
    }


}