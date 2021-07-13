package com.example.caredog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Sign_User_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_user);

        //다음 버튼을 누르면 회원가입_반려견 화면으로 이동
        Button sign_user_next = (Button) findViewById(R.id.sign_user_next);
        sign_user_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sign_Dog_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        //뒤로가기 버튼을 누르면 메인 화면으로 이동
        ImageButton sign_user_back = (ImageButton) findViewById(R.id.sign_user_back);
        sign_user_back.setOnClickListener(view -> { finish(); });
    }
}