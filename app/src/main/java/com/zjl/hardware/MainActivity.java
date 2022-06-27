package com.zjl.hardware;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zjl.rannotation.RouterPath;
import com.zjl.router.ZRouter;

@RouterPath(path = "app/main")
public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity_main_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**通过路由跳转*/
                ZRouter.INSTANCE.routTarget("bbp/steak", MainActivity.this);
                /**通过activity的名字进行隐式跳转，这里不要把包名、类名分开写在构造函数中否则无法跳转*/
            /*val intent = Intent()
            intent.component = ComponentName(MainActivity@ this, "com.zjl.food.bbq.BbqActivity")
            startActivity(intent)*/
            }
        });
    }
}
