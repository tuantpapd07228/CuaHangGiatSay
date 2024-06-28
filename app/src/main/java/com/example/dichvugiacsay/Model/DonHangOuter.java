package com.example.dichvugiacsay.Model;

public class DonHangOuter {
    private String id, date,  ship;

    public DonHangOuter(String id, String date, String ship) {
        this.id = id;
        this.date = date;
        this.ship = ship;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "DonHangOuter{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", ship='" + ship + '\'' +
                '}';
    }
}
