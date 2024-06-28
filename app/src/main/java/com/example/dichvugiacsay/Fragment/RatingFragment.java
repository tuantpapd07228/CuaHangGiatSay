package com.example.dichvugiacsay.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dichvugiacsay.Adapter.OrderDetailRatingAdapter;
import com.example.dichvugiacsay.Model.OrderDetail;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.OrderActivity;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.OrderDetailDAO;
import com.example.dichvugiacsay.data.RatingDAO;

import java.util.ArrayList;


public class RatingFragment extends Fragment {

    OrderDetailRatingAdapter orderDetailRatingAdapter;
    ArrayList<OrderDetail> orderDetails;
    RecyclerView recyclerView;
    OrderDetailDAO orderDetailDAO;

    OrderActivity activity ;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_rating, container, false);
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        l.setOrientation(LinearLayoutManager.VERTICAL);
        activity = (OrderActivity) getActivity();
        user = activity.getUser();
        orderDetailDAO = new OrderDetailDAO(getContext());
        recyclerView = v.findViewById(R.id.rating_rcv);
        recyclerView.setLayoutManager(l);
        setOrderDetailAdapter(user.getUsername());
        return v;
    }
    private void setOrderDetailAdapter(String username){
        orderDetails = new ArrayList<>();
        orderDetailDAO.getAll( username, new OrderDetailDAO.OrderDetailitf() {
            @Override
            public void xuli(Object obj) {
                orderDetails = (ArrayList<OrderDetail>) obj;
                orderDetailRatingAdapter = new OrderDetailRatingAdapter(orderDetails, getContext(), user,
                        new OrderDetailRatingAdapter.RatingITF() {
                            @Override
                            public void xuli(Object obj) {
                                RatingDAO ratingDAO = new RatingDAO(getContext());
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                LayoutInflater inflater = getLayoutInflater();
                                View view = inflater.inflate(R.layout.rating_service_dialog, null);
                                builder.setView(view);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                alertDialog.setCancelable(false);
                                RatingBar ratingBar = view.findViewById(R.id.rating_ratingbar);
                                EditText edtcomment = view.findViewById(R.id.rating_comment);
                                Button btnDong = view.findViewById(R.id.danhgiadialog_dong);

                                Button send = view.findViewById(R.id.rating_send);
                                btnDong.setOnClickListener(v->{
                                    alertDialog.dismiss();
                                });
//                                ratingBar.setIsIndicator(true);
                                ratingBar.setMax(5);
                                send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String comment = edtcomment.getText().toString().trim();

                                        if (comment.equals("")){

                                            Toast.makeText(getContext(), "Vui lòng viết nhận xét trước khi gửi", Toast.LENGTH_SHORT).show();
                                        }else{
                                            ratingDAO.insert(user.getId(), obj+"", comment, ratingBar.getRating(), new RatingDAO.RatingITF() {
                                                @Override
                                                public void xuli(Object obj) {
                                                    Toast.makeText(getContext(), "Cảm ơn bạn đã nhận xét", Toast.LENGTH_SHORT).show();
                                                    alertDialog.dismiss();
                                                }
                                            });
                                        }
                                    }
                                });

                                alertDialog.show();
                            }
                        });
                recyclerView.setAdapter(orderDetailRatingAdapter);
            }
        });

    }
}