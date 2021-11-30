package com.muazam.tech.smartinventory20;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muazam.tech.smartinventory20.Utils.AppPermission;
import com.muazam.tech.smartinventory20.Utils.CheckInternet;
import com.muazam.tech.smartinventory20.Utils.ConstVar;
import com.muazam.tech.smartinventory20.Utils.DialogClass;
import com.muazam.tech.smartinventory20.Utils.Dialog_Loading;
import com.muazam.tech.smartinventory20.Utils.MyPrefs;

public class ConfigureActivity extends AppCompatActivity {
    Context context;
    EditText et_identifier;
    ImageView img_identifier;
    AppPermission permission;
    Button btn_continue;
    Dialog_Loading loading;
    MyPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
        try {
            context = ConfigureActivity.this;
            Init();
            ClickListener();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }

    private void ClickListener() {
        try {
            btn_continue.setOnClickListener(view -> {
                if (!et_identifier.getText().toString().trim().equals("")) {
                    if (CheckInternet.CheckInternet(context)) {
                        CheckURL();
                    } else {
                        Toast.makeText(context, "No internet available", Toast.LENGTH_LONG).show();
                    }
                } else {
                    new DialogClass(context, "Message", "Please Enter Server Address");
                }
            });
            img_identifier.setOnClickListener(view -> {
                Intent intent = new Intent(ConfigureActivity.this, QrModuleActivity.class);
                intent.putExtra(ConstVar.from, ConstVar.scan_single);
                startActivityForResult(intent, 1);
            });
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }

    private void Init() {
        try {
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            context = ConfigureActivity.this;
            loading = new Dialog_Loading(context);
            prefs = new MyPrefs(context);
            et_identifier = findViewById(R.id.et_identifier);
            img_identifier = findViewById(R.id.img_identifier);
            permission = new AppPermission(context, ConfigureActivity.this);
            btn_continue = findViewById(R.id.btn_continue);
            et_identifier.setText(prefs.get_Val(ConstVar.URL));
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }

    private void CheckURL() {
        try {
            loading.show();
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    et_identifier.getText().toString().trim() + ConstVar.url_check,
                    response -> {
                        loading.dismiss();
                        prefs.put_Val(ConstVar.URL, et_identifier.getText().toString().trim());
                        if (getIntent().getStringExtra(ConstVar.from).equals(ConstVar.splash)) {
                            startActivity(new Intent(ConfigureActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            onBackPressed();
                        }
                    }, error -> {
                loading.dismiss();
                new DialogClass(context, "Message", "Wrong Connection String");
            });
            queue.add(stringRequest);
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                et_identifier.setText(data.getStringExtra("val"));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}