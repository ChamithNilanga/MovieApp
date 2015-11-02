package com.movieapp.cwe.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelWriter {
    public static Parcel writeParcel(Parcelable item) {
        Parcel parcel = Parcel.obtain();
        item.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        return parcel;
    }
}