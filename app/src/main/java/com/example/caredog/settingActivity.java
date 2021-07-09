package com.example.caredog;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class settingActivity extends AppCompatActivity {
    public static Context mContext;
    private ArrayList<String> list1;
    private ArrayList<String> list2;
    private TimePickerDialog.OnTimeSetListener callbackMethod1;
    private TimePickerDialog.OnTimeSetListener callbackMethod2;
    private RecyclerView recyclerView1, recyclerView2;
    String time;
    int num_food,num_medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.InitializeListener1();
        this.InitializeListener2();
        mContext = this;

        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView_food);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView_medicine);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        //저장된 밥 시간 정보 list에 불러오기
        num_food = preferenceData.getJ(mContext,"num_food");
        for(int i = 1; i<= num_food; i++) {
            time = preferenceData.getString(mContext,"time_food"+i);
            if (!time.equals("")) {
                list1.add(time);
            }
        }

        //list에 저장된 정보 recyclerView에 등록
        Adapter adapter1 = new Adapter("food",list1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        //저장된 약 시간 정보 불러오기
        num_medicine = preferenceData.getJ(mContext,"num_medicine");
        for(int i = 1; i<= num_medicine; i++) {
            time = preferenceData.getString(mContext,"time_medicine"+i);
            if (!time.equals("")) {
                list2.add(time);
            }
        }

        //list에 저장된 정보 recyclerView에 등록
        Adapter adapter2 = new Adapter("medicine",list2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

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
            time = hourOfDay + "시 " + minute + "분";
            list1.add(time);

            Adapter adapter1 = new Adapter("food",list1);
            RecyclerView recyclerView1 = findViewById(R.id.recyclerView_food);
            recyclerView1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recyclerView1.setAdapter(adapter1);

            //preferenceData 저장
            preferenceData.setJ(mContext,"num_food",++num_food);
            preferenceData.setString(mContext,"time_food"+ num_food,time);
            Toast.makeText(settingActivity.this,"밥 시간이 추가되었습니다",Toast.LENGTH_SHORT).show();
        };
    }

    //약 시간 list저장 후 recyclerView에 등록
    public void InitializeListener2() {
        callbackMethod2 = (view, hourOfDay, minute) -> {
            time = hourOfDay + "시 " + minute + "분";
            list2.add(time);

            Adapter adapter2 = new Adapter("medicine",list2);
            RecyclerView recyclerView2 = findViewById(R.id.recyclerView_medicine);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            recyclerView2.setAdapter(adapter2);

            //preferenceData 저장
            preferenceData.setJ(mContext,"num_medicine",++num_medicine);
            preferenceData.setString(mContext,"time_medicine"+ num_medicine,time);
            Toast.makeText(settingActivity.this,"약 시간이 추가되었습니다",Toast.LENGTH_SHORT).show();
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

