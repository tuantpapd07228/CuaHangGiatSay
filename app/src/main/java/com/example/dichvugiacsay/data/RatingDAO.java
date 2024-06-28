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
import com.example.dichvugiacsay.Model.DanhGia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RatingDAO {
    private Context context;
    private String insertURL = IP.IP +"/giatsay/RatingInsert.php";
    private String totalStarURL = IP.IP +"/giatsay/getRatingTotal.php";
    private String getCMTDanhGiaURL = IP.IP + "/giatsay/RatingGetComment.php";
    public RatingDAO(Context context) {
        this.context = context;
    }


    public interface RatingITF{void xuli(Object obj);}
    public void insert(String idkhachhang, String iddichvu, String nhanxet, Float sao, RatingITF xuli){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                map.put("idkhachhang",  idkhachhang+"");
                map.put("iddichvu", iddichvu+"");
                map.put("nhanxet", nhanxet);
                map.put("sao", sao+"");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getTotalStar(String iddichvu, RatingITF xuli){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, totalStarURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("rating dao 67", response);
                if (response.equals("") || response.isEmpty()){
                    response = "0";
                }
                Float fl = new Float(response);
                xuli.xuli(fl);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("iddichvu", iddichvu);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getDanhGia(String iddichvu, RatingITF xuli){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getCMTDanhGiaURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<DanhGia> arrayList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        arrayList.add(new DanhGia(
                                jsonObject.getString("username"),
                                jsonObject.getString("sao"),
                                jsonObject.getString("comment")
                        ));
                    }
                    xuli.xuli(arrayList);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("iddichvu", iddichvu);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
