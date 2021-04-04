package com.example.spotifyplaylistmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    private Context context; //context
    private ArrayList<PlayList> items; //data source of the list adapter

    public CustomListAdapter(Context context,ArrayList<PlayList> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.playlist_layout, parent, false);
        }

        // get current item to be displayed
        PlayList curretnPlaylist = (PlayList) getItem(position);

        // get the TextView for item name and item description
        ImageView imageView = (ImageView)
                convertView.findViewById(R.id.playlistImageView);
        TextView textView = (TextView)
                convertView.findViewById(R.id.playlistTextView);
        ImageButton button = (ImageButton)
                convertView.findViewById(R.id.playlistPlayView);

        //sets the text for item name and item description from the current item object
        textView.setText(curretnPlaylist.getName());
        Log.d("URLLL",curretnPlaylist.getImageURL());

        // returns the view for the current row
        return convertView;
    }
}
