package com.dimilionux.traveller;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sarere on 11/23/17.
 */

public class AdapterPlace extends RecyclerView.Adapter <AdapterPlace.RecommendPlacesAdapterHolder> {
    private List<Places> listPlaces;
    private Context mContext;
    private int layoutRes;
    private boolean staticList;

    public AdapterPlace(List<Places> listPlaces, Context mContext, int layoutRes){
        this.listPlaces = listPlaces;
        this.mContext = mContext;
        this.layoutRes = layoutRes;
    }

    public AdapterPlace(List<Places> listPlaces, Context mContext, int layoutRes, boolean staticList){
        this.listPlaces = listPlaces;
        this.mContext = mContext;
        this.layoutRes = layoutRes;
        this.staticList = staticList;
    }

    public class RecommendPlacesAdapterHolder extends RecyclerView.ViewHolder{
        private TextView namePlace;
        private RatingBar ratingBar;
        private Places place;
        private ImageView imgView;
        public RecommendPlacesAdapterHolder(View itemView) {
            super(itemView);

            namePlace = itemView.findViewById(R.id.namePlace);
            ratingBar = itemView.findViewById(R.id.ratingBarItem);
            imgView = itemView.findViewById(R.id.imgPlace);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, namePlace.getText().toString(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext,ActivityDetailPlace.class);
                    intent.putExtra("aboutPlace",place.aboutPlace);
                    intent.putExtra("namePlace",place.namePlace);
                    intent.putExtra("ratingPlace",place.rating);
                    intent.putExtra("id",place.id);
                    if(place.picture != null) {
                        intent.putExtra("picturePlace", place.picture);
                    }
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public RecommendPlacesAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);

        return new RecommendPlacesAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecommendPlacesAdapterHolder holder, int position) {
        Places place = listPlaces.get(position);
        holder.namePlace.setText(place.namePlace);
        holder.ratingBar.setRating(place.rating);
        holder.place = listPlaces.get(position);
        Log.d("pic",""+place.picture);
        if(place.picture != null) {
            Picasso.with(mContext).load(place.picture).into(holder.imgView);
        }

    }

    @Override
    public int getItemCount() {
        if(staticList && listPlaces.size() >= 5){
            return 5;
        }

        return listPlaces.size();
    }
}
