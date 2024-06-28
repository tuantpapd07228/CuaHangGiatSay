package com.example.dichvugiacsay.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.dichvugiacsay.Login;
import com.example.dichvugiacsay.Model.User;
import com.example.dichvugiacsay.R;
import com.example.dichvugiacsay.data.UserDAO;


public class Fragment_ChangePassword extends Fragment {

    private UserDAO userDAO;
    private EditText oldpass, newpass, cfpass;
    private Button changepw,btnBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__change_password,container,false);
        btnBack = view.findViewById(R.id.btnBackDMK);
        btnBack.setOnClickListener(v->{
            loadFragment(new Fragment_Account());
        });
        userDAO = new UserDAO(getActivity());
        oldpass = view.findViewById(R.id.edtPassOld);
        newpass = view.findViewById(R.id.edtNewPass);
        cfpass = view.findViewById(R.id.edtConfirmPass);
        changepw = view.findViewById(R.id.btnSave);
        Intent intent = getActivity().getIntent();
        User user = (User) intent.getSerializableExtra("user");
        changepw.setOnClickListener(v->{
            String oldpw = oldpass.getText().toString().trim();
            String newpw = newpass.getText().toString().trim();
            String cfpw = cfpass.getText().toString().trim();
            if (cfpw.equalsIgnoreCase(newpw)){
                if (validateForm(oldpw) && validateForm(newpw) && validateForm(cfpw)){
                    if (newpw.equalsIgnoreCase(cfpw)){
                        changePW(user.getUsername(), oldpw, newpw);
                        loadFragment(new Fragment_Account());
                    }
                }else{
                    Toast.makeText(getContext(), "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getContext(), "Mật khẩu mới phải nhập giống nhau", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    public void loadFragment(Fragment fragment){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_main,null);
        FrameLayout frameLayout = view.findViewById(R.id.frameLayoutMain);
        getActivity().getSupportFragmentManager().beginTransaction().replace(frameLayout.getId(),fragment).commit();
    }
    private void changePW(String username, String oldpw, String newpw){
        userDAO.changePassword(username , oldpw, newpw);
    }
    private boolean validateForm(String str){
        return (str.isEmpty() || str.equals("")) ? false :true;
    }
}