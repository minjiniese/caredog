package com.example.caredog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String id;

    private static String TAG = "caredog";
    private static String IP_ADDRESS = "39.115.62.183:3306";
    private TextView dogname;
    private TextView date;
    private String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dogname = findViewById(R.id.dogname);
        date = findViewById(R.id.date);
        Intent intent2 = getIntent();
        id = intent2.getStringExtra("id");
        MainActivity.InsertData task = new MainActivity.InsertData();
        task.execute("http://" + IP_ADDRESS + "/mainpage.php", id);

        ImageView bt_logout =  findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
            startActivity(intent);
            finish();
        });

        ImageView bt_map =  findViewById(R.id.bt_map);
        bt_map.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GooglemapActivity.class);
            startActivity(intent);
        });
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            if (result == null){

            }
            else {

                mJsonString = result;
                showResult();
            }
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String) params[1];
            String serverURL = (String) params[0];
            String postParameters = "&id=" + id;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
//                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

//                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
        private void showResult(){

            String TAG_JSON="webnautes";
            String TAG_DOGNAME = "dogname";
            String TAG_DATE = "date";


            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);

                    String dogname1 = item.getString(TAG_DOGNAME);
                    String date1 = item.getString(TAG_DATE);
                    dogname.setText(dogname1);
                    date.setText("우리가 함께한 시간, "+date1+"일");
                }

            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }

        }
    }
}
