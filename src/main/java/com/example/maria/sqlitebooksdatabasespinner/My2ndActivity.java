package com.example.maria.sqlitebooksdatabasespinner;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class My2ndActivity extends AppCompatActivity {

    private String query;
    private ListView mainListView;
    private static final String QUERY_EXTRA = "QUERY";
    private TextView filter;
    private MyAdapter adapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static Intent getLaunchIntent(Activity from, String query) {
        Intent intent = new Intent(from, My2ndActivity.class);
        intent.putExtra(QUERY_EXTRA, query);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);

        Intent intent = this.getIntent();
        query = intent.getStringExtra(QUERY_EXTRA);

        filter = (TextView) findViewById(R.id.filter);

        if (query.equals("all")) {
            filter.setText("All the books are listed below");
        } else {
            filter.setText("Books filter by " + query);
        }

        mainListView = (ListView) findViewById(R.id.mainListView);

        adapter = new MyAdapter(this, MainActivity.generateData(getApplicationContext(), query), query);

        mainListView.setAdapter(adapter);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Object o = mainListView.getItemAtPosition(position);
                Book b = (Book) o;
                switch(query){
                    case "id":
                        b = MainActivity.db.getBookById(Integer.toString(b.getId()));
                        Toast.makeText(getApplicationContext(),"Title: "+b.getTitle()+"\nAuthor: "+b.getAuthor() + "\nRatings: "+b.getRating() , Toast.LENGTH_LONG).show();
                        break;
                    case "author":
                        b.setId(position);
                        b = MainActivity.db.getBookByAuthorId(b.getAuthor(), Integer.toString(position));
                        Toast.makeText(getApplicationContext(),"Id: "+b.getId()+"\nTitle: "+b.getTitle() + "\nRatings: "+b.getRating() , Toast.LENGTH_LONG).show();
                        break;
                    case "title":
                        b = MainActivity.db.getBookByTitle(b.getTitle());
                        Toast.makeText(getApplicationContext(),"Id: "+b.getId()+"\nAuthor: "+b.getAuthor() + "\nRatings: "+b.getRating() , Toast.LENGTH_LONG).show();
                        break;
                    case "rating":
                        b.setId(position);
                        b = MainActivity.db.getBookById(Integer.toString(b.getId()));
                        Toast.makeText(getApplicationContext(),"Id: "+b.getId()+"\nAuthor: "+b.getAuthor() + "\nTitle: "+b.getTitle() , Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Id: "+b.getId()+"\nAuthor: "+b.getAuthor() + "\nTitle: "+b.getTitle()+ "\nRatings: "+b.getRating()  , Toast.LENGTH_LONG).show();
                        break;

                }
            }
        });


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("My2nd Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}