package com.muazam.tech.smartinventoryrfid20.Utils;

import android.app.Activity;
import android.view.View;

public class OnCreateFunctions {
    public OnCreateFunctions(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
