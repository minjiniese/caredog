package com.example.caredog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

//    private static final String WEATHER_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0";
//    private static final String SERVICE_KEY = "QI4z%2BORw2M8zkDKK%2F4WB5MuDFiIvFRde%2FA7EwFDMgNNniCP3liBpfHLLMLudpJx6I8z2YY2G32%2FCGdv4nchjnA%3D%3D";
//    WeatherInfoTask weatherTask;
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//    TextView text_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //다음 버튼을 누르면 회원가입_반려견 화면으로 이동
        ImageView bt_setting = findViewById(R.id.bt_setting);
        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), settingActivity.class);
                startActivity(intent);
            }
        });

        ImageView bt_logout = findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView bt_map = findViewById(R.id.bt_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GooglemapActivity.class);
                startActivity(intent);
            }
        });

        TextView bt_data = findViewById(R.id.bt_data);
        bt_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), dataActivity.class);
                startActivity(intent);
            }
        });

//        text_weather = (TextView) findViewById(R.id.textView3);
//        text_weather.setOnClickListener(view -> {
//            getWeatherInfo();
//        });
//    }
//
//    private void getWeatherInfo() {
//        if (weatherTask != null) {
//            weatherTask.cancel(true);
//        }
//        weatherTask = new WeatherInfoTask();
//        weatherTask.execute();
//    }
//
//    private class WeatherInfoTask extends AsyncTask<String, String, String> {
//        String value;
//        @Override
//        protected String doInBackground(String... params) {
//            StringBuilder urlBuilder = new StringBuilder(WEATHER_URL); /*URL*/
//            HttpURLConnection conn = null;
//            BufferedReader rd = null;
//            StringBuilder sb = null;
//            try {
//                urlBuilder.append("?").append(URLEncoder.encode("ServiceKey", "UTF-8")).append("=").append(SERVICE_KEY); /*Service Key*/
//                urlBuilder.append("&").append(URLEncoder.encode("ServiceKey", "UTF-8")).append("=").append(URLEncoder.encode("TEST_SERVICE_KEY", "UTF-8")); /*서비스 인증*/
//                urlBuilder.append("&").append(URLEncoder.encode("ftype", "UTF-8")).append("=").append(URLEncoder.encode("ODAM", "UTF-8")); /*파일구분 -ODAM: 동네예보실황 -VSRT: 동네예보초단기 -SHRT: 동네예보단기*/
//                urlBuilder.append("&").append(URLEncoder.encode("basedatetime", "UTF-8")).append("=").append(URLEncoder.encode("20170623140000", "UTF-8"));
//                /*각각의 base_time 로 검색 참고자료 참조 : 규정된 시각 정보를 넣어주어야 함 */
//                URL url = new URL(urlBuilder.toString());
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                conn.setRequestProperty("Content-type", "application/json");
//                System.out.println("Response code: " + conn.getResponseCode());
//
//                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                } else {
//                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//                }
//                sb = new StringBuilder();
//                String line;
//                while ((line = rd.readLine()) != null) {
//                    sb.append(line);
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            } finally {
//                if (conn != null) {
//                    conn.disconnect();
//                }
//                if (rd != null) {
//                    try {
//                        rd.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            String result = sb.toString();
//            JSONObject mainObject;
//            try {
//                mainObject = new JSONObject(result);
//                JSONArray itemArray = mainObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
////                for (int i = 0; i < itemArray.length(); i++) {
////                    JSONObject item = itemArray.getJSONObject(i);
////                    String category = item.getString("category");
////                    value = item.getString("fcstValue");
////                }
//                JSONObject item = itemArray.getJSONObject(0);
//                value = item.getString("fcstValue");
//            } catch (JSONException e) {
//                e.printStackTrace();
//                value = "error";
//            }
//            return value;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            text_weather.setText(s);
//        }
    }

}