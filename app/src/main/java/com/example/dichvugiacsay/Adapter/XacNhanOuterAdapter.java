package com.example.dichvugiacsay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Model.DonHang;
import com.example.dichvugiacsay.Model.DonHangOuter;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.DonHangDAO;

import java.util.ArrayList;

public class XacNhanOuterAdapter extends RecyclerView.Adapter<XacNhanOuterAdapter.ViewHolder> {
    DonHangDAO donHangDAO;
    Context context;
    ArrayList<DonHangOuter> outerArrayList;
    DonHangAdapter donHangAdapter;


    public XacNhanOuterAdapter(Context context, ArrayList<DonHangOuter> outerArrayList) {
        this.context = context;
        this.outerArrayList = outerArrayList;
        donHangDAO = new DonHangDAO(context);
    }



    @NonNull
    @Override
    public XacNhanOuterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xacnhan_outer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull XacNhanOuterAdapter.ViewHolder holder, int position) {
        DonHangOuter donHangOuter = outerArrayList.get(position);
        if (donHangOuter == null) return;
        setDataInner(holder.price, donHangOuter.getId(), holder.rcv, Integer.parseInt(donHangOuter.getShip()));
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.status.setText("Đã hủy");

            }
        });

        holder.date.setText(donHangOuter.getDate());
        holder.id.setText("#" +donHangOuter.getId());
        holder.ship.setText("Phí vận chuyển:" + donHangOuter.getShip() + " đ");

    }



    @Override
    public int getItemCount() {
        return outerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id, date, status, price, ship;
        private RecyclerView rcv;
        private Button cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.xacnhan_outer_id);
            date = itemView.findViewById(R.id.xacnhan_outer_date);
            status = itemView.findViewById(R.id.xacnhan_outer_status);
            cancel = itemView.findViewById(R.id.xacNhan_outer_huy);
            price = itemView.findViewById(R.id.xacnhan_outer_price);
            ship = itemView.findViewById(R.id.xacnhan_outer_ship);

            rcv = itemView.findViewById(R.id.xacnhan_outer_rcv);
        }
    }

    private void setDataInner(TextView price, String id , RecyclerView rcv, int ship){
        donHangDAO.getDonHangInner(id, new DonHangDAO.DonHangITF() {
            @Override
            public void xuli(Object object) {
                int tongtienhang = 0;
                ArrayList<DonHang> arr = (ArrayList<DonHang>) object;
                for (int i = 0; i < arr.size() ; i++) {
                    tongtienhang += (arr.get(i).getPrice() * arr.get(i).getQuantitty());
                }
                price.setText("Tổng tiền: "+(tongtienhang + ship) + " đ");
                donHangAdapter = new DonHangAdapter(arr, context);
                LinearLayoutManager l = new LinearLayoutManager(context);
                l.setOrientation(LinearLayoutManager.VERTICAL);
                rcv.setLayoutManager(l);
                rcv.setAdapter(donHangAdapter);
                donHangAdapter.notifyDataSetChanged();
            }
        });
    }
}
