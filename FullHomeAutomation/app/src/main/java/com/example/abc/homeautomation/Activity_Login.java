package com.example.abc.homeautomation;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import database.SQLiteAdapter;


public class Activity_Login extends Activity {
    RSA rsa = new RSA();
    //edittext declaration
    EditText edtUserName,edtPassword;
    String strUserName,strPassword;
    //Button declaration
    Button btnLogin;
    TextView textSignup;

    private ProgressDialog pDialog;


    SQLiteAdapter dbhelper;

    String flag="0";
    public static String userLogin="";
    public static  int PERMISSION_REQUEST_CODE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//set layout

        if (Build.VERSION.SDK_INT>=23){

            checkPermission();
        }

        //call sqllite database constructor
        dbhelper=new SQLiteAdapter(getApplicationContext());
        dbhelper.openToRead();
        int a=dbhelper.Get_Total_LoginStatus();

         if(a>0)
         {
             finish();
             Intent i=new Intent(getApplicationContext(),MainActivity.class);
             startActivity(i);

         }

        dbhelper.close();
        //initilisation of edittext
        edtUserName=(EditText)findViewById(R.id.editTextUserName);
        edtPassword=(EditText)findViewById(R.id.editTextPassword);
        btnLogin=(Button)findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //get data from editttext
                strUserName=edtUserName.getText().toString();
                strPassword=edtPassword.getText().toString();

                if(!strUserName.equals(""))
                {

                    if(!strPassword.equals(""))
                    {

                            //open database to read data from table
/*

                        dbhelper.openToRead();
                       ArrayList<User> userlist= dbhelper.retrieveAllUser();
                        for(int i=0;i<userlist.size();i++)
                        {
                            String username=userlist.get(i).getUsername();
                            String password=userlist.get(i).getPassword();
                            String name=userlist.get(i).getName();
                            //check username and password

                            System.out.println("## db password:"+password);
                            System.out.println("## str password:"+strPassword);


                           String decrypt_password =rsa.Decrypt(password, rsa.getPrivateKey()).trim();
                            System.out.println("## decrypt_password:"+decrypt_password);


                            if(strUserName.equals(username)&&strPassword.equals(decrypt_password))
                            {
                                flag="1";
                                userLogin=name;
                                break;

                            }
                        }
                        dbhelper.close();


                      if(flag.equals("1")){
*/

                        makeLoginTask();

/*

                    }else {

                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

                        edtPassword.setText("");
                        edtPassword.setHint("Please Enter Password");
                        edtUserName.setText("");
                        edtUserName.setHint("Please Enter UserName");


                    }

*/

                    }else{
                        edtPassword.setText("");
                        edtPassword.setHint("Please Enter Password");
                        edtPassword.requestFocus();
                    }

                }else{
                    edtUserName.setText("");
                    edtUserName.setHint("Please Enter UserName");
                    edtUserName.requestFocus();
                }


            }
        });


        textSignup=(TextView)findViewById(R.id.textViewSignup);
        textSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start signup Activity
              Intent i=new Intent(getApplicationContext(),Activity_SignUp.class);
                startActivity(i);


            }
        });
    }



    public static boolean hasPermissions(Context context, String... permissions) {

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    private void checkPermission(){
        String[] PERMISSIONS = {android.Manifest.permission.SEND_SMS,android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECEIVE_SMS};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        }


    }

    private void makeLoginTask() {
        pDialog= ProgressDialog.show(Activity_Login.this, "", "Please Wait", true, false); //show process Dialog

        Map<String, String> params=new HashMap<>();
        params.put("emailid",strUserName);
        params.put("password",strPassword);


        System.out.println("##  params.toString() " + params.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ProjectConfig.Login, params, this.createRequestSuccessListener(), this.createRequestErrorListener());

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

                pDialog.dismiss();
                String status = "";

                System.out.println("## in shake service response:" + response.toString());
                // ## in shake service response:{"message":"User Login Success","status":"Success"}
                try {
                    status=  response.getString("status");

                  String msg=response.getString("message");
                    if(status.equals("Success"))
                    {

                        pDialog.dismiss();
                        dbhelper.openToWrite();
                        dbhelper.deleteLoginStatus();
                        dbhelper.close();


                        dbhelper.openToWrite();
                        dbhelper.insertLoginStatus(1,"1");
                        dbhelper.close();


                        //Show message
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        finish();
                        //start MAin activity
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);


                    }
                    else
                    {
                        pDialog.dismiss();

                        Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        };

    }

}
