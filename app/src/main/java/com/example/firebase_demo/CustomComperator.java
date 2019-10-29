package com.example.firebase_demo;

import java.util.Comparator;

public class CustomComperator implements Comparator<Place> {
    @Override
    public int compare(Place place1, Place place2) {
        return place2.postDate.compareTo(place1.postDate);
    }
}
