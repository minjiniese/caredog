package com.example.caredog;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//setting에서 추가버튼 누르면 나올 시계 화면
public class timepickerActivity extends AppCompatActivity {

    private TextView textView_Date;
    private TimePickerDialog.OnTimeSetListener callbackMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timepicker);
        this.InitializeView();
        this.InitializeListener();

        textView_Date = (TextView)findViewById(R.id.textvieww);
    }

    public void InitializeView()
    {
        textView_Date = (TextView)findViewById(R.id.textvieww);
    }

    public void InitializeListener()
    {
        callbackMethod = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                textView_Date.setText(hourOfDay + "시" + minute + "분");
            }
        };
    }
    public void OnClickHandler(View view)
    {
        TimePickerDialog dialog = new TimePickerDialog(this, callbackMethod, 8, 10, true);

        dialog.show();
    }
}
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.timepicker);
//
//        TimePicker mTimePicker = (TimePicker) findViewById(R.id.timepicker);
//        int hour, min;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            hour = mTimePicker.getHour();
//            min = mTimePicker.getMinute();
//        } else {
//            hour = mTimePicker.getCurrentHour();
//            min = mTimePicker.getCurrentMinute();
//        }
//    }
//
//}

