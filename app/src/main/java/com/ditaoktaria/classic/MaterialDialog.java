package com.ditaoktaria.classic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


public class MaterialDialog extends DialogFragment implements View.OnClickListener {


    private Button buttonPlay;
    private TextView materialDesc,materialTitle;
    private String location;


    public MaterialDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_material_dialog, container);
        buttonPlay = (Button) view.findViewById(R.id.bt_play);
        materialTitle = (TextView) view.findViewById(R.id.tv_materialTitle);
        materialDesc = (TextView) view.findViewById(R.id.tv_materialDesc);
        getDialog().setTitle("Hello Material");
        String strtext = getArguments().getString("setMaterialDesc");
        String strtext1 = getArguments().getString("setMaterialName");
        location = getArguments().getString("setMaterialLocation");
        materialTitle.setText(strtext1);
         materialDesc.setText(strtext);
        buttonPlay.setOnClickListener(this);

        return view;
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

    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(v.getContext(), videoplayer.class);
        myIntent.putExtra("getLocation",location);
        startActivityForResult(myIntent, 0);
    }
}
