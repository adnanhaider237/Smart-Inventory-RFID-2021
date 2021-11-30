package com.muazam.tech.smartinventory20.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.muazam.tech.smartinventory20.Model.ModelPhyInvHdr;
import com.muazam.tech.smartinventory20.Model.ModelPhyInvItem;

import java.util.ArrayList;
import java.util.Collections;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Roche_101_DEV.db";

    public static final String table_DeviceConfig = "DeviceConfig";
    public static final String table_UserFile = "UserFile";
    public static final String table_SiteMaster = "SiteMaster";
    public static final String table_LocMaster = "LocMaster";
    public static final String table_SmMaster = "SmMaster";
    public static final String table_PhyInvHdr = "PhyInvHdr";
    public static final String table_PhyInvItem = "PhyInvItem";
    public static final String table_BeepDelay = "BeepDelay";
    //public static final String table_Session = "Session";

    public static final String col_ID = "ID";
    public static final String col_Beep = "Beep";

    public static final String col_DeviceCode = "DeviceCode";
    public static final String col_DeviceNameE = "DeviceNameE";
    public static final String col_DeviceNameA = "DeviceNameA";


    public static final String col_UserCode = "UserCode";
    public static final String col_UserName = "UserName";
    public static final String col_UserPassword = "UserPassword";

    public static final String col_SessionId = "SessionId";
    public static final String col_DeviceID = "DeviceID";


    public static final String col_StartTime = "StartTime";
    public static final String col_CloseTime = "CloseTime";
    public static final String col_Status = "Status";
    public static final String col_LocationCode = "LocationCode";
    public static final String col_UnitId = "UnitId";

    public static final String col_SiteCode = "SiteCode";
    public static final String col_SiteNameE = "SiteNameE";
    public static final String col_SiteNameA = "SiteNameA";
    public static final String col_CusCode = "CusCode";

    public static final String col_id = "id";
    public static final String col_LocCode = "LocCode";
    public static final String col_LocNameE = "LocNameE";
    public static final String col_LocNameA = "LocNameA";
    public static final String col_FlagDefault = "FlagDefault";

    public static final String col_SmCode = "SmCode";
    public static final String col_Smname = "Smname";
    public static final String col_SmnameA = "SmnameA";
    public static final String col_Smtel1 = "Smtel1";
    public static final String col_Smrating = "Smrating";
    public static final String col_Designation = "Designation";
    public static final String col_email = "email";
    public static final String col_mob = "mob";
    public static final String col_faxno = "faxno";


    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_DeviceConfig + " (" + col_SessionId + " TEXT," + col_DeviceCode + " TEXT ," + col_DeviceID + " TEXT ," + col_DeviceNameE + " TEXT ," + col_DeviceNameA + " TEXT) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_UserFile + " (" + col_UserCode + " TEXT," + col_UserName + " TEXT ," + col_UserPassword + " TEXT) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_SiteMaster + " (" + col_SiteCode + " TEXT," + col_SiteNameE + " TEXT ," + col_SiteNameA + " TEXT," + col_CusCode + " TEXT) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_LocMaster + " (" + col_id + " TEXT," + col_CusCode + " TEXT," + col_SiteCode + " TEXT," + col_LocCode + " TEXT," + col_LocNameE + " TEXT," + col_LocNameA + " TEXT," + col_FlagDefault + " TEXT) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_SmMaster + " (" + col_SmCode + " TEXT," + col_Smname + " TEXT," + col_SmnameA + " TEXT," + col_Smtel1 + " TEXT," + col_Smrating + " TEXT," + col_Designation + " TEXT," + col_email + " TEXT," + col_mob + " TEXT," + col_faxno + " TEXT) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_BeepDelay + " (id INTEGER PRIMARY KEY AUTOINCREMENT,Beep INTEGER) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS PhyInvHdr  (SessionId INTEGER PRIMARY KEY,DeviceID VARCHAR(15),UserCode VARCHAR(15),SiteCode VARCHAR(15),LocationCode VARCHAR(15),StartTime DATETIME,CloseTime DateTime, Status VARCHAR(10)) ");
        db.execSQL("CREATE TABLE IF NOT EXISTS PhyInvItem (id INTEGER PRIMARY KEY AUTOINCREMENT,SessionId INTEGER,DeviceID VARCHAR(15),SiteCode VARCHAR(15),LocationCode VARCHAR(15),UnitId VARCHAR(30))");
        //db.execSQL("CREATE TABLE IF NOT EXISTS " + table_Session + " (" + col_SessionId + " TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_SiteMaster);
        db.execSQL("DROP TABLE IF EXISTS " + table_LocMaster);
        db.execSQL("DROP TABLE IF EXISTS " + table_SmMaster);
        db.execSQL("DROP TABLE IF EXISTS " + table_PhyInvHdr);
        db.execSQL("DROP TABLE IF EXISTS " + table_PhyInvItem);
        db.execSQL("DROP TABLE IF EXISTS " + table_BeepDelay);
        onCreate(db);
    }


    public String insert_table_Beep(String beep) {
        String result;
        try {
            SQLiteDatabase db1 = this.getReadableDatabase();
            db1.delete(table_BeepDelay, "", null);

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_Beep, beep);

            long res = db.insert(table_BeepDelay, null, contentValues);
            if (res == -1)
                result = "0";
            else
                return "1";

        } catch (Exception ex) {
            result = ex.toString();
        }
        return result;
    }

    public String getBeep() {
        String result = "0";
        try {
            Cursor cur = null;
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select " + col_Beep + " from " + table_BeepDelay + " ", null);
            cur.moveToFirst();
            if (cur.getCount() > 0) {
                result = cur.getString(0);
            } else {
                result = "0";
            }
        } catch (Exception e) {
        }
        return result;
    }


    public String insert_table_DeviceConfig(String SessionId, String DeviceCode, String DeviceID, String DeviceNameE, String DeviceNameA) {
        String result;
        try {
            SQLiteDatabase db1 = this.getReadableDatabase();
            db1.delete(table_DeviceConfig, "", null);

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_SessionId, SessionId);
            contentValues.put(col_DeviceCode, DeviceCode);
            contentValues.put(col_DeviceID, DeviceID);
            contentValues.put(col_DeviceNameE, DeviceNameE);
            contentValues.put(col_DeviceNameA, DeviceNameA);

            long res = db.insert(table_DeviceConfig, null, contentValues);
            if (res == -1)
                result = "0";
            else
                return "1";

        } catch (Exception ex) {
            result = ex.toString();
        }
        return result;
    }


    public void Update_Device(String session) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(col_SessionId, session);
            db.update(table_DeviceConfig, values, " " + col_DeviceID + "=" + new DeviceInfo(context).getDeviceID() + "", null);
        } catch (Exception e) {
        }
    }

    /*public String Update_Session(String session) {
        String result = "";
        try {
            SQLiteDatabase db1 = this.getReadableDatabase();
            db1.delete(table_UserFile, "", null);

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_SessionId, session);
            long res = db.insert(table_UserFile, null, contentValues);
            if (res == -1)
                result = "0";
            else
                return "1";
        } catch (Exception e) {
            result = "0";
        }
        result = result;
    }*/

    public String insert_table_UserFile(String UserCode, String UserName, String UserPassword) {
        String result;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_UserCode, UserCode);
            contentValues.put(col_UserName, UserName);
            contentValues.put(col_UserPassword, UserPassword);

            long res = db.insert(table_UserFile, null, contentValues);
            if (res == -1)
                result = "0";
            else
                return "1";

        } catch (Exception ex) {
            result = ex.toString();
        }
        return result;
    }

    public String insert_table_SiteMaster(String SiteCode, String SiteNameE, String SiteNameA, String CusCode) {
        String result;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_SiteCode, SiteCode);
            contentValues.put(col_SiteNameE, SiteNameE);
            contentValues.put(col_SiteNameA, SiteNameA);
            contentValues.put(col_CusCode, CusCode);

            long res = db.insert(table_SiteMaster, null, contentValues);
            if (res == -1)
                result = "0";
            else
                return "1";

        } catch (Exception ex) {
            result = ex.toString();
        }
        return result;
    }

    public String insert_table_LocMaster(String id, String CusCode, String SiteCode, String LocCode, String LocNameE, String LocNameA, String FlagDefault) {
        String result;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_id, id);
            contentValues.put(col_CusCode, CusCode);
            contentValues.put(col_SiteCode, SiteCode);
            contentValues.put(col_LocCode, LocCode);
            contentValues.put(col_LocNameE, LocNameE);
            contentValues.put(col_LocNameA, LocNameA);
            contentValues.put(col_FlagDefault, FlagDefault);

            long res = db.insert(table_LocMaster, null, contentValues);
            if (res == -1)
                result = "0";
            else
                return "1";

        } catch (Exception ex) {
            result = ex.toString();
        }
        return result;
    }

    public String insert_table_SmMaster(String SmCode, String Smname, String SmnameA, String Smtel1, String Smrating, String Designation, String email, String mob, String faxno) {
        String result;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_SmCode, SmCode);
            contentValues.put(col_Smname, Smname);
            contentValues.put(col_SmnameA, SmnameA);
            contentValues.put(col_Smtel1, Smtel1);
            contentValues.put(col_Smrating, Smrating);
            contentValues.put(col_Designation, Designation);
            contentValues.put(col_email, email);
            contentValues.put(col_mob, mob);
            contentValues.put(col_faxno, faxno);

            long res = db.insert(table_SmMaster, null, contentValues);
            if (res == -1)
                result = "0";
            else
                return "1";

        } catch (Exception ex) {
            result = ex.toString();
        }
        return result;
    }

    public String insert_PhyHdr(int sessionID, String DeviceID, String UserCode, String LocationCode, String SiteCode,
                                String StartTime, String CloseTime, String Status) {
        String result;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_SessionId, sessionID);
            contentValues.put(col_DeviceID, DeviceID);
            contentValues.put(col_UserCode, UserCode);
            contentValues.put(col_SiteCode, SiteCode);
            contentValues.put(col_StartTime, StartTime);
            contentValues.put(col_CloseTime, CloseTime);
            contentValues.put(col_LocationCode, LocationCode);
            contentValues.put(col_Status, Status);

            long res = db.insert(table_PhyInvHdr, null, contentValues);
            if (res == -1)
                result = "0";
            else
                return "1";
        } catch (Exception ex) {
            result = ex.toString();
        }
        return result;
    }


    public String insert_PhyInvItem(String DeviceID, String session, String SiteCode, String LocationCode, String UnitId) {
        String result;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_DeviceID, DeviceID);
            contentValues.put(col_SessionId, session);
            contentValues.put(col_SiteCode, SiteCode);
            contentValues.put(col_LocationCode, LocationCode);
            contentValues.put(col_UnitId, UnitId);

            long res = db.insert(table_PhyInvItem, null, contentValues);
            if (res == -1)
                result = "0";
            else
                return "1";
        } catch (Exception ex) {
            result = ex.toString();
        }
        return result;
    }

    public boolean TagExist(String session, String SiteCode, String LocationCode, String UnitId) {
        Cursor cur;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            /*cur = db.rawQuery("select * from " + table_PhyInvItem + " where " +
                    "" + col_SessionId + "='" + session + "' and  " +
                    "" + col_SiteCode + "='" + SiteCode + "' and  " +
                    "" + col_LocationCode + "='" + LocationCode + "' and  " +
                    "" + col_UnitId + "='" + UnitId + "'" +
                    "", null);*/
            cur = db.rawQuery("select * from " + table_PhyInvItem + " where " +
                    "" + col_SessionId + "='" + session + "' and  " +
                    "" + col_SiteCode + "='" + SiteCode + "' and  " +
                    "" + col_LocationCode + "='" + LocationCode + "' and  " +
                    "" + col_UnitId + "='" + UnitId + "'" +
                    "", null);
            if (cur.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Cursor get_DeviceConfig() {
        Cursor cur = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + table_DeviceConfig + " ", null);
        } catch (Exception e) {
        }
        return cur;
    }

    public String checkSession() {
        String result = "0";
        try {
            Cursor cur = null;
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select " + col_SessionId + " from " + table_DeviceConfig + " ", null);
            cur.moveToFirst();
            if (cur.getCount() > 0) {
                result = cur.getString(0);
            } else {
                result = "0";
            }
        } catch (Exception e) {
        }
        return result;
    }

    public Cursor get_UserFile(String UserCode, String UserPassword) {
        Cursor cur = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + table_UserFile + " where UserCode='" + UserCode + "' and UserPassword='" + UserPassword + "' ", null);
        } catch (Exception e) {
        }
        return cur;
    }

    public Cursor get_SiteMaster() {
        Cursor cur = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + table_SiteMaster + " ", null);
        } catch (Exception e) {
        }
        return cur;
    }

    public Cursor get_LocMaster() {
        Cursor cur = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + table_LocMaster + " ", null);
        } catch (Exception e) {
        }
        return cur;
    }


    public void ResetBasicData() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            db.delete(table_UserFile, "", null);
            db.delete(table_SiteMaster, "", null);
            db.delete(table_LocMaster, "", null);
            db.delete(table_SmMaster, "", null);
            //db.delete(table_DeviceConfig, "", null);
        } catch (Exception e) {
        }
    }

    public void Upload() {
        try {
            MyPrefs prefs = new MyPrefs(context);
            ArrayList<ModelPhyInvHdr> lstHdr = new ArrayList<>();
            ArrayList<ModelPhyInvItem> lstItem = new ArrayList<>();
            ArrayList<Integer> lst_session = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur_hdr = db.rawQuery("select * from " + table_PhyInvHdr + " ", null);
            Cursor cur_itm = db.rawQuery("select * from " + table_PhyInvItem + " ", null);
            cur_hdr.moveToFirst();
            cur_itm.moveToFirst();
            if (cur_hdr.getCount() > 0 && cur_itm.getCount() > 0) {
                do {
                    //SessionId INTEGER PRIMARY KEY,   0
                    // DeviceID VARCHAR(15),          1
                    // UserCode VARCHAR(15),        2
                    // SiteCode VARCHAR(15),         3
                    // LocationCode VARCHAR(15),      4
                    // StartTime DATETIME,         5
                    // CloseTime DateTime,       6
                    // Status VARCHAR(10)) ");     7
                    lstHdr.add(new ModelPhyInvHdr(
                            "",
                            "" + cur_hdr.getString(1),
                            "",
                            "" + cur_hdr.getString(0),
                            "" + cur_hdr.getString(3),
                            "" + prefs.get_Val(ConstVar.USER_CODE),
                            "" + cur_hdr.getString(5),
                            "" + new GetTime().GetTime(),
                            "" + cur_hdr.getString(6),
                            "",
                            "" + cur_hdr.getString(7),
                            ""));
                    lst_session.add(Integer.parseInt(cur_hdr.getString(0)));
                } while (cur_hdr.moveToNext());
                do {//id INTEGER PRIMARY KEY AUTOINCREMENT, 0
                    // SessionId INTEGER,                   1
                    // DeviceID VARCHAR(15),    2
                    // SiteCode VARCHAR(15),    3
                    // LocationCode VARCHAR(15),    4
                    // UnitId VARCHAR(30))");   5
                    /*String site_code = "";
                    if (cur_itm.getString(4).contains(":")) {
                        String[] siteCode = cur_itm.getString(4).split(":");
                        if (siteCode.length == 2) {
                            site_code = siteCode[1];
                        } else {
                            site_code = siteCode[0];
                        }
                    }*/

                    lstItem.add(new ModelPhyInvItem(
                            "",
                            "" + cur_itm.getString(2),
                            "",
                            "" + cur_itm.getString(1),
                            "" + cur_itm.getString(3),
                            "" + cur_itm.getString(4),
                            "" + cur_itm.getString(5),
                            "" + hexToAscii(cur_itm.getString(5)),
                            "",
                            ""));
                } while (cur_itm.moveToNext());

                Dialog_Loading loading = new Dialog_Loading(context);
                loading.show();
                if (lstHdr.size() > 0 || lstItem.size() > 0) {

                    JsonObject parent = new JsonObject();
                    JsonArray jsonArray_1 = new Gson().toJsonTree(lstHdr).getAsJsonArray();
                    JsonArray jsonArray_2 = new Gson().toJsonTree(lstItem).getAsJsonArray();
                    parent.add("PhyInvHdr", jsonArray_1);
                    parent.add("PhyInvItems", jsonArray_2);
                    String vals_1 = parent.toString();
                    vals_1 = vals_1.replace("\\", "");


                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url = prefs.get_Val(ConstVar.URL) + ConstVar.url_UploadData;
                    String finalVals_ = vals_1;
                    StringRequest sr = new StringRequest(Request.Method.POST,
                            url,
                            res -> {
                                try {
                                    loading.dismiss();
                                    String result = res.trim().replace("\\", "");
                                    //if (result.equals("Insert Completed successfully")) {
                                    Collections.sort(lst_session);
                                    for (int i = 0; i < lst_session.size(); i++) {
                                        db.delete(table_PhyInvItem, " " + col_SessionId + "=" + lst_session.get(i) + "", null);
                                        db.delete(table_PhyInvHdr, " " + col_SessionId + "=" + lst_session.get(i) + "", null);
                                        //Update_Device(lst_session.get(i).toString());
                                    }
                                    //Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                                    new DialogClass(context, "Message", "Data Uploaded Successfully");
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
                    new DialogClass(context, "Message", "No new data to upload");
                }
            } else {
                new DialogClass(context, "Message", "No new data to upload");
            }
        } catch (Exception e) {
            new DialogClass(context, "Exception", e.getMessage());
        }
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

    public void CancelInventory(String session) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(col_Status, ConstVar.cancel);
            values.put(col_CloseTime, new GetTime().GetTime());
            db.update(table_PhyInvHdr, values, "  SessionId='" + session + "' and " + col_Status + "='open' ", null);
            db.delete(table_PhyInvItem, "  SessionId='" + session + "' ", null);
            ContentValues values_device = new ContentValues();
            int s = Integer.parseInt(session);
            s = s + 1;
            values_device.put(col_SessionId, s);
            db.update(table_DeviceConfig, values_device, " " + col_DeviceID + "='" + new DeviceInfo(context).getDeviceID() + "' ", null);
        } catch (Exception e) {
        }
    }

    public void CloseInventory(String session) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(col_Status, ConstVar.close);
            values.put(col_CloseTime, new GetTime().GetTime());

            db.update(table_PhyInvHdr, values, " SessionId='" + session + "' ", null);

            ContentValues values_device = new ContentValues();
            int s = Integer.parseInt(session);
            s = s + 1;
            values_device.put(col_SessionId, s);
            db.update(table_DeviceConfig, values_device, " " + col_DeviceID + "='" + new DeviceInfo(context).getDeviceID() + "' ", null);
        } catch (Exception e) {
        }
    }


    public Cursor GetTags(String session) {
        Cursor cur = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + table_PhyInvItem + " where " + col_SessionId + "='" + session + "'   ", null);
        } catch (Exception e) {
        }
        return cur;
    }

    public String GetCount(String session, String LocationCode, String SiteCode) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select count(*) from " + table_PhyInvItem + "" +
                    " where SessionId='" + session + "'" +
                    " and LocationCode= " + LocationCode + " and SiteCode=" + SiteCode + " ", null);
            cur.moveToFirst();
            return cur.getString(0);
        } catch (Exception e) {
            return "0";
        }
    }

    public String GetDate(String session) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select StartTime from " + table_PhyInvHdr + " where SessionId='" + session + "' ", null);
            cur.moveToFirst();
            return cur.getString(0);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean DataExist() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from " + table_PhyInvHdr + " ", null);
            cur.moveToFirst();
            if (cur.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean DataExistForSession(String session) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from " + table_PhyInvHdr + " where " + col_SessionId + "='" + session + "'  ", null);
            cur.moveToFirst();
            if (cur.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }


    public boolean IfSessionOpen(String session) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from " + table_PhyInvHdr + " where sessionID='" + session + "' and status='" + ConstVar.open + "' ", null);
            int count = cur.getCount();
            if (count > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }


    public String get_SessionNum() {
        String result = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select " + col_SessionId + " from " + table_DeviceConfig + " ", null);
            cur.moveToFirst();
            result = cur.getString(0);
            if (result == null) {
                return "0";
            }
        } catch (Exception e) {
            return "0";
        }
        return result;
    }

    public Cursor get_Data() {
        Cursor cur = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cur = db.rawQuery("select * from " + table_PhyInvHdr + " ", null);
        } catch (Exception e) {
            return cur;
        }
        return cur;
    }
}