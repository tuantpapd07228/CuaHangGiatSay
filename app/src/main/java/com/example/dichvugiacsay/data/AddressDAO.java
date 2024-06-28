package com.example.dichvugiacsay.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dichvugiacsay.Model.Address;
import com.example.dichvugiacsay.Model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddressDAO {
    private Context context;
    private String AddAddressURL = IP.IP + "/giatsay/AddressAdd.php";
    private String UpdateAddressURL = IP.IP + "/giatsay/AddressUpdate.php";
    private String DeleteAddressURL = IP.IP + "/giatsay/AddressDelete.php";
    private String getAddressURL = IP.IP + "/giatsay/Addressget.php";
    private ProgressDialog progressDialog;

    public AddressDAO(Context context) {
        this.context = context;
    }
    private RequestQueue requestQueue;
    public interface AddressITF{void xuli(Object obj);}
    public void getAll(String username, AddressITF xuli){
        progressDialog= new ProgressDialog(context);
        progressDialog.show();
        requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getAddressURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Address > arrayList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        arrayList.add(new Address(
                                jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("phone"),
                                jsonObject.getString("address")
                        ));
                    }
                    xuli.xuli(arrayList);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
    public void add(Address address, User user, AddressITF xuli){
        progressDialog= new ProgressDialog(context);
        progressDialog.show();
        requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AddAddressURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                xuli.xuli(null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idKhachHang", user.getId());
                map.put("address", address.getAddress());
                map.put("phone", address.getPhone());
                map.put("name", address.getName());
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
    public void update(Address address, AddressITF xuli){
        requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UpdateAddressURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Dã update thông tin", Toast.LENGTH_SHORT).show();
                xuli.xuli(null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idAddress", address.getId());
                map.put("name", address.getName());
                map.put("phone", address.getPhone());
                map.put("address", address.getAddress());
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void delete(Address address, AddressITF xuli){
        requestQueue = Volley.newRequestQueue(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DeleteAddressURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Dã delete thông tin", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                xuli.xuli(null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("err delete", error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", address.getId());
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
}
