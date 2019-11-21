package com.example.abc.homeautomation;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abc on 2/1/17.
 */

public class FCMlistner extends FirebaseMessagingService {

    String tickerText,contentTitle,arrayObj,msg;
    int id=001;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        Log.d("#######"," FCM msg is:- "+remoteMessage.getData().toString());


        String message=remoteMessage.getData().toString();
        message=message.substring(message.indexOf("=")+1,message.lastIndexOf("}")+1);
        Log.d("#######"," decoded msg:- "+message);

        //{"Prediction":"Prediction : Can not predict to ON The Light of Dinning Room...\nPrediction : Can not predict to OFF The Light of Dinning Room...\nPrediction : Can not predict to On The Light of Drawing Room...\nPrediction : You should have OFF the Light Of Drawing Room...","message":"Home Automation Prediction Notification : ","tickerText":"Prediction Notification","contentTitle":"Predication Notification"}}

        try {
            JSONObject jsonObject=new JSONObject(message);
         tickerText=jsonObject.getString("tickerText");
            msg=jsonObject.getString("Prediction");
         contentTitle=jsonObject.getString("message");


            Log.d("#######","  notification data  is:- "+tickerText+" "+msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       /* Intent myIntent = new Intent(this,Activity_Recently_Purchased.class);
        myIntent.putExtra("data",arrayObj);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
*/
        Notification myNotification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            myNotification = new Notification.Builder(this)
                    .setContentTitle(msg)
                    .setContentText(contentTitle)
                    .setTicker(tickerText)
                    .setWhen(System.currentTimeMillis())
                   /* .setContentIntent(pendingIntent)*/
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();

        }
        notificationManager.notify(id,myNotification);
        }


    }