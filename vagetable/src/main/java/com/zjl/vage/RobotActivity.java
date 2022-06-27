package com.zjl.vage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zjl.rannotation.RouterPath;

@RouterPath(path = "vegetable/robot")
public class RobotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomato);
    }
}
