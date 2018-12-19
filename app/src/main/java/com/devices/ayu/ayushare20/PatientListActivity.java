package com.devices.ayu.ayushare20;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class PatientListActivity extends AppCompatActivity {

    private enum HeartState{
        NORMAL,
        ABNORMAL,
        NOT_CHECKED
    }

    private class PatientDetail{
        final int patientID;
        final String name;
        final HeartState state;

        PatientDetail(final int patientID, final String name, final HeartState state){
            this.patientID = patientID;
            this.name = name;
            this.state = state;
        }
    }

    private final String standardFilePath;

    private ListView mPatientListView;

    private class PatientListAdapter extends BaseAdapter{

        private ArrayList<PatientDetail> patientDetails;
        private LayoutInflater layoutInflater;

        PatientListAdapter(){
            patientDetails = new ArrayList<>();
            layoutInflater = PatientListActivity.this.getLayoutInflater();
        }

        public void addPatient(PatientDetail detail){
            patientDetails.add(detail);
        }

        public PatientDetail getPatient(int position){
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
                convertView = layoutInflater.inflate(R.layout.patient_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.patientId = convertView.findViewById(R.id.patientId);
                viewHolder.patientName = convertView.findViewById(R.id.patientName);
                viewHolder.heartState = convertView.findViewById(R.id.heartState);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }

            PatientDetail detail = patientDetails.get(position);
            viewHolder.patientName.setText(detail.name);
            viewHolder.patientId.setText(Integer.toString(detail.patientID));
            switch(detail.state){
                case NORMAL:
                    viewHolder.heartState.setImageResource(R.drawable.ic_heart_normal);
                    break;
                case ABNORMAL:
                    viewHolder.heartState.setImageResource(R.drawable.ic_heart_abnormal);
                    break;
                case NOT_CHECKED:
                    viewHolder.heartState.setImageResource(R.drawable.ic_heart_unchecked);
                    break;
                default:
                    break;
            }

            return convertView;
        }
    }

    static class ViewHolder{
        TextView patientId;
        TextView patientName;
        ImageView heartState;
    }

    private PatientListAdapter mPatientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_list_layout);

        initPatientList();

        if (! patientDataAlreadyExists()){
            createPatientData();
            setToNoPatientDataFound();
        }

        JSONObject data = getPatientData();

        if( data != null ){
            populatePatientList();
        }

    }

    private boolean patientDataAlreadyExists(){
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "PatientRecordData");

        standardFilePath

        return false;
    }

    private void createPatientData(){

    }

    private void setToNoPatientDataFound(){

    }

    private JSONObject getPatientData(){
        return null;
    }

    private void populatePatientList(){
        mPatientListAdapter.notifyDataSetChanged();
    }

    private void initPatientList(){
        mPatientListAdapter = new PatientListAdapter();
        mPatientListView = findViewById(R.id.patient_list);
        mPatientListView.setAdapter(mPatientListAdapter);
    }

}
