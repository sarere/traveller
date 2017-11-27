package com.dimilionux.traveller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sarere on 11/24/17.
 */

public class AdapterMyTrip extends RecyclerView.Adapter <AdapterMyTrip.AdapterMyTripHolder>  {
    private List<MyTrip> myTripList;
    private Context mContext;

    public AdapterMyTrip(List<MyTrip> myTripList, Context mContext) {
        this.myTripList = myTripList;
        this.mContext = mContext;
    }

    public class AdapterMyTripHolder extends RecyclerView.ViewHolder{
        private TextView txtMyTrip,txtDestination,txtTimeTrip;
        public AdapterMyTripHolder(View itemView) {
            super(itemView);

            txtMyTrip = itemView.findViewById(R.id.txtTitleMyTrip);
            txtDestination = itemView.findViewById(R.id.txtTotalDestination);
            txtTimeTrip = itemView.findViewById(R.id.txtDateTrip);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, txtMyTrip.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public AdapterMyTrip.AdapterMyTripHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_trip, parent, false);

        return new AdapterMyTrip.AdapterMyTripHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterMyTrip.AdapterMyTripHolder holder, int position) {
        MyTrip myTrip = myTripList.get(position);
        holder.txtMyTrip.setText(myTrip.nameTrip);
        holder.txtTimeTrip.setText(myTrip.timeTrip);
        holder.txtDestination.setText(myTrip.totalDestination + " Destination");
    }

    @Override
    public int getItemCount() {
        return myTripList.size();
    }
}
