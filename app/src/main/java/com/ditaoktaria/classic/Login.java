package com.ditaoktaria.classic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends ActionBarActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Username = "userNameKey";
    public static final String idLecture = "idLecturerKey";
    public static final String StatusIsLogIn = "LoginKey";

    SharedPreferences sharedpreferences;
    private EditText usernameField,passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usernameField = (EditText)findViewById(R.id.username);
        passwordField = (EditText)findViewById(R.id.password);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        boolean statuslogin = sharedpreferences.getBoolean(StatusIsLogIn, false);
        if(statuslogin){
            Intent myIntent = new Intent(getApplicationContext(), ManageCourse.class);
            //id itu key, data value

            startActivityForResult(myIntent, 0);
        }

        Button lg = (Button) findViewById(R.id.bt_login);
        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProcessLogin().execute(usernameField.getText().toString(),passwordField.getText().toString());
                Log.d("hasil execute", String.valueOf(usernameField.getText()));




                //
            }
        });




    }

    private class ProcessLogin extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String url ="http://192.168.56.1/classicserver/server/login.php";

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("pass", password));
            ServiceHandler loginService = new ServiceHandler();
            String s = loginService.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);


            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("hasil login", s);
            try {
                //JSONArray response = new JSONArray(s);
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("status")) {
                    //kalo true proses selanjutnya
                    Log.d("hasil login", "success");

                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putBoolean(StatusIsLogIn,true);
                    editor.putString(Username, usernameField.getText().toString());
                    editor.putString(idLecture, jsonObject.getJSONArray("data").getJSONObject(0).getString("idLecturer"));
                    editor.commit();

                    //post idLecture ke activity managecourse
                    Intent myIntent = new Intent(getApplicationContext(), ManageCourse.class);
                    //id itu key, data value

                    startActivityForResult(myIntent, 0);

                }else {
                    //kalo false kasih alert
                    Log.d("hasil login", "gagal");

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_login, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
