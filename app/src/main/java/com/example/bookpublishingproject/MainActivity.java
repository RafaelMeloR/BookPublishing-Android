package com.example.bookpublishingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


        //Use FragmentManager to add courseFragment to fragment to container

        FragmentManager fn = getSupportFragmentManager();
        Fragment fragment = fn.findFragmentById(R.id.fragment_container_main);
        if(fragment==null)
        {
            fragment = new PublisherDisplayFragment();
            fn.beginTransaction()
                    .add(R.id.fragment_container_main,fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}