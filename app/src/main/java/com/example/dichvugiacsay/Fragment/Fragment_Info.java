package com.example.dichvugiacsay.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dichvugiacsay.MainActivity;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.UserDAO;
import com.google.android.material.textfield.TextInputEditText;

public class Fragment_Info extends Fragment {
    private TextView tv_name;
    private TextInputEditText edtName,edtEmail,edtPhone,edtAddress;

    private Button btnBack, btnSaveInfo;
    private MainActivity activity;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__info, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        tv_name = view.findViewById(R.id.tv_nameInfo);
        edtName = view.findViewById(R.id.edtUserNameInfo);
        edtEmail = view.findViewById(R.id.edtEmailInfo);
        edtPhone = view.findViewById(R.id.edtPhoneNumberInfo);
        edtAddress = view.findViewById(R.id.edtAddressInfo);
        btnSaveInfo = view.findViewById(R.id.btnSaveInfo);
        activity = (MainActivity) getActivity();
        user = activity.getUser();
        tv_name.setText(user.getName());
        edtName.setText(user.getName());
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhone());
        edtAddress.setText(user.getAddress());
        btnBack.setOnClickListener(v->{
            loadFragment(new Fragment_Account());
        });

        btnSaveInfo.setOnClickListener(v -> {
            String strname = edtName.getText().toString().trim();
            String strphone = edtPhone.getText().toString().trim();
            String stremail = edtEmail.getText().toString().trim();
            String straddress = edtAddress.getText().toString().trim();
            user.setName(strname);
            user.setAddress(straddress);
            user.setPhone(strphone);
            user.setEmail(stremail);
            if (validateForm(strname) && validateForm(strphone) && validateForm(stremail) && validateForm(straddress)){
                UserDAO userDAO = new UserDAO(getContext());
                userDAO.update(user, new UserDAO.UserITF() {
                    @Override
                    public void xuli(Object obj) {
                        edtName.setText(user.getName());
                        edtPhone.setText(user.getPhone());
                        Toast.makeText(getContext(), "Thông tin đã được thay đổi", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            Toast.makeText(getContext(), "Kiểm tra và nhập lại thông tin", Toast.LENGTH_SHORT).show();
        });
        return view;
    }

    private boolean validateForm(String str){
        return (str.isEmpty() || str.equals("")) ? false : true;
    }
    public void loadFragment(Fragment fragment){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_main,null);
        FrameLayout frameLayout = view.findViewById(R.id.frameLayoutMain);
        getActivity().getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(),fragment).commit();
    }
}