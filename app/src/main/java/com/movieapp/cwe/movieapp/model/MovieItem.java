package com.movieapp.cwe.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieItem implements Parcelable {

    private String name;
    private String year;
    private String genre;
    private boolean fiction;
    private ArrayList<String> cast;
    private int score;

    public MovieItem()
    {

    }

    public MovieItem(String name, String year, String genre, boolean fiction, ArrayList<String> cast, int score)
    {
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.fiction = fiction;
        this.cast = cast;
        this.score = score;
    }

    public MovieItem(Parcel source)
    {
        setName(source.readString());
        setYear(source.readString());
        setGenre(source.readString());
        setFiction(source.readByte() != 0);
        setCast(source.readArrayList(null));
        setScore(source.readInt());
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public boolean getFiction() {
        return fiction;
    }
    public void setFiction(boolean fiction) {
        this.fiction = fiction;
    }
    public ArrayList<String> getCast() {
        return cast;
    }
    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public void writeToParcel(Parcel dest, int arg1) {
        dest.writeString(name);
        dest.writeString(year);
        dest.writeString(genre);
        dest.writeByte((byte) (fiction ? 1 : 0));
        dest.writeList(cast);
        dest.writeInt(score);
    }

    public static final Creator<MovieItem> CREATOR
            = new Creator<MovieItem>()
    {
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        public MovieItem[] newArray(int size) {
            return new MovieItem[size];

        }

    };

    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String toString() {
        return getName();
    }

}