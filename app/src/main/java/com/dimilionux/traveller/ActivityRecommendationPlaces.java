package com.dimilionux.traveller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarere on 11/23/17.
 */

public class ActivityRecommendationPlaces extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterPlace adapter;
    private Button btnCulture, btnOutdoor, btnSouvenir, btnCulinary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recommendation_places);

        getSupportActionBar().setTitle(R.string.title_recommendation);

        recyclerView = findViewById(R.id.rvAllRecommendPlace);
        List<Places> data = new ArrayList<Places>();
        adapter = new AdapterPlace(data, this, R.layout.item_place_vertical_recycler);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        data.add(new Places("Parangtiris", 4));
        data.add(new Places("Sundak", 3));
        data.add(new Places("Kali Urang", 4));
        data.add(new Places("Gunung Kidul", 5));
        data.add(new Places("Kulon Progo", 5));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}
