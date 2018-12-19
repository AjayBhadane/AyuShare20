package com.devices.ayu.ayushare20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class Recording_Screen_Activity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState);
        setContentView(R.layout.aortic_entry);
        findViewById(R.id.aortic_btn).setOnClickListener(this);
        findViewById(R.id.mitral_btn).setOnClickListener(this);
        findViewById(R.id.pulmonary_btn).setOnClickListener(this);
        findViewById(R.id.tricuspid_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Test.class);
        Bundle data = new Bundle();
        switch (v.getId()){
            case R.id.aortic_btn:
                data.putString(Test.LOCATION, "AORTIC");
                break;
            case R.id.mitral_btn:
                data.putString(Test.LOCATION, "MITRAL");
                break;
            case R.id.pulmonary_btn:
                data.putString(Test.LOCATION, "PULMANORY");
                break;
            case R.id.tricuspid_btn:
                data.putString(Test.LOCATION, "TRICUSPID");
                break;
            default:
                break;
        }
        intent.putExtras(data);
        startActivity(intent);
    }
}
