package com.example.myfirstandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Method that's called when the button create account gets click
    public void launchCreateAccount(View view)
    {
        //Intent allows the first activity to send data to other activity.
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
}