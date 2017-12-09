package com.dimilionux.traveller;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarere on 11/27/17.
 */

public class ActivityDetailPlace extends AppCompatActivity implements OnMapReadyCallback {
    private RecyclerView recyclerView, recyclerViewReview;
    private AdapterFindTraveler adapter;
    private Button btnReview;
    private AdapterReview adapterReview;
    private int id;
    private TextView aboutPlace, txtReviewSubmit, txtReviewTitleSubmit;
    private ImageView picPlace;
    private RatingBar rating, ratingBarSubmit;
    private LinearLayout linearLayout;
    private SharedPreferences sp;
    private List<Review> dataReview = new ArrayList<Review>();
    MapView mapView;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_place);
        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("namePlace"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rvFindTraveller);
        rating = findViewById(R.id.ratingBarDetailPlace);
        aboutPlace = findViewById(R.id.aboutPlace);
        picPlace = findViewById(R.id.imgPlaceDetail);
        recyclerViewReview = findViewById(R.id.rvReview);
        btnReview = findViewById(R.id.btnWriteReview);
        txtReviewSubmit = findViewById(R.id.txtReviewSubmit);
        txtReviewTitleSubmit = findViewById(R.id.txtReviewTitleSubmit);
        ratingBarSubmit = findViewById(R.id.ratingBarSubmit);
        linearLayout = findViewById(R.id.reviewLayout);
        mapView = findViewById(R.id.mapViewLocation);

        id = intent.getIntExtra("id", 1);
        aboutPlace.setText(intent.getStringExtra("aboutPlace"));
        rating.setRating(intent.getFloatExtra("ratingPlace", 0));
        if (intent.getStringExtra("picturePlace") != null) {
            Picasso.with(this).load(intent.getStringExtra("picturePlace")).into(picPlace);
        }

        sp = getSharedPreferences("database", MODE_PRIVATE);

        if (!sp.getBoolean("isLogin", false)) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
        }


        txtReviewTitleSubmit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validation();
            }
        });

        txtReviewSubmit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validation();
            }
        });

        ratingBarSubmit.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Do what you want
                validation();
            }
        });


        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview(txtReviewTitleSubmit.getText().toString(), txtReviewSubmit.getText().toString(), ratingBarSubmit.getRating());
            }
        });


        List<User> data = new ArrayList<User>();
        adapter = new AdapterFindTraveler(data, this, R.layout.item_find_traveler, true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        data.add(new User("Rere", "08.00", "Hoi garap tugas progmobnya cepetan.. nanti gk selesai gmn sih hemmm"));
        data.add(new User("Stephan Kent", "23.00", "Gmn tugasnya?"));
        data.add(new User("Ditoa", "24.00", "aku garap apa nih??"));
        data.add(new User("Ditoa", "24.00", "aku garap apa nih??"));
        data.add(new User("Ditoa", "24.00", "aku garap apa nih??"));
        data.add(new User("Ditoa", "24.00", "aku garap apa nih??"));
        data.add(new User("Ditoa", "24.00", "aku garap apa nih??"));
        adapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.rvReview);
        adapterReview = new AdapterReview(getBaseContext(), dataReview, true);
        lm = new LinearLayoutManager(getBaseContext());
        recyclerViewReview.setLayoutManager(lm);
        recyclerViewReview.setItemAnimator(new DefaultItemAnimator());
        recyclerViewReview.setAdapter(adapterReview);
        new LoadData().execute();

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void validation() {
        String s1 = txtReviewTitleSubmit.getText().toString().trim();
        String s2 = txtReviewSubmit.getText().toString().trim();
        if (ratingBarSubmit.getRating() == 0 || s1.equals("") || s2.equals("")) {
            btnReview.setEnabled(false);
        } else {
            btnReview.setEnabled(true);
        }
    }

    private void submitReview(String title, String review, float rating) {
        String exactUrl = Endpoint.urlEndpoint + "submit-review";
        String email = sp.getString("userEmail", null);
        String dataPost = "{\"title\":\"" + title + "\",\"review\":\"" + review + "\",\"rating\":\"" + rating + "\",\"email\":\"" + email + "\",\"place_id\":\"" + id + "\"}";
        Log.d("DataPost", "" + dataPost);
        OkHttpClient client = new OkHttpClient();
        RequestBody reqBody = RequestBody.create(Endpoint.JSON, dataPost);
        Request req = new Request.Builder()
                .url(exactUrl)
                .post(reqBody)
                .build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("Response", "" + json);
                try {
                    JSONObject reader = new JSONObject(json);
                    String status = reader.getString("message");
                    Log.d("Response", "" + reader.getString("message"));
                    if (status.equals("failed")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("Response", "masukk");
                                Toast.makeText(getBaseContext(), R.string.failed, Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), R.string.success, Toast.LENGTH_LONG).show();
                                new LoadData().execute();
                            }
                        });
//
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
//        map.getUiSettings().setZoomControlsEnabled(true);
//        map.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LatLng position = new LatLng(0,0);


        map.addMarker(new MarkerOptions()
                .position(position)
                .title("Marker"));
    }

    private class LoadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(ActivityDetailPlace.this, "Loading Data", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            loadReview();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private void loadReview(){
        String exactUrl = Endpoint.urlEndpoint + "review";
        String dataPost = "{\"placeId\":"+id+"}";


        OkHttpClient client = new OkHttpClient();
            RequestBody reqBody = RequestBody.create(Endpoint.JSON,dataPost);
        Request req = new Request.Builder()
                .url(exactUrl)
                .post(reqBody)
                .build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONArray reader = new JSONArray(json);
                    dataReview.clear();
                    for (int i=0 ; i<reader.length() ; i++){
                        JSONObject data = new JSONObject(reader.getString(i));
                        Log.d("Response",""+data.getString("title"));
                        Review databaseReview = new Review(data.getString("title"), data.getString("review"), (float) data.getDouble("rating_review"), data.getInt("id"));

                        dataReview.add(databaseReview);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapterReview.notifyDataSetChanged();
                            }
                        });



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
