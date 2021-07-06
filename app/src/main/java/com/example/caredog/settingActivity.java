package com.example.caredog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class settingActivity extends AppCompatActivity {
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        list = new ArrayList<>();
        list.add("아이템 1");
        list.add("아이템 2");
        list.add("아이템 3");
        list.add("아이템 4");
        list.add("아이템 5");
        list.add("아이템 6");
        Adapter adapter = new Adapter(list);
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
            }
        });
    }
}

