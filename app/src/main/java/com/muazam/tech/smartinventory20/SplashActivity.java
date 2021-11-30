package com.muazam.tech.smartinventory20;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.muazam.tech.smartinventory20.Utils.ConstVar;
import com.muazam.tech.smartinventory20.Utils.DialogClass;
import com.muazam.tech.smartinventory20.Utils.MyPrefs;

public class SplashActivity extends AppCompatActivity {
    MyPrefs prefs;
    Context context;

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            context = SplashActivity.this;
            img = findViewById(R.id.image);
            prefs = new MyPrefs(context);
            new Handler().postDelayed(() -> {
                try {
                    img.setImageResource(R.drawable.splash_two);
                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage());
                }
            }, 2000);
            Thread mSplashThread = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(4000);
                        }
                    } catch (InterruptedException ex) {
                        ex.fillInStackTrace();
                    }
                    try {
                        if (!prefs.get_Val(ConstVar.URL).equals("")) {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        } else {
                            Intent intent = new Intent(context, ConfigureActivity.class);
                            intent.putExtra(ConstVar.from, ConstVar.splash);
                            startActivity(intent);
                        }
                        finish();
                    } catch (Exception ex) {
                        new DialogClass(context, "Exception", ex.getMessage());
                    }
                }
            };
            mSplashThread.start();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }
}