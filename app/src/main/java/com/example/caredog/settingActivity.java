package com.example.caredog;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class settingActivity extends AppCompatActivity {
    ArrayList<String> list1;
    ArrayList<String> list2;
    int hour = 0, min = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        list1 = new ArrayList<>();
        list1.add("아이템 1");
        list1.add("아이템 2");
        list1.add("아이템 3");
        list1.add("아이템 4");
        list1.add("아이템 5");
        list1.add("아이템 6");
        Adapter adapter = new Adapter(list1);
        RecyclerView recyclerView = findViewById(R.id.list_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        //뒤로가기 버튼을 누르면 메인 화면으로 이동
        ImageButton setting_back = (ImageButton) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton add_food = (ImageButton) findViewById(R.id.add_food);
        //밥 시간 추가 버튼 클릭 이벤트
        add_food.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TimePickerDialog mTimePickerDialog = new TimePickerDialog(
                        settingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                }, hour, min, false);
                mTimePickerDialog.show();
                // 시간 입력받아서 리스트 추가하는 작업부터 하세요
            }
        });
    }
}

//                TimepickerFragment mTimePickerFragment = new TimepickerFragment();
//                mTimePickerFragment.show(getSupportFragmentManager(), TimepickerFragment.FRAGMENT_TAG);
//                list2 = new ArrayList<>();
//                list2.add("아이템");
//
//                Adapter adapter2 = new Adapter(list2);
//                RecyclerView recyclerView2 = findViewById(R.id.list_medicine);
//                recyclerView2.setLayoutManager(new LinearLayoutManager(this));
//                recyclerView2.setAdapter(adapter);
//            }
//        }));
//    }
//}
