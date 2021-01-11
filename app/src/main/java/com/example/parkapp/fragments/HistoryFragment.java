package com.example.parkapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkapp.R;
import com.example.parkapp.adapters.HistoryRecyclerViewAdapter;
import com.example.parkapp.sqllite.DatabaseHandler;

import java.util.List;


public class HistoryFragment extends Fragment {
    RecyclerView historyRecycler;
    HistoryRecyclerViewAdapter historyAdapter;
    List historyList;
    Button clearButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_history, container, false);

        clearButton=v.findViewById(R.id.clear_history);
        DatabaseHandler db=(DatabaseHandler) new DatabaseHandler(this.getContext());

        historyRecycler =
                v.findViewById(R.id.history_recycler);

        LinearLayoutManager
                layoutManager = new
                LinearLayoutManager(getActivity());

        historyRecycler.setLayoutManager(layoutManager);



        historyList=db.getAllHistory();
        Log.d("history",historyList+"");

        historyAdapter = new HistoryRecyclerViewAdapter(getActivity(), historyList);
        historyRecycler.setAdapter(historyAdapter);

        Integer elementCount=historyList.size();
        if(elementCount ==0){
            clearButton.setVisibility(View.INVISIBLE);
        }


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();

                historyList=db.getAllHistory();
                historyAdapter = new HistoryRecyclerViewAdapter(getActivity(), historyList);
                historyRecycler.setAdapter(historyAdapter);
            }
        });


       return v;
    }
}