package com.example.parkapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkapp.R;
import com.example.parkapp.models.HistoryTicket;
import com.example.parkapp.sqllite.DatabaseHandler;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryViewHolder>{
    Context context;
    List<HistoryTicket> HistoryList;

    public HistoryRecyclerViewAdapter(Context context, List<HistoryTicket> HistoryList){
        this.context=context;
        this.HistoryList=HistoryList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryRecyclerViewAdapter.HistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.history_recycler_view_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Log.d("aaa",HistoryList.get(position)+"");
        holder.historyItem.setText("From  "+HistoryList.get(position).getDatefrom()
                +System.getProperty ("line.separator")+
                "To  "+HistoryList.get(position).getDateTo()+
                System.getProperty ("line.separator")+
                 "Price   "+HistoryList.get(position).getMontant());

        holder.historyItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertbox=new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage("Remove from history");
                alertbox.setTitle("remove");

                alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(HistoryList.get(position));

                    }

                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertbox.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return HistoryList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView historyItem;
        Button buttonClear;



        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            buttonClear=(Button) itemView.findViewById(R.id.clear_history);

            historyItem=itemView.findViewById(R.id.history_item);
            historyItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }

    public void removeItem(HistoryTicket item){

        int currPosition = HistoryList.indexOf(item);
        HistoryList.remove(currPosition);
        DatabaseHandler db=(DatabaseHandler) new DatabaseHandler(context);
        db.deleteSingle(item.getId());

        notifyDataSetChanged();



    }
}
