package com.movieapp.cwe.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.movieapp.cwe.movieapp.model.MovieItem;

public class MovieDetailFragment extends Fragment {
	private MovieItem item;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		item = (MovieItem) getArguments().getParcelable("ITEM");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item_detail, 
				container, false);
		TextView movieName = (TextView) view.findViewById(R.id.movieName);
		TextView movieYear = (TextView) view.findViewById(R.id.movieYear);
		TextView movierating = (TextView) view.findViewById(R.id.ratingText);
		TextView movieCast = (TextView) view.findViewById(R.id.textViewCast);
		ListView castItems = (ListView) view.findViewById(R.id.listView);
		if(item.getCast() != null && item.getCast().size() > 0) {
			movieCast.setVisibility(View.VISIBLE);
			ArrayAdapter<String> adapterItems = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_activated_1, item.getCast());
			castItems.setAdapter(adapterItems);
		}

		RatingBar movieRating = (RatingBar) view.findViewById(R.id.ratingBar);
		movieName.setText(item.getName());
		movieYear.setText(item.getYear());
		float rating= ((float)item.getScore()/2);
		movieRating.setRating(rating);
		movierating.setText(item.getScore()+"");
		return view;
	}
    
    public static MovieDetailFragment newInstance(MovieItem item) {
    	MovieDetailFragment fragmentDemo = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("ITEM", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}
