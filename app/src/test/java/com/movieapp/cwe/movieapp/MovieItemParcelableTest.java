package com.movieapp.cwe.movieapp;

import android.os.Parcel;

import com.movieapp.cwe.movieapp.model.MovieItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


import java.util.ArrayList;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk  = 18)
public class MovieItemParcelableTest {

    @Test
    public void testShouldSendAndReceiveName() {
        //Arrange
        final String expected = "name";
        final MovieItem entity = createDefaultEntity();
        entity.setName(expected);
        //Act
        MovieItem actual = sendAndReceive(entity);
        //Assert
        assertThat(actual.getName(), is(equalTo(expected)));
    }

    private MovieItem sendAndReceive(MovieItem entity) {
        Parcel p = ParcelWriter.writeParcel(entity);
        return MovieItem.CREATOR.createFromParcel(p);
    }

    private MovieItem createDefaultEntity() {
        MovieItem entity = new MovieItem();
        entity.setName("movie_name");
        entity.setYear("year");
        entity.setFiction(true);
        entity.setGenre("movie_genre");
        entity.setScore(5);
        entity.setCast(new ArrayList<String>());
        return entity;
    }
}
