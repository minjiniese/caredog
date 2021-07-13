package com.example.caredog_test;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.caredog.SubActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<<<< Temporary merge branch 1
        setContentView(R.layout.activity_main);
        //현우오빠 ㅎㅇ ㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇ
        //add -> commit -> push
=========
        setContentView(R.layout.activity_sign_user);

        //다음 버튼을 누르면 회원가입_반려견 화면으로 이동
        Button sign_user_next = (Button) findViewById(R.id.sign_user_next);
        sign_user_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
            }
        });
>>>>>>>>> Temporary merge branch 2
    }
}