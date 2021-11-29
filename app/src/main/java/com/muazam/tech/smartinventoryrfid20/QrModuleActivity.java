package com.muazam.tech.smartinventoryrfid20;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;
import com.muazam.tech.smartinventoryrfid20.Utils.ConstVar;
import com.muazam.tech.smartinventoryrfid20.Utils.GSON_Module;

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


        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);
        zXingScannerView.setAutoFocus(true);

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

            val = rawResult.getText();
            onBackPressed();

        }
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