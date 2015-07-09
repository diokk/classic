package com.ditaoktaria.classic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ManageMaterial extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private ListView material_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_course);

        this.material_list = (ListView) this.findViewById(R.id.material_list);

        new getAllCourseTask().execute(new ApiConnector());

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

    public void setListAdapter(JSONArray jsonArray){
        this.material_list.setAdapter(new CourseListViewAdapter(jsonArray,this));
        material_list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //manggil adapter

        JSONObject jsonObject = (JSONObject) material_list.getAdapter().getItem(position);
        try {
            String id1 = jsonObject.getString("id");
            //id matkul
            //bikin persis course list view adapter,material list cell juga
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //ambo pecik tarok di siko manggil asyn nyo

    private class getAllCourseTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        protected JSONArray doInBackground(ApiConnector... params) {
            // it is executed on Background thread

            return  params[0].GetAllSubject();
        }
        protected void onPostExecute(JSONArray jsonArray){

            setListAdapter(jsonArray);
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
