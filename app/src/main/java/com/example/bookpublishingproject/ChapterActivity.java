package com.example.bookpublishingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.bookpublishingproject.models.Book;

import java.util.ArrayList;

public class ChapterActivity extends AppCompatActivity {

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

          //Use FragmentManager to add courseFragment to fragment to container

          FragmentManager fn = getSupportFragmentManager();
          Fragment fragment = fn.findFragmentById(R.id.fragment_container_book);
          if(fragment==null)
          {
              fragment = new BookFragment();
              fn.beginTransaction()
                      .add(R.id.fragment_container_book,fragment)
                      .addToBackStack(null)
                      .commit();
          }
    }
}