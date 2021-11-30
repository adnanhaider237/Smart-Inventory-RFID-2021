package com.muazam.tech.smartinventory20.Utils;

import android.app.AlertDialog;
import android.content.Context;

public class DialogClass {
    Context context;
    String txt, title;

    public DialogClass(Context context, String title, String txt) {
        this.context = context;
        this.txt = txt;
        this.title = title;
        show();
    }

    public void show() {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage(txt);
            dialog.setTitle(title);
            dialog.setPositiveButton("Ok",
                    (dialog1, which) -> dialog1.dismiss());
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        } catch (Exception ex) {
        }
    }
}