package com.muazam.tech.smartinventoryrfid20.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muazam.tech.smartinventoryrfid20.Model.ModelPhyInvHdr;
import com.muazam.tech.smartinventoryrfid20.R;
import com.muazam.tech.smartinventoryrfid20.Utils.DialogClass;

import java.util.ArrayList;

public class AdapterPhyInvHdr extends
        RecyclerView.Adapter<AdapterPhyInvHdr.ViewHolder> {
    Context context;
    ArrayList<ModelPhyInvHdr> my_lst;

    public AdapterPhyInvHdr(Context context, ArrayList<ModelPhyInvHdr> lst) {
        this.context = context;
        my_lst = lst;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.layout_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            holder.tv_ID.setText(my_lst.get(position).getPhyInvHdrId());
        } catch (
                Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return my_lst.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ID;


        ViewHolder(View itemView) {
            super(itemView);
            try {
                tv_ID = itemView.findViewById(R.id.unit);
            } catch (Exception ex) {

            }
        }
    }

}
