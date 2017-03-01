package com.example.maria.sqlitebooksdatabasespinner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Spinner spinner;
    public static SqlHelper db;
    private String query = "All";
    public static List<Book> list;
    private String[] columnNames = {SqlHelper.KEY_ID, SqlHelper.KEY_TITLE, SqlHelper.KEY_AUTHOR, SqlHelper.KEY_RATING};
    private static Integer flagDBcreated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SqlHelper(getApplicationContext());
        List<String> columnNames = db.getnColumns();
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        for(int i=0; i<columnNames.size();i++){
            list.add(columnNames.get(i));
        }
        list.add("all");
        String[] valores = list.toArray(new String[list.size()]);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                query = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button = (Button) findViewById(R.id.queryBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(My2ndActivity.getLaunchIntent(MainActivity.this, query));
            }
        });
    }

    public static void fillDB(SqlHelper db){
        Log.d("Createdby", "Maria Garcia"); //Show the author of the DB
        // add Books
        db.addBook(new Book("Professional Android 4 Application Development", "Reto Meier", 1));
        db.addBook(new Book("Beginning Android 4 Application Development", "Wei-Meng Lee", 0));
        db.addBook(new Book("Programming Android", "Wallace Jackson", 3));
        db.addBook(new Book("Hello, Android", "Wallace Jackson", 4));
        // get all books
        list = db.getAllBooks();
        // update one book
        int j = db.updateBook(list.get(0), "Romeo & Juliet", "Shakespeare", 1);
        // delete one book
        db.deleteBook(list.get(0));
        // get all books

        db.getAllBooks();

        Log.d("nDBrecords", ""+db.getIds(list.get(0))); //Show the number of records in the DB
    }

    public static ArrayList<Book> generateData(Context context, String dataType){

        db = new SqlHelper(context);
        /** CRUD Operations **/
        if(flagDBcreated == 0) {
            flagDBcreated = 1;
            fillDB(db);
        } else {

        }

        switch(dataType){
            case "all":
                list = db.getAllBooks();
                break;
            case "id":
            case "author":
            case "title":
            case "rating":
            default:
                list = db.getBooksBy(dataType);
                break;
        }
        return convertList2ArrayList(list);
    }

    public static ArrayList<Book> convertList2ArrayList(List list){
        ArrayList<Book> arrayList = new ArrayList<Book>(list);
        Iterator iterator = arrayList.iterator();
        while(iterator.hasNext()){
            Book b = (Book) iterator.next();
        }
        return arrayList;
    }
}
