package com.devices.ayu.ayushare20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Device_Selector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayu_device_selector);
        Button ayulynk = (Button)findViewById((R.id.ayulynk));
        Button ayusync = (Button)findViewById((R.id.ayusync));

        ayulynk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Device_Selector.this, Patient_Entry_Activity.class);
                startActivity(intent);
            }
        });

        ayusync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Device_Selector.this, Patient_Entry_Activity.class);
                startActivity(intent);
            }
        });
    }
}
