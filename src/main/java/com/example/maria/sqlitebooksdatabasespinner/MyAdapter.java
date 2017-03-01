package com.example.maria.sqlitebooksdatabasespinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Book> {

    private final Context context;
    private final ArrayList<Book> itemsArrayList;
    private final String query;

    public MyAdapter(Context context, ArrayList<Book> itemsArrayList, String query) {

        super(context, R.layout.all, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.query = query;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        TextView idView;
        TextView titleView;
        TextView authorView;
        RatingBar ratingView;
        TextView type;

        // 2. Get rowView from inflater & 3. Get the two text view from the rowView & 4. Set the text for textView
        if (query.equals("all")) {
            rowView = inflater.inflate(R.layout.all, parent, false);
            idView = (TextView) rowView.findViewById(R.id.nId);
            titleView = (TextView) rowView.findViewById(R.id.title);
            authorView = (TextView) rowView.findViewById(R.id.author);
            ratingView = (RatingBar) rowView.findViewById(R.id.rating);

            idView.setText(Integer.toString(itemsArrayList.get(position).getId()));
            authorView.setText(itemsArrayList.get(position).getAuthor());
            titleView.setText(itemsArrayList.get(position).getTitle());
            ratingView.setRating(itemsArrayList.get(position).getRating());
        } else if (query.equals("rating")) {
            rowView = inflater.inflate(R.layout.rating, parent, false);
            ratingView = (RatingBar) rowView.findViewById(R.id.rating);

            ratingView.setRating(itemsArrayList.get(position).getRating());
        } else {
            rowView = inflater.inflate(R.layout.simplerow, parent, false);
            type = (TextView) rowView.findViewById(R.id.type);
            switch (query) {
                case "id":
                    type.setText(Integer.toString(itemsArrayList.get(position).getId()));
                    break;
                case "author":
                    type.setText(itemsArrayList.get(position).getAuthor());
                    break;
                case "title":
                    type.setText(itemsArrayList.get(position).getTitle());
                    break;
                default:
                    break;
            }
        }

        // 5. return rowView
        return rowView;
    }
}