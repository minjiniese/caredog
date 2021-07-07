package com.example.caredog;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class settingActivity extends AppCompatActivity {
    ArrayList<String> list1;
    ArrayList<String> list2;
    private TimePickerDialog.OnTimeSetListener callbackMethod1;
    private TimePickerDialog.OnTimeSetListener callbackMethod2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.InitializeListener1();
        this.InitializeListener2();

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        //뒤로가기 버튼을 누르면 메인 화면으로 이동
        ImageButton setting_back = (ImageButton) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        ImageButton add_food = (ImageButton) findViewById(R.id.add_food);
        add_food.setOnClickListener(this::OnClickHandler1);

        ImageButton add_medicine = (ImageButton) findViewById(R.id.add_medicine);
        add_medicine.setOnClickListener(this::OnClickHandler2);
    }

    //밥 시간 list저장 후 recyclerView에 등록
    public void InitializeListener1() {
        callbackMethod1 = (view, hourOfDay, minute) -> {
            list1.add(hourOfDay + "시 " + minute + "분");

            Adapter adapter1 = new Adapter(list1);
            RecyclerView recyclerView1 = findViewById(R.id.list_food);
            recyclerView1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recyclerView1.setAdapter(adapter1);
        };
    }

    //약 시간 list저장 후 recyclerView에 등록
    public void InitializeListener2() {
        callbackMethod2 = (view, hourOfDay, minute) -> {
            list2.add(hourOfDay + "시 " + minute + "분");

            Adapter adapter2 = new Adapter(list2);
            RecyclerView recyclerView2 = findViewById(R.id.list_medicine);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recyclerView2.setAdapter(adapter2);
        };
    }

    //밥 추가 클릭 핸들러
    public void OnClickHandler1(View view) {
        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod1, 8, 10, true);
        dialog.show();
    }

    //약 추가 클릭 핸들러
    public void OnClickHandler2(View view) {
        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, callbackMethod2, 8, 10, true);
        dialog.show();
    }
}

