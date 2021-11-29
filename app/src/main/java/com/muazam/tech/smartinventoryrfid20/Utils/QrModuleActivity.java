package com.muazam.tech.smartinventoryrfid20.Utils;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;
import com.muazam.tech.smartinventoryrfid20.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrModuleActivity extends AppCompatActivity implements
        ZXingScannerView.ResultHandler {
    ZXingScannerView zXingScannerView;
    MediaPlayer mp;

    GSON_Module gson;
    String val;
    Snackbar snackbar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_module);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


        gson = new GSON_Module();
        if (!getIntent().getStringExtra(ConstVar.from).equals(ConstVar.scan_single)) {
            ShowSnackBar();
        }

        db = new DatabaseHelper(getApplicationContext());

        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);
        zXingScannerView.setAutoFocus(true);

    }

    public void ShowSnackBar() {
        snackbar = Snackbar
                .make(findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE);

        View snackbarLayout = snackbar.getView();
        snackbarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins(0, 0, 0, 0);
        snackbarLayout.setLayoutParams(lp);
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult != null) {
            mp = MediaPlayer.create(this, R.raw.sound);
            mp.start();
            if (getIntent().getStringExtra(ConstVar.from).equals(ConstVar.scan_list)) {
                if (!db.TagExist("" + getIntent().getStringExtra(ConstVar.session),
                        "" + getIntent().getStringExtra(ConstVar.site),
                        "" + getIntent().getStringExtra(ConstVar.loc),
                        "" + (rawResult.getText()))) {
                    textView.setText(rawResult.getText());
                    addUnitID(rawResult.getText());
                }
                zXingScannerView.resumeCameraPreview(this);
            } else if (getIntent().getStringExtra(ConstVar.from).equals(ConstVar.scan_single)) {
                val = rawResult.getText();
                onBackPressed();
            }
        }
    }

    DatabaseHelper db;

    public void addUnitID(String unitID) {

        db.insert_PhyInvItem(
                "" + new DeviceInfo(getApplicationContext()).getDeviceID(),
                "" + getIntent().getStringExtra(ConstVar.session),
                "" + getIntent().getStringExtra(ConstVar.site),
                "" + getIntent().getStringExtra(ConstVar.loc),
                "" + unitID);
    }

    @Override
    protected void onDestroy() {
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (getIntent().getStringExtra(ConstVar.from).equals(ConstVar.scan_single)) {
            intent.putExtra(ConstVar.val, val);
        }
        setResult(RESULT_OK, intent);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        finish();
    }
}
