package com.example.dichvugiacsay.data;

import android.content.Context;
import android.content.Intent;
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
import com.example.dichvugiacsay.Model.Cart;
import com.example.dichvugiacsay.Model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartDAO {
    private Context context;
    private String updateURL = IP.IP + "/giatsay/CartUpdate.php";
    private String deleteURL = IP.IP + "/giatsay/CartDelete.php";
    private String insertURL = IP.IP + "/giatsay/cartInsert.php";
    private String readURL = IP.IP + "/giatsay/cartRead.php";

    public CartDAO(Context context) {
        this.context = context;
    }
    public interface CartITF{void xuli(Object obj);}
    public void readinner(String username, CartITF xuli ){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, readURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Cart> arrayList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        arrayList.add(new Cart(
                                jsonObject.getInt("idCart"),
                                jsonObject.getInt("idService"),
                                jsonObject.getInt("quantity"),
                                jsonObject.getInt("price"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                jsonObject.getString("img")
                        ));
                    }
                    xuli.xuli(arrayList);
                    Log.e("atuan", arrayList.toString());
                } catch (Exception e) {
                    Log.e("atuan err cart 58", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("atuan err cart 64", error.getMessage());
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


    public void delete(String idkhachhang, String iddichvu, CartITF xuli) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        xuli.xuli(null);
                        Log.e("cart delete thanh cong 93", iddichvu);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("err cart", error.getMessage());
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > map = new HashMap<>();
                map.put("idkhachhang", idkhachhang);
                map.put("iddichvu", iddichvu);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void insert(User user, String iddichvu){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("insert cartt 122" , error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idkhachhang", user.getId());
                map.put("iddichvu", iddichvu);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void update(String idCart, String soluong){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("update ", "update thanh cong");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("update ", "update that bai");

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("soluong", soluong);
                map.put("idgiohang", idCart);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
