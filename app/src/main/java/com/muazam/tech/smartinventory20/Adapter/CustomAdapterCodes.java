package com.muazam.tech.smartinventory20.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.muazam.tech.smartinventory20.R;
import com.muazam.tech.smartinventory20.Utils.DialogClass;
import com.muazam.tech.smartinventory20.Utils.Dialog_Loading;
import com.muazam.tech.smartinventory20.Utils.MyPrefs;

import java.util.ArrayList;

public class CustomAdapterCodes extends
        RecyclerSwipeAdapter<CustomAdapterCodes.ViewHolder> {
    Context context;
    ArrayList<String> lst_original;
    Activity activity;
    MyPrefs prefs;
    Dialog_Loading loading;


    public CustomAdapterCodes(Context context, ArrayList<String> lst, Activity activity) {
        this.context = context;
        this.lst_original = lst;
        this.activity = activity;
        prefs = new MyPrefs(context);
        loading = new Dialog_Loading(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.row_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            holder.tv_tag.setText(lst_original.get(position));

        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }


    @Override
    public long getItemId(int position) {
        return lst_original.indexOf(position);
    }

    @Override
    public int getItemCount() {
        return lst_original.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tag;
        LinearLayout lnr;

        ViewHolder(View itemView) {
            super(itemView);
            try {
                tv_tag = itemView.findViewById(R.id.tv_tag);
                lnr = itemView.findViewById(R.id.lnr);
            } catch (Exception ex) {

            }
        }
    }
}