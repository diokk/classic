package com.ditaoktaria.classic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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


public class ManageCourse extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private ListView course_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_course);

        this.course_list = (ListView) this.findViewById(R.id.course_list);

        // new getAllCourseTask().execute(new ApiConnector());

       // String data = getIntent().getStringExtra("dataAll");
        SharedPreferences pf= getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        new ProcessCourse().execute(pf.getString(Login.idLecture,""));


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

    public void setListAdapter(JSONArray jsonArray) {
        this.course_list.setAdapter(new CourseListViewAdapter(jsonArray, this));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //manggil adapter
        JSONObject jsonObject = (JSONObject) course_list.getAdapter().getItem(position);
        try {
            String id1 = jsonObject.getString("idCourse");
            Intent myIntent = new Intent(getApplicationContext(), ManageMaterial.class);
            //id itu key, data value
            myIntent.putExtra("matkulid",id1);

            startActivityForResult(myIntent, 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


     private class ProcessCourse extends AsyncTask<String,String,String>{

         @Override
         protected String doInBackground(String... params) {
             String idLecturer = params[0];

             String url ="http://192.168.56.1/classicserver/server/getMatkul.php";

             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             //kiri dari variable di php post, kanan variable android di atas tadi
             nameValuePairs.add(new BasicNameValuePair("id", idLecturer));
             ServiceHandler loginService = new ServiceHandler();
             String s = loginService.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);


             return s;
         }

         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);
             Log.d("hasil matkul", s);
             try {

                 JSONObject jsonObject = new JSONObject(s);

                 int check_matkul = jsonObject.getJSONArray("data_mk").length();
                 if(check_matkul==0){
                     Log.d("tak ada matkul", "tak ada");

                 }
                 else{
                 Log.d("matkul ada", "matkul ada");
                 course_list.setAdapter(new CourseListViewAdapter(jsonObject.getJSONArray("data_mk"), ManageCourse.this));
                 course_list.setOnItemClickListener(ManageCourse.this);}
             } catch (JSONException e) {
                 e.printStackTrace();
             }

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
