package com.dimilionux.traveller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class ActivityBase extends AppCompatActivity {
    private BottomNavigationView btmNav;
    private Fragment selectedFragment;
    public static int parentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base);

        btmNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Helper.disableShiftMode(btmNav);

        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.action_home:
                        selectedFragment = FragmentHome.newInstance();
                        break;
                    case R.id.action_my_trip:
                        selectedFragment = FragmentMyTrip.newInstance();
                        break;

                    case R.id.action_message:
                        selectedFragment = FragmentMessage.newInstance();
                        break;

                    case R.id.action_me:
                        selectedFragment = FragmentMe.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
                return true;
            }
        });


        if (parentFragment == R.string.home){
            selectedFragment = FragmentHome.newInstance();
        } else if (parentFragment == R.string.my_trip){
            selectedFragment = FragmentMyTrip.newInstance();
            btmNav.setSelectedItemId(R.id.action_my_trip);
        } else if (parentFragment == R.string.me){
            selectedFragment = FragmentMe.newInstance();
            btmNav.setSelectedItemId(R.id.action_me);
        } else {
            selectedFragment = FragmentHome.newInstance();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
    }
}
