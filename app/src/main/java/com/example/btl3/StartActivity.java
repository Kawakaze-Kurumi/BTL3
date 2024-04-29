package com.example.btl3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    Button btnStart, btnQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //Khai báo nút Start
        btnStart = findViewById(R.id.btnStart);
        //Khai báo nút Quit
        btnQuit = findViewById(R.id.btnQuit);
        //Hàm chuyển đổi từ màn hình start sang màn hình chơi
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Khai báo Intent
                Intent myintent = new Intent(StartActivity.this, MainActivity.class);
                //Khởi động Intent
                startActivity(myintent);
            }
        });
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}