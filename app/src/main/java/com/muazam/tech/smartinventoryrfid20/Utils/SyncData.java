package com.muazam.tech.smartinventoryrfid20.Utils;

import android.content.Context;

import com.muazam.tech.smartinventoryrfid20.Model.ModelSiteMaster;

import java.util.ArrayList;

public class SyncData {
    Context context;
    ArrayList<ModelSiteMaster> lst_sites;
    Dialog_Loading loading;

    public SyncData(Context context) {
        try {
            loading = new Dialog_Loading(context);
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

/*
    public void GetContacts() {
        try {
            lst_sites = new ArrayList<>();
            loading.show();

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    prefs.get_Val(ConstVar.URL) + ConstVar.url_getContacts,
                    res -> {
                        try {
                            loading.dismiss();
                            res = res.trim();
                            String result = res.trim().replace("\\", "");
                            result = result.substring(1);
                            String vals = removeLastChar(result);
                            JSONObject object = new JSONObject(vals);

                            JSONArray jsonArray = object.getJSONArray("Table");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject j_Object = jsonArray.getJSONObject(i);
                                lst_contacts.add(new ModelSites(
                                        "" + j_Object.getString("KAUSTContactsID"),
                                        "" + j_Object.getString("KAUSTID"),
                                        "" + j_Object.getString("Name"),
                                        "" + j_Object.getString("LFO"),
                                        "" + j_Object.getString("Mobile"),
                                        "" + j_Object.getString("Office"),
                                        "" + j_Object.getString("OfficeLoc"),
                                        "" + j_Object.getString("EmailAddress"),
                                        "" + j_Object.getString("DelInst"),
                                        "" + j_Object.getString("Title"),
                                        "" + j_Object.getString("Department"),
                                        "" + j_Object.getString("Company")
                                ));
                            }
                            customAdapterContacts = new CustomAdapterContacts(context, lst_contacts, ContactListActivity.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            recyclerView.setAdapter(customAdapterContacts);
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
*/

}
