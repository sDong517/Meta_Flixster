package com.example.meta_flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    String posterPath;
    String title;
    String overview;
    String backdropPath;
    int movieId;

    double rating;


    //Required empty constructor by Parcel
    public Movie() {
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString( "overview");
        backdropPath = jsonObject.getString("backdrop_path");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length();i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    //GETTERS
    public String getPosterPath() {
        return String.format( "https://image.tmdb.org/t/p/w342/%s", posterPath);

        //posterPath;
    }

    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public double getRating(){
        return rating;
    }

    public int getMovieId(){
        return movieId;
    }

    public String getTitle(){
        return title;
    }

    public String getOverview(){
        return overview;
    }
}