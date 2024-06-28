package com.example.dichvugiacsay.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Model.User_CardView;
import com.example.dichvugiacsay.R;

import java.util.List;

public class User_CardViewAdapter extends RecyclerView.Adapter<User_CardViewAdapter.UserViewHolder>{

    private Context context;
    private List<User_CardView> mList;
    public interface User_CardViewITF{void xuli(int pos);}

    User_CardViewITF userCardViewITF;
    public User_CardViewAdapter(Context context, User_CardViewITF userCardViewITF) {
        this.context = context;
        this.userCardViewITF = userCardViewITF;
    }

    public void setData(List<User_CardView> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User_CardView userCartView = mList.get(position);
        if(userCartView == null) {
            return;
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCardViewITF.xuli(userCartView.getId());
            }
        });
        holder.imgUser.setImageResource(userCartView.getResourceImage());
        holder.txtName.setText(userCartView.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();

    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgUser;
        private TextView txtName;
        private CardView cardView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_cardviewlayout);
            imgUser = itemView.findViewById(R.id.imgCartView);
            txtName = itemView.findViewById(R.id.textViewName);

        }
    }
}
