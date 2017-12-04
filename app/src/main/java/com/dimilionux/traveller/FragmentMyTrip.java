package com.dimilionux.traveller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarere on 11/22/17.
 */

public class FragmentMyTrip extends Fragment {
    private RecyclerView recyclerView;
    private AdapterMyTrip adapter;
    private FragmentActivity view;
    private SharedPreferences sp;

    public static FragmentMyTrip newInstance() {
        FragmentMyTrip fragment = new FragmentMyTrip();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.my_trip);

        view = getActivity();
        sp = view.getSharedPreferences("database",view.MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.rvMyTrip);

        if(!sp.getBoolean("isLogin",false)) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
        List<MyTrip> data = new ArrayList<MyTrip>();
        adapter = new AdapterMyTrip(data, getContext());

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        data.add(new MyTrip("Yogyakarta", "Unscheduled", 5));
        data.add(new MyTrip("Bali", "Unscheduled", 10));
        data.add(new MyTrip("Bekasi", "Unscheduled", 1));
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_trip, container, false);
    }
}
