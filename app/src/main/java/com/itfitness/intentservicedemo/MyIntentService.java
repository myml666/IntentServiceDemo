package com.itfitness.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

/**
 * @ProjectName: IntentServiceDemo
 * @Package: com.itfitness.intentservicedemo
 * @ClassName: MyIntentService
 * @Description: java类作用描述
 * @Author: LML
 * @CreateDate: 2018/9/13 16:01
 * @UpdateUser: 更新者：
 * @UpdateDate: 2018/9/13 16:01
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private int count=0;
    private boolean isRunning=true;
    private LocalBroadcastManager mLocalBroadcastManager;
    public MyIntentService() {
        super("MyIntentService");
        mLocalBroadcastManager=LocalBroadcastManager.getInstance(this);
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        count=0;
        isRunning=true;
        while (isRunning){
            try {
                count++;
                if(count>=100){
                    isRunning=false;
                }
                Thread.sleep(100);
                sendThreadStatus(count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendThreadStatus(int progress) {
        Intent intent = new Intent(MainActivity.ACTION);
        intent.putExtra("progress", progress);
        mLocalBroadcastManager.sendBroadcast(intent);
    }
}
