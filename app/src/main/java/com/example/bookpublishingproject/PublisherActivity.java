package com.example.bookpublishingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class PublisherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);


        //Use FragmentManager to add courseFragment to fragment to container

        FragmentManager fn = getSupportFragmentManager();
        Fragment fragment = fn.findFragmentById(R.id.fragment_container_update);
        if(fragment==null)
        {
            fragment = new PublisherUpdateFragment();
            fn.beginTransaction()
                    .add(R.id.fragment_container_update,fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}