package com.muazam.tech.smartinventoryrfid20.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternet {
    public static boolean CheckInternet(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager)context .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }
}
