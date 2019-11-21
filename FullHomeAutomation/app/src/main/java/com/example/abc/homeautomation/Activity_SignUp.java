package com.example.abc.homeautomation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class Activity_SignUp extends Activity {

    //edittext declaration
    EditText edtEmailid,edtPassword,edtName,editTextUsername,edtMobile1;
    String strPassword,strName,strUsername,strMobile1;
    Button buttonRegister;

    private ProgressDialog pDialog;
    String regid;
    RSA rsa = new RSA();
    SQLiteAdapter dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);//set layout

     //database constructor call
        dbhelper=new SQLiteAdapter(getApplicationContext());
        //initilisation of edittext

        SharedPreferences preferences=this.getSharedPreferences("FCMtoken",MODE_PRIVATE);
        regid=preferences.getString("Token","");


        edtName=(EditText)findViewById(R.id.editTextName);
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {




            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name=s.toString();

                if(validation.isNAme(name))
                {

                }else{
                    edtName.setError("Enter Valid Name");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextUsername=(EditText)findViewById(R.id.editTextUsername);
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {




            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name=s.toString();

                if(validation.isValidEmail(name))
                {

                }else{
                    editTextUsername.setError("Enter Valid Email");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtMobile1=(EditText)findViewById(R.id.editTextMobile1);
        edtMobile1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {




            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name=s.toString();

                if(validation.isMobNo(name))
                {

                }else{
                    edtMobile1.setError("Mobile number must be 10 digit");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        edtPassword=(EditText)findViewById(R.id.editTextPassword);
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {




            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name=s.toString();

                if(name.length()>5)
                {
                    if(validation.isalphanumeric(name))
                    {

                    }else{
                        edtPassword.setError("Password must be 6 character and alphanumric");
                    }

                }else{
                    edtPassword.setError("Password must be 6 character and alphanumric");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        buttonRegister=(Button)findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get data from editttext
                strPassword = edtPassword.getText().toString();
                strName = edtName.getText().toString();
                strUsername = editTextUsername.getText().toString();
                strMobile1 = edtMobile1.getText().toString();


                    if (!strName.equals("")) {

                        if(validation.isNAme(strName))
                        {


                        if (!strUsername.equals("")) {
                            if (validation.isValidEmail(strUsername)) {
                            if (!strPassword.equals("")) {
                                if(strPassword.length()>5)
                                {
                                    if(validation.isalphanumeric(strPassword))
                                    {


                                if (!strMobile1.equals("")) {

                                    if (validation.isMobNo(strMobile1)) {


                                        makeRegisterTask();
                                       //store in sqllite database

                                   //  String encrypt_strPassword= AES.encrypt(strPassword);
                                        /*String encrypt_strPassword= rsa.Encrypt(strPassword + " ", rsa.getPublicKey()).trim();

                                        System.out.println("## db strPassword:"+strPassword);
                                        System.out.println("## db encrypt_strPassword:"+encrypt_strPassword.toString());

                                            dbhelper.openToWrite();
                                            dbhelper.insertUser(strName, strUsername, encrypt_strPassword.toString(), strMobile1);
                                            dbhelper.close();


                                               finish();;
                                            //start login activity
                                            Intent i=new Intent(getApplicationContext(),Activity_Login.class);
                                            startActivity(i);

*/


                                    } else {
                                        edtMobile1.setText("");
                                        edtMobile1.setHint("Please Enter valid Mobile number");
                                        edtMobile1.requestFocus();
                                    }

                                } else {
                                    edtMobile1.setText("");
                                    edtMobile1.setHint("Please Enter Mobile");
                                    edtMobile1.requestFocus();
                                }

                                    }else{
                                        edtPassword.setError("Password must be 6 character and alphanumric");
                                    }

                                }else{
                                    edtPassword.setError("Password must be 6 character and alphanumric");
                                }
                            } else {
                                edtPassword.setText("");
                                edtPassword.setHint("Please Enter Password");
                                edtPassword.requestFocus();
                            }


                            } else {
                                editTextUsername.setText("");
                                editTextUsername.setHint("Please Enter valid email");
                                editTextUsername.requestFocus();
                            }
                        } else {
                            editTextUsername.setText("");
                            editTextUsername.setHint("Please Enter UserName");
                            editTextUsername.requestFocus();
                        }
                        }else{
                            edtName.setError("Enter Valid Name");
                        }
                    } else {
                        edtName.setText("");
                        edtName.setHint("Please Enter Name");
                            edtName.requestFocus();
                    }

        }
        });



    }


    private void makeRegisterTask() {

        pDialog = ProgressDialog.show(Activity_SignUp.this, "", "Please Wait", true, false);//show process Dialog
// rationid,userName,password,gcmid

        Map<String, String> params = new HashMap<>();
        params.put("name", strName);
        params.put("emailid", strUsername);
        params.put("password", strPassword);
        params.put("mobileno", strMobile1);
        params.put("fcmid",regid);

        System.out.println("##  params.toString() " + params.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ProjectConfig.Register, params, this.createRequestSuccessListener(), this.createRequestErrorListener());

        requestQueue.add(jsObjRequest);

    }

    private Response.Listener<JSONObject> createRequestSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                pDialog.dismiss();
                String result = "", status = "";
                System.out.println("## in shake service response:" + response.toString());
               //## in shake service response:{"message":"User Registered Failed"}
                try {
                    result = response.getString("status");
                    status=response.getString("message");
                    if(result.equals("Success")) {
                        if (status.equals("User Registered Successfully"))
                        {/*
                            String encrypt_strPassword= rsa.Encrypt(strPassword + " ", rsa.getPublicKey()).trim();

                            System.out.println("## db strPassword:"+strPassword);
                            System.out.println("## db encrypt_strPassword:"+encrypt_strPassword.toString());
*/
                            dbhelper.openToWrite();
                            dbhelper.insertUser(strName, strUsername, strPassword, strMobile1);
                            dbhelper.close();


                            finish();
                            //start login activity
                            Intent i = new Intent(getApplicationContext(), Activity_Login.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                        {
                            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

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




}
