package com.muazam.tech.smartinventory20;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muazam.tech.smartinventory20.Utils.CheckInternet;
import com.muazam.tech.smartinventory20.Utils.DatabaseHelper;
import com.muazam.tech.smartinventory20.Utils.DialogClass;
import com.muazam.tech.smartinventory20.Utils.Dialog_Loading;
import com.muazam.tech.smartinventory20.Utils.MyPrefs;
import com.muazam.tech.smartinventory20.Utils.Sync;

public class LandingActivity extends AppCompatActivity {
    Context context;
    Button btn_inventory, btn_single, btn_sync, btn_view_inventory, btn_Physical_Inventory_Doc_Create;
    Dialog_Loading loading;
    MyPrefs prefs;
    DatabaseHelper db;
    ImageView img_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
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
            context = LandingActivity.this;
            img_settings = findViewById(R.id.img_settings);
            btn_inventory = findViewById(R.id.btn_inventory);
            btn_single = findViewById(R.id.btn_single);
            btn_sync = findViewById(R.id.btn_sync);
            btn_view_inventory = findViewById(R.id.btn_view_data);
            btn_Physical_Inventory_Doc_Create = findViewById(R.id.btn_Physical_Inventory_Doc_Create);
            loading = new Dialog_Loading(context);
            prefs = new MyPrefs(context);
            db = new DatabaseHelper(context);
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    private void ClickListener() {
        try {
            img_settings.setOnClickListener(v -> {
                startActivity(new Intent(LandingActivity.this, SettingsActivity.class));
            });
            btn_inventory.setOnClickListener(v -> {
                startActivity(new Intent(LandingActivity.this, MainActivity.class));
            });
            btn_single.setOnClickListener(v -> {
                startActivity(new Intent(LandingActivity.this, SingleTagActivity.class));
            });
            btn_sync.setOnClickListener(v -> {
                if (CheckInternet.CheckInternet(context)) {
                    Sync sync = new Sync(context);
                    sync.SyncData(true);
                } else {
                    Toast.makeText(context, "No internet available", Toast.LENGTH_LONG).show();
                }
            });
            btn_view_inventory.setOnClickListener(v -> {
                startActivity(new Intent(LandingActivity.this, InventoryDataActivity.class));
            });
            btn_Physical_Inventory_Doc_Create.setOnClickListener(v -> {
                startActivity(new Intent(LandingActivity.this, PhysicalInventoryDocCreateActivity.class));
            });
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }
}