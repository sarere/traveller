package com.dimilionux.traveller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import okhttp3.Response;


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
    private List<Places> data = new ArrayList<Places>();


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
        adapter = new AdapterPlace(data, getContext(), R.layout.item_place_horizontal_recycler, true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        txtBtnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBase.parentFragment = R.string.home;
                Intent intent = new Intent(getContext(),ActivityPlaces.class);
                intent.putExtra("TitleActionbar",R.string.title_recommendation);
                intent.putExtra("category","all");
                startActivity(intent);
            }
        });

        btnCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBase.parentFragment = R.string.home;
                Intent intent = new Intent(getContext(),ActivityPlaces.class);
                intent.putExtra("TitleActionbar",R.string.culture);
                intent.putExtra("category","culture");
                startActivity(intent);
            }
        });

        btnCulinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBase.parentFragment = R.string.home;
                Intent intent = new Intent(getContext(),ActivityPlaces.class);
                intent.putExtra("TitleActionbar",R.string.culinary);
                intent.putExtra("category","culinary");
                startActivity(intent);
            }
        });

        btnOutdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBase.parentFragment = R.string.home;
                Intent intent = new Intent(getContext(),ActivityPlaces.class);
                intent.putExtra("TitleActionbar",R.string.outdoor);
                intent.putExtra("category","outdoor");
                startActivity(intent);
            }
        });

        btnSouvenir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBase.parentFragment = R.string.home;
                Intent intent = new Intent(getContext(),ActivityPlaces.class);
                intent.putExtra("TitleActionbar",R.string.souvenir);
                intent.putExtra("category","souvenir");
                startActivity(intent);
            }
        });

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getContext(), "Loading Data", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String exactUrl = Endpoint.urlEndpoint + "place";
            OkHttpClient client = new OkHttpClient();
//            RequestBody reqBody = RequestBody.create(JSON,data);
            Request req = new Request.Builder()
                    .url(exactUrl)
                    .get()
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
                        for (int i=0 ; i<reader.length() ; i++){
                            JSONObject dataJSON = new JSONObject(reader.getString(i));
                            Log.d("Response",""+dataJSON.getString("name_place"));

                            String namePlace = dataJSON.getString("name_place");
                            float rating = (float) dataJSON.getDouble("rating");
                            String aboutPlace = dataJSON.getString("about");
                            String picture = dataJSON.getString("picture_url");
                            int id = dataJSON.getInt("id");


                            data.add(new Places(namePlace,rating, aboutPlace, id, picture));

                            try{
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }catch (Exception e){

                            }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rvRecommendPlace);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
