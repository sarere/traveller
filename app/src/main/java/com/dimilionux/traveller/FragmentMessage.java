package com.dimilionux.traveller;

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

public class FragmentMessage extends Fragment {
    private RecyclerView recyclerView;
    private AdapterMessage adapter;
    private FragmentActivity view;

    public static FragmentMessage newInstance() {
        FragmentMessage fragment = new FragmentMessage();
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.message);

        view = getActivity();
        recyclerView = view.findViewById(R.id.rvMessages);
        List<User> data = new ArrayList<User>();
        adapter = new AdapterMessage(data, getContext(), R.layout.item_massenger);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        data.add(new User("Rere", "08.00", "Hoi garap tugas progmobnya cepetan.. nanti gk selesai gmn sih hemmm"));
        data.add(new User("Stephan Kent", "23.00","Gmn tugasnya?"));
        data.add(new User("Ditoa", "24.00","aku garap apa nih??"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }
}
