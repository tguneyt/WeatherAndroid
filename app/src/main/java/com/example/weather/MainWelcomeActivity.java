package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class MainWelcomeActivity extends AppCompatActivity {
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    FirstLoginListClass firstLoginListClass;
    String S_firslog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_welcome);
//        Bundle bundle = getIntent().getExtras();
//
//        S_firslog = bundle.getString("x");
        firstLoginListClass =new FirstLoginListClass(this);
        firstLoginListClass.firslogin();
//
//        preferences = getSharedPreferences("b", MODE_PRIVATE);
//        S_firslog = preferences.getString("x","");
//        firstLoginListClass =new FirstLoginListClass(this);
//        firstLoginListClass.firslogin();
//
//        preferences = getSharedPreferences("b", MODE_PRIVATE);
//        editor = preferences.edit();
//        editor.putString("x", "f");
//        editor.apply();
//        editor.commit();

    }

}