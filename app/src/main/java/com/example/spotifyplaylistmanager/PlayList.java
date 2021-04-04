package com.example.spotifyplaylistmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PlayList {

    public PlayList(String id, String name, JsonObject tracks, JsonArray images) {
        this.id = id;
        this.name = name;
        this.tracks = tracks;
        this.images = images;
    }

    String id;
    String name;
    JsonObject tracks;//Tienen que ser procesados
    JsonArray images;
    public String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL(){
        if(imageUrl == null)
            setImageURL();
        return imageUrl;
    }

    public void setImageURL() {
        if(images.size() != 0) {
            imageUrl = images.get(0).getAsJsonObject().get("url").getAsString();
        }
        else imageUrl = "EMPTY URL";
    }

   /* public Bitmap getImageBitmap() {
        if (images.size() != 0) {
            Bitmap bm = null;
            try {
                URL aURL = new URL(images.get(0).getAsJsonObject().get("url").getAsString());
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException | JsonSyntaxException e) {
                Log.e(TAG, "Error getting bitmap", e);
                return bm;
            }
        }
        return null;
    }*/
}
