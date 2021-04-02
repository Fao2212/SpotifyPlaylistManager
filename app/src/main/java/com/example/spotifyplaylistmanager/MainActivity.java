package com.example.spotifyplaylistmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    UserService userService;
    String userId;
    //ArrayList<Track> defaultTracks;
    ArrayList<String> defaultTracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        userId = sharedPreferences.getString("userid","xd");
        Log.d("Debugging",userId);

        createDefaultPlaylist();
        //createPlayList();
        addSongToPlaylist(defaultTracks,"6AxlVoj568l5ubn4EY8pb7");

    }

    private void createDefaultPlaylist()
    {
        defaultTracks.add("spotify:track:0qQ9VcVPkWDTnff6HIhISU");
    }

    private void createPlayList(){
        UserService userService = new UserService(getApplicationContext());
        userService.createPlaylist(() -> {
        },"Test2","APII TEST");;
    }

    //TODO:TIENE QUE CAMBIAR A SONGS
    private void addSongToPlaylist(ArrayList<String> uris,String playlistId){
        UserService userService = new UserService(getApplicationContext());
        userService.addSong(()->{},uris,playlistId);
    }
}