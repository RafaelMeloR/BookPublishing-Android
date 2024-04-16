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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookpublishingproject.database.PublishingBaseHelper;
import com.example.bookpublishingproject.models.Publisher;

import java.util.ArrayList;


public class PublisherDisplayFragment extends Fragment {

    private  EditText editTextPubId;
    private  EditText editTextPubName;
    private  EditText editTextPubAdd;
    private Button buttonPrev;
    private  Button buttonNext;
    private Button buttonPublisherDetails;
    private Button showBookButton;
    private int currentIndex=0;
    public static   Publisher[] all_Publisher;
    public static String TAG="Course Project";
    public static String KEY_INDEX = "index";
    Context context;

    public void updateAllPublisher()
    {
        Context context = getContext().getApplicationContext();
        PublishingBaseHelper publisherBaseHelper = new PublishingBaseHelper(context);
        ArrayList<Publisher>consult = publisherBaseHelper.readPublisher();
        all_Publisher= new Publisher[consult.size()];
        for(int i = 0; i<consult.size();i++)
        {
            all_Publisher[i]=consult.get(i);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fragment will receive a callback from FragmentManager if
        setHasOptionsMenu(true);

        Publisher PublisherRecord1 = new Publisher(1,"Publisher 1","Montreal");
        Publisher PublisherRecord2 = new Publisher(2,"Publisher 2","Quebec");
        Publisher PublisherRecord3 = new Publisher(3,"Publisher 3","Ottawa");

        //DATA STRUCTURE ARRAY OF OBJECTS
        all_Publisher = new Publisher[]{PublisherRecord1,PublisherRecord2,PublisherRecord3};

        context = getContext().getApplicationContext();

        PublishingBaseHelper publishingBaseHelper = new PublishingBaseHelper(context);

        ArrayList<Publisher> consult = publishingBaseHelper.readPublisher();
       if(consult.isEmpty()) {
            publishingBaseHelper.addNewPublisher(PublisherRecord1);
            publishingBaseHelper.addNewPublisher(PublisherRecord2);
            publishingBaseHelper.addNewPublisher(PublisherRecord3);
       }
        else
        {
            this.updateAllPublisher();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_publisher_display, container, false);

        //get the view of toolbarPublisherDisplay
        Toolbar toolbarPublisherDisplay = (Toolbar) v.findViewById(R.id.toolbarPublisherDisplay);

        AppCompatActivity activity =(AppCompatActivity) getActivity() ;
        activity.setSupportActionBar(toolbarPublisherDisplay);


       if(savedInstanceState!=null)
        {
            currentIndex=savedInstanceState.getInt(KEY_INDEX);
        }

        //Get the view of editTextPubId
        editTextPubId= (EditText) v.findViewById(R.id.editTextPubId);
        editTextPubId.setText(Integer.toString(all_Publisher[currentIndex].getP_id()));
        //Get the view of editTextPubName
        editTextPubName=(EditText) v.findViewById(R.id.editTextPubName);
        editTextPubName.setText(all_Publisher[currentIndex].getP_name());
        //Get the view of editTextPubAdd
        editTextPubAdd=(EditText) v.findViewById(R.id.editTextPubAdd);
        editTextPubAdd.setText(all_Publisher[currentIndex].getP_address());
        //Get the view of buttonPrev
        buttonPrev=(Button) v.findViewById(R.id.buttonPrev);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex - 1 + all_Publisher.length) % all_Publisher.length;
                editTextPubId.setText(Integer.toString(all_Publisher[currentIndex].getP_id()));
                editTextPubName.setText(all_Publisher[currentIndex].getP_name());
                editTextPubAdd.setText(all_Publisher[currentIndex].getP_address());
            }
        });
        //Get the view of buttonNext
        buttonNext=(Button) v.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex=(currentIndex+1)%all_Publisher.length;
                editTextPubId.setText(Integer.toString(all_Publisher[currentIndex].getP_id()));
                editTextPubName.setText(all_Publisher[currentIndex].getP_name());
                editTextPubAdd.setText(all_Publisher[currentIndex].getP_address());
            }
        });
        //Get the view of buttonPublisherDetails
        buttonPublisherDetails=(Button) v.findViewById(R.id.buttonPublisherDetails);
        buttonPublisherDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The first approach is to call startActivity as unidirectional communication
                //Only used when sending data from parent activity to child activity
                String pubIdStr = editTextPubId.getText().toString().trim();
                String pubName  =editTextPubName.getText().toString() ;
                String pubAdd  = editTextPubAdd.getText().toString();

                if (pubIdStr.isEmpty() || pubName.isEmpty() || pubAdd.isEmpty()) {
                    Toast.makeText(PublisherDisplayFragment.this.context, "Complete all fields before continue", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    int pubId = Integer.parseInt(pubIdStr);
                    if (pubId <= 0) {
                        Toast.makeText(PublisherDisplayFragment.this.context, "Please enter valid values for pubId ID", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Proceed with  fragment transaction
                    PublisherUpdateFragment publisherUpdateFragment = PublisherUpdateFragment.newInstance(pubId, pubName, pubAdd);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_main, publisherUpdateFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        //Get the view of showBookButton
        showBookButton=(Button) v.findViewById(R.id.showBookButton);
        showBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pubIdStr = editTextPubId.getText().toString().trim();
                String pubName  =editTextPubName.getText().toString() ;
                String pubAdd  = editTextPubAdd.getText().toString();

                if (pubIdStr.isEmpty() || pubName.isEmpty() || pubAdd.isEmpty()) {
                    Toast.makeText(PublisherDisplayFragment.this.context, "Complete all fields before continue", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    int pubId = Integer.parseInt(pubIdStr);
                    if (pubId <= 0) {
                        Toast.makeText(PublisherDisplayFragment.this.context, "Please enter valid values for pubId ID", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Proceed with  fragment transaction
                    BookFragment bookFragment = BookFragment.newInstance(pubId, pubName, pubAdd);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_main, bookFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

       return v;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState: called");
        savedInstanceState.putInt(KEY_INDEX,currentIndex);
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
            Toast.makeText(context, "Item 1 selected", Toast.LENGTH_SHORT).show();
            //Start new activity courseMapActivity
            intent = new Intent( getActivity(), PublisherDisplayFragment.class);
           // startActivity(intent);
            return  true;
        }
        else if(id == R.id.BookPublishingoptionitem2)
        {
                String pubIdStr = editTextPubId.getText().toString().trim();
                String pubName  =editTextPubName.getText().toString() ;
                String pubAdd  = editTextPubAdd.getText().toString();

                if (pubIdStr.isEmpty() || pubName.isEmpty() || pubAdd.isEmpty()) {
                    Toast.makeText(PublisherDisplayFragment.this.context, "Complete all fields before continue", Toast.LENGTH_SHORT).show();

                }
                else {
                    int pubId = Integer.parseInt(pubIdStr);
                    if (pubId <= 0) {
                        Toast.makeText(PublisherDisplayFragment.this.context, "Please enter valid values for pubId ID", Toast.LENGTH_SHORT).show();

                    }
                    // Proceed with  fragment transaction
                    BookFragment bookFragment = BookFragment.newInstance(pubId, pubName, pubAdd);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_main, bookFragment)
                            .addToBackStack(null)
                            .commit();
                    return  true;
                 }
        }
        else if(id == R.id.BookPublishingOptionitem3)
        {
            Toast.makeText(context, "Item 3 selected", Toast.LENGTH_SHORT).show();
            return  true;
        }
        else if(id == R.id.BookPublishingOptionitem4)
        {
            Toast.makeText(context, "Item 4 selected", Toast.LENGTH_SHORT).show();
            return  true;
        }
        else
        if(id == R.id.BookPublishingOptionitem5)
        {
            Toast.makeText(context, "Item 5 selected", Toast.LENGTH_SHORT).show();
            return  true;
        }



        return super.onOptionsItemSelected(item);
    }

    public  void onResume() {

        super.onResume();
        this.updateAllPublisher();
        editTextPubId.setText(Integer.toString(all_Publisher[currentIndex].getP_id()));
        editTextPubName.setText(all_Publisher[currentIndex].getP_name());
        editTextPubAdd.setText(all_Publisher[currentIndex].getP_address());
    }
}