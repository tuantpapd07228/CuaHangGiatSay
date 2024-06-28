package com.example.dichvugiacsay.Model;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private String name;
    private String  idOrder, quantity,  price;
    private String status;
    private String idStore, idService;
    private String img;

    public OrderDetail(String name, String idOrder, String quantity, String price, String status, String idStore, String idService, String img) {
        this.name = name;
        this.idOrder = idOrder;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.idStore = idStore;
        this.idService = idService;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
