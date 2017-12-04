package com.dimilionux.traveller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sarere on 11/23/17.
 */

public class ActivityPlaces extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterPlace adapter;
    private int  titleActionbar;
    private List<Places> data = new ArrayList<Places>();
    private Button btnCulture, btnOutdoor, btnSouvenir, btnCulinary;
    private String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recommendation_places);

        Intent intent = getIntent();
        titleActionbar = intent.getIntExtra("TitleActionbar",R.string.title_recommendation);

        getSupportActionBar().setTitle(getString(titleActionbar));
        recyclerView = findViewById(R.id.rvAllRecommendPlace);
        adapter = new AdapterPlace(data, this, R.layout.item_place_vertical_recycler);
        category = intent.getStringExtra("category");

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getBaseContext(), "Loading Data", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request req;

            if(category.equals("all")){
                String exactUrl = Endpoint.urlEndpoint + "place";

                req = new Request.Builder()
                        .url(exactUrl)
                        .get()
                        .build();
            } else {
                String exactUrl = Endpoint.urlEndpoint + "specify";
                String dataPost = "{\"category\":\"" + category + "\"}";
                RequestBody reqBody = RequestBody.create(Endpoint.JSON, dataPost);
                req = new Request.Builder()
                        .url(exactUrl)
                        .post(reqBody)
                        .build();
            }

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    Log.d("Response",""+json);
                    try {
                        JSONArray reader = new JSONArray(json);
                        for (int i=0 ; i<reader.length() ; i++){
                            JSONObject dataJSON = new JSONObject(reader.getString(i));
                            Log.d("Response",""+dataJSON.getString("name_place"));

                            String namePlace = dataJSON.getString("name_place");
                            float rating = (float) dataJSON.getDouble("rating");
                            String aboutPlace = dataJSON.getString("about");
                            String picture = dataJSON.getString("picture_url");
                            int id = dataJSON.getInt("id");


                            data.add(new Places(namePlace,rating, aboutPlace, id, picture));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    adapter.notifyDataSetChanged();
                                }
                            });



                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}
