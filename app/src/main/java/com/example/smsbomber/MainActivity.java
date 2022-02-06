package com.example.smsbomber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    //properties
    private EditText phone;
    private EditText sms;
    private Button send;
    private LinearLayout laySms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();
    }

    /*
        * Initialisations
     */
    private void initActivity(){
        // graphics objects recuperation
        phone = (EditText) findViewById(R.id.txtPhone);
        sms = (EditText) findViewById(R.id.txtSms);
        send = (Button) findViewById(R.id.btnSend);
        laySms = (LinearLayout) findViewById(R.id.laySms);

        //management of the "click" event on the send button
        createOnClickSendButton();
    }

    /*
        *"click" on the send button : send of the message
     */
    private void createOnClickSendButton(){
        send.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                //permission control
                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED)
                {
                    for (int i = 0; i <= 50; i++){
                    SmsManager.getDefault().sendTextMessage(phone.getText().toString(), null,
                            sms.getText().toString(), null, null);}

                    // message for say that the sms was send
                    Toast.makeText(MainActivity.this, "SMS sent succesfully !", Toast.LENGTH_SHORT).show();
                    // delete the text
                    sms.setText("");
                }else
                {
                    // ask one time to give the permission
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.SEND_SMS))
                    {
                        String[] permissions = {Manifest.permission.SEND_SMS};
                        //show the permission demande
                        ActivityCompat.requestPermissions(MainActivity.this, permissions, 2);
                    }else
                    {
                        //show a message that says "the permission is obligatoire
                        permissionRequiredMessage();

                    }
                }

            }
        });
    }
    /*
        * Inform the user that the permission is require
     */

    private void permissionRequiredMessage(){
        Snackbar.make(laySms, "permission Required Message", Snackbar.LENGTH_LONG).setAction
                ("Parameters", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        final Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }).show();
    }

}