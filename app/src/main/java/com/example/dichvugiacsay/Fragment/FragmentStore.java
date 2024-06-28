package com.example.dichvugiacsay.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dichvugiacsay.Adapter.ServiceAdapter;
import com.example.dichvugiacsay.Adapter.SliderStoreAdapter;
import com.example.dichvugiacsay.Model.Service;
import com.example.dichvugiacsay.Model.SliderImageStore;
import com.example.dichvugiacsay.Model.Store;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.CartDAO;
import com.example.dichvugiacsay.data.StoreDAO;

import java.util.ArrayList;
import java.util.List;

public class FragmentStore extends Fragment {
    @Nullable
    private int idStore;
    private StoreDAO storeDAO;
    private Store store;
    private TextView name, address;
    private ServiceAdapter serviceAdapter;
    private ViewPager2 viewPager2;
    private RecyclerView recyclerView;
    private Button btnBack;

    private CartDAO cartDAO;

    User user;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment,container,false);
        viewPager2 = view.findViewById(R.id.ViewPagerSlide);
        Intent intent = getActivity().getIntent();
        idStore = intent.getIntExtra("id", 0);
        cartDAO = new CartDAO(getContext());
        storeDAO = new StoreDAO(getContext());
        storeDAO = new StoreDAO(getActivity());
        name = view.findViewById(R.id.storeName);
        address = view.findViewById(R.id.storeAddress);
        btnBack = view.findViewById(R.id.btnBackCuaHang1);
        recyclerView = view.findViewById(R.id.recyclerViewService);
        LinearLayoutManager l = new LinearLayoutManager(getActivity());
        l.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(l);
        user = (User) intent.getSerializableExtra("user");
        setData();
        btnBack.setOnClickListener(v->{
            loadFragment(new Fragment_HomePage());
        });
        List<SliderImageStore> list = new ArrayList<>();
        list.add(new SliderImageStore(R.drawable.giatui));
        list.add(new SliderImageStore(R.drawable.giathap));
        SliderStoreAdapter adapter = new SliderStoreAdapter(list,viewPager2);
        adapter.setListener(new SliderStoreAdapter.SliderListener() {
            @Override
            public void onClickItem(int pos) {
                switch (pos){
                    case 0:dialogData1(R.layout.alert_dialog_quy_tac_giat_ui);
                            break;
                    case 1: dialogData1(R.layout.alert_dialog_quy_tac_giat_hap);
                            break;
                }
            }
        });
        viewPager2.setAdapter(adapter);
        return view;
    }

    private void dialogData1(int layoutId){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(layoutId, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
        ImageView btnClose = alertDialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
    public void loadFragment(Fragment fragment){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_main,null);
        FrameLayout frameLayout = view.findViewById(R.id.frameLayoutMain);
        getActivity().getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(),fragment).commit();
    }
    private void setData(){
        storeDAO.getStore(idStore, new StoreDAO.XuLiStore() {
            @Override
            public void xuli(Object store1) {
                Log.e("atuan stor", store1.toString());
                store = (Store) store1;
                name.setText(store.getName());
                address.setText(store.getAddress());
            }
        });
        storeDAO.getAllService(new StoreDAO.XuLiStore() {
            @Override
            public void xuli(Object obj) {
                serviceAdapter = new ServiceAdapter((ArrayList<Service>) obj, getActivity(), new ServiceAdapter.AddCart() {
                    @Override
                    public void addcart(int idService) {
                        cartDAO.insert(user, idService+"");
                    }
                });
                recyclerView.setAdapter(serviceAdapter);
            }
        });
    }


}
