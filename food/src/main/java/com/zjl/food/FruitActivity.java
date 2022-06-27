package com.zjl.food;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zjl.rannotation.RouterPath;


@RouterPath(path = "food/fruit")
public class FruitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
    }
}