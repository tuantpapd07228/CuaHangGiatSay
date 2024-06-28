package com.example.dichvugiacsay.Fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dichvugiacsay.Adapter.Slider_Image_Adapter;
import com.example.dichvugiacsay.Adapter.StoreAdapter;
import com.example.dichvugiacsay.Adapter.TipsAdapter;
import com.example.dichvugiacsay.Adapter.User_CardViewAdapter;
import com.example.dichvugiacsay.MainActivity;
import com.example.dichvugiacsay.Model.Slider_Image;
import com.example.dichvugiacsay.Model.Store;
import com.example.dichvugiacsay.Model.Tips;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.Model.User_CardView;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.ServiceDetailActivity;
import com.example.dichvugiacsay.data.StoreDAO;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class Fragment_HomePage extends Fragment {
    View view;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Slider_Image> list;

    private RecyclerView recyclerView, recyclerView1, recyclerView2;

    private User_CardViewAdapter userCartViewAdapter;

    private TipsAdapter tipsAdapter;

    private StoreAdapter storeAdapter;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private StoreDAO storeDAO;

    private MainActivity mainActivity;

    private User user;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = viewPager2.getCurrentItem();
            if(currentPosition == list.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(currentPosition + 1);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__home_page, container, false);
        storeDAO = new StoreDAO(getContext());
        viewPager2 = view.findViewById(R.id.view_pager_2);
        circleIndicator3 = view.findViewById(R.id.circle_Indicator3);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView2 = view.findViewById(R.id.recyclerView2);
        mainActivity = (MainActivity) getActivity();

        // set các thuộc tính viewPager2
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        user = mainActivity.getUser();

        // Thực hiện chuyển động cho hình ảnh
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        // hiển thị hình ở giữa lớn hơn những hình còn lại
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);

        list = getListImage();
        Slider_Image_Adapter slider_image_adapter = new Slider_Image_Adapter(list);
        viewPager2.setAdapter(slider_image_adapter);
        // liên kết viewPager2 với circleIndicator
        circleIndicator3.setViewPager(viewPager2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });



        userCartViewAdapter = new User_CardViewAdapter(getContext(), new User_CardViewAdapter.User_CardViewITF() {
            @Override
            public void xuli(int pos) {
                if (pos == 6){
                    Toast.makeText(mainActivity, "Đang phát triên thêm dịch vụ", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(getContext(), ServiceDetailActivity.class);
                    i.putExtra("id", pos);
                    i.putExtra("user",user);
                    startActivity(i);
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        userCartViewAdapter.setData(getListUserCartView());
        recyclerView.setAdapter(userCartViewAdapter);


        tipsAdapter = new TipsAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setFocusable(false);
        recyclerView1.setNestedScrollingEnabled(false);

        tipsAdapter.setData(getListTipsCardView());
        recyclerView1.setAdapter(tipsAdapter);
        tipsAdapter.setListener(new TipsAdapter.TipsListener() {
            @Override
            public void onItemClick(int pos) {
                switch (pos){
                    case 0:dialogData1(R.layout.alert_dialog_tip2);
                            break;
                    case 1:dialogData1(R.layout.alert_dialog_tip1);
                            break;
                    case 2:dialogData1(R.layout.alert_dialog_tip3);
                            break;
                }

            }
        });

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager1);
        storeAdapter = new StoreAdapter(getContext());

        storeDAO.getAll(new StoreDAO.XuLiStore() {
            @Override
            public void xuli(Object obj) {
                ArrayList<Store > arr = (ArrayList<Store>) obj;

                storeAdapter.setData(arr);
                recyclerView2.setAdapter(storeAdapter);
            }
        });

        storeAdapter.setListener(new StoreAdapter.StoreListener() {
            @Override
            public void onItemClick(int pos) {
                FragmentStore fragmentStore = new FragmentStore();
                Bundle bundle = new Bundle();
                bundle.putInt("id", pos+1);
                fragmentStore.setArguments(bundle);
                loadFragment(fragmentStore);

            }
        });
        return view;
    }


    private List<User_CardView> getListUserCartView(){
        List<User_CardView> list = new ArrayList<>();
        list.add(new User_CardView(1, R.drawable.icon_dichvu1, "Giặt giày"));
        list.add(new User_CardView(2 ,R.drawable.icon_dichvu2, "Giặt sấy"));
        list.add(new User_CardView(3, R.drawable.icon_dichvu, "Giặt ủi"));
        list.add(new User_CardView(4, R.drawable.icon_dichvu3, "Giặt hấp"));
        list.add(new User_CardView(5, R.drawable.icon_dichvu4, "Giặt vest"));
        list.add(new User_CardView(6, R.drawable.icon_dichvu5, "Khác"));

        return list;
    }


    private List<Tips> getListTipsCardView(){
        List<Tips> tipsList = new ArrayList<>();
        tipsList.add(new Tips(R.drawable.meovat1, "Mẹo vặt quần áo không hôi vào mùa mưa."));
        tipsList.add(new Tips(R.drawable.meovat3, "Chọn quần jeans phù hợp với dáng người"));
        tipsList.add(new Tips(R.drawable.meovat2, "Làm thế nào để quần áo khô nhanh trong mùa mưa ẩm?"));

        return tipsList;
    }



    private List<Slider_Image> getListImage() {
        List<Slider_Image> list = new ArrayList<>();
        list.add(new Slider_Image(R.drawable.slider_image2));
        list.add(new Slider_Image(R.drawable.slider_image1));
        list.add(new Slider_Image(R.drawable.slider_image));

        return list;
    }

    private void dialogData1(int layoutId) {
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


}