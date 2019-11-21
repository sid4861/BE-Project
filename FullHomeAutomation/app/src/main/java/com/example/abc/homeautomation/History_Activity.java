package com.example.abc.homeautomation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import database.History;
import database.SQLiteAdapter;

public class History_Activity extends Activity {
    SQLiteAdapter sqLiteAdapter;
     List<History> notifications=new ArrayList<>();
    RecyclerView reclyerview;
    History_Adaptor parkingHistoryAdaptor;
    private ProgressDialog pDialog;
    Button clearhistory;

 int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

        sqLiteAdapter=new SQLiteAdapter(getApplicationContext());

        clearhistory= (Button) findViewById(R.id.btnhistory);


        reclyerview=(RecyclerView)findViewById(R.id.reclyerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reclyerview.setLayoutManager(mLayoutManager);
        reclyerview.setItemAnimator(new DefaultItemAnimator());

        parkingHistoryAdaptor=new History_Adaptor(notifications,this);
        reclyerview.setAdapter(parkingHistoryAdaptor);

       // GetListTask();

        clearhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteAdapter.openToWrite();
                sqLiteAdapter.deletehistory();
                sqLiteAdapter.close();

                notifications.clear();
                parkingHistoryAdaptor.notifyDataSetChanged();

            }
        });

        sqLiteAdapter.openToRead();
        ArrayList<History> notifications1=sqLiteAdapter.retrieveAllHistorylist();

        for(int i=0;i<notifications1.size();i++)
        {

            notifications.add(new History(i,notifications1.get(i).getLampname()+"",notifications1.get(i).getTime()+"",notifications1.get(i).getStatus()+""));

        }

        sqLiteAdapter.close();

    }







}
