package com.example.caredog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView bt_image = findViewById(R.id.image);
        bt_image.setOnClickListener(view -> {

        });

        ImageView bt_setting =  findViewById(R.id.bt_setting);
        bt_setting.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), settingActivity.class);
            startActivity(intent);
        });

        ImageView bt_logout =  findViewById(R.id.bt_logout);
        bt_logout.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
            startActivity(intent);
            finish();
        });

        ImageView bt_map =  findViewById(R.id.bt_map);
        bt_map.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GooglemapActivity.class);
            startActivity(intent);
        });

        TextView bt_data =  findViewById(R.id.bt_data);
        bt_data.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), dataActivity.class);
            startActivity(intent);
        });
    }

}
