package com.example.dichvugiacsay.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dichvugiacsay.Model.Service;
import com.example.dichvugiacsay.Model.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreDAO {
    private Context context;
    private String StoreURL = IP.IP + "/giatsay/getStore.php";
    private String getAllUrl = IP.IP + "/giatsay/getAllStore.php";
    private String getAllServiceURL = IP.IP + "/giatsay/getAllService.php";
    public StoreDAO(Context context) {
        this.context = context;
    }
    public interface XuLiStore{void xuli(Object obj);}
    public void getStore(int id, XuLiStore xuLiStore){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, StoreURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Store store = new Store(
                            id,
                            jsonObject.getString("name"),
                            jsonObject.getString("address"),
                            jsonObject.getString("email"),
                            jsonObject.getString("phone")
                    );
                    xuLiStore.xuli(store);
                } catch (JSONException e) {
                    Log.e("atuan errr", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("atuan errr1", error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", id+"");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getAll( XuLiStore xuLiStore){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getAllUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Store> arr = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        arr.add(new Store(
                                jsonObject.getInt("idStore"),
                                jsonObject.getString("name"),
                                jsonObject.getString("address"),
                                jsonObject.getString("email"),
                                jsonObject.getString("phone")
                        ));
                    }
                    xuLiStore.xuli(arr);
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void getAllService(XuLiStore xuLiStore){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getAllServiceURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Service> arr = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        arr.add(new Service(
                                jsonObject.getInt("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                jsonObject.getString("price"),
                                jsonObject.getString("img")

                        ));
                        xuLiStore.xuli(arr);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
