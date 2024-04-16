package com.example.bookpublishingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        //Use FragmentManager to add courseFragment to fragment to container

        FragmentManager fn = getSupportFragmentManager();
        Fragment fragment = fn.findFragmentById(R.id.fragment_container_chapter);
        if(fragment==null)
        {
            fragment = new ChapterFragment();
            fn.beginTransaction()
                    .add(R.id.fragment_container_chapter,fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}