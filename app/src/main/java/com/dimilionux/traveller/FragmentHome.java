package com.dimilionux.traveller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sarere on 11/22/17.
 */

public class FragmentHome extends Fragment {
    private ImageView imgView;
    private RecyclerView recyclerView;
    private AdapterPlace adapter;
    private FragmentActivity view;
    private Button btnCulture, btnOutdoor, btnSouvenir, btnCulinary;
    private TextView txtBtnSeeAll;


    public static FragmentHome newInstance() {
        FragmentHome fragment = new FragmentHome();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        view = getActivity();
        imgView = view.findViewById(R.id.imgViewBg);
        btnCulture = view.findViewById(R.id.btnCulture);
        btnOutdoor = view.findViewById(R.id.btnOutdoor);
        btnSouvenir = view.findViewById(R.id.btnSouvenir);
        btnCulinary = view.findViewById(R.id.btnCulinary);
        txtBtnSeeAll = view.findViewById(R.id.btnSeeAll);

        ((AppCompatActivity)view).getSupportActionBar().hide();

        Bitmap bmOrginal= BitmapFactory.decodeResource(this.getResources(), R.drawable.yoja_min);

        imgView.setImageBitmap(bmOrginal);

        recyclerView = view.findViewById(R.id.rvRecommendPlace);
        List<Places> data = new ArrayList<Places>();
        adapter = new AdapterPlace(data, getContext(), R.layout.item_place_horizontal_recycler);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        txtBtnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBase.parentFragment = getString(R.string.home);
                Intent intent = new Intent(getContext(),ActivityRecommendationPlaces.class);
                startActivity(intent);
            }
        });

        data.add(new Places("Parangtiris", 4));
        data.add(new Places("Sundak", 3));
        data.add(new Places("Kali Urang", 4));
        data.add(new Places("Gunung Kidul", 5));
        data.add(new Places("Kulon Progo", 5));

        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rvRecommendPlace);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
