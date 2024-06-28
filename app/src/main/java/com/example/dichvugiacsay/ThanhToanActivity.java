package com.example.dichvugiacsay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dichvugiacsay.Adapter.ThanhToanAdapter;
import com.example.dichvugiacsay.Model.Address;
import com.example.dichvugiacsay.Model.Cart;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.data.AddressDAO;
import com.example.dichvugiacsay.data.CartDAO;
import com.example.dichvugiacsay.data.DonHangDAO;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ThanhToanActivity extends AppCompatActivity {
    private ThanhToanAdapter thanhToanAdapter;
    private RecyclerView rcv;
    private LinearLayoutManager l;
    private TextView txtdate, txttienhang, txttongtienhang, txtname, txtaddress;
    private Button back;
    private Button btndathang;
    private LinearLayout linearLayout;
    private ArrayList<Cart> cartArrayList;
    private User user;
    int tienhangkship = 0;
    private AddressDAO addressDAO;
    private Address address;
    private DonHangDAO donHangDAO;
    private CartDAO cartDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        Intent i = getIntent();
        address = (Address) i.getSerializableExtra("address");
        user = (User) i.getSerializableExtra("user");
        cartArrayList = (ArrayList<Cart>) i.getSerializableExtra("arrItem");
        addressDAO = new AddressDAO(this);
        rcv = findViewById(R.id.thanhtoan_rcv);
        cartDAO = new CartDAO(this);
        donHangDAO = new DonHangDAO(this);
        thanhToanAdapter = new ThanhToanAdapter(cartArrayList, this);
        l = new LinearLayoutManager(this);
        l.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(l);
        rcv.setAdapter(thanhToanAdapter);
        anhxa();
        setData();

    }
    private void anhxa(){
        txtdate = findViewById(R.id.thanhtoan_date);
        txttienhang = findViewById(R.id.thanhtoan_tienhang);
        txttongtienhang = findViewById(R.id.thanhtoan_tongtienhang);
        txtname = findViewById(R.id.thanhtoan_name_phone);
        txtaddress = findViewById(R.id.thanhtoan_address);
        btndathang = findViewById(R.id.thanhtoan_btndathang);
        linearLayout = findViewById(R.id.thanhtoan_info);
        back = findViewById(R.id.thanhtoan_back);
    }
    private void setData(){
        if (address == null){
            addressDAO.getAll(user.getUsername(), new AddressDAO.AddressITF() {
                @Override
                public void xuli(Object obj) {
                    ArrayList<Address> arr = (ArrayList<Address>) obj;
                    try {
                        address = arr.get(0);
                        txtname.setText(address.getName() + " | " +address.getPhone());
                        txtaddress.setText(address.getAddress());
                    }catch (Exception e){
                        // tao 1 ddialog them dia chi vao day
                    }
                }
            });
        }else{
            txtname.setText(address.getName() + " | " +address.getPhone());
            txtaddress.setText(address.getAddress());
        }
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        txtdate.setText(day+"/"+(month+1)+"/"+year);
        for (int i = 0; i < cartArrayList.size(); i++) {
            tienhangkship += cartArrayList.get(i).getQuantity() * cartArrayList.get(i).getPriceService();
        }
        Locale localeVN = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeVN);
        String tienhangkshipdinhdang = numberFormat.format(tienhangkship);
        String tongtienhangformat = numberFormat.format(tienhangkship+15000);
        txttongtienhang.setText(tongtienhangformat);
        txttienhang.setText(tienhangkshipdinhdang);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThanhToanActivity.this, AddressActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("arrItem", cartArrayList);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartArrayList.size() == 0 || cartArrayList.isEmpty()){
                    Toast.makeText(ThanhToanActivity.this, "Vui lòng thêm sản phẩm để tiếp tục", Toast.LENGTH_SHORT).show();
                }else {
                    String ngaydathang = year+"-"+(month+1)+"-"+day;
                    setBtndathang(address, ngaydathang, (tienhangkship+15000)+"",getIdDonHang()+"", cartArrayList);
                    Toast.makeText(ThanhToanActivity.this, "Đã nhận được đơn hàng", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ThanhToanActivity.this, OrderActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
    private void setBtndathang(Address address2, String date, String tonghoadon, String iddonhang, ArrayList<Cart> cartArrayList1){
        donHangDAO.insert(iddonhang, user.getId(), date, tonghoadon, address2, new DonHangDAO.DonHangITF() {
            @Override
            public void xuli(Object object) {
                for (int i = 0; i < cartArrayList1.size(); i++) {
                    int i1 = i;
                    donHangDAO.insertCTDH(iddonhang, cartArrayList1.get(i).getIdService()+"", cartArrayList1.get(i).getQuantity()+"", new DonHangDAO.DonHangITF() {
                        @Override
                        public void xuli(Object object) {
                            cartDAO.delete(user.getId(), cartArrayList1.get(i1).getIdService()+"", new CartDAO.CartITF() {
                                @Override
                                public void xuli(Object obj) {
                                    Log.e("thanh toan acti", cartArrayList1.get(i1).getIdService()+"");
                                }
                            });
                        }
                    });

                }
            }
        });
    }


    private int getIdDonHang(){
        return (int) (Math.abs(System.currentTimeMillis()) % Integer.MAX_VALUE);
    }

}