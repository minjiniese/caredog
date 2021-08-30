package com.example.caredog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUp1_User_Activity extends AppCompatActivity {

    private static String TAG = "caredog";
    private static String IP_ADDRESS = "39.115.62.183:3306";


    private EditText mEditTextid;
    private EditText mEditTextpw;
    private EditText mEditTextname;
    private RadioGroup radiogroup;
    private EditText mEditTextph;
    private TextView mTextViewResult;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_user);

        //다음 버튼을 누르면 회원가입_반려견 화면으로 이동
        Button sign_user_next = (Button) findViewById(R.id.sign_user_next);

        mEditTextid = (EditText) findViewById(R.id.editText_id);
        mEditTextpw = (EditText) findViewById(R.id.editText_pw);
        mEditTextname = (EditText) findViewById(R.id.editText_name);
        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        mEditTextph = (EditText) findViewById(R.id.editText_ph);
        mTextViewResult = (TextView) findViewById(R.id.textView_result);
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rg_male:
                        sex = "male";
                        break;
                    case R.id.rg_female:
                        sex = "female";
                        break;
                }
            }
        });
        sign_user_next.setOnClickListener(view -> {

            String id = mEditTextid.getText().toString();
            String pw = mEditTextpw.getText().toString();
            String name = mEditTextname.getText().toString();
            String ph = mEditTextph.getText().toString();
            sex = "male";
            InsertData task = new InsertData();
            task.execute("http://" + IP_ADDRESS + "/signupuser.php", id, pw, name, sex, ph);

            try {
                Thread.sleep(5000);
                if(mTextViewResult.equals("1")) {
                    Intent intent = new Intent(getApplicationContext(), SignUp2_Dog_Activity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        //뒤로가기 버튼을 누르면 메인 화면으로 이동
        ImageButton sign_user_back = (ImageButton) findViewById(R.id.sign_user_back);
        sign_user_back.setOnClickListener(view -> {
            finish();
        });
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SignUp1_User_Activity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String) params[1];
            String pw = (String) params[2];
            String name = (String) params[3];
            String sex = (String) params[4];
            String ph = (String) params[5];

            String serverURL = (String) params[0];
            String postParameters = "&id=" + id + "&pw=" + pw + "&name=" + name + "&sex=" + sex + "&ph=" + ph;


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
                Log.d(TAG, "POST response code - " + responseStatusCode);

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

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}