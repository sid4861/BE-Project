package com.example.abc.homeautomation;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import database.SQLiteAdapter;


public class IncomingSms extends BroadcastReceiver {
	SQLiteAdapter dbhandler;
	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();

	public void onReceive(Context context, Intent intent) {

		dbhandler=new SQLiteAdapter(context);


		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();

		try {
			
			if (bundle != null) {
				
				final Object[] pdusObj = (Object[]) bundle.get("pdus");
				
				for (int i = 0; i < pdusObj.length; i++) {
					
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();
					
			        String senderNum = phoneNumber;
			        String message = currentMessage.getDisplayMessageBody();

			        Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
			        
			      System.out.println("## senderNum:" + senderNum + " :message:" + message);

					// Toast.makeText(context, "message1 : " + message, Toast.LENGTH_SHORT).show();

					 Toast.makeText(context, "senderNum : " + senderNum, Toast.LENGTH_SHORT).show();
					Toast.makeText(context, "ProjectConfig.MobilenNumber: " + ProjectConfig.MobilenNumber, Toast.LENGTH_SHORT).show();


					if((ProjectConfig.MobilenNumber).equals(""+senderNum.trim()))
					{

						try{
							System.out.println("## ProjectConfig.MobilenNumber:" + ProjectConfig.MobilenNumber + " :message:" + message);

							System.out.println("## senderNum:" + senderNum + " :message:" + message);

							String message1=message.trim();

							String shortflag = message1.substring(0, 2);


							if(shortflag.equals("temp"))
							{
								dbhandler.openToWrite();
								dbhandler.update_Temp(1,message1+"");
								dbhandler.close();
							}
							if(shortflag.equals("1"))
							{
								dbhandler.openToWrite();
								dbhandler.update_light(1, "OFF");
								dbhandler.close();
							}

							if(shortflag.equals("2"))
							{
								dbhandler.openToWrite();
								dbhandler.update_light(2, "OFF");
								dbhandler.close();
							}

							if(shortflag.equals("3"))
							{
								dbhandler.openToWrite();
								dbhandler.update_light(3, "OFF");
								dbhandler.close();
							}
							if(shortflag.equals("4"))
							{
								dbhandler.openToWrite();
								dbhandler.update_light(4, "OFF");
								dbhandler.close();
							}



						}catch (Exception e)
						{	System.out.println("## Exception" + e);

						}


					}



				} // end for loop
              } // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" + e);
			System.out.println("## Exception 1" + e);
		}
	}

	
	
}