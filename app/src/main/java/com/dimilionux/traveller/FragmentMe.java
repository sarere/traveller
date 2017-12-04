package com.dimilionux.traveller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sarere on 11/22/17.
 */

public class FragmentMe extends Fragment {
    private FragmentActivity view;
    private ListView listView;
    private AdapterMe adapterMe;
    private ArrayList<MeMenu> models = new ArrayList<MeMenu>();
    private SharedPreferences sp;

    public static FragmentMe newInstance() {
        FragmentMe fragment = new FragmentMe();
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.my_traveller);



        view = getActivity();
        sp = view.getSharedPreferences("database",view.MODE_PRIVATE);
        adapterMe = new AdapterMe(view.getBaseContext(), generateData());
        listView = view.findViewById(R.id.listViewMe);
        listView.setAdapter(adapterMe);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MeMenu meMenu = models.get(position);
                Boolean isFinish = false;
                Class activity = ActivityBase.class;

                if(meMenu.getTitle() == getString(R.string.login)){
                   activity = ActivityLogin.class;
                   isFinish = false;
                } else if(meMenu.getTitle() == getString(R.string.logout)){
                    SharedPreferences.Editor spEdit = sp.edit();
                    spEdit.putBoolean("isLogin", false);
                    activity = ActivityBase.class;
                    ActivityBase.parentFragment = R.string.me;
                    isFinish = true;
                    spEdit.commit();
                } else if(meMenu.getTitle() == getString(R.string.register)){
                    activity = ActivityRegister.class;
                    ActivityBase.parentFragment = R.string.me;
                    isFinish = false;
                }


                Intent intent = new Intent(getContext(),activity );
                startActivity(intent);
                if(isFinish){
                    getActivity().finish();
                }
            }
        });
    }

    private ArrayList<MeMenu> generateData(){
        if(!sp.getBoolean("isLogin",false)) {
            models.add(new MeMenu(R.mipmap.ic_launcher, getString(R.string.login)));
            models.add(new MeMenu(R.mipmap.ic_launcher, getString(R.string.register)));
        } else {
            models.add(new MeMenu(R.mipmap.ic_launcher, getString(R.string.setting)));
            models.add(new MeMenu(R.mipmap.ic_launcher, getString(R.string.write_review)));
            models.add(new MeMenu(R.mipmap.ic_launcher, getString(R.string.edit_find_traveler_list)));
            models.add(new MeMenu(R.mipmap.ic_launcher, getString(R.string.logout)));
        }

        return models;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }
}
