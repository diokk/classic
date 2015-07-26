package com.ditaoktaria.classic;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ditaoktaria on 4/24/15.
 */
public class MaterialListViewAdapter extends BaseAdapter {

    private JSONArray dataArray;
    private Activity activity;
    private static LayoutInflater inflater = null;

    public MaterialListViewAdapter(JSONArray jsonArray, Activity a){

        this.dataArray = jsonArray;
        this.activity = a;

        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return this.dataArray.length();
    }

    @Override
    public Object getItem(int position) {

        try{

        return this.dataArray.getJSONObject(position);}

        catch (JSONException e) {
            return e.getStackTrace();


        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //set up convert view if it is null
        ListCell cell;
        if (convertView==null)
        {
            convertView = inflater.inflate(R.layout.course_list_cell, null);
            cell = new ListCell();
            cell.materialCode = (TextView) convertView.findViewById(R.id.course_code_list);
            cell.materialName = (TextView) convertView.findViewById(R.id.course_name_list);
            cell.materialImage = (ImageView) convertView.findViewById(R.id.course_img_list);
            convertView.setTag(cell);

        }

        else
        {
            cell = (ListCell) convertView.getTag();

        }
        // change the data of cell
        try
        {
            JSONObject jsonObject = this.dataArray.getJSONObject(position);
            cell.materialCode.setText(jsonObject.getString("idMaterial"));
            cell.materialName.setText(jsonObject.getString("materialTitle"));
            // ini berdasar idmatakuliah
            //String course_name = jsonObject.getString("mk_id");


        }

        catch (JSONException e) {
            e.printStackTrace();

        }

        return convertView;
    }

    private class ListCell
    {
        private TextView materialCode;
        private TextView materialName;
        private ImageView materialImage;
    }
}
