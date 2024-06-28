package com.example.dichvugiacsay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Model.DonHang;
import com.example.dichvugiacsay.Model.DonHangOuter;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.CartDAO;
import com.example.dichvugiacsay.data.DonHangDAO;

import java.util.ArrayList;

public class DonHoanThanhAdapter extends RecyclerView.Adapter<DonHoanThanhAdapter.ViewHolder> {

    ArrayList<DonHangOuter> donHangArrayList;
    Context context;
    CartDAO cartDAO;
    User user;
    DonHangDAO donHangDAO;
    private ArrayList<DonHang> arr;
    private DonHangAdapter donHangAdapter;


    public DonHoanThanhAdapter(User user, ArrayList<DonHangOuter> donHangArrayList, Context context) {
        this.user = user;
        this.donHangArrayList = donHangArrayList;
        this.context = context;
        cartDAO = new CartDAO(context);
        donHangDAO = new DonHangDAO(context);
    }

    @NonNull
    @Override
    public DonHoanThanhAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoanthanh, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHoanThanhAdapter.ViewHolder holder, int position) {
        DonHangOuter donHangOuter = donHangArrayList.get(position);
        holder.id.setText(donHangOuter.getId());
        holder.date.setText(donHangOuter.getDate());
        LinearLayoutManager l = new LinearLayoutManager(context);
        l.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rcv.setLayoutManager(l);
        cartDAO.readinner(user.getUsername(), new CartDAO.CartITF() {
            @Override
            public void xuli(Object obj) {
                setData(holder.rcv, donHangOuter.getId(),holder.price );
            }
        });
    }

    @Override
    public int getItemCount() {
        return donHangArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id, date, price;
        private RecyclerView rcv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.hoanthanh_outer_price);
            id= itemView.findViewById(R.id.hoanthanh_outer_id);
            date= itemView.findViewById(R.id.hoanthanh_outer_date);
            rcv= itemView.findViewById(R.id.hoanthanh_outer_rcv);

        }
    }
    private void setData(RecyclerView rcv, String id, TextView txt){
        donHangDAO.getDonHangInner(id, new DonHangDAO.DonHangITF() {
            @Override
            public void xuli(Object object) {
                int sumprice = 0;
                arr = (ArrayList<DonHang>) object;
                for (int i = 0; i < arr.size(); i++) {
                    sumprice += (arr.get(i).getPrice() * arr.get(i).getQuantitty());
                }
                txt.setText("Tổng tiền: "+ (sumprice + 15000) +"đ");
                donHangAdapter = new DonHangAdapter(arr, context);
                rcv.setAdapter(donHangAdapter);
            }
        });
    }
}
