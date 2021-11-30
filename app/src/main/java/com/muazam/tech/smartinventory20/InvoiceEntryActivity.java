package com.muazam.tech.smartinventory20;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muazam.tech.smartinventory20.Utils.ConstVar;
import com.muazam.tech.smartinventory20.Utils.DatabaseHelper;
import com.muazam.tech.smartinventory20.Utils.DeviceInfo;
import com.muazam.tech.smartinventory20.Utils.DialogClass;
import com.muazam.tech.smartinventory20.Utils.Dialog_Loading;
import com.muazam.tech.smartinventory20.Utils.GSON_Module;
import com.muazam.tech.smartinventory20.Utils.MyPrefs;
import com.rscja.deviceapi.RFIDWithUHFUART;
import com.rscja.deviceapi.entity.UHFTAGInfo;

public class InvoiceEntryActivity extends AppCompatActivity {
    Context context;
    Button btn_start, btn_view_tags, btn_stop;
    TextView tv_session, tv_date, tv_site, tv_location, tv_no_of_tags;

    Dialog_Loading loading;
    MyPrefs prefs;


    GSON_Module gson;
    String session, site, loc;
    DatabaseHelper db;
    RFIDWithUHFUART mReader = null;
    public Handler handler;
    boolean loopFlag = false;
    MediaPlayer mp;
    int beep_delay = 500;
    String LOCATION_CODE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_entry);
        try {
            Init();
            ClickListener();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    private void Init() {
        try {
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            context = InvoiceEntryActivity.this;
            prefs = new MyPrefs(context);

            btn_start = findViewById(R.id.btn_start);
            btn_view_tags = findViewById(R.id.btn_view_tags);
            btn_stop = findViewById(R.id.btn_stop);

            tv_session = findViewById(R.id.tv_session);
            tv_date = findViewById(R.id.tv_date);
            tv_site = findViewById(R.id.tv_site);
            tv_location = findViewById(R.id.tv_location);
            tv_no_of_tags = findViewById(R.id.tv_no_of_tags);

            loading = new Dialog_Loading(context);
            db = new DatabaseHelper(context);
            String beep = db.getBeep();
            if (!beep.isEmpty()) {
                if (!beep.equalsIgnoreCase("0")) {
                    beep_delay = Integer.parseInt(beep);
                } else {
                    beep_delay = 500;
                }
            }

            gson = new GSON_Module();

            session = getIntent().getStringExtra(ConstVar.session);
            site = getIntent().getStringExtra(ConstVar.site);
            loc = getIntent().getStringExtra(ConstVar.loc);
            if (getIntent().getStringExtra(ConstVar.loc).contains(":")) {
                String a[] = getIntent().getStringExtra(ConstVar.loc).split(":");
                LOCATION_CODE = a[1].trim();
            } else {
                LOCATION_CODE = getIntent().getStringExtra(ConstVar.loc);
            }
            tv_session.setText(getIntent().getStringExtra(ConstVar.session));
            tv_date.setText(db.GetDate(tv_session.getText().toString().trim()));
            tv_site.setText(getIntent().getStringExtra(ConstVar.site));
            tv_location.setText(getIntent().getStringExtra(ConstVar.loc));
            tv_no_of_tags.setText(db.GetCount(tv_session.getText().toString().trim(),
                    LOCATION_CODE,
                    getIntent().getStringExtra(ConstVar.site)));
            try {

                mReader = RFIDWithUHFUART.getInstance();
            } catch (Exception ex) {
                return;
            }
            if (mReader != null) {
                new InitTask().execute();
            }
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String result = msg.obj + "";
                    addUnitID(result);
                }
            };
            mp = MediaPlayer.create(InvoiceEntryActivity.this, R.raw.sound);
            PlaySound();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    int previous = 0;
    int current = 0;

    public void PlaySound() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        sleep(beep_delay);
                        current = Integer.parseInt(tv_no_of_tags.getText().toString());
                        if (current > previous) {
                            mp.reset();
                            mp = MediaPlayer.create(InvoiceEntryActivity.this, R.raw.sound);
                            mp.start();
                            previous = Integer.parseInt(tv_no_of_tags.getText().toString());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }


    public void addUnitID(String unitID) {
        /*mp = MediaPlayer.create(this, R.raw.sound);
        mp.start();*/
        String LOCATION_CODE="";
        if (getIntent().getStringExtra(ConstVar.loc).contains(":")) {
            String a[] = getIntent().getStringExtra(ConstVar.loc).split(":");
            LOCATION_CODE = a[1].trim();
        } else {
            LOCATION_CODE = getIntent().getStringExtra(ConstVar.loc);
        }
        if (!db.TagExist(
                "" + getIntent().getStringExtra(ConstVar.session),
                "" + getIntent().getStringExtra(ConstVar.site),
                "" + LOCATION_CODE,
                "" + unitID)) {
            db.insert_PhyInvItem(
                    "" + new DeviceInfo(getApplicationContext()).getDeviceID(),
                    "" + getIntent().getStringExtra(ConstVar.session),
                    "" + getIntent().getStringExtra(ConstVar.site),
                    "" + LOCATION_CODE,
                    "" + unitID);
            tv_no_of_tags.setText(db.GetCount(tv_session.getText().toString().trim(),
                    LOCATION_CODE,
                    getIntent().getStringExtra(ConstVar.site)));

        }

    }

    private void ClickListener() {
        try {
            btn_start.setOnClickListener(v -> {
                try {
                    loopFlag = true;
                    Toast.makeText(context, "StartScan() : RFID Started", Toast.LENGTH_SHORT).show();
                    if (mReader.startInventoryTag()) {
                        new TagThread().start();
                    }
                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
            btn_stop.setOnClickListener(view -> {
                try {
                    if (mReader != null) {
                        mReader.stopInventory();
                        loopFlag = false;
                        Toast.makeText(context, "RFID Stopped", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
            btn_view_tags.setOnClickListener(v -> {
                try {
                    if (mReader != null) {
                        mReader.free();
                        Toast.makeText(context, "RFID Stopped", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(InvoiceEntryActivity.this, TagsListActivity.class);
                    intent.putExtra(ConstVar.session, tv_session.getText().toString().trim());
                    startActivity(intent);
                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }


    class TagThread extends Thread {
        public void run() {
            String strTid;
            String strResult;
            UHFTAGInfo res = null;
            while (loopFlag) {
                res = mReader.readTagFromBuffer();
                if (res != null) {
                    strTid = res.getTid();
                    if (strTid.length() != 0 && !strTid.equals("0000000" +
                            "000000000") && !strTid.equals("000000000000000000000000")) {
                        strResult = "TID:" + strTid + "\n";
                    } else {
                        strResult = "";
                    }
                    Log.i("data", "EPC:" + res.getEPC() + "|" + strResult);
                    Message msg = handler.obtainMessage();
                    msg.obj = strResult + res.getEPC();

                    handler.sendMessage(msg);
                }
            }
        }
    }

    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        Dialog_Loading loading;

        @Override
        public Boolean doInBackground(String... params) {

            mReader.init();
            return true;
        }

        @Override
        public void onPostExecute(Boolean reuslt) {
            super.onPostExecute(reuslt);
            loading.dismiss();
            loopFlag = true;
            Toast.makeText(context, "InitTask : RFID powered on", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            try {
                loading = new Dialog_Loading(InvoiceEntryActivity.this);
                loading.show();
            } catch (Exception e) {

            }

        }
    }


    @Override
    protected void onDestroy() {
        if (mReader != null) {
            mReader.free();
            Toast.makeText(context, "onDestroy : RFID Stopped", Toast.LENGTH_SHORT).show();

        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mReader != null) {
            mReader.free();
            Toast.makeText(context, "onBackPressed : RFID Stopped", Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}