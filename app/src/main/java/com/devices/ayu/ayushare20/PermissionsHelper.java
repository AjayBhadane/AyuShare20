package com.devices.ayu.ayushare20;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

public class PermissionsHelper {
    private static final String TAG = "PermissionsHelper";

    private static final String[] REQUIRED_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_ALLOW = 0;


    private static String[] getRequiredPermissions(final Context context, String[] requiredPermissions){
        ArrayList<String> pendingPermissions = new ArrayList<>();

        for(String permission: requiredPermissions){
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                pendingPermissions.add(permission);
        }

        return pendingPermissions.toArray(new String[pendingPermissions.size()]);
    }

    public static void askPermissions(final Context context){

        String[] permissions = getRequiredPermissions(context, REQUIRED_PERMISSIONS);

        if(permissions.length > 0)
            ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_ALLOW);
    }
}
