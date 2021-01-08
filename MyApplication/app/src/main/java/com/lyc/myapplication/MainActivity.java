package com.lyc.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private  IMyAidlInterface iMyAidlInterface;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("MainActivity", "onServiceConnected: success");
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("MainActivity", "onServiceDisconnected: success");
//            iLeoAidl = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt=findViewById(R.id.bt);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.bestmath.app", "com.bestmath.app.MyService"));
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("MainActivity", "success"+iMyAidlInterface.getData());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
