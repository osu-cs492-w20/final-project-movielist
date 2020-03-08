package com.example.movielist.data;

import java.io.Serializable;

public class MovieNameSearchResult implements Serializable {
    public int id;

    public String original_language;
    public String title;
    public String release_date;
    public String overview;

    public String poster_path;  //Either one of these are what is needed for image query
    public String backdrop_path;

    public float popularity;
    public int vote_count;
    public float vote_average;
}

