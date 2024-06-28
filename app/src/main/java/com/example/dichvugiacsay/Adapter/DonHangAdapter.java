package com.example.dichvugiacsay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Model.DonHang;
import com.example.dichvugiacsay.R;

import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {

    private ArrayList<DonHang> arr;
    private Context context;

    public DonHangAdapter(ArrayList<DonHang> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @NonNull
    @Override
    public DonHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xacnhan_inner, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.ViewHolder holder, int position) {
        DonHang donHang = arr.get(position);
        if (donHang == null) return;
        int id = context.getResources().getIdentifier("drawable/"+donHang.getImg() , null , context.getPackageName());
        holder.img.setImageResource(id);
        holder.name.setText(donHang.getName());
        holder.price.setText((donHang.getQuantitty() * donHang.getPrice()) + " đ");
        holder.description.setText(donHang.getDescription());
        holder.quantity.setText("SL: "+donHang.getQuantitty());


    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name, description, quantity, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.xacNhan_img);
            name = itemView.findViewById(R.id.xacNhan_name);
            description = itemView.findViewById(R.id.xacNhan_mota);
            quantity = itemView.findViewById(R.id.xacNhan_quantity);
            price = itemView.findViewById(R.id.xacNhan_price);
        }
    }
}
