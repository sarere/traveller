package com.dimilionux.traveller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sarere on 11/24/17.
 */

public class AdapterMessage extends RecyclerView.Adapter <AdapterMessage.MessageAdapterHolder>  {
    private List<User> listUser;
    private Context mContext;
    private int layoutRes;

    public AdapterMessage(List<User> listUser, Context mContext, int layoutRes){
        this.listUser = listUser;
        this.mContext = mContext;
        this.layoutRes = layoutRes;
    }

    public class MessageAdapterHolder extends RecyclerView.ViewHolder{
        private TextView txtUserName,txtChatPreview,txtTimeChat;
        private ImageView imgUser;
        public MessageAdapterHolder(View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtChatPreview = itemView.findViewById(R.id.txtChatPreview);
            txtTimeChat = itemView.findViewById(R.id.txtTimeChat);
            imgUser = itemView.findViewById(R.id.imgUser);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, txtUserName.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public AdapterMessage.MessageAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);

        return new AdapterMessage.MessageAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterMessage.MessageAdapterHolder holder, int position) {
        User user = listUser.get(position);
        holder.txtUserName.setText(user.userName);
        holder.txtTimeChat.setText(user.timeChat);
        holder.txtChatPreview.setText(user.previewMessage);
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }
}
