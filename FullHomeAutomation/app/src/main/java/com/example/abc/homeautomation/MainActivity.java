package com.example.abc.homeautomation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import database.SQLiteAdapter;


public class MainActivity extends Activity {

    private Switch light1,light2;
    SQLiteAdapter dbhandler;

    String result;
    String time,room,status;
    ProgressDialog pDialog;
  Button buttonHistory;
    String light1_Status,light2_Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbhandler=new SQLiteAdapter(getApplicationContext());

        dbhandler.openToRead();
        light1_Status=  dbhandler.getLight1Status();
        light2_Status=  dbhandler.getLight2Status();
        light1 = (Switch) findViewById(R.id.light1);
        light2 = (Switch) findViewById(R.id.light2);
        dbhandler.close();

        if(light1_Status.equals("-1"))
        {

            dbhandler.openToWrite();
            dbhandler.insertStatus("1", "OFF");
            dbhandler.close();

            /*String time=getdate();
            dbhandler.openToWrite();
            dbhandler.insertHistory("Drawing room",time,"OFF");
            dbhandler.close();
*/
        }else{
            if(light1_Status.equals("ON"))
            {
                light1.setChecked(true);
            }else{
                light1.setChecked(false);
            }


        }

        if(light2_Status.equals("-1"))
        {
            dbhandler.openToWrite();
            dbhandler.insertStatus("2","OFF");
            dbhandler.close();

/*

            String time=getdate();
            dbhandler.openToWrite();
            dbhandler.insertHistory("Dining room",time,"OFF");
            dbhandler.close();
*/

        }else{

            if(light2_Status.equals("ON"))
            {
                light2.setChecked(true);
            }else{
                light2.setChecked(false);
            }

        }

        buttonHistory=(Button)findViewById(R.id.buttonHistory);
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),History_Activity.class);
                startActivity(i);

            }
        });





        light1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {


                if (!isChecked) {
                     //off
                  //  new SendSMS(ProjectConfig.MobilenNumber,"@1F").execute();

                    time=getdate();
                    room="Drawing room";
                    status="OFF";
                    makestatusTask(time,"Drawing","OFF");
                    dbhandler.openToWrite();
                    dbhandler.update_light(1, "OFF");
                    dbhandler.close();
                    dbhandler.openToWrite();
                    dbhandler.insertHistory(room,time,status);
                    dbhandler.close();



                } else {
                    //on
                   // new SendSMS(ProjectConfig.MobilenNumber,"@1O").execute();


                    String time=getdate();

                    room="Drawing room";
                    status="ON";
                    makestatusTask(time,"Drawing","ON");
                    dbhandler.openToWrite();
                    dbhandler.update_light(1,"ON");
                    dbhandler.close();

                    dbhandler.openToWrite();
                    dbhandler.insertHistory(room,time,status);
                    dbhandler.close();

                }

            }
        });


        light2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                System.out.println("## isChecked:"+isChecked);
                if (!isChecked) {
                    //new SendSMS(ProjectConfig.MobilenNumber,"@2F").execute();

                     time=getdate();
                    room="Dining room";
                    status="OFF";
                    makestatusTask(time,"Dining","OFF");
                    dbhandler.openToWrite();
                    dbhandler.update_light(2,"OFF");
                    dbhandler.close();
                    dbhandler.openToWrite();
                    dbhandler.insertHistory(room,time,status);
                    dbhandler.close();

                } else {
                  //  new SendSMS(ProjectConfig.MobilenNumber,"@2O").execute();


                    time=getdate();
                    room="Dining room";
                    status="ON";
                    makestatusTask(time,"Dining","ON");
                    dbhandler.openToWrite();
                    dbhandler.update_light(2,"ON");
                    dbhandler.close();

                    dbhandler.openToWrite();
                    dbhandler.insertHistory(room,time,status);
                    dbhandler.close();


                }

            }
        });


    }


 String getdate()
 {
     DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
     Date date = new Date();
     return  ""+dateFormat.format(date);
 }



    public void makestatusTask(String time,String roomname,String status)
    {
        pDialog= ProgressDialog.show(MainActivity.this, "", "Please Wait", true, false); //show process Dialog

        Map<String, String> params=new HashMap<>();
        params.put("datetime",time);
        params.put("roomname",roomname);
        params.put("lightstatus",status);


        System.out.println("##  param - " + params.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ProjectConfig.savestaus, params, this.createRequestSuccessListener(), this.createRequestErrorListener());
        requestQueue.add(jsObjRequest);
    }

    private Response.ErrorListener createRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("##", "##" + error.toString());
                pDialog.dismiss();
            }
        };
    }

    private Response.Listener<JSONObject> createRequestSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("#######", "####" + response.toString());
                //{"message":"Action Saved ?","status":"Saved"}
                pDialog.dismiss();
                try {
                    String result=response.getString("status");
                    if(result.equals("Saved"))
                    {
                        Log.d("#########"," room status :- "+ room+" "+time+" "+status);
                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                        dbhandler.openToWrite();
                        dbhandler.insertHistory(room,time,status);
                        dbhandler.close();

                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("##  e " + e);
                }

            }
        };

    };


    public class SendSMS extends AsyncTask<Void, Void, Void> {

        String number, msg;

        public SendSMS(String number, String msg) {
            this.number = number;
            this.msg = msg;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            result = SendSms.sendSMS(number, msg);
            Log.e("##########"," msg :- "+msg);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("#############", "send Sms result :- " + result);
            try {
                JSONObject obj=new JSONObject(result);

                String errorresult=obj.getString("ErrorMessage");

                Toast.makeText(getApplicationContext()," msg "+errorresult,Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
