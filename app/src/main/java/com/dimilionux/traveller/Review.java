package com.dimilionux.traveller;

/**
 * Created by sarere on 11/28/17.
 */

public class Review {
    public int id;
    public String titleReview, contentReview;
    public float ratingReview;

    public Review (String titleReview, String contentReview, float ratingReview, int id){
        this.titleReview = titleReview;
        this.contentReview = contentReview;
        this.ratingReview = ratingReview;
        this.id = id;
    }
}
