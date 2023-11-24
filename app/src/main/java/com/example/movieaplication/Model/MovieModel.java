package com.example.movieaplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    private int id;
    private String poster_path, title, release_date, overview, original_language;
    private float vote_average;


    public MovieModel(int id, String poster_path, String title, String release_date, String overview, String original_language, float vote_average) {
        this.id = id;
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
        this.overview = overview;
        this.original_language = original_language;
        this.vote_average = vote_average;
    }

    protected MovieModel(Parcel in) {
        id = in.readInt();
        poster_path = in.readString();
        title = in.readString();
        release_date = in.readString();
        overview = in.readString();
        original_language = in.readString();
        vote_average = in.readFloat();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public float getVote_average() {
        return vote_average;
    }

    //parceleble berfungsi untuk ...
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeString(original_language);
        dest.writeFloat(vote_average);
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "id=" + id +
                ", poster_path='" + poster_path + '\'' +
                ", title='" + title + '\'' +
                ", release_date='" + release_date + '\'' +
                ", overview='" + overview + '\'' +
                ", original_language='" + original_language + '\'' +
                ", vote_average=" + vote_average +
                '}';
    }
}
