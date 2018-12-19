package com.devices.ayu.ayushare20;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Test extends AppCompatActivity {
    public static final String LOCATION = "LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText(savedInstanceState.getString(LOCATION));
        setContentView(tv);
    }
}
