package com.example.parkapp;

import android.content.Context;
import android.widget.Toast;

public class Message {

    public static void longMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void shortMessage(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();

    }

}
