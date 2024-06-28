package com.example.dichvugiacsay.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Adapter.DonHoanThanhAdapter;
import com.example.dichvugiacsay.Model.DonHangOuter;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.OrderActivity;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.CartDAO;
import com.example.dichvugiacsay.data.DonHangDAO;

import java.util.ArrayList;


public class CompleteFragment extends Fragment {

    private RecyclerView rcv;
    private DonHoanThanhAdapter donHoanThanhAdapter;
    private DonHangDAO donHangDAO;
    private User user;
    private OrderActivity orderActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_complete, container, false);
        rcv = v.findViewById(R.id.complete_fragment);
        LinearLayoutManager l =new LinearLayoutManager(getContext());
        l.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(l);
        donHangDAO = new DonHangDAO(getContext());
        orderActivity = (OrderActivity) getActivity();
        user = orderActivity.getUser();
        setData();

        return v;
    }

    private void setData(){
        donHangDAO.getOutter(user.getUsername(), new CartDAO.CartITF() {
            @Override
            public void xuli(Object obj) {
                donHoanThanhAdapter = new DonHoanThanhAdapter(user, (ArrayList<DonHangOuter>) obj, getContext());
                rcv.setAdapter(donHoanThanhAdapter);
            }
        }, "hoanthanh");
    }


}