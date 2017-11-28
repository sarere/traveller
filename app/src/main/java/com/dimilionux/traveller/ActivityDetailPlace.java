package com.dimilionux.traveller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarere on 11/27/17.
 */

public class ActivityDetailPlace extends AppCompatActivity {
    RecyclerView recyclerView, recyclerViewReview;
    AdapterFindTraveler adapter;
    AdapterReview adapterReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_place);
        Intent intent = getIntent();
        String namePlace = intent.getStringExtra("namePlace");
        getSupportActionBar().setTitle(namePlace);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rvFindTraveller);
        recyclerViewReview = findViewById(R.id.rvReview);
        List<User> data = new ArrayList<User>();
        adapter = new AdapterFindTraveler(data, this);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        data.add(new User("Rere", "08.00", "Hoi garap tugas progmobnya cepetan.. nanti gk selesai gmn sih hemmm"));
        data.add(new User("Stephan Kent", "23.00","Gmn tugasnya?"));
        data.add(new User("Ditoa", "24.00","aku garap apa nih??"));
        adapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.rvReview);
        List<Review> dataReview = new ArrayList<Review>();
        adapterReview = new AdapterReview(this, dataReview);

        lm = new LinearLayoutManager(this);
        recyclerViewReview.setLayoutManager(lm);
        recyclerViewReview.setItemAnimator(new DefaultItemAnimator());
        recyclerViewReview.setAdapter(adapterReview);

        dataReview.add(new Review("Good Place", "Good place if you go with your couple", 4));
        dataReview.add(new Review("Nice!!!", "I can do anything with my couple",5));
        dataReview.add(new Review("Disgusting for family", "What the hell this place",1));
        adapterReview.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
