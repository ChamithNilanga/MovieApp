package com.movieapp.cwe.movieapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.movieapp.cwe.movieapp.model.MovieItem;

public class MovieListFragment extends Fragment {
	private ArrayAdapter<MovieItem> adapterItems;
	private ListView movieListView;
	private ArrayList<MovieItem> movieItems;

	private OnItemSelectedListener listener;

	public interface OnItemSelectedListener {
		public void onItemSelected(MovieItem i);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnItemSelectedListener) {
			listener = (OnItemSelectedListener) context;
		} else {
			throw new ClassCastException(context.toString()
					+ " must implement MovieListFragment.OnItemSelectedListener");
		}
	}

	@Override
	public void onDetach() {
		listener = null;
		super.onDetach();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		movieItems = getArguments().getParcelableArrayList("MOVIELIST");

		adapterItems = new ArrayAdapter<MovieItem>(getActivity(),
				android.R.layout.simple_list_item_activated_1, movieItems);
		View view = inflater.inflate(R.layout.fragment_items_list, container,
				false);
		movieListView = (ListView) view.findViewById(R.id.lvItems);
		setActivateOnItemClick(true);
		movieListView.setAdapter(adapterItems);
		movieListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View item, int position,
					long rowId) {
				MovieItem i = adapterItems.getItem(position);
				listener.onItemSelected(i);
			}
		});
		return view;
	}

	private void setActivateOnItemClick(boolean activateOnItemClick) {
		movieListView.setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	public static MovieListFragment newInstance(Bundle bundle) {
		MovieListFragment fragmentlist = new MovieListFragment();
		fragmentlist.setArguments(bundle);
		return fragmentlist;
	}
}
