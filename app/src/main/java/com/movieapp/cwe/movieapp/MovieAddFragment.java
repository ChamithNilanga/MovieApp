package com.movieapp.cwe.movieapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.movieapp.cwe.movieapp.model.MovieItem;

import java.util.ArrayList;

public class MovieAddFragment extends Fragment {

    private ArrayList<String> genreItems;
    private EditText movieName;
    private EditText movieYear;
    private Spinner fiction;
    private Spinner genre;
    private Spinner score;
    private EditText cast;
    private final ArrayList<String> castitems  = new ArrayList<String>();

    public static MovieAddFragment newInstance(Bundle args) {
        MovieAddFragment fragment = new MovieAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        genreItems = getArguments().getStringArrayList("GENRELIST");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_add, container, false);
        movieName = (EditText) view.findViewById(R.id.movie_name_edit);
        movieYear = (EditText) view.findViewById(R.id.movie_year_edit);
        fiction = (Spinner) view.findViewById(R.id.fiction_spinner);
        genre = (Spinner) view.findViewById(R.id.genre_spinner);
        score = (Spinner) view.findViewById(R.id.score_spinner);
        cast = (EditText) view.findViewById(R.id.movie_cast_edit);
        Button addButton = (Button) view.findViewById(R.id.addButton);
        ListView castList = (ListView) view.findViewById(R.id.cast_list);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, genreItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genre.setAdapter(dataAdapter);

        final ArrayAdapter<String> castAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, castitems);
        castList.setAdapter(castAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(cast.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Cast can't be empty", Toast.LENGTH_SHORT).show();
                } else{
                    castitems.add(cast.getText().toString());
                    cast.setText("");
                    castAdapter.notifyDataSetChanged();
                }
            }
        });

//        if (savedInstanceState != null) {
//            movieName.setText(savedInstanceState.getString("NAME"));
//            movieYear.setText(savedInstanceState.getString("YEAR"));
//            fiction.setSelection(savedInstanceState.getInt("FICTION"));
//            genre.setSelection(savedInstanceState.getInt("GENRE"));
//            score.setSelection(savedInstanceState.getInt("SCORE"));
//        }

        return view;
    }

    public MovieItem collectDatatoPost() {
        MovieItem movieToPost = new MovieItem();

        movieToPost.setName(movieName.getText().toString());
        movieToPost.setYear(movieYear.getText().toString());
        movieToPost.setFiction(getFiction(fiction.getSelectedItem().toString()));
        movieToPost.setGenre(genre.getSelectedItem().toString());
        movieToPost.setScore(Integer.parseInt(score.getSelectedItem().toString()));
        movieToPost.setCast(castitems);
        return movieToPost;

    }

    private boolean getFiction(String fiction){
        if(fiction.equalsIgnoreCase("yes"))
            return true;
        else
            return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString("NAME", movieName.getText().toString());
//        outState.putString("YEAR", movieYear.getText().toString());
//        outState.putInt("FICTION", fiction.getSelectedItemPosition());
//        outState.putInt("GENRE", genre.getSelectedItemPosition());
//        outState.putInt("SCORE", score.getSelectedItemPosition());
    }
}
