package com.example.dichvugiacsay.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dichvugiacsay.Fragment.OnBoardDingFragment;
import com.example.dichvugiacsay.Fragment.OnBoarDingFragment1;
import com.example.dichvugiacsay.Fragment.OnBoarDingFragment2;
import com.example.dichvugiacsay.Onboarding.OnBoarDingActivity;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(OnBoarDingActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OnBoardDingFragment();
            case 1:
                return new OnBoarDingFragment1();
            case 2:
                return new OnBoarDingFragment2();
            default:
                return new OnBoardDingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
