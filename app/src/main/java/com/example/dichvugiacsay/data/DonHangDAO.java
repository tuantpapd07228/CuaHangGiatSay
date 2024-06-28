package com.example.dichvugiacsay.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dichvugiacsay.Model.Address;
import com.example.dichvugiacsay.Model.DonHang;
import com.example.dichvugiacsay.Model.DonHangOuter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DonHangDAO {
    private Context context;
    private String DonhangInnerURL = IP.IP + "/giatsay/getOrderInner.php";
    private String readOutterURL = IP.IP + "/giatsay/getDonHangOuter.php";
    private String hoanthanhOutterURL = IP.IP + "/giatsay/getDonHangHoanThanh.php";
    private String dangGiaoURL = IP.IP + "/giatsay/getDonHangDangGiao.php";
    private String InsertURL = IP.IP + "/giatsay/DonHanginsert.php";
    private String InsertCTDHURL = IP.IP + "/giatsay/detailOrderInsert.php";
    public interface DonHangITF{void xuli(Object object);}
    public DonHangDAO(Context context) {
        this.context = context;
    }
    public void getDonHangInner(String iddonhang, DonHangITF xuli){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DonhangInnerURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<DonHang> arrayList = new ArrayList<>();
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        arrayList.add(new DonHang(
                                jsonObject.getInt("quantity"),
                                jsonObject.getInt("price"),
                                jsonObject.getString("img"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description"),
                                jsonObject.getString("address")
                        ));
                    }
                    Log.e("atuan arr", arrayList.toString());
                    xuli.xuli(arrayList);
                }catch (Exception e){
                    Log.e("error xacnhan " , e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error xacnhan " , error.getMessage());

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("iddonhang", iddonhang);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getOutter(String username , CartDAO.CartITF xuli){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, readOutterURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<DonHangOuter> arrayList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        arrayList.add(new DonHangOuter(
                                jsonObject.getString("id"),
                                jsonObject.getString("date"),
                                jsonObject.getString("ship")
                        ));
                    }
                    xuli.xuli(arrayList);
                    Log.e("atuan", arrayList.toString());
                } catch (Exception e) {
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
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getOutter(String username , CartDAO.CartITF xuli, String str){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, hoanthanhOutterURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<DonHangOuter> arrayList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        arrayList.add(new DonHangOuter(
                                jsonObject.getString("id"),
                                jsonObject.getString("date"),
                                jsonObject.getString("ship")
                        ));
                    }
                    xuli.xuli(arrayList);
                    Log.e("atuan", arrayList.toString());
                } catch (Exception e) {
                    Log.e("atuan err don hang 99", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("atuan err don hang 105", error.getMessage());
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

    public void getOutter(String username , CartDAO.CartITF xuli, int stt){
        if (stt == 2){
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, dangGiaoURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ArrayList<DonHangOuter> arrayList = new ArrayList<>();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            arrayList.add(new DonHangOuter(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("date"),
                                    jsonObject.getString("ship")
                            ));
                        }
                        xuli.xuli(arrayList);
                        Log.e("arr danggiao 181", arrayList.toString());
                    } catch (Exception e) {
                        Log.e("atuan err don hang 141", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("atuan err don hang 147", error.getMessage());
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

    }

    public void insert(String iddonhang, String idkhachhang, String date, String tongtien, Address address, DonHangITF xuli){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, InsertURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                xuli.xuli(null);
                Log.e("donhang", "insert thanh cong 174" );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error isert donhang " , error.getMessage());

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idDonHang", iddonhang);
                map.put("idkhachhang", idkhachhang);
                map.put("ngaydathang", date);
                map.put("tongtien", tongtien);
                map.put("hoten", address.getName());
                map.put("sdt", address.getPhone());
                map.put("diachi", address.getAddress());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void insertCTDH(String iddonhang, String iddichvu, String soluong, DonHangITF xuli) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("iddonhang", iddonhang);
            jsonObject.put("iddichvu", iddichvu);
            jsonObject.put("soluong", soluong);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                InsertCTDHURL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("insertCTDH", "onResponse: " + response.toString());
                        xuli.xuli(null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error insertCTDH", error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

}
