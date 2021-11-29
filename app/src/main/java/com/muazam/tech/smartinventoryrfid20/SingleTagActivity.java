package com.muazam.tech.smartinventoryrfid20;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muazam.tech.smartinventoryrfid20.Utils.ConstVar;
import com.muazam.tech.smartinventoryrfid20.Utils.DialogClass;
import com.muazam.tech.smartinventoryrfid20.Utils.Dialog_Loading;
import com.muazam.tech.smartinventoryrfid20.Utils.MyPrefs;

import org.json.JSONArray;
import org.json.JSONObject;

public class SingleTagActivity extends AppCompatActivity {

    private Button btnScan, btn_clear, btnGetDetail;
    private TextView tv_ascii, tv_status, tv_item_code, tv_item_name, tv_alt_item_code, tv_batch_number, tv_expiry_date, tv_location, tv_site_name, tv_configure;
    private EditText et_tag;

    MyPrefs prefs;
    ImageView img_scan;
    Context context;
    Dialog_Loading loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tag);
        try {
            Init();
            ClickListener();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }

    private void Init() {
        try {
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            context =SingleTagActivity.this;
            prefs = new MyPrefs(context);

            btnGetDetail = findViewById(R.id.btnGetDetail);
            btnScan = findViewById(R.id.btnScan);
            btn_clear = findViewById(R.id.btnClear);

            img_scan = findViewById(R.id.img_scan);

            tv_ascii = findViewById(R.id.tv_ascii);
            tv_status = findViewById(R.id.tv_status);
            tv_item_code = findViewById(R.id.tv_item_code);
            tv_item_name = findViewById(R.id.tv_item_name);
            tv_alt_item_code = findViewById(R.id.tv_alt_item_code);
            tv_batch_number = findViewById(R.id.tv_batch_number);
            tv_expiry_date = findViewById(R.id.tv_expiry_date);
            tv_location = findViewById(R.id.tv_location);
            tv_site_name = findViewById(R.id.tv_site_name);
            tv_configure = findViewById(R.id.tv_configure);

            et_tag = findViewById(R.id.et_tag);

        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }

    private void ClickListener() {
        try {
            img_scan.setOnClickListener(view -> {
                Intent intent = new Intent(SingleTagActivity.this, QrModuleActivity.class);
                startActivityForResult(intent, 1);
            });
            tv_configure.setOnClickListener(view -> {
                Intent intent = new Intent(SingleTagActivity.this, ConfigureActivity.class);
                intent.putExtra(ConstVar.from, "aa");
                startActivity(intent);
            });

            btnGetDetail.setOnClickListener(view -> {
                if (!et_tag.getText().toString().trim().equals("")) {
                    if (!prefs.get_Val(ConstVar.URL).equals("")) {
                        funGetData();
                    } else {
                        Toast.makeText(context, "Set the URL First", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Scan the Tag first", Toast.LENGTH_SHORT).show();
                }
            });
            btnScan.setOnClickListener(view -> {
                Intent intent = new Intent(SingleTagActivity.this, QrModuleActivity.class);
                intent.putExtra(ConstVar.from, ConstVar.scan_single);
                startActivityForResult(intent, 1);
                //et_tag.setText("524131303030303030303536");
            });
            et_tag.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    tv_ascii.setText(hexToAscii(et_tag.getText().toString().trim()));
                }
            });
            btn_clear.setOnClickListener(view -> {
                fun_clear();
            });
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                et_tag.setText(data.getStringExtra("val"));
                tv_ascii.setText(hexToAscii(et_tag.getText().toString().trim()));
            }
        }
    }

    private void fun_clear() {
        tv_status.setText("");
        tv_ascii.setText("");
        et_tag.setText("");
        tv_item_code.setText("");
        tv_item_name.setText("");
        tv_alt_item_code.setText("");
        tv_batch_number.setText("");
        tv_expiry_date.setText("");
        tv_site_name.setText("");
        tv_location.setText("");
    }

    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");
        try {
            for (int i = 0; i < hexStr.length(); i += 2) {
                String str = hexStr.substring(i, i + 2);
                output.append((char) Integer.parseInt(str, 16));
            }
        } catch (Exception ex) {
            return "";
        }
        return output.toString();
    }

    private void funGetData() {
        try {
            if (!et_tag.getText().toString().trim().equalsIgnoreCase("")) {
                loading = new Dialog_Loading(context);
                loading.show();

                String url = prefs.get_Val(ConstVar.URL) +
                        ConstVar.url_TagDetail + et_tag.getText().toString().trim();

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
                                    tv_status.setText("Valid Tag");
                                    JSONObject j_Object = jsonArray.getJSONObject(0);
                                    tv_item_code.setText(j_Object.getString("ItemCode"));
                                    tv_item_name.setText(j_Object.getString("ItemNameE"));
                                    tv_alt_item_code.setText(j_Object.getString("ItemCode"));
                                    tv_batch_number.setText(j_Object.getString("BatchNum"));
                                    String[] val = j_Object.getString("ExpiryDate").split("T");
                                    tv_expiry_date.setText(val[0]);
                                    tv_site_name.setText(j_Object.getString("SiteNameE"));
                                    tv_location.setText(j_Object.getString("LocNameE"));
                                } else {
                                    tv_status.setText("Invalid Tag");
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