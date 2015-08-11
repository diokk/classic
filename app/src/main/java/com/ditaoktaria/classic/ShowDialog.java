package com.ditaoktaria.classic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.ditaoktaria.classic.ShowMaterialDialog.EditNameDialogListener;


public class ShowDialog extends ActionBarActivity {


    public class FragmentDialogDemo extends FragmentActivity implements EditNameDialogListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.manage_course);
            showEditDialog();
        }

        private void showEditDialog() {
            FragmentManager fm = getSupportFragmentManager();
           ShowMaterialDialog editNameDialog = new ShowMaterialDialog();
            editNameDialog.show(fm, "activity_material_dialog");

        }

        @Override
        public void onFinishEditDialog(String inputText) {
           // Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Hi, ", Toast.LENGTH_SHORT).show();
        }
    }
}
