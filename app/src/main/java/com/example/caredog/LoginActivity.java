package com.example.caredog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private static String TAG = "caredog";
    private static String IP_ADDRESS = "39.115.62.183:3306";

    private EditText mEditTextid;
    private EditText mEditTextpw;
    private TextView mTextViewResult;
    char result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextid = (EditText) findViewById(R.id.idInput);
        mEditTextpw = (EditText) findViewById(R.id.passwordInput);
        mTextViewResult = (TextView) findViewById(R.id.textView_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        //메인화면으로 이동
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            String id = mEditTextid.getText().toString();
            String pw = mEditTextpw.getText().toString();

            InsertData task = new InsertData();
            task.execute("http://" + IP_ADDRESS + "/login.php", id, pw);

            mEditTextpw.setText("");
        });

        //회원가입으로 이동
        Button signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignUp1_User_Activity.class);
            startActivity(intent);
        });
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(LoginActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            result2 = result.toString().charAt(0);

            mTextViewResult.setText(result);

            if(result2 == '1') {
                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("id", mEditTextid.getText().toString());
                startActivity(intent);
                finish();
            }

            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String) params[1];
            String pw = (String) params[2];

            String serverURL = (String) params[0];
            String postParameters = "&id=" + id + "&pw=" + pw;


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