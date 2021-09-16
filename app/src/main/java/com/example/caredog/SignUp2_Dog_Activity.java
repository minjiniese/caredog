package com.example.caredog;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class SignUp2_Dog_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static String TAG = "caredog";
    private static String IP_ADDRESS = "39.115.62.183:3306";

    String id;
    EditText dog_name;
    EditText dog_type;
    Spinner dog_spinner;
    String[] item;
    RadioGroup radiogroup;
    String dogsex;
    TextView mTextViewResult;
    TextView born_text;
    DatePickerDialog.OnDateSetListener callbackMethod;
    Calendar calendar = Calendar.getInstance();
    char result2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_dog);

        this.InitializeView();
        this.InitializeListener();

        Intent intent = getIntent();
        id = intent.getStringExtra("userid");
        dog_name = (EditText)findViewById(R.id.editText_dogname) ;
        dog_type = (EditText)findViewById(R.id.editText_dogtype);
        dog_spinner = (Spinner)findViewById(R.id.dog_spinner);
        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        mTextViewResult = (TextView) findViewById(R.id.textView_result);
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        dog_spinner.setOnItemSelectedListener(this);

        item = new String[]{"강","아","지"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dog_spinner.setAdapter(adapter);

        radiogroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.rg_female:
                    dogsex = "f";
                    break;
                case R.id.rg_male:
                    dogsex = "m";
                    break;
                case R.id.rg_neuterfemale:
                    dogsex = "nf";
                    break;
                case R.id.rg_neutermale:
                    dogsex = "nm";
                    break;
            }
        });


        //완료 버튼을 누르면 회원가입_사용자 화면으로 이동
        Button complete = (Button) findViewById(R.id.complete);
        complete.setOnClickListener(view -> {
            String dogname = dog_name.getText().toString();
            String dogtype = dog_type.getText().toString();
            String date = born_text.getText().toString();
            SignUp2_Dog_Activity.InsertData task = new SignUp2_Dog_Activity.InsertData();
            task.execute("http://" + IP_ADDRESS + "/signupdog.php", id, dogname, dogtype, date, dogsex);

        });

        //뒤로가기 버튼을 누르면 회원가입_사용자 화면으로 이동
        ImageButton sign_dog_back = (ImageButton) findViewById(R.id.sign_dog_back);
        sign_dog_back.setOnClickListener(view -> {
            Intent intent2 = new Intent(getApplicationContext(), SignUp1_User_Activity.class);
            startActivity(intent2);
            finish();
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dog_type.setText(item[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        dog_type.setText("");
    }

    public void InitializeView()
    {
        born_text = (TextView)findViewById(R.id.born_text);
    }

    public void InitializeListener()
    {
        callbackMethod = (view, year, month, dayOfMonth) -> born_text.setText(year + "-" + (month+1) + "-" + dayOfMonth);
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        dialog.show();
    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SignUp2_Dog_Activity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            result2 = result.toString().charAt(0);

            mTextViewResult.setText(result);

            if(result2 == '1') {
                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                intent3.putExtra("id", id);
                startActivity(intent3);
                finish();
            }

            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String id = (String) params[1];
            String dogname = (String) params[2];
            String dogtype = (String) params[3];
            String date = (String) params[4];
            String dogsex = (String) params[5];

            String serverURL = (String) params[0];
            String postParameters = "&id=" + id + "&dogname=" + dogname + "&dogtype=" + dogtype + "&date=" + date + "&dogsex=" + dogsex;


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