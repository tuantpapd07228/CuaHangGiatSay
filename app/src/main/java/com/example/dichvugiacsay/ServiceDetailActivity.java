package com.example.dichvugiacsay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dichvugiacsay.Adapter.DanhGiaAdapter;
import com.example.dichvugiacsay.Model.DanhGia;
import com.example.dichvugiacsay.Model.Service;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.data.CartDAO;
import com.example.dichvugiacsay.data.RatingDAO;
import com.example.dichvugiacsay.data.StoreDAO;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ServiceDetailActivity extends AppCompatActivity {

    private TextView name, description, price, sao;
    private ImageView img;
    private Button btnaddCart,back, btndanhgia;
    private CartDAO cartDAO;
    private StoreDAO storeDAO;
    private Service service;
    private User user;
    int id  = 0;
    private RatingDAO ratingDAO;
    Float totalpoint;
    private DecimalFormat decimalFormat = new DecimalFormat("#.#");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);
        storeDAO = new StoreDAO(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        ratingDAO = new RatingDAO(ServiceDetailActivity.this);
        user = (User) intent.getSerializableExtra("user");
//        Log.e("id and user activity", id+"-"+user.toString());

        cartDAO = new CartDAO(ServiceDetailActivity.this);
        storeDAO.getAllService(new StoreDAO.XuLiStore() {
            @Override
            public void xuli(Object obj) {
                ArrayList<Service> arrayList = (ArrayList<Service>) obj;
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getId() == id){
                        service = arrayList.get(i);
                        setData(service);
                        break;
                    }
                }
            }
        });
        anhxa();
    }
    private void anhxa(){
        img = findViewById(R.id.servicedetail_img);
        back = findViewById(R.id.servicedetail_back);
        name = findViewById(R.id.servicedetail_name);
        description = findViewById(R.id.servicedetail_description);
        price = findViewById(R.id.servicedetail_price);
        sao = findViewById(R.id.servicedetail_sao);
        img = findViewById(R.id.servicedetail_img);
        btnaddCart = findViewById(R.id.servicedetail_addcart);
        btndanhgia = findViewById(R.id.servicedetail_danhgia);
    }

    private void setData(Service sv){
        Log.e("service detail", service.toString());

        int idimg = getApplication().getResources().getIdentifier("drawable/"+sv.getImg(), null, getPackageName());
        img.setImageResource(idimg);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ServiceDetailActivity.this, MainActivity.class);
                intent1.putExtra("fragmentCart", 1);
                intent1.putExtra("user",user);
                startActivity(intent1);
            }
        });
        name.setText(sv.getName());
        price.setText(sv.getPrice());
        description.setText(sv.getDescription());

        ratingDAO.getTotalStar(sv.getId() + "", new RatingDAO.RatingITF() {
            @Override
            public void xuli(Object obj) {
                if (obj == null){
                    sao.setText("Chưa có đánh giá");
                }else {
                    String formatpoint = decimalFormat.format(obj);
                    sao.setText(formatpoint+"");
                    totalpoint = (Float) obj;
                }
            }
        });

        btnaddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("user ser" , user.toString()+service.getId());
                cartDAO.insert(user, service.getId()+"");
            }
        });
        btndanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDanhGiaDialog();
            }
        });
    }

    private void setDanhGiaDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.danhgia_dialog, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        RatingBar ratingBar = view.findViewById(R.id.danhgiadialog_ratingbar);
        if (ratingBar != null) {
            ratingBar.setIsIndicator(true);
        }
        ratingBar.setRating(totalpoint);
        TextView tongsao = view.findViewById(R.id.danhgiadialog_tongsao);
        RecyclerView rcv = view.findViewById(R.id.danhgiadialog_rcv);
        Button btndong  = view.findViewById(R.id.danhgiadialog_dong);
        LinearLayoutManager l = new LinearLayoutManager(ServiceDetailActivity.this);
        l.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(l);
        tongsao.setText(decimalFormat.format(totalpoint));

        ratingDAO.getDanhGia(service.getId() + "", new RatingDAO.RatingITF() {
            @Override
            public void xuli(Object obj) {
                DanhGiaAdapter danhGiaAdapter = new DanhGiaAdapter((ArrayList<DanhGia>) obj, ServiceDetailActivity.this);
                rcv.setAdapter(danhGiaAdapter);
            }
        });
        btndong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}