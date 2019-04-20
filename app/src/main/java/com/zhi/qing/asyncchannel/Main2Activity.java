package com.zhi.qing.asyncchannel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhi.qing.asyncchannel.ActivityToActivity.OriginActivity;
import com.zhi.qing.asyncchannel.activityToFragment.MainActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void testbutton1(View view) {
        Intent intent=new Intent(this,OriginActivity.class);
        startActivity(intent);
    }

    public void testbutton2(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
