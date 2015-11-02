package com.movieapp.cwe.movieapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.movieapp.cwe.movieapp.model.MovieItem;

public class MovieDetailActivity extends AppCompatActivity {
	MovieDetailFragment fragmentItemDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		MovieItem item = (MovieItem) getIntent().getParcelableExtra("ITEM");
		if (savedInstanceState == null) {
			fragmentItemDetail = MovieDetailFragment.newInstance(item);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.flDetailContainer, fragmentItemDetail);
			ft.commit();
		}
	}
}
