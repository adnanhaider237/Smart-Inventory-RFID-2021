package com.muazam.tech.smartinventory20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muazam.tech.smartinventory20.Model.ModelLogin;
import com.muazam.tech.smartinventory20.Model.ModelParams;
import com.muazam.tech.smartinventory20.Utils.AppPermission;
import com.muazam.tech.smartinventory20.Utils.CheckInternet;
import com.muazam.tech.smartinventory20.Utils.ConstVar;
import com.muazam.tech.smartinventory20.Utils.DatabaseHelper;
import com.muazam.tech.smartinventory20.Utils.DialogClass;
import com.muazam.tech.smartinventory20.Utils.Dialog_Loading;
import com.muazam.tech.smartinventory20.Utils.MyPrefs;
import com.muazam.tech.smartinventory20.Utils.ParamGetter;
import com.muazam.tech.smartinventory20.Utils.Sync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    Context context;
    Button btn_ok, btn_quit;
    EditText et_user_code, et_pwd;
    Dialog_Loading loading;
    MyPrefs prefs;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            Init();
            ClickListener();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }


    private void Init() {
        try {
            setContentView(R.layout.activity_login);
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            context = LoginActivity.this;
            new AppPermission(context, this);
            et_user_code = findViewById(R.id.et_user_code);
            et_pwd = findViewById(R.id.et_pwd);
            btn_ok = findViewById(R.id.btn_ok);
            loading = new Dialog_Loading(context);
            prefs = new MyPrefs(context);

            btn_quit = findViewById(R.id.btn_quit);
            db = new DatabaseHelper(context);
            prefs.put_Val(ConstVar.USER_CODE, "");
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }

    private void ClickListener() {
        btn_ok.setOnClickListener(view -> {
            if (CheckInternet.CheckInternet(context)) {
                funLogin();
            } else {
                Cursor cur = db.get_UserFile(et_user_code.getText().toString().trim(), et_pwd.getText().toString().trim());
                if (cur.getCount() > 0) {
                    startActivity(new Intent(context, LandingActivity.class));
                } else {
                    new DialogClass(context, "Message", "Wrong User Code/ Password");
                }
            }
        });
        btn_quit.setOnClickListener(view -> Exit());
    }

    private void Exit() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Confirm");
            builder.setMessage("Are you sure to Quit?");
            builder.setPositiveButton("YES", (dialog, which) -> {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            });
            builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
            AlertDialog alert = builder.create();

            alert.show();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }

    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);

    }


    private void funLogin() {
        try {
            if (!et_user_code.getText().toString().trim().equalsIgnoreCase("") &&
                    !et_pwd.getText().toString().trim().equalsIgnoreCase("")) {

                ArrayList<ModelLogin> lst_login = new ArrayList<>();
                lst_login.add(new ModelLogin(et_user_code.getText().toString().trim(),
                        et_pwd.getText().toString().trim()));
                loading.show();

                String url = prefs.get_Val(ConstVar.URL) + ConstVar.url_login;
                ArrayList<ModelParams> params = new ArrayList<>();
                params.add(new ModelParams(ConstVar.USER_CODE, et_user_code.getText().toString()));
                params.add(new ModelParams(ConstVar.USER_PASSWORD, et_pwd.getText().toString()));
                url += ParamGetter.getValue(params);
                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest sr = new StringRequest(Request.Method.GET,
                        url,
                        res -> {
                            try {
                                loading.dismiss();
                                res = res.trim();
                                String result = res.trim().replace("\\", "");
                                result = result.substring(1);
                                String vals = removeLastChar(result);
                                JSONObject object = new JSONObject(vals);

                                JSONArray jsonArray = object.getJSONArray("Table");

                                if (jsonArray.length() > 0) {
                                    Sync sync = new Sync(context);
                                    sync.SyncData(false);
                                    prefs.put_Val(ConstVar.USER_CODE, et_user_code.getText().toString());
                                    startActivity(new Intent(context, LandingActivity.class));
                                } else {
                                    new DialogClass(context, "Message", "Wrong User Code/ Password");
                                }
                            } catch (Exception ex) {
                                loading.dismiss();
                                new DialogClass(context, "Exception", ex.getMessage());
                            }
                        },
                        error -> {
                            try {
                                loading.dismiss();
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            } catch (Exception ex) {
                                loading.dismiss();
                                new DialogClass(context, "Exception", ex.getMessage());

                            }

                        }) {

                };
                queue.add(sr);


            } else {
                new DialogClass(context, "Message", "Enter User Code and Password");

            }
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}