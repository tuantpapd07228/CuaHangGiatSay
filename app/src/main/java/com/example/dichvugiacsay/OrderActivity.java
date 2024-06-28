package com.example.dichvugiacsay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.dichvugiacsay.Adapter.Order_Adapter_Fragment;
import com.example.dichvugiacsay.Model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OrderActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra  ("user");

        tabLayout = findViewById(R.id.orderTablayout);
        viewPager2 = findViewById(R.id.orderViewPager);

        Order_Adapter_Fragment adapterFragment = new Order_Adapter_Fragment(this);
        viewPager2.setAdapter(adapterFragment);
        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> {
            switch (position){
                case 0:
                    tab.setIcon(R.drawable.baseline_av_timer_24);
                    tab.setText("Chờ xác nhận");
                    break;
                case 1:
                    tab.setIcon(R.drawable.baseline_fire_truck_24);
                    tab.setText("Đang giao");
                    break;
                case 2:
                    tab.setIcon(R.drawable.baseline_assignment_turned_in_24);
                    tab.setText("Hoàn thành");
                    break;
                case 3:
                    tab.setIcon(R.drawable.baseline_star_border_purple500_24);
                    tab.setText("Đánh giá");
                    break;
            }
        })).attach();
    }
    public User getUser(){
        return user;
    }
}