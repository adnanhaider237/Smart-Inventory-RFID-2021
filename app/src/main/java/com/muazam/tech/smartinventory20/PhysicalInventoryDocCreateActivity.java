package com.muazam.tech.smartinventory20;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ParseException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.muazam.tech.smartinventory20.Adapter.AdapterPhyInvHdr;
import com.muazam.tech.smartinventory20.Model.ModelParams;
import com.muazam.tech.smartinventory20.Model.ModelPhyInvHdr;
import com.muazam.tech.smartinventory20.Model.ModelPhyInvHeader;
import com.muazam.tech.smartinventory20.Utils.ConstVar;
import com.muazam.tech.smartinventory20.Utils.DialogClass;
import com.muazam.tech.smartinventory20.Utils.Dialog_Loading;
import com.muazam.tech.smartinventory20.Utils.MyPrefs;
import com.muazam.tech.smartinventory20.Utils.ParamGetter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PhysicalInventoryDocCreateActivity extends AppCompatActivity {

    TextView tv_date_g, et_site_code, tv_docNum;
    Button btn_Save;
    ImageView img_Search;
    Dialog_Loading loading;
    MyPrefs prefs;
    ArrayList<ModelPhyInvHdr> lst_data;
    String SiteNameE, CusCode, CusName;
    RecyclerView recyclerView;
    AdapterPhyInvHdr adapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_inventory_doc_create);
        try {
            Init();
            ClickListener();
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void Init() {
        try {
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            context = this;
            tv_date_g = findViewById(R.id.tv_date_g);
            et_site_code = findViewById(R.id.et_site_code);
            tv_docNum = findViewById(R.id.tv_docNum);

            btn_Save = findViewById(R.id.btn_Save);
            img_Search = findViewById(R.id.img_Search);
            loading = new Dialog_Loading(this);
            prefs = new MyPrefs(this);
            lst_data = new ArrayList<>();
            recyclerView = findViewById(R.id.recyclerView);
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void ClickListener() {
        try {
            img_Search.setOnClickListener(v -> {
                try {
                    if(!et_site_code.getText().toString().equals("")){
                        loading.show();
                        String url = prefs.get_Val(ConstVar.URL) + ConstVar.GetDataOfSite;
                        ArrayList<ModelParams> params = new ArrayList<>();
                        params.add(new ModelParams(ConstVar.SiteCode, et_site_code.getText().toString()));
                        url += ParamGetter.getValue(params);

                        RequestQueue queue = Volley.newRequestQueue(this);
                        StringRequest sr = new StringRequest(Request.Method.GET,
                                url,
                                res -> {
                                    try {
                                        loading.dismiss();
                                        //String result = new GetJSONResponse().GetJSONResponse(res);

                                        JSONObject jObjResponse = new JSONObject(res);

                                        String status = jObjResponse.getString("Status");

                                        JSONObject jsonStatus = new JSONObject(status);
                                        String statusCode = jsonStatus.getString("StatusCode");
                                        String StatusDescription = jsonStatus.getString("StatusDescription");

                                        if (statusCode.equals("0")) {
                                            Toast.makeText(this, StatusDescription, Toast.LENGTH_SHORT).show();
                                        } else if (statusCode.equals("1")) {
                                            String Table = jObjResponse.getString("JSONData");
                                            JSONObject jObjResponse_1 = new JSONObject(Table);

                                            JSONArray jsonArray_PhyInvHdr = jObjResponse_1.getJSONArray("TablePhyInvHdr");
                                            JSONArray jsonArray_SiteMaster = jObjResponse_1.getJSONArray("TableSiteMaster");

                                            JSONObject j_Object_siteMaster = jsonArray_SiteMaster.getJSONObject(0);
                                            SiteNameE = j_Object_siteMaster.getString("SiteNameE");
                                            CusCode = j_Object_siteMaster.getString("CusCode");
                                            CusName = j_Object_siteMaster.getString("CusNameE");

                                            lst_data = new ArrayList<>();
                                            for (int i = 0; i < jsonArray_PhyInvHdr.length(); i++) {
                                                JSONObject j_Object = jsonArray_PhyInvHdr.getJSONObject(i);
                                                lst_data.add(new ModelPhyInvHdr(
                                                        "" + j_Object.getString("PhyInvSessionNum"),
                                                        "" + j_Object.getString("DeviceId"),
                                                        "" + j_Object.getString("DeviceCode"),
                                                        "" + j_Object.getString("PhyInvDocNum"),
                                                        "" + j_Object.getString("SiteCode"),
                                                        "" + j_Object.getString("StartTime"),
                                                        "" + j_Object.getString("PostingDate"),
                                                        "" + j_Object.getString("CloseTime"),
                                                        "" + j_Object.getString("StatusCode"),
                                                        "" + j_Object.getString("Status"),
                                                        "" + j_Object.getString("BrCode"),
                                                        "" + j_Object.getString("PhyInvHdrId"),
                                                        ""));
                                            }
                                            adapter = new AdapterPhyInvHdr(this, lst_data);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                                            recyclerView.setAdapter(adapter);
                                            btn_Save.setEnabled(true);
                                        }


                                    } catch (Exception ex) {
                                        loading.dismiss();
                                        new DialogClass(this, "Exception", ex.getMessage());
                                    }

                                },
                                error -> {
                                    try {
                                        loading.dismiss();
                                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    } catch (Exception ex) {
                                        loading.dismiss();
                                        new DialogClass(this, "Exception", ex.getMessage());

                                    }

                                }) {
                        };
                        queue.add(sr);
                    }else{
                        Toast.makeText(context, "Please select Doc Num", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    new DialogClass(this, "Exception", ex.getMessage());
                }
            });
            tv_date_g.setOnClickListener(v -> {
                try {
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    tv_date_g.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                } catch (Exception ex) {
                    new DialogClass(this, "Exception", ex.getMessage());
                }

            });
            btn_Save.setOnClickListener(v -> {
                try {
                    if (lst_data.size() > 0) {
                        if (!tv_date_g.getText().toString().equals("")) {
                            ArrayList<ModelPhyInvHeader> lstPhyHeader = new ArrayList<>();
                            lstPhyHeader.add(new ModelPhyInvHeader(
                                    "" + et_site_code.getText().toString(),
                                    prefs.get_Val(ConstVar.USER_CODE) + "",
                                    "" + formatdate(tv_date_g.getText().toString())));
                            JsonArray jsonArrayHeader = new Gson().toJsonTree(lstPhyHeader).getAsJsonArray();
                            JsonArray jsonArrayHdr = new Gson().toJsonTree(lst_data).getAsJsonArray();
                            JsonObject parent = new JsonObject();
                            parent.add("PhysicalInventoryHdr", jsonArrayHeader);
                            parent.add("PhyInvHdr", jsonArrayHdr);
                            String vals_1 = parent.toString();
                            vals_1 = vals_1.replace("\\", "");

                            String finalVals_ = vals_1;

                            //new DialogClass(this, "", vals_1);
                            loading.show();
                            String url = prefs.get_Val(ConstVar.URL) + ConstVar.url_CreatePhysicalInventoryDocCreate;
                            RequestQueue queue = Volley.newRequestQueue(context);
                            StringRequest sr = new StringRequest(Request.Method.POST,
                                    url,
                                    res -> {
                                        try {
                                            loading.dismiss();
                                            JSONObject jObjResponse = new JSONObject(res);

                                            String status = jObjResponse.getString("Status");

                                            JSONObject jsonStatus = new JSONObject(status);
                                            String statusCode = jsonStatus.getString("StatusCode");
                                            String StatusDescription = jsonStatus.getString("StatusDescription");

                                            if (statusCode.equals("0")) {
                                                Toast.makeText(this, StatusDescription, Toast.LENGTH_SHORT).show();
                                            } else {
                                                String Table = jObjResponse.getString("JSONData");
                                                JSONObject jObjResponse_1 = new JSONObject(Table);

                                                JSONArray jsonArray = jObjResponse_1.getJSONArray("Table_ds1_0");
                                                JSONObject j_Object = jsonArray.getJSONObject(0);
                                                Toast.makeText(context, "Saved at DocNum: " + j_Object.getString("Column1"), Toast.LENGTH_SHORT).show();
                                                btn_Save.setEnabled(false);
                                                tv_docNum.setText(j_Object.getString("Column1"));

                                                JSONArray jsonArray_counted = jObjResponse_1.getJSONArray("Table_ds_count_9");

                                                if (jsonArray_counted.length() > 0) {
                                                    StringBuilder msg = new StringBuilder();
                                                    for (int i = 0; i < jsonArray_counted.length(); i++) {
                                                        JSONObject j_Object_counted = jsonArray_counted.getJSONObject(i);
                                                        msg.append(j_Object_counted.getString("ActionNameE")).append(" : ").append(j_Object_counted.getString("COUNT")).append("\n");
                                                    }

                                                    new DialogClass(this, "Message", msg.toString());
                                                }
                                            }
                                        } catch (Exception ex) {
                                            loading.dismiss();
                                            new DialogClass(context, "Exception", ex.getMessage());
                                        }
                                    },
                                    error -> {
                                        try {
                                            loading.dismiss();
                                            new DialogClass(context, "Exception", "Something went wrong on Server Side");
                                        } catch (Exception ex) {
                                            loading.dismiss();
                                            new DialogClass(context, "Exception", ex.getMessage());

                                        }
                                    }) {
                                @Override
                                public byte[] getBody() {
                                    return finalVals_.getBytes();
                                }

                                public String getBodyContentType() {
                                    return "application/json; charset=utf-8";
                                }

                            };
                            queue.add(sr);
                        } else {
                            Toast.makeText(context, "Please select Date", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "List is empty", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    new DialogClass(this, "Exception", ex.getMessage());
                }
            });
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    public String formatdate(String fdate) {
        String datetime = null;
        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date convertedDate = inputFormat.parse(fdate);
            datetime = d.format(convertedDate);

        } catch (ParseException | java.text.ParseException e) {
            return "1900-01-01";
        }
        return datetime;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
};