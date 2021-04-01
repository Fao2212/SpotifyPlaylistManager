package com.example.spotifyplaylistmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    UserService userService;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        userId = sharedPreferences.getString("userid","xd");
        Log.d("Debugging",userId);

        createPlayList();
    }

    private void createPlayList(){
        Log.d("QIUEEEEE", "GOT USER INFORMATION" );
        UserService userService = new UserService(getApplicationContext());
        userService.createPlaylist(() -> {
        },"Test1","APII TEST",userId);;
    }
}