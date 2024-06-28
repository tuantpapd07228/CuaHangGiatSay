package com.example.dichvugiacsay.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dichvugiacsay.Fragment.Fragment_Account;
import com.example.dichvugiacsay.Fragment.Fragment_Cart;
import com.example.dichvugiacsay.Fragment.Fragment_HomePage;

public class ViewPagerNavigationBottomAdapter extends FragmentStateAdapter {
    public ViewPagerNavigationBottomAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment_HomePage();
            case 1:
                return new Fragment_Cart();
            case 2:
                return new Fragment_Account();
            default:
                return new Fragment_HomePage();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
