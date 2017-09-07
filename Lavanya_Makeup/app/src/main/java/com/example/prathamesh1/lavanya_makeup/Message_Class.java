package com.example.prathamesh1.lavanya_makeup;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Prathamesh 1 on 07-05-2017.
 */

public class Message_Class extends AppCompatActivity{

    Button send;
    EditText receiver,TV_message;
    String mobStr, msg;
    StringBuilder mess = new StringBuilder();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        send = (Button) findViewById(R.id.send);
        receiver  = (EditText) findViewById(R.id.MES_TV_Mobile);
        TV_message = (EditText) findViewById(R.id.MES_TV_MESSAGE);

        receiver.setEnabled(false);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        mess.append(sp.getString("message_body","No content"));
        TV_message.setText(mess.toString());

        final String receMobNo = getIntent().getStringExtra("mobile");
        receiver.setText(receMobNo);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobStr = receiver.getText().toString();
                msg = TV_message.getText().toString();

                try{
                    if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                        }
                    }else{
                        sendSMS(mobStr,msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Message_Class.this,"Message not sent",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS(mobStr,msg);
                }else{
                    Toast.makeText(Message_Class.this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void sendSMS(String mobileNo,String msg){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(mobileNo,null,msg,null,null);
        Toast.makeText(Message_Class.this,"Message sent",Toast.LENGTH_SHORT).show();
        finish();
    }
}
