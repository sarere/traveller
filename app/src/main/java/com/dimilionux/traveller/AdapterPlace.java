package com.dimilionux.traveller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sarere on 11/23/17.
 */

public class AdapterPlace extends RecyclerView.Adapter <AdapterPlace.RecommendPlacesAdapterHolder> {
    private List<Places> listPlaces;
    private Context mContext;
    private int layoutRes;

    public AdapterPlace(List<Places> listPlaces, Context mContext, int layoutRes){
        this.listPlaces = listPlaces;
        this.mContext = mContext;
        this.layoutRes = layoutRes;
    }

    public class RecommendPlacesAdapterHolder extends RecyclerView.ViewHolder{
        private TextView namePlace;
        private RatingBar ratingBar;
        public RecommendPlacesAdapterHolder(View itemView) {
            super(itemView);

            namePlace = itemView.findViewById(R.id.namePlace);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, namePlace.getText().toString(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext,ActivityDetailPlace.class);
                    intent.putExtra("namePlace",namePlace.getText().toString());
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
        Places film = listPlaces.get(position);
        holder.namePlace.setText(film.namePlace);
        holder.ratingBar.setRating(film.rating);
    }

    @Override
    public int getItemCount() {
        return listPlaces.size();
    }
}
