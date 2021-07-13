package com.example.caredog;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SubActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText dog_type;
    Spinner dog_spinner;
    String[] item;
    private TextView born_text;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_dog);

        this.InitializeView();
        this.InitializeListener();

        dog_type = (EditText)findViewById(R.id.dog_type);
        dog_spinner = (Spinner)findViewById(R.id.dog_spinner);

        dog_spinner.setOnItemSelectedListener(this);

        item = new String[]{"강","아","지"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dog_spinner.setAdapter(adapter);

        //뒤로가기 버튼을 누르면 회원가입_사용자 화면으로 이동
        ImageButton sign_dog_back = (ImageButton) findViewById(R.id.sign_dog_back);
        sign_dog_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.example.caredog_test.MainActivity.class);
                startActivity(intent);
            }
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
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                born_text.setText(year + "-" + month + "-" + dayOfMonth);
            }
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        dialog.show();
    }
}