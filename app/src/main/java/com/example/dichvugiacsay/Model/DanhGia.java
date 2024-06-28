package com.example.dichvugiacsay.Model;

import java.io.Serializable;

public class DanhGia implements Serializable {
    private String username, sao, comment;

    public DanhGia(String username, String sao, String comment) {
        this.username = username;
        this.sao = sao;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSao() {
        return sao;
    }

    public void setSao(String sao) {
        this.sao = sao;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "DanhGia{" +
                "username='" + username + '\'' +
                ", sao='" + sao + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
