package com.example.caredog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_dog);

        //뒤로가기 버튼을 누르면 회원가입_사용자 화면으로 이동
        ImageButton sign_dog_back = (ImageButton) findViewById(R.id.sign_dog_back);
        sign_dog_back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            //지혜2
        });
    }
}
