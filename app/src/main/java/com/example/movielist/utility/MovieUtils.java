package com.example.movielist.utility;

import android.graphics.Movie;
import android.net.Uri;

import com.example.movielist.data.MovieDetails;
import com.example.movielist.data.MovieNameSearchResult;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MovieUtils {
    private final static String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3";
    private final static String MOVIE_SEARCH = "/search";
    private final static String MOVIE_CONFIG = "/configuration";
    private final static String MOVIE = "/movie";
    private final static String QUERY = "query";
    private final static String API_KEY_PARAM = "api_key";
    private final static String MOVIE_API_KEY = "f25cbd8869292a9e9082fa3dac0c1d1a";
    private final static String MOVIE_PAGE = "page";
    private final static String MOVIE_IMG_BASE_URL = "https://image.tmdb.org/t/p/";

    static class MovieResults {
        MovieListItem[] results;
    }

    static class MovieListItem {
        int id;
        String original_language;
        String title;
        String release_date;
        String overview;
        String poster_path;
        String backdrop_path;
        float popularity;
        int vote_count;
        float vote_average;
    }
    static class MovieDetailsItem {
        Integer id;
        String imdb_id;
        Integer budget;
        String homepage;
        String original_language;
        String original_title;
        String overview;
        String poster_path;
        String release_date;
        String revenue;
        String runtime;
        String title;
        String tagline;
        float vote_average;
        float vote_count;
    }
    //Calls configuration to get base_url path and file_size(NEED file_path from movie get to complete)
    public static String buildConfigMovieURL(){
        return Uri.parse(MOVIE_DB_BASE_URL + MOVIE_CONFIG).buildUpon()
                .appendQueryParameter(API_KEY_PARAM,MOVIE_API_KEY)
                .build()
                .toString();
    }

    //Searches for all movies found by searching movieName
    //TODO Add page number for requesting next page. EX: appendQueryParameter(MOVIE_PAGE,2)
    //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query="name of movie to search"
    public static String buildMovieNameSearchURL(String  movieName){
        return Uri.parse(MOVIE_DB_BASE_URL + MOVIE_SEARCH + MOVIE ).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, MOVIE_API_KEY)
                .appendQueryParameter(QUERY,movieName)
                .build()
                .toString();
    }
    //Gets detail
    //https://api.themoviedb.org/3/movie/{movieID}?api_key={api_key}
    public static String buildMovieSearchByID(String movieID) {
        return Uri.parse(MOVIE_DB_BASE_URL + MOVIE + "/" + movieID).buildUpon()
                .appendQueryParameter(API_KEY_PARAM,MOVIE_API_KEY)
                .build()
                .toString();
    }
    //Gets images
    public static String buildMovieImageSearch(String fileSize){
        return Uri.parse(MOVIE_IMG_BASE_URL + fileSize + "/").buildUpon()
                .appendQueryParameter(API_KEY_PARAM,MOVIE_API_KEY)
                .build()
                .toString();
    }
    public static ArrayList<MovieNameSearchResult> parseMovieNameJSON(String movieNameJSON){
        Gson gson = new Gson();
        MovieResults results = gson.fromJson(movieNameJSON, MovieResults.class);
        if(results != null && results.results != null) {
            ArrayList<MovieNameSearchResult> movieItems = new ArrayList<>();

            for(MovieListItem listItem : results.results) {
                MovieNameSearchResult movieResult = new MovieNameSearchResult();

                movieResult.id = listItem.id;
                movieResult.original_language = listItem.original_language;
                movieResult.title = listItem.title;
                movieResult.release_date = listItem.release_date;
                movieResult.overview = listItem.overview;
                movieResult.poster_path = listItem.poster_path;
                movieResult.backdrop_path = listItem.backdrop_path;
                movieResult.popularity = listItem.popularity;
                movieResult.vote_count = listItem.vote_count;
                movieResult.vote_average = listItem.vote_average;


                movieItems.add(movieResult);
            }
            return movieItems;
        } else {
            return null;
        }
    }
    public static MovieDetails parseMovieDetailsJSON(String movieDetailsJSON) {
        Gson gson = new Gson();
        MovieDetailsItem result = gson.fromJson(movieDetailsJSON, MovieDetailsItem.class);
        if (result != null ) {
            MovieDetails movieDetails = new MovieDetails();
            movieDetails.id = result.id;
            movieDetails.imdb_id = result.imdb_id;
            movieDetails.budget = result.budget;
            movieDetails.homepage = result.homepage;
            movieDetails.original_language = result.original_language;
            movieDetails.original_title = result.original_title;
            movieDetails.overview = result.overview;
            movieDetails.poster_path = result.poster_path;
            movieDetails.release_date = result.release_date;
            movieDetails.revenue = result.revenue;
            movieDetails.runtime = result.runtime;
            movieDetails.title = result.title;
            movieDetails.tagline = result.tagline;
            movieDetails.vote_average = result.vote_average;
            movieDetails.vote_count = result.vote_count;
            return movieDetails;
        } else {
            return null;
        }
    }

}
