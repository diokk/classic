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


    private void showEditDialog(Bundle bundle) {
        FragmentManager fm = getSupportFragmentManager();
        MaterialDialog materialNameDialog = new MaterialDialog();
        materialNameDialog.setArguments(bundle);
        materialNameDialog.show(fm, "activity_material_dialog");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_course);
        this.course_list = (ListView) this.findViewById(R.id.course_list);
       // SharedPreferences pf= getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        String getIdCourse = getIntent().getStringExtra("putIdCourse");
        int getIdParent = getIntent().getIntExtra("putIdParent", 0);
        new ProcessMaterial().execute(getIdCourse, String.valueOf(getIdParent));




    }

    private class ProcessMaterial extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            String postCourse = params[0];
            String postParent = params[1];
            String url ="http://192.168.56.1/classicserver/server/getMaterial.php";
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //kanan dari andro, kiri variable $php
            nameValuePairs.add(new BasicNameValuePair("idCourse", postCourse));
            nameValuePairs.add(new BasicNameValuePair("idParent", postParent));
            ServiceHandler loginService = new ServiceHandler();
            String s = loginService.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("hasil materi", s);
            try {

                JSONArray jsonArray = new JSONArray(s);
               // if (jsonObject.getBoolean("status")) {

                    course_list.setAdapter(new MaterialListViewAdapter(jsonArray, ManageMaterial.this));
                    //

                course_list.setOnItemClickListener(ManageMaterial.this);

                //}else {
                    //kalo false kasih alert

                //}
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

            String jenis = jsonObject.getString("jenis");
            if(jenis.equalsIgnoreCase("folder")){
                String getIdCourse= jsonObject.getString("id_course");
                int getIdParent=jsonObject.getInt("id_folder");
                new ProcessMaterial().execute(getIdCourse, String.valueOf(getIdParent));

            }
            else if(jenis.equalsIgnoreCase("file")) {

                String material_name = jsonObject.getString("name");
                String material_desc = jsonObject.getString("description");
                String material_location = jsonObject.getString("location");

                Bundle bundle = new Bundle();
                bundle.putString("setMaterialDesc", material_desc);
                bundle.putString("setMaterialName", material_name);
                bundle.putString("setMaterialLocation", material_location);


                showEditDialog(bundle);

            }


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
