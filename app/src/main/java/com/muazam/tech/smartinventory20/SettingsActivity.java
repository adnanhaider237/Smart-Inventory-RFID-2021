package com.muazam.tech.smartinventory20;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muazam.tech.smartinventory20.Utils.DatabaseHelper;
import com.muazam.tech.smartinventory20.Utils.DialogClass;

public class SettingsActivity extends AppCompatActivity {

    Context context;
    Button btn_ok;
    ImageView img_minus, img_plus;
    TextView tv_beep;
    int beep_delay = 500;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
            context = SettingsActivity.this;
            btn_ok = findViewById(R.id.btn_ok);
            img_minus = findViewById(R.id.img_minus);
            img_plus = findViewById(R.id.img_plus);
            tv_beep = findViewById(R.id.tv_beep);

            db = new DatabaseHelper(this);
            String beep = db.getBeep();
            if (!beep.isEmpty()) {
                if (!beep.equalsIgnoreCase("0")) {
                    tv_beep.setText(beep);
                    beep_delay = Integer.parseInt(beep);
                } else {
                    tv_beep.setText("500");
                    beep_delay = 500;
                }
            }
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    private void ClickListener() {
        try {
            btn_ok.setOnClickListener(v -> {
                try {
                    db.insert_table_Beep(String.valueOf(beep_delay));
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
            img_plus.setOnClickListener(v -> {
                try {
                    beep_delay += 100;
                    tv_beep.setText(String.valueOf(beep_delay));

                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
            img_minus.setOnClickListener(v -> {
                try {
                    if (beep_delay > 500) {
                        beep_delay -= 100;
                        tv_beep.setText(String.valueOf(beep_delay));
                    } else {
                        Toast.makeText(context, "Not less than 500", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    new DialogClass(context, "Exception", ex.getMessage()).show();
                }
            });
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}