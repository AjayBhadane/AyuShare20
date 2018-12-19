package com.devices.ayu.ayushare20;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PatientListActivity extends AppCompatActivity {

    private enum State{
        NORMAL,
        ABNORMAL,
        NOT_CHECKED
    }

    private class PatientDetails{
        final int patientID;
        final String name;
        final State state;

        PatientDetails(final int patientID, final String name, final State state){
            this.patientID = patientID;
            this.name = name;
            this.state = state;
        }
    }

    private class PatientListAdapter extends BaseAdapter{

        private ArrayList<PatientDetails> patientDetails;
        private LayoutInflater layoutInflater;

        PatientListAdapter(){
            patientDetails = new ArrayList<>();
            layoutInflater = PatientListActivity.this.getLayoutInflater();
        }

        public PatientDetails getPatient(int position){
            return patientDetails.get(position);
        }

        public void clear(){patientDetails.clear();}

        @Override
        public int getCount() {
            return patientDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return patientDetails.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if(convertView == null){

            }

            return null;
        }
    }

    static class ViewHolder{
        TextView patientId;
        TextView patientName;
        View heartState;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_list_layout);
    }

}
