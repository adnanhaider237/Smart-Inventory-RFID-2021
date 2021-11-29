package com.muazam.tech.smartinventoryrfid20;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muazam.tech.smartinventoryrfid20.Model.ModelLocMaster;
import com.muazam.tech.smartinventoryrfid20.Model.ModelSiteMaster;
import com.muazam.tech.smartinventoryrfid20.Model.ModelSmMaster;
import com.muazam.tech.smartinventoryrfid20.Utils.CheckInternet;
import com.muazam.tech.smartinventoryrfid20.Utils.ConstVar;
import com.muazam.tech.smartinventoryrfid20.Utils.DatabaseHelper;
import com.muazam.tech.smartinventoryrfid20.Utils.DeviceInfo;
import com.muazam.tech.smartinventoryrfid20.Utils.DialogClass;
import com.muazam.tech.smartinventoryrfid20.Utils.Dialog_Loading;
import com.muazam.tech.smartinventoryrfid20.Utils.GetTime;
import com.muazam.tech.smartinventoryrfid20.Utils.MyPrefs;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Context context;
    ImageView img_upload;
    Button btn_start, btn_cancel, btn_close;
    TextView tv_session, tv_site, tv_loc_code;
    ArrayList<ModelSiteMaster> lst_site;
    ArrayList<ModelLocMaster> lst_location;
    ArrayList<ModelSmMaster> lst_Sm;
    Dialog_Loading loading;
    MyPrefs prefs;
    DatabaseHelper db;
    public Handler handler;
    MediaPlayer mp;

    TextView tv_count;
    int count = 0;
    Thread thread;

    public void PlaySound() {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        sleep(500);
                        //mp.reset();
                        mp = MediaPlayer.create(MainActivity.this, R.raw.sound);
                        mp.start();
                        tv_count.setText(String.valueOf(count));
                        count++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            tv_count = findViewById(R.id.tv_count);
            Init();
            ClickListener();
            //mp = MediaPlayer.create(MainActivity.this, R.raw.sound);
            //PlaySound();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    private void Init() {
        try {
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            context = MainActivity.this;

            img_upload = findViewById(R.id.img_upload);

            btn_start = findViewById(R.id.btn_start);
            btn_cancel = findViewById(R.id.btn_cancel);
            btn_close = findViewById(R.id.btn_close);

            tv_session = findViewById(R.id.tv_session);
            tv_site = findViewById(R.id.tv_site);
            tv_loc_code = findViewById(R.id.tv_loc_code);

            lst_site = new ArrayList<>();
            lst_Sm = new ArrayList<>();
            loading = new Dialog_Loading(context);
            prefs = new MyPrefs(context);
            db = new DatabaseHelper(context);
            if (!prefs.get_Val(ConstVar.branch).equals("")) {
                tv_site.setText(prefs.get_Val(ConstVar.branch));
                tv_site.setEnabled(false);
            }
            if (!prefs.get_Val(ConstVar.loc_code).equals("")) {
                tv_loc_code.setText(prefs.get_Val(ConstVar.loc_code));
                tv_loc_code.setEnabled(false);
            }
            tv_session.setText(db.get_SessionNum());
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }


    private void ClickListener() {
        try {
            img_upload.setOnClickListener(v -> {
                try {
                    if (CheckInternet.CheckInternet(context)) {
                        db.Upload();
                    } else {
                        Toast.makeText(context, "No internet available", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
            btn_start.setOnClickListener(v -> {
                try {
                    if (!tv_loc_code.getText().toString().trim().equals("") &&
                            !tv_site.getText().toString().trim().equals("")) {
                        fun_start();
                    } else {
                        new DialogClass(context, "Message", "Select Site/Location Code");
                    }
                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
            btn_cancel.setOnClickListener(v -> {
                try {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage(context.getString(R.string.cancel_session_message));
                    dialog.setTitle("Message");
                    dialog.setPositiveButton("Ok",
                            (dialog1, which) -> {
                                dialog1.dismiss();
                                fun_cancel();
                                tv_session.setText(db.get_SessionNum());
                            });
                    dialog.setNegativeButton("No",
                            (dialog1, which) -> {
                                dialog1.dismiss();
                            });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
            btn_close.setOnClickListener(v -> {
                try {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage(context.getString(R.string.cancel_session_message));
                    dialog.setTitle("Message");
                    dialog.setPositiveButton("Ok",
                            (dialog1, which) -> {
                                dialog1.dismiss();
                                fun_close();
                                tv_session.setText(db.get_SessionNum());
                            });
                    dialog.setNegativeButton("No",
                            (dialog1, which) -> {
                                dialog1.dismiss();
                            });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();

                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });

            tv_site.setOnClickListener(v -> {
                try {
                    fun_getSite();
                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
            tv_loc_code.setOnClickListener(v -> {
                try {
                    fun_getLocCode();
                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }


    private void fun_cancel() {
        try {
            tv_site.setEnabled(true);
            tv_loc_code.setEnabled(true);
            if (db.DataExistForSession(tv_session.getText().toString().trim())) {
                db.CancelInventory(tv_session.getText().toString().trim());
            }
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    private void fun_close() {
        try {
            tv_site.setEnabled(true);
            tv_loc_code.setEnabled(true);
            db.CloseInventory(tv_session.getText().toString().trim());
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    private void fun_start() {
        try {
            tv_site.setEnabled(false);
            tv_loc_code.setEnabled(false);
            /*if (tv_session.getText().toString().trim().equals("0")) {
                if (!db.checkSession().equals("0") || !db.checkSession().equals("")) {
                    db.insert_table_DeviceConfig(
                            "1",
                            "",
                            "" + new DeviceInfo(context).getDeviceID(),
                            "" + new DeviceInfo(context).getDeviceModel(),
                            "" + new DeviceInfo(context).getDeviceModel());
                    db.insert_PhyHdr(1,
                            "" + new DeviceInfo(context).getDeviceID(),
                            "" + prefs.get_Val(ConstVar.USER_CODE),
                            "" + tv_loc_code.getText().toString().trim(),
                            "" + tv_site.getText().toString().trim(),
                            "" + new GetTime().GetTime(),
                            "",
                            "" + ConstVar.open);
                } else {
                    db.insert_table_DeviceConfig(
                            "" + db.checkSession(),
                            "",
                            "" + new DeviceInfo(context).getDeviceID(),
                            "" + new DeviceInfo(context).getDeviceModel(),
                            "" + new DeviceInfo(context).getDeviceModel());
                    db.insert_PhyHdr(Integer.parseInt(db.checkSession()),
                            "" + new DeviceInfo(context).getDeviceID(),
                            "" + prefs.get_Val(ConstVar.USER_CODE),
                            "" + tv_loc_code.getText().toString().trim(),
                            "" + tv_site.getText().toString().trim(),
                            "" + new GetTime().GetTime(),
                            "",
                            "" + ConstVar.open);
                }
            } else*/
            {
                if (!db.IfSessionOpen(tv_session.getText().toString().trim())) {
                    /*int session = Integer.parseInt(tv_session.getText().toString().trim());
                    session = session + 1;*/
                    db.insert_PhyHdr(Integer.parseInt(tv_session.getText().toString().trim()),
                            "" + new DeviceInfo(context).getDeviceID(),
                            "" + prefs.get_Val(ConstVar.USER_CODE),
                            "" + tv_loc_code.getText().toString().trim(),
                            "" + tv_site.getText().toString().trim(),
                            "" + new GetTime().GetTime(),
                            "",
                            "" + ConstVar.open);
                    //db.Update_Device(session + "");
                }
            }
            //getMaxID();
            Next();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }


    private void Next() {
        try {
            Intent intent = new Intent(this, InvoiceEntryActivity.class);
            intent.putExtra(ConstVar.session, tv_session.getText().toString().trim());
            intent.putExtra(ConstVar.site, tv_site.getText().toString().trim());
            intent.putExtra(ConstVar.loc, tv_loc_code.getText().toString().trim());
            startActivity(intent);
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }


    private void fun_getSite() {
        try {
            DatabaseHelper db = new DatabaseHelper(context);
            Cursor cursor = db.get_SiteMaster();
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                lst_site = new ArrayList<>();
                do {
                    lst_site.add(new ModelSiteMaster(
                            "" + cursor.getString(0),
                            "" + cursor.getString(1),
                            "" + cursor.getString(2),
                            "" + cursor.getString(3)));
                } while (cursor.moveToNext());
                String[] list = new String[lst_site.size()];

                for (int i = 0; i < lst_site.size(); i++) {
                    list[i] = lst_site.get(i).getSiteCode();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Site Code");

                builder.setItems(list, (dialog, which) -> {
                    if (!tv_site.getText().toString().trim().equals(list[which])) {
                        prefs.put_Val(ConstVar.branch, list[which]);
                        tv_site.setText(list[which]);
                        tv_loc_code.setText("");
                        prefs.put_Val(ConstVar.loc_code, "");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                new DialogClass(context, "Message", "Download the data from server first").show();
            }
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    private void fun_getLocCode() {
        try {
            DatabaseHelper db = new DatabaseHelper(context);
            Cursor cursor = db.get_LocMaster();
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                lst_location = new ArrayList<>();
                do {
                    if (cursor.getString(2).equals(prefs.get_Val(ConstVar.branch))) {
                        lst_location.add(new ModelLocMaster(
                                "" + cursor.getString(0),
                                "" + cursor.getString(1),
                                "" + cursor.getString(2),
                                "" + cursor.getString(3),
                                "" + cursor.getString(4),
                                "" + cursor.getString(5),
                                "" + cursor.getString(6)));
                    }
                } while (cursor.moveToNext());

                String[] list = new String[lst_location.size()];

                for (int i = 0; i < lst_location.size(); i++) {
                    list[i] = lst_location.get(i).getLocNameE() + ":" + lst_location.get(i).getLocCode();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Location Code");

                builder.setItems(list, (dialog, which) -> {
                    tv_loc_code.setText(list[which]);
                    prefs.put_Val(ConstVar.loc_code, list[which]);
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                new DialogClass(context, "Message", "Download the data from server first").show();
            }
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}