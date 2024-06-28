package com.example.dichvugiacsay.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Adapter.CartAdapter;
import com.example.dichvugiacsay.MainActivity;
import com.example.dichvugiacsay.Model.Cart;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.ThanhToanActivity;
import com.example.dichvugiacsay.data.CartDAO;

import java.util.ArrayList;

public class Fragment_Cart extends Fragment {
    private RecyclerView rcv;
    private Button btndat;
    private CartAdapter cartAdapter;
    private CartDAO cartDAO;
    private TextView txtprice;
    private ArrayList<Cart> arr;
    private MainActivity mainActivity;
    private ArrayList<Cart> arrIdItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__cart, container, false);
        cartDAO = new CartDAO(getContext());
        arrIdItem = new ArrayList<>();
        rcv = view.findViewById(R.id.cartFragment_rcv);
        btndat = view.findViewById(R.id.cartFragment_datdichvu);
        txtprice = view.findViewById(R.id.cartFramgent_price);
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        l.setOrientation(RecyclerView.VERTICAL);
        rcv.setLayoutManager(l);
        mainActivity = (MainActivity) getActivity();
        btndat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtndat(arrIdItem);
            }
        });
        setData();
        return view;
    }
    private void setData(){
        ArrayList<Cart> arrprice = new ArrayList<>();
        cartDAO.readinner(mainActivity.getUser().getUsername(), new CartDAO.CartITF() {
            @Override
            public void xuli(Object obj) {
                arr = (ArrayList<Cart>) obj;
                cartAdapter = new CartAdapter(getContext(),mainActivity.getUser(), arr, new CartAdapter.CartITF() {
                    @Override
                    public void xuli(Object obj) {
                    }
                }, new CartAdapter.CartITF() {
                    @Override
                    public void xuli(Object obj) {
                        // plus
                        arrIdItem = (ArrayList<Cart>) obj;

                    }
                }, new CartAdapter.CartITF() {
                    @Override
                    public void xuli(Object obj) {
                        int giatien = (int) obj;
                        txtprice.setText(giatien + " VND");
                    }
                });
                rcv.setAdapter(cartAdapter);
            }
        });

    }
    private void setBtndat(ArrayList<Cart> cartArrayList){
        if (cartArrayList.size() == 0 || cartArrayList.isEmpty()){
            Toast.makeText(getContext(), "Vui lòng thêm sản phẩm để tiếp tục", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), ThanhToanActivity.class);
        intent.putExtra("user", mainActivity.getUser());
        intent.putExtra("arrItem", cartArrayList);
        txtprice.setText("0");
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
}