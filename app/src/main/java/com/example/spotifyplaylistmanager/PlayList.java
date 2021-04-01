package com.example.spotifyplaylistmanager;

import com.google.gson.JsonObject;

import java.util.List;

public class PlayList {

    public PlayList(String id, String name, JsonObject tracks) {
        this.id = id;
        this.name = name;
        this.tracks = tracks;
    }

    String id;
    String name;
    JsonObject tracks;
}
