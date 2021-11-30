package com.muazam.tech.smartinventory20.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.muazam.tech.smartinventory20.Model.ModelSummary;
import com.muazam.tech.smartinventory20.R;
import com.muazam.tech.smartinventory20.TagsListActivity;
import com.muazam.tech.smartinventory20.Utils.ConstVar;
import com.muazam.tech.smartinventory20.Utils.DialogClass;
import com.muazam.tech.smartinventory20.Utils.Dialog_Loading;
import com.muazam.tech.smartinventory20.Utils.MyPrefs;

import java.util.ArrayList;

public class CustomAdapterSummary extends
        RecyclerSwipeAdapter<CustomAdapterSummary.ViewHolder> {
    Context context;
    ArrayList<ModelSummary> lst_original;
    Activity activity;
    MyPrefs prefs;
    Dialog_Loading loading;


    public CustomAdapterSummary(Context context, ArrayList<ModelSummary> lst, Activity activity) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            holder.tv_session.setText(lst_original.get(position).getSession());
            holder.tv_site.setText(lst_original.get(position).getSite());
            holder.tv_location.setText(lst_original.get(position).getLocation());
            holder.tv_time.setText(lst_original.get(position).getStartTime() + " TO " + lst_original.get(position).getCloseTime());
            holder.tv_no_of_tags.setText(lst_original.get(position).getNo_of_tags());
            holder.tv_status.setText(lst_original.get(position).getStatus());
            holder.lnr.setOnClickListener(v -> {
                Intent intent = new Intent(context, TagsListActivity.class);
                intent.putExtra(ConstVar.session, lst_original.get(position).getSession().trim());
                context.startActivity(intent);
            });

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
        TextView tv_session, tv_site, tv_location, tv_time, tv_no_of_tags, tv_status;
        LinearLayout lnr;

        ViewHolder(View itemView) {
            super(itemView);
            try {
                tv_session = itemView.findViewById(R.id.tv_session);
                tv_site = itemView.findViewById(R.id.tv_site);
                tv_location = itemView.findViewById(R.id.tv_location);
                tv_time = itemView.findViewById(R.id.tv_time);
                tv_no_of_tags = itemView.findViewById(R.id.tv_no_of_tags);
                tv_status = itemView.findViewById(R.id.tv_status);
                lnr = itemView.findViewById(R.id.lnr);
            } catch (Exception ex) {

            }
        }
    }
}