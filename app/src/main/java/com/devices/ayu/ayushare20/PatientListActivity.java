package com.devices.ayu.ayushare20;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PatientListActivity extends AppCompatActivity {
    private static final String TAG = PatientListActivity.class.getCanonicalName();

    private static final int FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN;

    private enum HeartState{
        NORMAL,
        ABNORMAL,
        NOT_CHECKED
    }

    private class PatientDetail{
        int patientID;
        String name;
        HeartState state;

        PatientDetail(){
            this.patientID = -1;
            this.name = "N/A";
            this.state = HeartState.NOT_CHECKED;
        }

        PatientDetail(final int patientID, final String name, final HeartState state){
            this.patientID = patientID;
            this.name = name;
            this.state = state;
        }

        public void setPatientID(final int patientID){
            this.patientID = patientID;
        }

        public void setName(final String name){
            this.name = name;
        }

        public void setState(final HeartState state){
            this.state = state;
        }
    }

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
            if(detail.patientID == -1)
                viewHolder.patientId.setText("N/A");
            else
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
        setFullscreen();
        setContentView(R.layout.patient_list_layout);

        initPatientList();

        if (! patientDataAlreadyExists()){
            createPatientData();
        }

        try{
            populatePatientData();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void setFullscreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(FULLSCREEN, FULLSCREEN);

        getSupportActionBar().hide();
    }

    private boolean patientDataAlreadyExists(){
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "PatientRecordData");

        if(! folder.exists()){
            return false;
        }

        File file = new File(folder.getAbsolutePath(), "data.json");

        return file.exists();
    }

    private void createPatientData(){
        String defaultContent = "{" +
                                "   \"default_entry\": {"+
                                "       \"name\" : \"N/A\","+
                                "       \"id\" : -1,"+
                                "       \"heartState\" : \"not_checked\""+
                                "   }"+
                                "}";

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "PatientRecordData");

        if(! folder.exists()){
            folder.mkdir();
        }

        File file = new File(folder.getAbsolutePath(), "data.json");

        if(!file.exists()){
            try{
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(defaultContent.getBytes());
                outputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private File getStandardFile(){
        File folder  = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "PatientRecordData");
        return new File(folder, "data.json");
    }

    private void populatePatientData() throws IOException{
        JsonReader reader;

        reader = new JsonReader(new FileReader(getStandardFile()));

        reader.beginObject();

        while(reader.hasNext()){
            reader.nextName();
            reader.beginObject();

            PatientDetail detail = new PatientDetail();

            for(int i = 0; i < 3 ;i++){
                switch (reader.nextName()){
                    case "name":
                        detail.setName(reader.nextString());
                        break;
                    case "id":
                        detail.setPatientID(reader.nextInt());
                        break;
                    case "heartState":{
                        switch (reader.nextString()){
                            case "normal":
                                detail.setState(HeartState.NORMAL);
                                break;
                            case "abnormal":
                                detail.setState(HeartState.ABNORMAL);
                                break;
                            case "not_checked":
                                detail.setState(HeartState.NOT_CHECKED);
                                break;
                        }
                    }
                        break;
                    default:
                        break;
                }
            }

            reader.endObject();

            mPatientListAdapter.addPatient(detail);
            mPatientListAdapter.notifyDataSetChanged();

        }
    }

    private void initPatientList(){
        mPatientListAdapter = new PatientListAdapter();
        mPatientListView = findViewById(R.id.patient_list);
        mPatientListView.setAdapter(mPatientListAdapter);
    }
}
