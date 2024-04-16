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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookpublishingproject.database.PublishingBaseHelper;
import com.example.bookpublishingproject.models.Book;
import com.example.bookpublishingproject.models.Publisher;

import java.util.ArrayList;


public class BookFragment extends Fragment {

    private EditText BookId;
    private EditText BookAuthor;
    private EditText BookTitle;
    private EditText ISBN;
    private Spinner BookType;
    private EditText Price;
    private Spinner BookPld;
    private Button addBook;
    private Button addChapterButton;
    ArrayList<Book> all_Books;
    ArrayList<Publisher> publishers;
    private static  final String EXTRA_PUB_ID="com.example.bookpublishingproject.pub_id";
    private static  final String EXTRA_PUB_NAME="com.example.bookpublishingproject.pub_name";
    private static  final String EXTRA_PUB_ADD="com.example.bookpublishingproject.pub_add";
    Context context;
    PublishingBaseHelper publishingBaseHelper;


    public static BookFragment newInstance(int pubId, String pubName, String pubAdd) {
        BookFragment fragment = new BookFragment();
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
        Context context = getContext().getApplicationContext();
        publishingBaseHelper = new PublishingBaseHelper(context);
        ArrayList<Publisher>consult = publishingBaseHelper.readPublisher();
        publishers=consult;

        if( publishingBaseHelper.readBook().isEmpty())
        {
            all_Books = new ArrayList<>();
        }
        else
        {
            all_Books = publishingBaseHelper.readBook();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_book, container, false);

        //get the view of toolbarPublisherDisplay
        Toolbar toolbarPublisherDisplay = (Toolbar) v.findViewById(R.id.toolbarBook);

        AppCompatActivity activity =(AppCompatActivity) getActivity() ;
        activity.setSupportActionBar(toolbarPublisherDisplay);

         //Get the view of editTextBookId
        BookId = (EditText) v.findViewById(R.id.editTextBookId);

        //Get the view of editTextBookAuthor
        BookAuthor = (EditText) v.findViewById(R.id.editTextBookAuthor);

        //Get the view of editTextBookTitle
        BookTitle = (EditText) v.findViewById(R.id.editTextBookTitle);

        //Get the view of editTextISBN
        ISBN = (EditText) v.findViewById(R.id.editTextISBN);

        //Get the view of spinner1
        BookType = (Spinner) v.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(getContext(),R.array.book_types,android.R.layout.simple_spinner_item
        );
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BookType.setAdapter(adapterType);

        //Get the view of editTextPrice
        Price = (EditText) v.findViewById(R.id.editTextPrice);

        //get view BookPLdSpinner
        BookPld = (Spinner) v.findViewById(R.id.spinnerBookPld);
        // Create a list to store the names of publishers
        ArrayList<String> publisherNames = new ArrayList<>();
        // Extract the names from the Publisher objects
                for (Publisher publisher : publishers) {
                    publisherNames.add(publisher.getP_id()+" "+publisher.getP_name());
                }
        // Create the ArrayAdapter using the list of publisher names
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, publisherNames);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BookPld.setAdapter(adapter);

        //Get the view addBookButton
        addBook = (Button) v.findViewById(R.id.addBookButton);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book bookTemp = new Book(
                        Integer.parseInt(BookId.getText().toString()),
                        BookAuthor.getText().toString(),
                        BookTitle.getText().toString(),
                        ISBN.getText().toString(),
                        BookType.getSelectedItem().toString(),
                        Double.parseDouble(Price.getText().toString()),
                        Integer.parseInt(BookPld.getSelectedItem().toString().substring(0, BookPld.getSelectedItem().toString().indexOf(' ')))
                );
                all_Books.add(bookTemp);
                publishingBaseHelper.addNewBook(bookTemp);
                Toast.makeText(getContext(), "Book Added", Toast.LENGTH_SHORT).show();
            }
        });

        //Get the view addChapterButton
        addChapterButton= (Button) v.findViewById(R.id.addChapterButton);
        addChapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String BookIdT =BookId.getText().toString();
                String BookAuthort=BookAuthor.getText().toString();
                String PriceT=Price.getText().toString();

                if (BookIdT.isEmpty() || PriceT.isEmpty() || BookAuthort.isEmpty()) {
                    Toast.makeText(getContext(), "Complete all fields before continue", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    int BookIdI = Integer.parseInt(BookIdT);
                    double PriceI = Double.parseDouble(PriceT);
                    if (BookIdI <= 0) {
                        Toast.makeText(getContext(), "Please enter valid values for Book ID", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Proceed with  fragment transaction
                    ChapterFragment chapterFragment = ChapterFragment.newInstance(BookIdI, BookAuthort, PriceI);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_main, chapterFragment)
                            .addToBackStack(null)
                            .commit();

                }
            }
        });

        return  v;
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


                // Proceed with  fragment transaction
                BookFragment bookFragment = BookFragment.newInstance(0,"","");
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_main, bookFragment)
                        .addToBackStack(null)
                        .commit();
                return  true;

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