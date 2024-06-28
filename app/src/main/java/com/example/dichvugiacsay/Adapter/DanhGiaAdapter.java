package com.example.dichvugiacsay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Model.DanhGia;
import com.example.dichvugiacsay.R;

import java.util.ArrayList;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ViewHolder> {
    private ArrayList<DanhGia> arrayList;
    private Context context;

    public DanhGiaAdapter(ArrayList<DanhGia> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DanhGiaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhgia, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhGiaAdapter.ViewHolder holder, int position) {
        DanhGia danhGia = arrayList.get(position);
        holder.username.setText(danhGia.getUsername());
        holder.ratingBar.setIsIndicator(true);
        holder.ratingBar.setRating(Float.parseFloat(danhGia.getSao()));
        holder.cmt.setText(danhGia.getComment());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView username, cmt;
        private RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.item_danhgia_username);
            cmt = itemView.findViewById(R.id.item_danhgia_cmt);
            ratingBar = itemView.findViewById(R.id.item_danhgia_star);
        }
    }
}