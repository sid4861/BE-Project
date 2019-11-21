package com.example.abc.homeautomation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import database.History;


/**
 * Created by abc on 1/10/16.
 */

public class History_Adaptor extends RecyclerView.Adapter<History_Adaptor.MyViewHolder>{
  Context context;
     List<History>  histories;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_historylayout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        final History histories1=histories.get(position);
        holder.textviewstatus.setText("Status:"+histories1.getStatus());
        holder.textview_time.setText("Time:"+histories1.getTime());
        holder.textviewlampname.setText("Lamp Name:"+histories1.getLampname());

    }

    @Override
    public int getItemCount() {
        return histories.size();
    }


    public History_Adaptor(List<History> histories, Context context) {
        this.context=context;
        this.histories=histories;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView textviewlampname,textview_time,textviewstatus;

        public MyViewHolder(View itemView) {
            super(itemView);


            textviewlampname=(TextView)itemView.findViewById(R.id.textviewlampname);
            textviewstatus=(TextView)itemView.findViewById(R.id.textviewstatus);
            textview_time=(TextView)itemView.findViewById(R.id.textview_time);

        }
    }
}
