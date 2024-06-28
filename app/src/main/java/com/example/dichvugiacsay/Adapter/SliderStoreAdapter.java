package com.example.dichvugiacsay.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dichvugiacsay.Model.SliderImageStore;
import com.example.dichvugiacsay.R;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderStoreAdapter extends RecyclerView.Adapter<SliderStoreAdapter.SliderCuaHangViewHolder> {
    private List<SliderImageStore> list;
    private ViewPager2 viewPager2;
    private SliderListener sliderListener;

    public void setListener(SliderListener sliderListener) {
        this.sliderListener = sliderListener;
    }

    public interface SliderListener{
        void onClickItem(int pos);
    }

    public SliderStoreAdapter(List<SliderImageStore> list, ViewPager2 viewPager2) {
        this.list = list;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderCuaHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderCuaHangViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderCuaHangViewHolder holder, int position) {
        holder.setImage(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && sliderListener!= null) {
                    sliderListener.onClickItem(adapterPosition);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SliderCuaHangViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView roundedImageView;

        public SliderCuaHangViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.imgSlider);
        }
        public void setImage(SliderImageStore sliderImageCuaHang){
            roundedImageView.setImageResource(sliderImageCuaHang.getImg());
        }
    }

}
