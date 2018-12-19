package com.devices.ayu.ayushare20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Patient_Entry_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_entry);
        Button create_patient = (Button)findViewById(R.id.create_patient_btn);

        create_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_Entry_Activity.this, Recording_Screen_Activity.class);
                startActivity(intent);
            }
        });
    }
}
