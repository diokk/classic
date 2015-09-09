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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
    private View getUsernameBar;
    private SharedPreferences pf;
    private int ID_LOGOUT = 3232;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_course);

        this.course_list = (ListView) this.findViewById(R.id.course_list);

        // new getAllCourseTask().execute(new ApiConnector());

       // String data = getIntent().getStringExtra("dataAll");
        pf= getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        new ProcessCourse().execute(pf.getString(Login.idLecture, ""));




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
            int id2 = 0;
            Intent myIntent = new Intent(getApplicationContext(), ManageMaterial.class);
            //id itu key, data value
            myIntent.putExtra("putIdCourse",id1);
            myIntent.putExtra("putIdParent",id2);

            startActivityForResult(myIntent, 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


     private class ProcessCourse extends AsyncTask<String,String,String>{

         @Override
         protected String doInBackground(String... params) {
             String idLecturer = params[0];
             Log.d("idlect",idLecturer);

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


    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item = menu.findItem(R.id.action_settings);
        String username= pf.getString(Login.Username,"");
        item.setTitle(username);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       menu.add(Menu.NONE, ID_LOGOUT, Menu.NONE, "Logout");
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "ye profil di klik", Toast.LENGTH_SHORT).show();
                break;
            case 3232:
                Toast.makeText(getApplicationContext(), "yee logout", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
