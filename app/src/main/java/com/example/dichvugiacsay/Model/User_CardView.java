package com.example.dichvugiacsay.Model;

public class User_CardView {
    private int id, resourceImage;
    private String name;


    public User_CardView(int id, int resourceImage, String name) {
        this.id = id;
        this.resourceImage = resourceImage;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
