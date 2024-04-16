package com.example.bookpublishingproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookpublishingproject.database.PublishingBaseHelper;
import com.example.bookpublishingproject.models.Book;
import com.example.bookpublishingproject.models.Chapter;

import java.util.ArrayList;

public class ChapterFragment extends Fragment {

    private TextView BookInfoChapter;
    private EditText ChapIdChapter;
    private EditText  PubAddChapter;
    private  EditText PubPriceChapter;
    private Button AddChapter;
    private static  final String EXTRA_BOOK_ID="com.example.bookpublishingproject.BookId";
    private static  final String EXTRA_BOOK_AUTHOR="com.example.bookpublishingproject.BookAuthor";
    private static  final String EXTRA_BOOK_PRICE="com.example.bookpublishingproject.Price";

    public int bookIdRetrieve ;
    public String bookAuthorRetrieve ;
    public double bookPriceRetrieve ;

    public static ChapterFragment newInstance(int BookId, String BookAuthor, double Price) {
        ChapterFragment fragment = new ChapterFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_BOOK_ID,BookId);
        args.putString(EXTRA_BOOK_AUTHOR, BookAuthor);
        args.putDouble(EXTRA_BOOK_PRICE, Price);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment will receive a callback from FragmentManager if
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            bookIdRetrieve = getArguments().getInt(EXTRA_BOOK_ID);
            bookAuthorRetrieve = getArguments().getString(EXTRA_BOOK_AUTHOR);
            bookPriceRetrieve = getArguments().getDouble(EXTRA_BOOK_PRICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_chapter, container, false);

        //get the view of toolbarPublisherDisplay
        Toolbar toolbarPublisherDisplay = (Toolbar) v.findViewById(R.id.toolbarChapter);

        AppCompatActivity activity =(AppCompatActivity) getActivity() ;
        activity.setSupportActionBar(toolbarPublisherDisplay);

        //Get the view BookInfoChapter
        BookInfoChapter=(TextView) v.findViewById(R.id.textViewBookInfoChapter);
        BookInfoChapter.setText("B_id: "+bookIdRetrieve+" Author: "+bookAuthorRetrieve+" Price $CAD: "+bookPriceRetrieve+" Price $Euro: "+Book.calculate_Price_Euro(bookPriceRetrieve));

        //Get the view ChapIdChapter
        ChapIdChapter = (EditText) v.findViewById(R.id.editTextChapIdChapter);

        //Get the view PubAddChapter
        PubAddChapter = (EditText) v.findViewById(R.id.editTextPubAddChapter);

        //Get the view PubPriceChapter
        PubPriceChapter =  (EditText) v.findViewById(R.id.editTextPubPriceChapter);

        //Get the view
        AddChapter= (Button) v.findViewById(R.id.buttonAddChapter);
        AddChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chapter chapter = new Chapter(bookIdRetrieve,Integer.parseInt(ChapIdChapter.getText().toString()),
                        PubAddChapter.getText().toString(),Double.parseDouble(PubPriceChapter.getText().toString()));
                PublishingBaseHelper publishingBaseHelper = new PublishingBaseHelper(getContext());
                publishingBaseHelper.addNewChapter(chapter);
                Toast.makeText(getContext(), "Chapter " + ChapIdChapter.getText().toString() + " Added", Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //inflate menu defined in menu resource
        inflater.inflate(R.menu.menu_publisher_book, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id =item.getItemId();
        Intent intent;

        if(id == R.id.BookPublishingOptionitem1)
        {
            Toast.makeText(getContext(), "Item 1 selected", Toast.LENGTH_SHORT).show();
            //Start new activity courseMapActivity
            intent = new Intent( getActivity(), PublisherDisplayFragment.class);
            startActivity(intent);
            return  true;
        }
        else if(id == R.id.BookPublishingoptionitem2)
        {

                // Proceed with  fragment transaction
                BookFragment bookFragment = BookFragment.newInstance(0,"", "");
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_main, bookFragment)
                        .addToBackStack(null)
                        .commit();
                return  true;

        }
        else if(id == R.id.BookPublishingOptionitem3)
        {
            Toast.makeText(getContext(), "Item 3 selected", Toast.LENGTH_SHORT).show();
            return  true;
        }
        else if(id == R.id.BookPublishingOptionitem4)
        {
            Toast.makeText(getContext(), "Item 4 selected", Toast.LENGTH_SHORT).show();
            return  true;
        }
        else
        if(id == R.id.BookPublishingOptionitem5)
        {
            Toast.makeText(getContext(), "Item 5 selected", Toast.LENGTH_SHORT).show();
            return  true;
        }



        return super.onOptionsItemSelected(item);
    }

}