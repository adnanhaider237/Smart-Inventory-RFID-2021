package com.muazam.tech.smartinventory20.Utils;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muazam.tech.smartinventory20.Model.ModelParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.muazam.tech.smartinventory20.Utils.SyncData.removeLastChar;


public class Sync {
    Dialog_Loading loading;
    DatabaseHelper db;
    Context context;
    MyPrefs prefs;

    public Sync(Context context) {
        this.context = context;
        db = new DatabaseHelper(context);
        loading = new Dialog_Loading(context);
        prefs = new MyPrefs(context);
    }

    public void SyncData(boolean flag) {
        try {
            String url = prefs.get_Val(ConstVar.URL) + ConstVar.url_getData;
            ArrayList<ModelParams> params = new ArrayList<>();
            params.add(new ModelParams(ConstVar.Device_Code, new DeviceInfo(context).getDeviceID()));
            url += ParamGetter.getValue(params);
            loading.show();
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    res -> {
                        try {
                            loading.dismiss();
                            res = res.trim();
                            String result = res.trim().replace("\\", "");
                            result = result.substring(1);
                            String vals = removeLastChar(result);
                            JSONObject object = new JSONObject(vals);

                            JSONArray jsonArray_SiteMaster = object.getJSONArray("Table");
                            JSONArray jsonArray_LocMaster = object.getJSONArray("Table1");
                            JSONArray jsonArray_SmMaster = object.getJSONArray("Table2");
                            JSONArray jsonArray_UserFile = object.getJSONArray("Table3");
                            JSONArray jsonArray_Device = object.getJSONArray("Table4");

                            db.ResetBasicData();

                            for (int i = 0; i < jsonArray_SiteMaster.length(); i++) {
                                JSONObject j_Object = jsonArray_SiteMaster.getJSONObject(i);
                                db.insert_table_SiteMaster(
                                        "" + j_Object.getString("SiteCode"),
                                        "" + j_Object.getString("SiteNameE"),
                                        "" + j_Object.getString("SiteNameA"),
                                        "" + j_Object.getString("CusCode"));
                            }

                            for (int i = 0; i < jsonArray_LocMaster.length(); i++) {
                                JSONObject j_Object = jsonArray_LocMaster.getJSONObject(i);
                                db.insert_table_LocMaster(
                                        "" + j_Object.getString("id"),
                                        "" + j_Object.getString("CusCode"),
                                        "" + j_Object.getString("SiteCode"),
                                        "" + j_Object.getString("LocCode"),
                                        "" + j_Object.getString("LocNameE"),
                                        "" + j_Object.getString("LocNameA"),
                                        "" + j_Object.getString("FlagDefault"));
                            }

                            for (int i = 0; i < jsonArray_SmMaster.length(); i++) {
                                JSONObject j_Object = jsonArray_SmMaster.getJSONObject(i);
                                db.insert_table_SmMaster(
                                        "" + j_Object.getString("SmCode"),
                                        "" + j_Object.getString("Smname"),
                                        "" + j_Object.getString("SmnameA"),
                                        "" + j_Object.getString("Smtel1"),
                                        "" + j_Object.getString("Smrating"),
                                        "" + j_Object.getString("Designation"),
                                        "" + j_Object.getString("email"),
                                        "" + j_Object.getString("mob"),
                                        "" + j_Object.getString("faxno"));
                            }

                            for (int i = 0; i < jsonArray_UserFile.length(); i++) {
                                JSONObject j_Object = jsonArray_UserFile.getJSONObject(i);
                                db.insert_table_UserFile(
                                        "" + j_Object.getString("UserCode"),
                                        "" + j_Object.getString("UserName"),
                                        "" + j_Object.getString("UserPassword"));
                            }
                            Cursor cur = db.get_DeviceConfig();

                            //Login
                            if (!flag) {
                                if (cur.getCount() <= 0) {
                                    if (jsonArray_Device.length() > 0) {
                                        JSONObject j_Object = jsonArray_Device.getJSONObject(0);
                                        db.insert_table_DeviceConfig(
                                                "" + j_Object.getString("LastSessionNum"),
                                                "",
                                                "" + new DeviceInfo(context).getDeviceID(),
                                                "" + new DeviceInfo(context).getDeviceModel(),
                                                "" + new DeviceInfo(context).getDeviceModel());
                                        new DialogClass(context, "Message", "Sync Successfully");
                                    } else {
                                        UploadDeviceMaster();
                                    }
                                }
                            } else if (jsonArray_Device.length() > 0) {
                                JSONObject j_Object = jsonArray_Device.getJSONObject(0);
                                int session = Integer.parseInt(j_Object.getString("LastSessionNum"));
                                session = session + 1;
                                if (session > 0) {
                                    db.insert_table_DeviceConfig(
                                            "" + session,
                                            "",
                                            "" + new DeviceInfo(context).getDeviceID(),
                                            "" + new DeviceInfo(context).getDeviceModel(),
                                            "" + new DeviceInfo(context).getDeviceModel());
                                }
                            }
                            new DialogClass(context, "Message", "Sync Successfully");

                        } catch (Exception ex) {
                            loading.dismiss();
                            new DialogClass(context, "Exception", ex.getMessage());
                        }
                    }, error -> {
                try {
                    loading.dismiss();
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    loading.dismiss();
                    new DialogClass(context, "Exception", ex.getMessage());
                }
            });
            queue.add(stringRequest);
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }

    }

    private void UploadDeviceMaster() {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = prefs.get_Val(ConstVar.URL) + ConstVar.url_InsertDevice;
            ArrayList<ModelParams> params = new ArrayList<>();
            params.add(new ModelParams(ConstVar.deviceID, new DeviceInfo(context).getDeviceID()));
            params.add(new ModelParams(ConstVar.deviceName, new DeviceInfo(context).getDeviceModel()));
            url += ParamGetter.getValue(params);
            StringRequest sr = new StringRequest(Request.Method.GET,
                    url,
                    res -> {
                        try {
                            loading.dismiss();
                            db.insert_table_DeviceConfig(
                                    "1",
                                    "",
                                    "" + new DeviceInfo(context).getDeviceID(),
                                    "" + new DeviceInfo(context).getDeviceModel(),
                                    "" + new DeviceInfo(context).getDeviceModel());
                            new DialogClass(context, "Message", "Sync Successfully");
                        } catch (Exception ex) {
                            loading.dismiss();
                            new DialogClass(context, "Exception", ex.getMessage());
                        }
                    },
                    error -> {
                        try {
                            loading.dismiss();

                        } catch (Exception ex) {
                            loading.dismiss();
                            new DialogClass(context, "Exception", ex.getMessage());
                        }
                    }) {
            };
            queue.add(sr);
        } catch (Exception ex) {

        }
    }
}
