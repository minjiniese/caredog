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
import androidx.appcompat.widget.SwitchCompat;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
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

    private MqttAndroidClient mqttAndroidClient;
    private static String TAG = "caredog";
    private static String IP_ADDRESS = "39.115.62.183:33333";
    private TextView dogname;
    private TextView date;
    private TextView dataheart;
    private TextView datawalk;
    SwitchCompat wavesensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wavesensor = findViewById(R.id.wavesensor);
        dogname = findViewById(R.id.dogname);
        date = findViewById(R.id.date);
        dataheart = findViewById(R.id.data_heart);
        datawalk = findViewById(R.id.data_walk);
        Intent intent2 = getIntent();
        id = intent2.getStringExtra("id");

        MainActivity.InsertData task = new MainActivity.InsertData();
        task.execute("http://" + IP_ADDRESS + "/mainpage.php", id);

        ImageView bt_logout = findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
            startActivity(intent);
            finish();
        });

        ImageView bt_map = findViewById(R.id.bt_map);
        bt_map.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GooglemapActivity.class);
            startActivity(intent);
        });

        mqttAndroidClient = new MqttAndroidClient(this, "tcp://" + "39.115.62.183" + ":30444", MqttClient.generateClientId());


        try {

            IMqttToken token = mqttAndroidClient.connect(getMqttConnectionOption());    //mqtttoken 이라는것을 만들어 connect option을 달아줌

            token.setActionCallback(new IMqttActionListener() {

                @Override

                public void onSuccess(IMqttToken asyncActionToken) {

                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions());    //연결에 성공한경우


                    try {

                        mqttAndroidClient.subscribe("caredog/sensor", 0);
                        mqttAndroidClient.subscribe("caredog/BPM", 0);
                        mqttAndroidClient.subscribe("caredog/stepCnt", 0);

                    } catch (MqttException e) {

                        e.printStackTrace();

                    }

                }


                @Override

                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {   //연결에 실패한경우

                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

                }

            });

        } catch (

                MqttException e) {

            e.printStackTrace();

        }

        wavesensor.setOnCheckedChangeListener((buttonView, isChecked) -> {

            try {
                if (wavesensor.isChecked()) {
                    mqttAndroidClient.publish("caredog/sensor", "on".getBytes(), 0, false);
                    Toast.makeText(getApplicationContext(), "sensor on", Toast.LENGTH_SHORT).show();

                } else {
                    mqttAndroidClient.publish("caredog/sensor", "off".getBytes(), 0, false);
                    Toast.makeText(getApplicationContext(), "sensor off", Toast.LENGTH_SHORT).show();
                }
            } catch (MqttException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        mqttAndroidClient.setCallback(new MqttCallback() {  //클라이언트의 콜백을 처리하는부분

            @Override

            public void connectionLost(Throwable cause) {


            }


            @Override

            public void messageArrived(String topic, MqttMessage message) throws Exception {    //모든 메시지가 올때 Callback method


                if (topic.equals("caredog/BPM")) {     //topic 별로 분기처리하여 작업을 수행할수도있음

                    String msg = new String(message.getPayload());
                    dataheart.setText(msg);

                }

                if (topic.equals("caredog/stepCnt")) {     //topic 별로 분기처리하여 작업을 수행할수도있음

                    String msg = new String(message.getPayload());
                    datawalk.setText(msg);
                }

            }


            @Override

            public void deliveryComplete(IMqttDeliveryToken token) {


            }

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

            String TAG_JSON = "webnautes";
            String TAG_DOGNAME = "dogname";
            String TAG_DATE = "date";

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    String dogname1 = item.getString(TAG_DOGNAME);
                    String date1 = item.getString(TAG_DATE);
                    dogname.setText(dogname1);
                    date.setText("우리가 함께한 시간, " + date1 + "일");
                }

            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
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
    }

    private DisconnectedBufferOptions getDisconnectedBufferOptions() {

        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();

        disconnectedBufferOptions.setBufferEnabled(true);

        disconnectedBufferOptions.setBufferSize(100);

        disconnectedBufferOptions.setPersistBuffer(true);

        disconnectedBufferOptions.setDeleteOldestMessages(false);

        return disconnectedBufferOptions;

    }


    private MqttConnectOptions getMqttConnectionOption() {

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        mqttConnectOptions.setCleanSession(false);

        mqttConnectOptions.setAutomaticReconnect(true);

        mqttConnectOptions.setWill("aaa", "I am going offline".getBytes(), 1, true);

        return mqttConnectOptions;

    }
}
