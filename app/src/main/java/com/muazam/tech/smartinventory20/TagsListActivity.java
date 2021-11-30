package com.muazam.tech.smartinventory20;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muazam.tech.smartinventory20.Adapter.CustomAdapterCodes;
import com.muazam.tech.smartinventory20.Utils.ConstVar;
import com.muazam.tech.smartinventory20.Utils.DatabaseHelper;
import com.muazam.tech.smartinventory20.Utils.DialogClass;

import java.util.ArrayList;

public class TagsListActivity extends AppCompatActivity {
    DatabaseHelper db;
    CustomAdapterCodes adapterCodes;
    ArrayList<String> lst_codes;
    Context context;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_list);
        try {
            Init();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    private void Init() {
        try {
            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            context = TagsListActivity.this;
            recyclerView = findViewById(R.id.recyclerView);
            db = new DatabaseHelper(context);
            lst_codes = new ArrayList<>();
            getData(getIntent().getStringExtra(ConstVar.session));
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage()).show();
        }
    }

    public void getData(String session) {
        try {
            Cursor cur = db.GetTags(session);
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                do {
                    lst_codes.add(cur.getString(5));
                } while (cur.moveToNext());
                adapterCodes = new CustomAdapterCodes(context, lst_codes, TagsListActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapterCodes);
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