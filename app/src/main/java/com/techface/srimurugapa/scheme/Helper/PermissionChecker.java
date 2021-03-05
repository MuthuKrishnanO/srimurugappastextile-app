package com.techface.srimurugapa.scheme.Helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;


public class PermissionChecker {
    public static String[] REQUIRED_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * GPS Permission
     */
    public static String[] GPS_PERMISSION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    /**
     * Context
     */
    private final Context context;

    public PermissionChecker(Context context) {
        this.context = context;
    }

    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }
}
