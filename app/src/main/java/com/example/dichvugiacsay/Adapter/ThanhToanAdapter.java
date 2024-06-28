package com.example.dichvugiacsay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Model.Cart;
import com.example.dichvugiacsay.R;

import java.util.ArrayList;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.ViewHolder> {
    ArrayList<Cart> cartArrayList;
    Context context;

    public ThanhToanAdapter(ArrayList<Cart> cartArrayList, Context context) {
        this.cartArrayList = cartArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanhtoan, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = cartArrayList.get(position);
        int idimg = context.getResources().getIdentifier("drawable/"+cart.getImg(), null, context.getPackageName());
        holder.img.setImageResource(idimg);
        holder.name.setText(cart.getNameService());
        holder.price.setText((cart.getQuantity() * cart.getPriceService()) +"");
        holder.quantity.setText("x"+cart.getQuantity());
        holder.description.setText(cart.getDescription());
    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, description, quantity, price;
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_thanhtoan_name);
            description = itemView.findViewById(R.id.item_thanhtoan_description);
            quantity = itemView.findViewById(R.id.item_thanhtoan_quantity);
            price = itemView.findViewById(R.id.item_thanhtoan_price);
            img = itemView.findViewById(R.id.item_thanhtoan_img);
        }
    }
}
