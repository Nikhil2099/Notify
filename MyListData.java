package com.example.notify;

public class MyListData{
    private String description,city;
    private int imgId;


    public MyListData(String description, String city, int imgId) {
        this.description = description;
        this.imgId = imgId;
        this.city = city;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getImgId() {
        return imgId;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}