package com.example.caredog;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class dataActivity extends AppCompatActivity {
    private TextView textView_date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        this.InitializeView();
        this.InitializeListener();

        //뒤로가기 버튼을 누르면 메인 화면으로 이동
        ImageButton data_back = (ImageButton) findViewById(R.id.data_back);
        data_back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        ImageButton calender = (ImageButton) findViewById(R.id.calender);
        calender.setOnClickListener(this::OnClickHandler);
    }


    public void InitializeView()
    {
        textView_date = (TextView)findViewById(R.id.textView_date);
    }

    public void InitializeListener()
    {
        callbackMethod = (view, year, monthOfYear, dayOfMonth) -> {
            monthOfYear = monthOfYear + 1;
            textView_date.setText(year + "년 " + monthOfYear + "월 " + dayOfMonth + "일");
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);

        dialog.show();
    }
}
