package com.example.bookpublishingproject.database;

import static com.example.bookpublishingproject.database.PublishingDbSchema.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bookpublishingproject.models.Book;
import com.example.bookpublishingproject.models.Chapter;
import com.example.bookpublishingproject.models.Publisher;

import java.util.ArrayList;

public class PublishingBaseHelper extends SQLiteOpenHelper {

    private static  final int VERSION = 1;
    private static final String DATABASE_NAME="publishingBase.db";

    public PublishingBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public PublishingBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ PublisherTable.NAME+"("+
                        PublisherTable.Cols.P_ID+" PRIMARY KEY,"+
                        PublisherTable.Cols.P_NAME+","+
                        PublisherTable.Cols.P_ADDRESS+")"
                );
        db.execSQL("CREATE TABLE "+ BookTable.NAME+"("+
                BookTable.Cols.B_ID+" PRIMARY KEY,"+
                BookTable.Cols.B_AUTHOR+","+
                BookTable.Cols.B_TITLE+","+
                BookTable.Cols.B_ISBN+","+
                BookTable.Cols.B_TYPE+","+
                BookTable.Cols.B_PRICE+","+
                //"FOREIGN KEY(" + BookTable.Cols.P_ID + ") REFERENCES " + PublisherTable.NAME + "(" + PublisherTable.Cols.P_ID + "));"
                BookTable.Cols.P_ID+")"
        );
        db.execSQL("CREATE TABLE "+ chapterTable.NAME+"("+
                chapterTable.Cols.B_ID+","+
                chapterTable.Cols.C_NO+","+
                chapterTable.Cols.C_TITLE+","+
                chapterTable.Cols.C_PRICE+","+
                "PRIMARY KEY (" + chapterTable.Cols.B_ID + ", " + chapterTable.Cols.C_NO + ")" +
                ")"

        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static ContentValues getContentValues(Publisher publisher)
    {
        ContentValues values = new ContentValues();
        values.put(PublisherTable.Cols.P_ID, publisher.getP_id());
        values.put(PublisherTable.Cols.P_NAME, publisher.getP_name());
        values.put(PublisherTable.Cols.P_ADDRESS, publisher.getP_address());
        return values;
    }
    public static ContentValues getContentValues(Book book)
    {
        ContentValues values = new ContentValues();
        values.put(BookTable.Cols.B_ID, book.getB_id());
        values.put(BookTable.Cols.B_AUTHOR, book.getB_author());
        values.put(BookTable.Cols.B_TITLE, book.getB_title());
        values.put(BookTable.Cols.B_ISBN, book.getB_isbn());
        values.put(BookTable.Cols.B_TYPE, book.getB_type());
        values.put(BookTable.Cols.B_PRICE, book.getB_price());
        values.put(BookTable.Cols.P_ID, book.getP_id());
        return values;
    }
    public static ContentValues getContentValues(Chapter chapter)
    {
        ContentValues values = new ContentValues();
        values.put(chapterTable.Cols.B_ID, chapter.getB_id());
        values.put(chapterTable.Cols.C_NO, chapter.getC_no());
        values.put(chapterTable.Cols.C_TITLE, chapter.getC_title());
        values.put(chapterTable.Cols.C_PRICE, chapter.getC_price());
        return values;
    }
        //PUBLISHER
        //ADD
        public void addNewPublisher(Publisher publisher)
        {
            //Writing data into db
            SQLiteDatabase db= getWritableDatabase();

            //Creating values from ContentValues
            ContentValues values = getContentValues(publisher);

            //Insert values into table row
            db.insert(PublisherTable.NAME, null, values);

            //close db
            db.close();
        }
        //DELETE
        public void deletePublisher(Publisher publisher)
        {
            String pub_idString = Integer.toString(publisher.getP_id());

            SQLiteDatabase db =this.getWritableDatabase();
            //Delete values into table row
            db.delete(PublisherTable.NAME,PublisherTable.Cols.P_ID+"="+pub_idString,null);

            //close db
            db.close();
        }
        //UPDATE
        public void updatePublisher(Publisher publisher)
        {
            String pub_idString = Integer.toString(publisher.getP_id());
            //Creating values from ContentValues
            ContentValues values = getContentValues(publisher);

            SQLiteDatabase db =this.getWritableDatabase();
            //UPDATE values into table row
            db.update(PublisherTable.NAME, values,PublisherTable.Cols.P_ID+"="+pub_idString,null);

            //close db
            db.close();
        }
        //READ
        public ArrayList<Publisher> readPublisher()
        {
            //Reading data into db
            SQLiteDatabase db= this.getReadableDatabase();

            //Use cursor as data structure
            Cursor cursorPublisher = db.rawQuery("SELECT * FROM " + PublisherTable.NAME, null);

            //Create arrayList courseModelArrayList
            ArrayList<Publisher> PublisherModalArrayList = new ArrayList<>();

            //Moving cursor to first position
            if(cursorPublisher.moveToFirst())
            {
                do{
                    //Populate ArrayLit
                    PublisherModalArrayList.add(new Publisher(cursorPublisher.getInt(0),
                            cursorPublisher.getString(1),
                            cursorPublisher.getString(2)
                    ));
                }while (cursorPublisher.moveToNext());


            }
            return PublisherModalArrayList;
        }
        //SEARCH
        public Publisher searchPublisher(int pubId)
        {
            SQLiteDatabase db = this.getReadableDatabase();

            // Use cursor as data structure
            Cursor cursorPublisher= db.rawQuery("SELECT * FROM " + PublisherTable.NAME + " WHERE " + PublisherTable.Cols.P_ID + "="+String.valueOf(pubId),null);

            Publisher publisherModal = null;

            // Check if the cursor contains data
            if (cursorPublisher.moveToFirst()) {
                // Populate billingModal from the cursor
                publisherModal = new Publisher(
                        cursorPublisher.getInt(cursorPublisher.getColumnIndex(PublisherTable.Cols.P_ID)),
                        cursorPublisher.getString(cursorPublisher.getColumnIndex(PublisherTable.Cols.P_NAME)),
                        cursorPublisher.getString(cursorPublisher.getColumnIndex(PublisherTable.Cols.P_ADDRESS))
                );
            }

            // Close the cursor and database connection
            cursorPublisher.close();
            db.close();

            return publisherModal;
        }
    //BOOK
    //ADD
    public void addNewBook(Book book)
    {
        //Writing data into db
        SQLiteDatabase db= this.getWritableDatabase();

        //Creating values from ContentValues
        ContentValues values = getContentValues(book);

        //Insert values into table row
        db.insert(BookTable.NAME, null, values);

        //close db
        db.close();
    }

    public ArrayList<Book> readBook()
    {
        //Reading data into db
        SQLiteDatabase db= this.getReadableDatabase();

        //Use cursor as data structure
        Cursor cursorBook = db.rawQuery("SELECT * FROM " + BookTable.NAME, null);

        //Create arrayList courseModelArrayList
        ArrayList<Book> BookModalArrayList = new ArrayList<>();

        //Moving cursor to first position
        if(cursorBook.moveToFirst())
        {
            do{
                //Populate ArrayLit
                BookModalArrayList.add(new Book(cursorBook.getInt(0),
                        cursorBook.getString(1),
                        cursorBook.getString(2),
                        cursorBook.getString(3),
                        cursorBook.getString(4),
                        cursorBook.getDouble(5),
                        cursorBook.getInt(6)
                ));
            }while (cursorBook.moveToNext());


        }
        return BookModalArrayList;
    }

    //Chapter
    //ADD
    public void addNewChapter(Chapter chapter)
    {
        //Writing data into db
        SQLiteDatabase db= getWritableDatabase();

        //Creating values from ContentValues
        ContentValues values = getContentValues(chapter);

        //Insert values into table row
        db.insert(chapterTable.NAME, null, values);

        //close db
        db.close();
    }


}
