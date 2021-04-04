package com.example.spotifyplaylistmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    UserService userService;
    String userId;
    String userName;
    //ArrayList<Track> defaultTracks;
    ArrayList<String> defaultTracks = new ArrayList<>();
    ArrayList<PlayList> playlists;
    PlayList createdPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        userId = sharedPreferences.getString("userid","ERROR");
        userName = sharedPreferences.getString("username","ERROR");
        EditText editText = findViewById(R.id.textLabel);
        editText.setText("Welcome "+userName);
        Log.d("Debugging",userId);
        getPlaylists();
        createDefaultPlaylist();

        final Button addPlaylistButton = findViewById(R.id.addPlaylistButton);
        addPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout placeHolder = (FrameLayout) findViewById(R.id.addPlayListLayout);
                View layout = getLayoutInflater().inflate(R.layout.add_playlist,placeHolder);
                findViewById(R.id.playlistViewer).setVisibility(View.INVISIBLE);
                setButtons();
            }
        });
        final Button refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPlaylists();
                showPlaylists();
            }
        });

    }

    public void setButtons()
    {
        final Button cancelPlayListButton = findViewById(R.id.cancelButton);
        cancelPlayListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAddPlaylistView();
            }
        });

        final Button addPlayListButton = findViewById(R.id.addPlayListBtn);//TODO:Volver a recargar las playlist haciendo clear a las viejas
        addPlayListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.newPlaylistName);
                EditText description = findViewById(R.id.newPlaylistDescription);
                createPlayList(name.getText().toString(),description.getText().toString());
                closeAddPlaylistView();
                getPlaylists();
            }
        });


    }

    private void closeAddPlaylistView(){
        ViewGroup vg = (ViewGroup)findViewById(R.id.cancelButton).getParent();
        vg.removeAllViews();
        findViewById(R.id.playlistViewer).setVisibility(View.VISIBLE);
    }

    //TODO:Agregar las canciones que pide el asistente
    private void createDefaultPlaylist()
    {
        defaultTracks.add("spotify:track:3z8h0TU7ReDPLIbEnYhWZb");
        defaultTracks.add("spotify:track:2aibwv5hGXSgw7Yru8IYTO");
        defaultTracks.add("spotify:track:0pqnGHJpmpxLKifKRmU6WP");
    }

    private void createPlayList(String playlistName, String playlistDescription){
        UserService userService = new UserService(getApplicationContext());
        userService.createPlaylist(() -> {
            createdPlaylist = userService.getCreatedPlaylist();
            playlists.add(createdPlaylist);
            addSongToPlaylist(defaultTracks,createdPlaylist.id);
        },playlistName,playlistDescription);;
    }

    //TODO:TIENE QUE CAMBIAR A SONGS
    private void addSongToPlaylist(ArrayList<String> uris,String playlistId){
        UserService userService = new UserService(getApplicationContext());
        userService.addSong(()->{},uris,playlistId);
    }

    private void getPlaylists(){
        UserService userService = new UserService(getApplicationContext());
        userService.getUserPlaylists(() -> {
            playlists = userService.getPlaylists();
            Log.d("Getting playlists", "GOT USER Palylist");
            // We use commit instead of apply because we need the information stored immediately
        });;

    }
    private void showPlaylists(){
        ListView listView = findViewById(R.id.playlistViewer);
        Context context = listView.getContext();
        CustomListAdapter listAdapter = new CustomListAdapter(context,playlists);
        listView.setAdapter(listAdapter);
    }
}