package com.example.dichvugiacsay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Model.Tips;
import com.example.dichvugiacsay.R;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsHolder>{
    private Context context;
    private List<Tips> mList;
    private TipsListener tipsListener;

    public interface TipsListener{
        void onItemClick( int pos);
    }

    public void setListener(TipsListener tipsListener) {
        this.tipsListener = tipsListener;
    }

    public TipsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Tips> list) {
        this.mList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TipsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tips, parent, false);
        return new TipsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipsHolder holder, int position) {
        Tips tips = mList.get(position);
        if(tips == null) {
            return;
        }
        holder.imgTips.setImageResource(tips.getResourceId());
        holder.txtTips.setText(tips.getName());
        holder.itemView.setOnClickListener(v->{
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && tipsListener != null) {
                tipsListener.onItemClick(adapterPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class TipsHolder extends RecyclerView.ViewHolder{
        private ImageView imgTips;
        private TextView txtTips;
        public TipsHolder(@NonNull View itemView) {
            super(itemView);

            imgTips = itemView.findViewById(R.id.imgTips);
            txtTips = itemView.findViewById(R.id.txtTips);
        }
    }
}
