package com.itfitness.intentservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String ACTION="com.itfitness.intentservicedemo.myintentserviceaction";
    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mMyBroadcastReceiver;
    private TextView tv;
    private ProgressBar pb;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initBroadcastReceiver();
    }

    private void initView() {
        mButton = findViewById(R.id.bt);
        tv = (TextView) findViewById(R.id.tv);
        pb = (ProgressBar) findViewById(R.id.prb);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyIntentService.class);
                startService(intent);
            }
        });
    }

    private void initBroadcastReceiver() {
        if (mLocalBroadcastManager==null) {
            mLocalBroadcastManager=LocalBroadcastManager.getInstance(this);
        }
        if(mMyBroadcastReceiver==null){
            mMyBroadcastReceiver = new MyBroadcastReceiver();
        }
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION);
        mLocalBroadcastManager.registerReceiver(mMyBroadcastReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocalBroadcastManager!=null&&mMyBroadcastReceiver!=null){
            mLocalBroadcastManager.unregisterReceiver(mMyBroadcastReceiver);
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION)){
                int progress = intent.getIntExtra("progress", 0);
                if(progress>0&&progress<100){
                    tv.setText("线程进行中。。。。");
                }else if(progress>=100){
                    tv.setText("线程结束");
                }
                pb.setProgress(progress);
            }
        }
    }
}
