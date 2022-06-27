package com.zjl.food.bbq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.zjl.food.R;
import com.zjl.rannotation.RouterPath;
import com.zjl.router.ZRouter;

@RouterPath(path = "food/steak")
public class BbqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbq);
        findViewById(R.id.activity_bbq_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZRouter.INSTANCE.routTarget("wm/noodles", BbqActivity.this);
            }
        });
    }
}