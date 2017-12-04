package com.dimilionux.traveller;

/**
 * Created by sarere on 11/23/17.
 */

public class Places {
    public int id;
    public String namePlace, aboutPlace;
    public String picture = "http://192.168.0.102:8000/picture/place/";
    public float rating;


    public Places(String namePlace, float rating, String aboutPlace,int id, String picture){
        this.id = id;
        this.namePlace = namePlace;
        this.rating = rating;
        this.aboutPlace = aboutPlace;
        if(picture != "null") {
            this.picture += picture;
        } else {
            this.picture = null;
        }
    }
}
