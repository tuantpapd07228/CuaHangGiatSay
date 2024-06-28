package com.example.dichvugiacsay.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dichvugiacsay.Model.OrderDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderDetailDAO {
    private Context context;
    private String getOrderDetail = IP.IP + "/giatsay/getorderDetailForRating.php";

    public OrderDetailDAO(Context context) {
        this.context = context;
    }

    public interface OrderDetailitf{void xuli(Object obj);}

    public void getAll(String username , OrderDetailitf xuli){
        ArrayList<OrderDetail> arr = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getOrderDetail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                OrderDetail orderDetail;
                try {
                JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
//                        id, int idOrder, int idService, int quantity,  int price, int status
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        arr.add(new OrderDetail(
                                jsonObject.getString("name"),
                                jsonObject.getString("idOrder"),
                                jsonObject.getString("quantity"),
                                jsonObject.getString("price"),
                                jsonObject.getString("status"),
                                jsonObject.getString("idStore"),
                                jsonObject.getString("idService"),
                                jsonObject.getString("img")));
                    }
                    xuli.xuli(arr);

                }

                catch (Exception e) {
                    Log.e("atuan err" ,e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
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

}
