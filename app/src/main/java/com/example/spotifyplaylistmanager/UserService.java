package com.example.spotifyplaylistmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    private static final String ENDPOINT = "https://api.spotify.com/v1/me";
    private static final String USERPLAYLISTENDPOINT = "https://api.spotify.com/v1/me/playlists";
    private ArrayList<PlayList> playlists = new ArrayList<>();
    private SharedPreferences msharedPreferences;
    private RequestQueue mqueue;
    private User user;
    private User ol;

    public UserService(RequestQueue queue, SharedPreferences sharedPreferences) {
        mqueue = queue;
        msharedPreferences = sharedPreferences;
    }

    public UserService(Context context){
        msharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        mqueue = Volley.newRequestQueue(context);
    }

    public User getUser() {
        return user;
    }

    public void get(final VolleyCallBack callBack) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(ENDPOINT, null, response -> {
            Gson gson = new Gson();
            user = gson.fromJson(response.toString(), User.class);
            callBack.onSuccess();
        }, error -> get(() -> {

        })) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = msharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        mqueue.add(jsonObjectRequest);
    }

    public void createPlaylist(final VolleyCallBack callBack,String playlistName,String playlistDescription) {
        final String CREATEPLAYLISTREQUEST = "https://api.spotify.com/v1/users/"+msharedPreferences.getString("userid","eer")+"/playlists";
        JSONObject request = new JSONObject();
        try {
            request.put("name",playlistName);
            request.put("description",playlistDescription);
            request.put("public",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Test",request.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,CREATEPLAYLISTREQUEST, request
        , response -> {
            Gson gson = new Gson();
            Log.d("Create Playlist","Playlist Created");
            callBack.onSuccess();
        }, error -> get(() -> {

        })) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = msharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        mqueue.add(jsonObjectRequest);
    }

    public ArrayList<PlayList> getUserPlaylists(final VolleyCallBack callBack) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, USERPLAYLISTENDPOINT, null, response -> {
                    Gson gson = new Gson();
                    JSONArray jsonArray = response.optJSONArray("items");
                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);
                            PlayList playlist = gson.fromJson(object.toString(), PlayList.class);
                            Log.d("GETPLAYLIST",playlist.name);
                            playlists.add(playlist);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    callBack.onSuccess();
                }, error -> {
                    // TODO: Handle error

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = msharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        mqueue.add(jsonObjectRequest);
        return playlists;
    }

    public void addSong(VolleyCallBack callback,ArrayList<String> songs,String playlistId)  {
        String uris = "https://api.spotify.com/v1/playlists/"+playlistId+"/tracks?uris=";
        Log.d("DEBUGGING",songs.toString());
        for (String uri:songs) {
            uris+=uri+",";
        }
        final String ADDSONGTOPLAYLIST = uris.substring(0,uris.length()-1);
        Log.d("DEBUGGING",ADDSONGTOPLAYLIST);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ADDSONGTOPLAYLIST,null, response -> {
            Gson gson = new Gson();
            Log.d("DEBUGGING","ADDEDSONG");
            callback.onSuccess();
        }, error -> {
            // TODO: Handle error

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = msharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        mqueue.add(jsonObjectRequest);
    }
}
