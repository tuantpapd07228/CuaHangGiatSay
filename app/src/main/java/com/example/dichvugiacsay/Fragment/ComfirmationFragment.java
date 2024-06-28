package com.example.dichvugiacsay.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Adapter.XacNhanOuterAdapter;
import com.example.dichvugiacsay.Model.DonHangOuter;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.OrderActivity;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.CartDAO;
import com.example.dichvugiacsay.data.DonHangDAO;

import java.util.ArrayList;


public class ComfirmationFragment extends Fragment {

    XacNhanOuterAdapter xacNhanOuterAdapter;
    RecyclerView rcv;
    CartDAO cartDAO;
    DonHangDAO donHangDAO;
    User user;

    OrderActivity orderActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comfirmation, container, false);
        rcv = v.findViewById(R.id.comfirm_rcv);
        cartDAO = new CartDAO(getContext());
        orderActivity = (OrderActivity) getActivity();
        user = orderActivity.getUser();
        donHangDAO = new DonHangDAO(getContext());
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        l.setOrientation(RecyclerView.VERTICAL);
        rcv.setLayoutManager(l);
        setData();


        return v;
    }

    private void setData(){
        donHangDAO.getOutter(user.getUsername(), new CartDAO.CartITF() {
            @Override
            public void xuli(Object obj) {
                ArrayList<DonHangOuter> arr = (ArrayList<DonHangOuter>) obj;
                Log.e("arr setdata", arr.toString());
                xacNhanOuterAdapter = new XacNhanOuterAdapter(getContext(), arr);
                rcv.setAdapter(xacNhanOuterAdapter);
            }
        });
    }
}