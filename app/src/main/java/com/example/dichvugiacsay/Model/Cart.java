package com.example.dichvugiacsay.Model;

import java.io.Serializable;

public class Cart implements Serializable {
    private int  idCart, idService, quantity, priceService;
    private String nameService, description , img;

    public Cart(int idCart, int idService, int quantity, int priceService, String nameService, String description, String img) {
        this.idCart = idCart;
        this.idService = idService;
        this.quantity = quantity;
        this.priceService = priceService;
        this.nameService = nameService;
        this.description = description;
        this.img = img;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    @Override
    public String toString() {
        return "Cart{" +
                ", idService=" + idService +
                ", quantity=" + quantity +
                ", priceService=" + priceService +
                ", nameService='" + nameService + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriceService() {
        return priceService;
    }

    public void setPriceService(int priceService) {
        this.priceService = priceService;
    }
}
