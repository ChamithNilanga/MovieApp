package com.movieapp.cwe.movieapp.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.movieapp.cwe.movieapp.model.MovieItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WSClient {

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is),
				8192);
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private static String getWSResult(String myurl) throws IOException {
		InputStream is = null;
		String contentAsString = null;

		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			int response = conn.getResponseCode();
			Log.d("WSClient", "The response is: " + response);
			is = conn.getInputStream();
			contentAsString = convertStreamToString(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return contentAsString;
	}

	public static ArrayList<MovieItem> getMovieList(String url) {
		ArrayList<MovieItem> movieItems = new ArrayList<MovieItem>();
		Gson gson = new Gson();
		try {
			Type listType = new TypeToken<List<MovieItem>>() {}.getType();
			movieItems = gson.fromJson(getWSResult(url), listType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return movieItems;
	}

	public static ArrayList<String> getGenreList(String url) {
		ArrayList<String> genreItems = new ArrayList<String>();
		Gson gson = new Gson();
		try {
			Type listType = new TypeToken<List<String>>() {}.getType();
			genreItems = gson.fromJson(getWSResult(url), listType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return genreItems;
	}

	public static void postData(String myurl, MovieItem movieToPost){
		Gson gson = new Gson();
		String json = gson.toJson(movieToPost);

		HttpURLConnection con = null;
		try {
			URL url = new URL(myurl);
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(json);
			wr.flush();
			StringBuilder sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if(HttpResult == HttpURLConnection.HTTP_OK){
				String inputStream = convertStreamToString(con.getInputStream());
				System.out.println(inputStream);

			}else{
				System.out.println(con.getResponseMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
