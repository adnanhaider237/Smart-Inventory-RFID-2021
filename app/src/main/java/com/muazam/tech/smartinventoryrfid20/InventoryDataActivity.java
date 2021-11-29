package com.muazam.tech.smartinventoryrfid20;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muazam.tech.smartinventoryrfid20.Adapter.CustomAdapterSummary;
import com.muazam.tech.smartinventoryrfid20.Model.ModelSummary;
import com.muazam.tech.smartinventoryrfid20.Utils.DatabaseHelper;
import com.muazam.tech.smartinventoryrfid20.Utils.DialogClass;

import java.util.ArrayList;

public class InventoryDataActivity extends AppCompatActivity {
    Context context;
    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<ModelSummary> lst;
    CustomAdapterSummary customAdapterSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_data);
        try {
            Init();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    private void Init() {
        try {
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            context = InventoryDataActivity.this;
            recyclerView = findViewById(R.id.recyclerView);
            db = new DatabaseHelper(context);
            lst = new ArrayList<>();
            fun_GetData();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    private void fun_GetData() {
        try {
            Cursor cur = db.get_Data();
            cur.moveToFirst();
            if (cur.getCount() > 0) {
                do {
                    lst.add(new ModelSummary(
                            "" + cur.getString(0),
                            "" + cur.getString(3),
                            "" + cur.getString(4),
                            "" + cur.getString(5),
                            "" + cur.getString(6),
                            "" + cur.getString(7),
                            "" + db.GetCount(cur.getString(0),
                                    cur.getString(4),
                                    cur.getString(3))));
                } while (cur.moveToNext());
                customAdapterSummary = new CustomAdapterSummary(context, lst, InventoryDataActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(customAdapterSummary);
            } else {
                new DialogClass(context, "Message", "No data found").show();
            }
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