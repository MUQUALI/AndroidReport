package com.example.firebase_demo;

import java.io.Serializable;
import java.util.Date;

public class Place implements Serializable {
    String name;
    String title;
    Date postDate;
    String address;
    String bus;
    String hotel;
    String morePlace;
    String food;
    String detail;
    String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getMorePlace() {
        return morePlace;
    }

    public void setMorePlace(String morePlace) {
        this.morePlace = morePlace;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Place() {

    }

    public Place(String name, String title, Date postDate, String address,
                 String bus, String hotel, String morePlace, String food,
                 String detail, String imageUrl) {
        this.name = name;
        this.title = title;
        this.postDate = postDate;
        this.address = address;
        this.bus = bus;
        this.hotel = hotel;
        this.morePlace = morePlace;
        this.food = food;
        this.detail = detail;
        this.imageUrl = imageUrl;
    }


}
