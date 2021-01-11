package com.example.parkapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parkapp.R;
import com.example.parkapp.sqllite.DatabaseHandler;


public class StatisticsFragment extends Fragment {

  TextView ticketCountText,totalProfitText,topTicketPriceText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_statistics, container, false);

        ticketCountText=v.findViewById(R.id.ticketCountText);
        totalProfitText=v.findViewById(R.id.totalProfitText);
        topTicketPriceText=v.findViewById(R.id.topTicketPriceText);

        DatabaseHandler db=new DatabaseHandler(getActivity());
        ticketCountText.setText(db.getTicketsCount()+"");
        totalProfitText.setText(db.getTotalProfit()+"");
        topTicketPriceText.setText(db.mostExpensiveTicket()+"");



        return v;
    }
}