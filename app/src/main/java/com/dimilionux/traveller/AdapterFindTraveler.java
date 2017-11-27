package com.dimilionux.traveller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sarere on 11/27/17.
 */

public class AdapterFindTraveler extends RecyclerView.Adapter <AdapterFindTraveler.AdapterFindTravelerHolder>{
    public Context mContext;
    public List<User> userList;

    public AdapterFindTraveler(List<User> userList, Context mContext){
        this.mContext = mContext;
        this.userList = userList;
    }

    public class AdapterFindTravelerHolder extends RecyclerView.ViewHolder{
        TextView userName;
        Button btnSendMessage;

        public AdapterFindTravelerHolder(View itemView){
            super(itemView);

            userName = itemView.findViewById(R.id.userNameFindTraveler);
            btnSendMessage = itemView.findViewById(R.id.btnSendMessage);

            btnSendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, userName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public AdapterFindTravelerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_traveler, parent, false);
        return new AdapterFindTravelerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterFindTravelerHolder holder, int position) {
        User user = userList.get(position);

        holder.userName.setText(user.userName);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
