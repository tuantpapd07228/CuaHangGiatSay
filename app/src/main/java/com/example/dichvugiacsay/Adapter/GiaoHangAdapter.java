package com.example.dichvugiacsay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Model.DonHang;
import com.example.dichvugiacsay.Model.DonHangOuter;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.DonHangDAO;

import java.util.ArrayList;

public class GiaoHangAdapter extends RecyclerView.Adapter<GiaoHangAdapter.ViewHolder> {
    private final ArrayList<DonHangOuter> donHangArrayList;
    private final Context context;
    private final DonHangDAO donHangDAO;
    private LinearLayoutManager l;
    private DonHangAdapter donHangAdapter;
    private ArrayList<DonHang> arr;

    public GiaoHangAdapter(ArrayList<DonHangOuter> donHangArrayList, Context context) {
        this.donHangArrayList = donHangArrayList;
        this.context = context;

        donHangDAO = new DonHangDAO(context);
    }

    @NonNull
    @Override
    public GiaoHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giaohang, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GiaoHangAdapter.ViewHolder holder, int position) {
        DonHangOuter donHangOuter = donHangArrayList.get(position);
        holder.id.setText("#"+ donHangOuter.getId());
        holder.date.setText(donHangOuter.getDate());
        l = new LinearLayoutManager(context);
        l.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rcv.setLayoutManager(l);
        setData(holder.rcv, donHangOuter.getId(), holder.price, Integer.parseInt(donHangOuter.getShip()));
    }

    @Override
    public int getItemCount() {
        return donHangArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView date;
        private final TextView price;
        private RecyclerView rcv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.giaohang_outer_id);
            date = itemView.findViewById(R.id.giaohang_outer_date);
            price = itemView.findViewById(R.id.giaohang_outer_price);
            rcv = itemView.findViewById(R.id.giaohang_outer_rcv);
        }
    }
    private void setData(RecyclerView rcv, String id, TextView txt, int ship){
        donHangDAO.getDonHangInner(id, new DonHangDAO.DonHangITF() {
            @Override
            public void xuli(Object object) {
                int sumprice = 0;
                arr = (ArrayList<DonHang>) object;
                for (int i = 0; i < arr.size(); i++) {
                    sumprice += (arr.get(i).getPrice() * arr.get(i).getQuantitty());
                }
                txt.setText("Tổng tiền: "+ (sumprice + ship) +"đ");
                donHangAdapter = new DonHangAdapter(arr, context);
                rcv.setAdapter(donHangAdapter);
            }
        });
    }
}
