package com.movieapp.cwe.movieapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.movieapp.cwe.movieapp.model.MovieItem;
import com.movieapp.cwe.movieapp.network.WSClient;
import com.movieapp.cwe.movieapp.util.Constant;

import java.util.ArrayList;

public class MovieAddActivity extends AppCompatActivity implements CustomDialogFragment.DialogClickListener{

    private ArrayList<String> genreItems;
    private FrameLayout viewLayout;
    private ProgressBar progressBar;
    private MovieItem moveiToPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_add);
        viewLayout = (FrameLayout)findViewById(R.id.addMovieContainer);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        if(savedInstanceState == null) {
            GetMovieGenreTypeTask task = new GetMovieGenreTypeTask();
            task.execute();
        } else{
            genreItems = savedInstanceState.getStringArrayList("GENRELIST");
            createFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_add, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("GENRELIST", genreItems);
//        outState.putString("NAME", movieName.getText().toString());
//        outState.putString("YEAR", movieYear.getText().toString());
//        outState.putInt("FICTION", fiction.getSelectedItemPosition());
//        outState.putInt("GENRE", genre.getSelectedItemPosition());
//        outState.putInt("SCORE", score.getSelectedItemPosition());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                Fragment fragment = getFragmentManager().findFragmentById(R.id.addMovieContainer);
                if (fragment instanceof MovieAddFragment) {
                    moveiToPost = ((MovieAddFragment) fragment).collectDatatoPost();
                    if (moveiToPost != null)
                        validateData();
                }
                return true;
            case R.id.action_cancle:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void postData(){
        PostDataTask task = new PostDataTask();
        task.execute();
    }

    public void validateData(){
        if(moveiToPost.getName().equalsIgnoreCase("")){
            Toast.makeText(this, "Movie Name can't be empty", Toast.LENGTH_SHORT).show();
        } else if(moveiToPost.getYear().equalsIgnoreCase("")){
            Toast.makeText(this, "Movie Year can't be empty", Toast.LENGTH_SHORT).show();
        } else if(moveiToPost.getCast().size() <= 0){
            showDialog();
        } else {
            postData();
        }
    }

    private void showDialog() {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onOkClick() {
        postData();
    }

    @Override
    public void onCancleClick() {

    }

    private class PostDataTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            viewLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            WSClient.postData(Constant.BASE_URL,moveiToPost);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MovieAddActivity.this,"Data Succsesfully Uploaded", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private class GetMovieGenreTypeTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            genreItems = WSClient.getGenreList(Constant.BASE_URL + Constant.GENRE);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            createFragment();
        }
    }

    private void createFragment(){
        viewLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Bundle args = new Bundle();
        args.putStringArrayList("GENRELIST", genreItems);
        MovieAddFragment fragmentAdd = MovieAddFragment.newInstance(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.addMovieContainer, fragmentAdd);
        ft.commit();
    }
}
