package com.example.aptitudemasteradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public void tutorial(View view)
    {
        Intent intent = new Intent(MainActivity.this,Tutorial.class);
        startActivity(intent);

    }
    public void quiz(View view)
    {
        Intent intent = new Intent(MainActivity.this,Quiz.class);
        startActivity(intent);

    }
    public void adduser(View view)
    {
        Intent intent = new Intent(MainActivity.this,AddUser.class);
        startActivity(intent);
    }
    public void reset(View view)
    {
        Intent intent = new Intent(MainActivity.this,ResetPin.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
