/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ANGEL
 */
package com.example.abc.homeautomation;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class SendSms {
	public static String retval = "";
	public final static String APIKEY="I7Whw8Z5iUKQaeaJSF5FGA";

	public static String sendSMS(String mobileNumber, String msg){
		
		String rsp = "";

		Log.e("##########"," msgssssss :- "+mobileNumber+"  "+msg);
		try {

			// Push the HTTP Request
			//URL url = new URL("https://www.smsgatewayhub.com/api/mt/SendSMS?APIKey="+APIKEY+"&senderid=TESTIN&channel=2&DCS=0&flashsms=0&number="+mobileNumber+"&text="+msg+"&route=1");
			URL url = new URL("https://www.smsgatewayhub.com/api/mt/SendSMS?APIKey="+APIKEY+"&senderid=TESTIN&channel=2&DCS=0&flashsms=0&number="+ProjectConfig.MobilenNumber+"&text="+msg+"&route=1");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.flush();
			System.out.println("################# Inside send SMS function ");
			// Read The Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				// Process line…
				retval += line;
			}
			wr.close();
			rd.close();

			System.out.println("################# send sms return value "+retval);
			rsp = retval;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}

/*

	public static String SendSmss(String user, String pwd, String to, String msg, String sid, String fl) {
		String rsp = "";

		try {
			// Construct The Post Data

			String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
			data += "&" + URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8");
			data += "&" + URLEncoder.encode("to", "UTF-8") + "=" + URLEncoder.encode(to, "UTF-8");
			data += "&" + URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");
			data += "&" + URLEncoder.encode("sid", "UTF-8") + "=" + URLEncoder.encode(sid, "UTF-8");
			data += "&" + URLEncoder.encode("fl", "UTF-8") + "=" + URLEncoder.encode(fl, "UTF-8");

			// Push the HTTP Request
			URL url = new URL("http://login.smsgatewayhub.com/smsapi/pushsms.aspx?");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Read The Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				// Process line…
				retval += line;
			}
			wr.close();
			rd.close();

			System.out.println(retval);
			rsp = retval;

		} catch (Exception e) {
			// e.printStackTrace();
		}
		return rsp;
	}

*/


	public static void main(String[] args) {
		System.out.println(sendSMS("9960426568","msg".replace(" ","%20")));
	}
}