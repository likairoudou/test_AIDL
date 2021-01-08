package com.bestmath.app_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bestmath.app_client.R;

import java.util.List;

import aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private IMyAidlInterface iMyAidlInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setAction("com.bestmath.app.MyService");
//        serviceIntent.setAction("com.example.remoteservicedemo.RemoteService");
        intent.setPackage("com.bestmath.app");
//        final Intent eintent = new Intent(createExplicitFromImplicitIntent(this,intent));

        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iMyAidlInterface=IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        },BIND_AUTO_CREATE);

        Button bt =findViewById(R.id.tt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s=iMyAidlInterface.getData();
                    Toast.makeText(getApplicationContext(),"这是弹窗"+s,Toast.LENGTH_SHORT).show();

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//
//    public void click(View view){
//
//        try {
//            String s=iMyAidlInterface.getData();
//            Toast.makeText(getApplicationContext(),"这是弹窗"+s,Toast.LENGTH_SHORT).show();
//
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }

}
