package com.muazam.tech.smartinventory20.Utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.util.ArrayList;

public class DeviceInfo {
    Context context;
    ArrayList<String> lst_device;

    public DeviceInfo(Context context) {
        this.context = context;
    }

    public ArrayList<String> getID() {
        try {
            lst_device = new ArrayList<>();
            lst_device.add(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
            lst_device.add(Build.MANUFACTURER + " " + Build.MODEL);

        } catch (Exception ex) {
            return lst_device;
        }
        return lst_device;
    }

    public String getDeviceID() {
        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
            return "";
        }
    }

    public String getDeviceModel() {
        try {
            return Build.MANUFACTURER + " " + Build.MODEL;
        } catch (Exception ex) {
            return "";
        }
    }
}
