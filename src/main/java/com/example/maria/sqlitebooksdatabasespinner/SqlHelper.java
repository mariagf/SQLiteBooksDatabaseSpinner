package com.example.maria.sqlitebooksdatabasespinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SqlHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 10;
    // Database Name
    private static final String DATABASE_NAME = "BookDB";
    // Books table name
    private static final String TABLE_BOOKS = "books";
    // Books Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_RATING = "rating";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + "title TEXT, " +
                "author TEXT, " + "rating INTEGER )";
        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");
        // create fresh books table
        this.onCreate(db);
    }

    /*CRUD operations (create "add", read "get", update, delete) */
    public void addBook(Book book){
        Log.d("addBook", book.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, book.getTitle()); // get title
        values.put(KEY_AUTHOR, book.getAuthor()); // get author
        values.put(KEY_RATING, book.getRating()); // get author
        // 3. insert
        db.insert(TABLE_BOOKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values
        // 4. Close dbase
        db.close();
    }

    // Get All Books
    public List<Book> getAllBooks() {
        List<Book> books = new LinkedList<Book>();
        // 1. build the query
        String query = "SELECT * FROM " + TABLE_BOOKS;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build book and add it to list
        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));
                book.setRating(Integer.parseInt(cursor.getString(3)));
                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }
        Log.d("getAllBooks()", books.toString());
        return books; // return books
    }

    // Get Books by
    public List<Book> getBooksBy(String type) {
        List<Book> books = new LinkedList<Book>();
        // 1. build the query
        String query;
        switch (type){
            case "id":
                query = "SELECT id FROM " + TABLE_BOOKS;
                break;
            case "author":
                query = "SELECT author FROM " + TABLE_BOOKS;
                break;
            case "title":
                query = "SELECT title FROM " + TABLE_BOOKS;
                break;
            case "rating":
                query = "SELECT rating FROM " + TABLE_BOOKS;
                break;
            default:
                query = "SELECT * FROM " + TABLE_BOOKS;
                break;
        }
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build book and add it to list
        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();

                switch(type){
                    case "id":
                        book.setId(Integer.parseInt(cursor.getString(0)));
                        break;
                    case "title":
                        book.setTitle(cursor.getString(0));
                        break;
                    case "author":
                        book.setAuthor(cursor.getString(0));
                        break;
                    case "rating":
                        book.setRating(Integer.parseInt(cursor.getString(0)));
                        break;
                    default:
                        break;
                }

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }
        Log.d("getBooksBy("+type+")", books.toString());
        return books; // return books
    }

    // Get Book by Id
    public Book getBookById(String id) {
        Book b = new Book();
        Cursor cursor = getReadableDatabase().rawQuery("select * from books where id = ?", new String[] { id });
        if (cursor.moveToFirst()) {
            do {
                b = new Book();
                b.setId(Integer.parseInt(cursor.getString(0)));
                b.setTitle(cursor.getString(1));
                b.setAuthor(cursor.getString(2));
                b.setRating(Integer.parseInt(cursor.getString(3)));

            } while (cursor.moveToNext());
        }
        return b; // return books
    }
    // Get Book by Title
    public Book getBookByTitle(String title) {
        Book b = new Book();
        Cursor cursor = getReadableDatabase().rawQuery("select * from books where title = ?", new String[] { title });
        if (cursor.moveToFirst()) {
            do {
                b = new Book();
                b.setId(Integer.parseInt(cursor.getString(0)));
                b.setTitle(cursor.getString(1));
                b.setAuthor(cursor.getString(2));
                b.setRating(Integer.parseInt(cursor.getString(3)));

            } while (cursor.moveToNext());
        }
        return b; // return books
    }
    // Get Book by Id
    public Book getBookByAuthorId(String author, String id) {
        Book b = new Book();
        Cursor cursor = getReadableDatabase().rawQuery("select * from books where id = ?", new String[] { id });
        if (cursor.moveToFirst()) {
            do {
                b = new Book();
                b.setId(Integer.parseInt(id));
                b.setTitle(cursor.getString(1));
                b.setAuthor(cursor.getString(2));
                b.setRating(Integer.parseInt(cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        return b; // return books
    }

    // Updating single book
    public int updateBook(Book book, String newTitle, String newAuthor, Integer newRating) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", newTitle);
        values.put("author", newAuthor);
        values.put("rating", newRating);
        // 3. updating row
        int i = db.update(TABLE_BOOKS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(book.getId()) }); //selection args
        // 4. close dbase
        db.close();
        Log.d("UpdateBook", book.toString());
        return i;
    }

    // Deleting single book
    public void deleteBook(Book book) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete
        db.delete(TABLE_BOOKS, KEY_ID+" = ?",
                new String[] { String.valueOf(book.getId()) });
        //3. close
        db.close();
        Log.d("deleteBook", book.toString());
    }

    public int getIds(Book book) {
        String selectQuery = "SELECT id FROM " + TABLE_BOOKS;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        c.moveToFirst();
        int total = c.getCount();
        return total;
    }

    public List<String> getnColumns() {
        String selectQuery = "SELECT * FROM " + TABLE_BOOKS;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);
        List<String> columnNames = new ArrayList<String>();
        for(int i =0 ; i<c.getColumnCount(); i++){
            columnNames.add(c.getColumnName(i));
        }
        return columnNames;
    }

}