package com.ditaoktaria.classic;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

/**
 * Created by ditaoktaria on 4/24/15.
 */
public class CourseListViewAdapter extends BaseAdapter {

    private JSONArray dataArray;
    private Activity activity;
    private static LayoutInflater inflater = null;

    public CourseListViewAdapter(JSONArray jsonArray, Activity a){

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
            cell.courseCode = (TextView) convertView.findViewById(R.id.course_code_list);
            cell.courseName = (TextView) convertView.findViewById(R.id.course_name_list);
            cell.courseImage = (ImageView) convertView.findViewById(R.id.course_img_list);
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
            cell.courseCode.setText(jsonObject.getString("id"));
            cell.courseName.setText(jsonObject.getString("name"));
            String course_name = jsonObject.getString("id");

            if (course_name.equals("5315c2"))
            {
                cell.courseImage.setImageResource(R.mipmap.ic_action_computer);
            }
            else if (course_name.equals("49ea5c"))
            {
                cell.courseImage.setImageResource(R.mipmap.ic_action_not_secure);
            }
            else if (course_name.equals("0c0615"))
            {
                cell.courseImage.setImageResource(R.mipmap.ic_action_network_cell);
            }
        }

        catch (JSONException e) {
            e.printStackTrace();

        }

        return convertView;
    }

    private class ListCell
    {
        private TextView courseCode;
        private TextView courseName;
        private ImageView courseImage;
    }
}
