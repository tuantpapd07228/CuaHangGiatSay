package com.example.dichvugiacsay.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dichvugiacsay.Fragment.ComfirmationFragment;
import com.example.dichvugiacsay.Fragment.CompleteFragment;
import com.example.dichvugiacsay.Fragment.RatingFragment;
import com.example.dichvugiacsay.Fragment.WaitingDeliveryFragment;

public class Order_Adapter_Fragment extends FragmentStateAdapter {
    public Order_Adapter_Fragment(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ComfirmationFragment();
            case 1:
                return new WaitingDeliveryFragment();
            case 2:
                return new CompleteFragment();
            case 3:
                return new RatingFragment();
        }
        return new ComfirmationFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
