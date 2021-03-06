package com.dimilionux.traveller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sarere on 11/27/17.
 */

public class AdapterReview extends RecyclerView.Adapter <AdapterReview.AdapterReviewHolder>{
    public Context mContext;
    public List<Review> reviewList;
    public boolean staticList;

    public AdapterReview(Context mContext, List<Review> reviewList){
        this.mContext = mContext;
        this.reviewList = reviewList;
    }

    public AdapterReview(Context mContext, List<Review> reviewList, boolean staticList){
        this.mContext = mContext;
        this.reviewList = reviewList;
        this.staticList = staticList;
    }

    public class AdapterReviewHolder extends RecyclerView.ViewHolder {
        TextView titleReview, contentReview;
        RatingBar ratingBar;

        public  AdapterReviewHolder (View itemView) {
            super(itemView);

            titleReview = itemView.findViewById(R.id.titleItemReview);
            contentReview = itemView.findViewById(R.id.contentItemReview);
            ratingBar = itemView.findViewById(R.id.ratingBarReview);
        }
    }

    @Override
    public AdapterReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new AdapterReviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterReviewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.titleReview.setText(review.titleReview);
        holder.contentReview.setText(review.contentReview);
        holder.ratingBar.setRating(review.ratingReview);
    }

    @Override
    public int getItemCount() {
        if(staticList && reviewList.size() >= 5){
            return 5;
        }

        return reviewList.size();
    }
}
