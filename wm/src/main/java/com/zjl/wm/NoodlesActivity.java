package com.zjl.wm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.zjl.rannotation.RouterPath;
import com.zjl.router.ZRouter;

@RouterPath(path = "wm/noodles")
public class NoodlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noodles);
        findViewById(R.id.activity_noodles_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZRouter.INSTANCE.routTarget("com/main", NoodlesActivity.this);
            }
        });
    }
}