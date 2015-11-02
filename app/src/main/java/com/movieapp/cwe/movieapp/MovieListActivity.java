package com.movieapp.cwe.movieapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.movieapp.cwe.movieapp.model.MovieItem;
import com.movieapp.cwe.movieapp.network.WSClient;
import com.movieapp.cwe.movieapp.util.Constant;

import java.util.ArrayList;


public class MovieListActivity extends AppCompatActivity implements MovieListFragment.OnItemSelectedListener {
	private boolean isTwoPane = false;
	private ArrayList<MovieItem> movieItems;
	private LinearLayout viewLayout;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		viewLayout = (LinearLayout)findViewById(R.id.fragmentLayout);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
	}

	@Override
	protected void onStart(){
		super.onStart();
		GetMovieItemsTask task = new GetMovieItemsTask();
		task.execute();
	}

	private void determinePaneLayout() {
		FrameLayout fragmentMovieDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
		if (fragmentMovieDetail != null) {
			isTwoPane = true;
			Bundle args = new Bundle();
			args.putParcelableArrayList("MOVIELIST", movieItems);
			MovieListFragment fragmentList = MovieListFragment.newInstance(args);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.flListContainer, fragmentList);
			ft.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.items, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add_movie:
				openAddMovieActivity();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void openAddMovieActivity(){
		Intent myIntent = new Intent(MovieListActivity.this, MovieAddActivity.class);
		startActivity(myIntent);
	}

	@Override
	public void onItemSelected(MovieItem item) {
		if (isTwoPane) {
			MovieDetailFragment fragmentItem = MovieDetailFragment.newInstance(item);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.flDetailContainer, fragmentItem);
			ft.commit();
		} else {
			Intent i = new Intent(this, MovieDetailActivity.class);
			i.putExtra("ITEM", item);
			startActivity(i);
		}
	}

	private class GetMovieItemsTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			viewLayout.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			movieItems = WSClient.getMovieList(Constant.BASE_URL);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			determinePaneLayout();
			viewLayout.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
		}
	}

}
