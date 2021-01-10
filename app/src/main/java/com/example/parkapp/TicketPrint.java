package com.example.parkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TicketPrint extends AppCompatActivity {

    TextView fromDateTextView,toDateTextView,montantTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_print);

        Bundle extras=getIntent().getExtras();
        String mont=extras.getString("montant");
        String nTime=extras.getString("nowTime");
        String tTime=extras.getString("toTime");

        montantTextView=(TextView) findViewById(R.id.montantTextView);
        fromDateTextView=(TextView) findViewById(R.id.fromDateTextView);
        toDateTextView=(TextView) findViewById(R.id.toDateTextView);

        montantTextView.setText(mont);

        fromDateTextView.setText(nTime);
        toDateTextView.setText(tTime);


    }
}