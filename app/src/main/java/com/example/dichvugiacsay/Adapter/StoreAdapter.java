package com.example.dichvugiacsay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Fragment.Fragment_HomePage;
import com.example.dichvugiacsay.Model.Store;
import com.example.dichvugiacsay.R;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreHolder>{

    private Context context;
    private List<Store> mList;
    private StoreListener storeListener;

    public interface StoreListener{
        void onItemClick( int pos);
    }

    public void setListener(StoreListener storeListener) {
        this.storeListener = storeListener;
    }

    public StoreAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Store> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_store, parent, false);
        return new StoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreHolder holder, int position) {
        Store store = mList.get(position);
        if(store == null) {
            return;
        }
        holder.imgStore.setImageResource(R.drawable.cuahang1);
        holder.imgStar.setImageResource(R.drawable.star);
        holder.imgLocation.setImageResource(R.drawable.icon_10);
        holder.txtNameStore.setText(store.getName());
        holder.txtLocation.setText(store.getAddress());
        holder.txtNumber.setText("5");
        holder.txtNumber1.setText("1.1 km");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && storeListener != null) {
                    storeListener.onItemClick(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mList != null) {
            return mList.size();
        }
        return 0;
    }



    public class StoreHolder extends RecyclerView.ViewHolder{
        private ImageView imgStore, imgStar, imgLocation;
        private TextView txtNameStore, txtLocation, txtNumber, txtNumber1;
        public StoreHolder(@NonNull View itemView) {
            super(itemView);

            imgStore = itemView.findViewById(R.id. imgStore);
            imgStar = itemView.findViewById(R.id.imgStar);
            imgLocation = itemView.findViewById(R.id.imgLocation);
            txtNameStore = itemView.findViewById(R.id.txtNameStore);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtNumber1 = itemView.findViewById(R.id.txtNumber1);
        }


    }

}
