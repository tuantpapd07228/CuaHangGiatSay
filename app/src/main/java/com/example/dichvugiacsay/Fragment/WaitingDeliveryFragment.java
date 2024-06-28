package com.example.dichvugiacsay.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Adapter.GiaoHangAdapter;
import com.example.dichvugiacsay.Model.DonHangOuter;
import com.example.dichvugiacsay.OrderActivity;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.CartDAO;
import com.example.dichvugiacsay.data.DonHangDAO;

import java.util.ArrayList;


public class WaitingDeliveryFragment extends Fragment {

    private DonHangDAO donHangDAO;
    private ArrayList<DonHangOuter> donHangOuterArrayList;
    private OrderActivity orderActivity;
    private RecyclerView rcv;
    private LinearLayoutManager l;
    private GiaoHangAdapter giaoHangAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_waiting_delivery, container, false);
        donHangDAO = new DonHangDAO(getContext());
        orderActivity = (OrderActivity) getActivity();
        l = new LinearLayoutManager(getContext());
        l.setOrientation(LinearLayoutManager.VERTICAL);
        rcv= v.findViewById(R.id.danggiao_rcv);
        rcv.setLayoutManager(l);
        setData();

        return v;
    }
    private void setData(){
        donHangDAO.getOutter(orderActivity.getUser().getUsername(), new CartDAO.CartITF() {
            @Override
            public void xuli(Object obj) {
                donHangOuterArrayList = (ArrayList<DonHangOuter>) obj;
                giaoHangAdapter = new GiaoHangAdapter(donHangOuterArrayList, getContext());
                rcv.setAdapter(giaoHangAdapter);
            }
        }, 2);
    }
}