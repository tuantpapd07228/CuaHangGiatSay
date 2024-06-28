package com.example.dichvugiacsay.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Model.Slider_Image;
import com.example.dichvugiacsay.R;

import java.util.List;

public class Slider_Image_Adapter extends RecyclerView.Adapter<Slider_Image_Adapter.ImageViewHolder>{

    private final List<Slider_Image> list;


    public Slider_Image_Adapter(List<Slider_Image> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Slider_Image slider_image = list.get(position);
        if(slider_image == null) {
            return;
        }
        holder.img_sider.setImageResource(slider_image.getResourceId());
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img_sider;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img_sider = itemView.findViewById(R.id.img_sider);
        }
    }
}
