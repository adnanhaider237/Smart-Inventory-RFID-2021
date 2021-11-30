package com.muazam.tech.smartinventory20.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetTime {
    public String GetTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
