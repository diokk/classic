package com.ditaoktaria.classic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ManageMaterial extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private ListView course_list;
    private String set_material_description;
    private String set_material_title;


    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        MaterialDialog materialNameDialog = new MaterialDialog();
        materialNameDialog.show(fm, "activity_material_dialog");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_course);
        this.course_list = (ListView) this.findViewById(R.id.course_list);
       // SharedPreferences pf= getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        String getmatkulid = getIntent().getStringExtra("matkulid");
        new ProcessMaterial().execute(getmatkulid);


        Button ac = (Button) findViewById(R.id.bt_account);
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), EditAccount.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button pdf = (Button) findViewById(R.id.bt_materials);
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), pdfplayer.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button vid = (Button) findViewById(R.id.bt_edit_materials);
        vid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), videoplayer.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

    private class ProcessMaterial extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            String postMatkul = params[0];
            String url ="http://192.168.56.1/classicdevel/server/getMateri.php";
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //kanan dari andro, kiri variable $php
            nameValuePairs.add(new BasicNameValuePair("idMatkul", postMatkul));
            ServiceHandler loginService = new ServiceHandler();
            String s = loginService.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("hasil materi", s);
            try {

                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("status")) {
                    Log.d("hasil login", "success");

                    course_list.setAdapter(new MaterialListViewAdapter(jsonObject.getJSONArray("data_materi"), ManageMaterial.this));
                    course_list.setOnItemClickListener(ManageMaterial.this);


                }else {
                    //kalo false kasih alert
                    Log.d("hasil login", "gagal");

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    }

    public void setListAdapter(JSONArray jsonArray){
        this.course_list.setAdapter(new MaterialListViewAdapter(jsonArray,this));
        course_list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //manggil adapter

        JSONObject jsonObject = (JSONObject) course_list.getAdapter().getItem(position);
        try {
            String material_name = jsonObject.getString("materialTitle");
            String material_desc = jsonObject.getString("fileName");

            Bundle bundle = new Bundle();
            bundle.putString("setMaterialDesc", material_desc);
            MaterialDialog fragobj = new MaterialDialog();
            fragobj.setArguments(bundle);

            showEditDialog();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_materials, menu);
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
