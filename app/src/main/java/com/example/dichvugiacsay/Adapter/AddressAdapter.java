package com.example.dichvugiacsay.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.AddressActivity;
import com.example.dichvugiacsay.Model.Address;
import com.example.dichvugiacsay.R;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private ArrayList<Address> addressArrayList;
    private AddressActivity context;
    public interface AddressAdapterITF{void xuli(Object obj);}
    private AddressAdapterITF xuli, update;

    public AddressAdapter(ArrayList<Address> addressArrayList, AddressActivity context, AddressAdapterITF xuli, AddressAdapterITF update) {
        this.addressArrayList = addressArrayList;
        this.context = context;
        this.xuli = xuli;
        this.update= update;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = addressArrayList.get(position);
        if (address == null) return;

        holder.name_phone.setText(address.getName()+ " | "+ address.getPhone());
        holder.address.setText(address.getAddress());

        holder.lnlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.chk.setChecked(true);
                xuli.xuli(address);
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update.xuli(address);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name_phone , address, update;
        private CheckBox chk;
        private LinearLayout lnlo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_phone = itemView.findViewById(R.id.itemaddress_namephone);
            address = itemView.findViewById(R.id.itemaddress_address);
            update = itemView.findViewById(R.id.itemaddress_update);
            chk = itemView.findViewById(R.id.itemaddress_chk);
            lnlo = itemView.findViewById(R.id.itemaddress_linearlayoutaddress);
        }
    }

}
