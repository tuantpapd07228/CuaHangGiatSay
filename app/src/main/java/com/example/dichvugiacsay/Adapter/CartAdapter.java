package com.example.dichvugiacsay.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Model.Cart;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.CartDAO;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<Cart> arr, arrpprice;
    public interface CartITF {void xuli(Object obj);}

    CartITF  sub, plus, sumprice;
    private Context context;
    private CartDAO cartDAO;
    private User user;

    public CartAdapter(Context context ,User user, ArrayList<Cart> arr,CartITF sub, CartITF plus, CartITF sumprice) {
        this.context = context;
        this.user = user;
        this.arr = arr;
        this.sub = sub;
        this.plus = plus;
        this.sumprice = sumprice;
        arrpprice = new ArrayList<>();
        cartDAO = new CartDAO(context);
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cart = arr.get(position);
        if (cart ==  null) return;
        try{
            int idImg = context.getResources().getIdentifier("drawable/"+cart.getImg(), null , context.getPackageName() );
            holder.img.setImageResource(idImg);
            holder.name.setText(cart.getNameService());
            holder.price.setText((cart.getQuantity() * cart.getPriceService())+"");
            holder.quantity.setText(cart.getQuantity()+"");
            holder.description.setText(cart.getDescription());

            holder.choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        arrpprice.add(cart);
                        sumprice.xuli(setPirce(arrpprice));

//                    Log.e("atuan list ", arrpprice.toString());
                    }else{
                        for (int i = 0; i < arrpprice.size(); i++) {
                            if (arrpprice.get(i).getIdService() == cart.getIdService()){
                                arrpprice.remove(i);
                                sumprice.xuli(setPirce(arrpprice));

                            }
                        }
                        notifyDataSetChanged();
                    }
                    plus.xuli(arrpprice);
                }
            });
            holder.sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cart.setQuantity(cart.getQuantity() - 1);
                    for (int i = 0; i < arrpprice.size(); i++) {
                        if (arrpprice.get(i).getIdService() == cart.getIdService()){
                            arrpprice.get(i).setQuantity(cart.getQuantity());
                            if (cart.getQuantity() == 0){
                                arrpprice.remove(i);

                            }
                        }
                    }
                    if (cart.getQuantity() != 0){
                        cartDAO.update(cart.getIdCart()+"", cart.getQuantity()+"");
                        Log.e("delete cart", "da delete cart");
                        Log.e("delete so luong", cart.getIdService()+"====="+ cart.getQuantity()+"");
                    }
                    if (cart.getQuantity() == 0){
                        arr.remove(cart);
                        cartDAO.delete(user.getId(), cart.getIdService()+"", new CartDAO.CartITF() {
                            @Override
                            public void xuli(Object obj) {

                            }
                        });
                        notifyDataSetChanged();
                    }
                    sumprice.xuli(setPirce(arrpprice));
                    holder.quantity.setText(cart.getQuantity()+"");
                    holder.price.setText((cart.getQuantity() * cart.getPriceService()) +"");
                    plus.xuli(arrpprice);
                }
            });
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    cart.setQuantity(cart.getQuantity() + 1);
                    for (int i = 0; i < arrpprice.size(); i++) {
                        if (arrpprice.get(i).getIdService() == cart.getIdService()){
                            arrpprice.get(i).setQuantity(cart.getQuantity());


                        }
                    }
                    cartDAO.update(cart.getIdCart()+"", cart.getQuantity()+"");
                    Log.e("delete so luong", cart.getIdService()+"====="+ cart.getQuantity()+"");

                    Log.e("delete cart", "da update cart");
                    sumprice.xuli(setPirce(arrpprice));
                    holder.quantity.setText(cart.getQuantity()+"");
                    holder.price.setText((cart.getQuantity() * cart.getPriceService()) +"");
                    plus.xuli(arrpprice);
                }
            });
        }catch (Exception e){
            Log.e("error", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, description, price, sub, quantity, plus;
        private CheckBox choice;
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.itemCart_img);
            name = itemView.findViewById(R.id.itemCard_name);
            description = itemView.findViewById(R.id.itemCard_Description);
            price = itemView.findViewById(R.id.itemCard_price);
            sub = itemView.findViewById(R.id.itemCard_sub);
            quantity = itemView.findViewById(R.id.itemCard_quantity);
            plus = itemView.findViewById(R.id.itemCard_plus);
            choice = itemView.findViewById(R.id.itemCard_choice);
        }
    }
    private int setPirce(ArrayList<Cart> arrprice1){
        int sumprice = 0;
        for (int i = 0; i < arrprice1.size(); i++) {
            sumprice += arrprice1.get(i).getQuantity() * arrprice1.get(i).getPriceService();
        }
        return sumprice;
    }
}
