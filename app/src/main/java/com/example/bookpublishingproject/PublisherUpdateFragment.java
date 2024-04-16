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
import android.widget.Toast;

import com.example.bookpublishingproject.database.PublishingBaseHelper;
import com.example.bookpublishingproject.models.Publisher;


public class PublisherUpdateFragment extends Fragment {
    private EditText editTextPubId;
    private  EditText editTextPubName;
    private  EditText editTextPubAdd;
    private Button buttonPublisherAdd;
    private Button buttonPublisherClear;
    private Button buttonPublisherSearch;
    private Button buttonPublisherUpdate;
    private Button buttonPublisherDelete;
    public int pubIdRetrieve ;
    public String pubNameRetrieve;
    public String pubAddRetrieve;
    private static  final String EXTRA_PUB_ID="com.example.bookpublishingproject.pub_id";
    private static  final String EXTRA_PUB_NAME="com.example.bookpublishingproject.pub_name";
    private static  final String EXTRA_PUB_ADD="com.example.bookpublishingproject.pub_add";
    Context context;
    public PublishingBaseHelper publishingBaseHelper;


    public static PublisherUpdateFragment newInstance(int pubId, String pubName, String pubAdd) {
        PublisherUpdateFragment fragment = new PublisherUpdateFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_PUB_ID,pubId);
        args.putString(EXTRA_PUB_NAME, pubName);
        args.putString(EXTRA_PUB_ADD, pubAdd);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fragment will receive a callback from FragmentManager if
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            pubIdRetrieve = getArguments().getInt(EXTRA_PUB_ID);
            pubNameRetrieve = getArguments().getString(EXTRA_PUB_NAME);
            pubAddRetrieve = getArguments().getString(EXTRA_PUB_ADD);
        }
        context = getContext().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_publisher_update, container, false);

        //get the view of toolbarPublisherDisplay
        Toolbar toolbarPublisherDisplay = (Toolbar) v.findViewById(R.id.toolbarPublisherUpdate);

        AppCompatActivity activity =(AppCompatActivity) getActivity() ;
        activity.setSupportActionBar(toolbarPublisherDisplay);

        publishingBaseHelper = new PublishingBaseHelper(context);

        //Get the view of editTextPubId
        editTextPubId= (EditText) v.findViewById(R.id.editTextPubIdPublisher);
        editTextPubId.setText(Integer.toString(pubIdRetrieve));
        //Get the view of editTextPubName
        editTextPubName=(EditText) v.findViewById(R.id.editTextPubNamePublisher);
        editTextPubName.setText(pubNameRetrieve);
        //Get the view of editTextPubAdd
        editTextPubAdd=(EditText) v.findViewById(R.id.editTextPubAddPublisher);
        editTextPubAdd.setText(pubAddRetrieve);

        //Get the view of buttonPublisherUpdatePublisher
        buttonPublisherUpdate = (Button) v.findViewById(R.id.buttonPublisherUpdatePublisher);
        buttonPublisherUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publisher publisher = new Publisher(Integer.parseInt(editTextPubId.getText().toString()), editTextPubName.getText().toString(), editTextPubAdd.getText().toString());
                publishingBaseHelper.updatePublisher(publisher);
                Toast.makeText(PublisherUpdateFragment.this.context, "Publisher " + editTextPubId.getText().toString() + " Updated ", Toast.LENGTH_SHORT).show();
            }
        });

        //Get the view of buttonPublisherClearPublisher
        buttonPublisherClear = (Button) v.findViewById(R.id.buttonPublisherClearPublisher);
        buttonPublisherClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextPubAdd.setText("");
                editTextPubName.setText("");
                editTextPubId.setText("");

            }
        });

        //Get the view of buttonPublisherAddPublisher
        buttonPublisherAdd = (Button) v.findViewById(R.id.buttonPublisherAddPublisher);
        buttonPublisherAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publisher publisher = new Publisher(Integer.parseInt(editTextPubId.getText().toString()), editTextPubName.getText().toString(), editTextPubAdd.getText().toString());
                publishingBaseHelper.addNewPublisher(publisher);
                Toast.makeText(PublisherUpdateFragment.this.context, "Publisher " + editTextPubId.getText().toString() + " Added", Toast.LENGTH_SHORT).show();
            }
        });

        //Get the view of buttonPublisherSearchPublisher
        buttonPublisherSearch = (Button) v.findViewById(R.id.buttonPublisherSearchPublisher);
        buttonPublisherSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publisher temp = publishingBaseHelper.searchPublisher(Integer.parseInt(editTextPubId.getText().toString()));
                if(temp!=null)
                {
                    editTextPubId.setText(Integer.toString(temp.getP_id()));
                    editTextPubName.setText(temp.getP_name());
                    editTextPubAdd.setText(temp.getP_address());
                }
                else
                {
                    Toast.makeText(PublisherUpdateFragment.this.context, "Publisher " + editTextPubId.getText().toString() + " doesn't no exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Get the view of buttonPublisherDeletePublisher
        buttonPublisherDelete = (Button) v.findViewById(R.id.buttonPublisherDeletePublisher);
        buttonPublisherDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publisher publisher = new Publisher(Integer.parseInt(editTextPubId.getText().toString()), editTextPubName.getText().toString(), editTextPubAdd.getText().toString());
                publishingBaseHelper.deletePublisher(publisher);
                Toast.makeText(PublisherUpdateFragment.this.context, "Publisher " + editTextPubId.getText().toString() + " Deleted", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "Item 1 selected", Toast.LENGTH_SHORT).show();
            //Start new activity courseMapActivity
            intent = new Intent( getActivity(), PublisherDisplayFragment.class);
            startActivity(intent);
            return  true;
        }
        else if(id == R.id.BookPublishingoptionitem2)
        {
            String pubIdStr = editTextPubId.getText().toString().trim();
            String pubName  =editTextPubName.getText().toString() ;
            String pubAdd  = editTextPubAdd.getText().toString();

            if (pubIdStr.isEmpty() || pubName.isEmpty() || pubAdd.isEmpty()) {
                Toast.makeText(PublisherUpdateFragment.this.context, "Complete all fields before continue", Toast.LENGTH_SHORT).show();

            }
            else {
                int pubId = Integer.parseInt(pubIdStr);
                if (pubId <= 0) {
                    Toast.makeText(PublisherUpdateFragment.this.context, "Please enter valid values for pubId ID", Toast.LENGTH_SHORT).show();

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

}