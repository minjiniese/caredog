package com.example.caredog;

import android.os.Build;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//setting에서 추가버튼 누르면 나올 시계 화면
public class timepickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timepicker);

        TimePicker mTimePicker = (TimePicker) findViewById(R.id.timepicker);
        int hour, min;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = mTimePicker.getHour();
            min = mTimePicker.getMinute();
        } else {
            hour = mTimePicker.getCurrentHour();
            min = mTimePicker.getCurrentMinute();
        }
    }

}

