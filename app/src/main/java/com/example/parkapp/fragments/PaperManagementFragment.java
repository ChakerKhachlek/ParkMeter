package com.example.parkapp.fragments;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkapp.R;

import java.io.IOException;


public class PaperManagementFragment extends Fragment {

    MediaPlayer mp=new MediaPlayer();
    ImageView fullPaper;
    ImageView statusPaper;
    TextView bobineSizeText;
    int bobineSize;


    public static final String STATUS_PREFERENCE = "bobinePreference";
    public static final String BOBINE_FIELD = "BOBINE";
    SharedPreferences sharedPreferences;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_paper_management, container, false);

        sharedPreferences = getActivity().getSharedPreferences(STATUS_PREFERENCE, Context.MODE_PRIVATE);

        fullPaper=(ImageView) v.findViewById(R.id.bobineFull);
        fullPaper.setOnTouchListener(new OnTouchListener());

        bobineSizeText =(TextView) v.findViewById(R.id.bobineSize);

        statusPaper=(ImageView) v.findViewById(R.id.bobineStatus);
        statusPaper.setOnDragListener(new OnDragListner());

        checkBobineStatus();

        return v;
    }

    private void checkBobineStatus(){

       bobineSize=sharedPreferences.getInt(BOBINE_FIELD, 0);

        if(bobineSize == 0) {
            bobineSizeText.setText(bobineSize+" CM");
            statusPaper.setBackgroundResource(R.mipmap.empty_roll);

        }
        else
        {
            bobineSizeText.setText(bobineSize+" CM");
            statusPaper.setBackgroundResource(R.mipmap.paper);

        }

    }

    private class OnTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ClipData data = ClipData.newPlainText("","");
                    View.DragShadowBuilder shadowBuilder=new View.DragShadowBuilder(v);
                    v.setVisibility(View.INVISIBLE);
                    v.startDrag(data,shadowBuilder,v,0);
                    return true;
                case MotionEvent.ACTION_UP:
                    return true;
                default:
                    return false;


            }
        }
    }

    private class OnDragListner implements View.OnDragListener{


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
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(BOBINE_FIELD,1000);
                    editor.commit();
                    checkBobineStatus();
                 //playing paper change sound effect
                    if(mp.isPlaying())
                    {
                        mp.stop();
                    }

                    playSoundEffect("paper_change.mp3");

                    break;


                case DragEvent.ACTION_DRAG_ENDED:
                    View view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);

                    break;


            }
            return true;
        }
    }

    public void playSoundEffect(String effectUrl){
        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = getActivity().getAssets().openFd(effectUrl);
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}