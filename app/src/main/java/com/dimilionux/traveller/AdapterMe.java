package com.dimilionux.traveller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sarere on 12/2/17.
 */

public class AdapterMe extends ArrayAdapter<MeMenu> {
    private final Context mContext;
    private final ArrayList<MeMenu> meMenuArrayList;

    public AdapterMe(Context mContext, ArrayList<MeMenu> meMenuArrayList) {

        super(mContext, R.layout.item_menu_me, meMenuArrayList);

        this.mContext = mContext;
        this.meMenuArrayList = meMenuArrayList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
        if(!meMenuArrayList.get(position).isGroupHeader()){
            rowView = inflater.inflate(R.layout.item_menu_me, parent, false);

            // 3. Get icon,title & counter views from the rowView
            TextView titleView =  rowView.findViewById(R.id.item_title);

            // 4. Set the text for textView
            titleView.setText(meMenuArrayList.get(position).getTitle());
        }
        else{
            rowView = inflater.inflate(R.layout.item_header_me, parent, false);
            TextView titleView = rowView.findViewById(R.id.header);
            titleView.setText(meMenuArrayList.get(position).getTitle());
        }

        // 5. retrn rowView
        return rowView;
    }
}
