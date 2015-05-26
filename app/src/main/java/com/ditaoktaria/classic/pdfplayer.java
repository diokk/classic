package com.ditaoktaria.classic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebSettings;


public class pdfplayer extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfplayer);
/*
        Uri uri = Uri.parse("http://192.168.56.1/classicdevel/viewpdf.html");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        webview.getSettings().setJavaScriptEnabled(true);
        startActivity(intent);
        */


        WebView webview = (WebView) findViewById(R.id._webview);
        //setContentView(webview);
        webview.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("http://192.168.56.1/classicdevel/pdfjs/web/viewer.html");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdfplayer, menu);
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
