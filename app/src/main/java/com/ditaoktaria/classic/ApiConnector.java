package com.ditaoktaria.classic;

import android.util.Log;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ditaoktaria on 4/13/15.
 */
public class ApiConnector {

    public JSONArray GetAllCourses() {

        String url = "http://192.168.56.1/classicdevel/server/getAllCourses.php";

        URL _url = null;
        HttpURLConnection connection = null;

        try {
            _url = new URL(url);

            connection = (HttpURLConnection) _url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String data = "";
            String line = null;
            while((line = reader.readLine()) != null) {
                data += line;
            }

            Log.d("skripsi-client", data);

            JSONArray response = new JSONArray(data);
            return response;
        }
        catch (Exception ex) {
            Log.d("skripsi-client", "Exception: " + ex.getMessage());
        }

        return null;
    }


}
