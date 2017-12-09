package com.dimilionux.traveller;

import java.util.List;

/**
 * Created by sarere on 11/23/17.
 */

public class Places {
    public int id;
    public String namePlace, aboutPlace;
    public String picture = Endpoint.urlEndpoint + "picture/place/";
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

    public boolean isDataExist(int id, List<Places> dataList){
        if(dataList.contains(id)){
            return true;
        }
        return false;
    }
}
