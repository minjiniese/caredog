package com.example.caredog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_user);

        //다음 버튼을 누르면 회원가입_반려견 화면으로 이동
        Button sign_user_next = (Button) findViewById(R.id.sign_user_next);
        sign_user_next.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SubActivity.class);
            startActivity(intent);
        });
    }
}