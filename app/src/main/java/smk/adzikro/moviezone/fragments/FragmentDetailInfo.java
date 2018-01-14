package smk.adzikro.moviezone.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import smk.adzikro.moviezone.R;
import smk.adzikro.moviezone.adapter.ListOtherFrom;
import smk.adzikro.moviezone.adapter.ListSimilarMovies;
import smk.adzikro.moviezone.adapter.ListTrailerMovieAdapter;
import smk.adzikro.moviezone.objek.Movie;

/**
 * Created by server on 11/15/17.
 */

public class FragmentDetailInfo extends Fragment {
    private static final String TAG ="FragmentDetailInfo" ;
    private static final String KEY ="kuncina" ;
    private Movie movie;
    ListTrailerMovieAdapter adapter;
    public static FragmentDetailInfo newInstance(Movie movie){
        FragmentDetailInfo info = new FragmentDetailInfo();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY,movie);
        info.setArguments(bundle);
        Log.e(TAG,"Waktu di create "+movie.getTitle());
        return info;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle bundle){
        super.onCreateView(inflater,parent,bundle);
        final View view = inflater.inflate(R.layout.detail_list_info,parent,false);
        if(getArguments()!=null){
            movie = getArguments().getParcelable(KEY);
        }
        Log.e(TAG,"Waktu di createView "+movie.getTitle());
        init(view);
        putValue();
        return view;
    }
    TextView tx_rate, tx_review, tx_releaseDate, tx_directed, tx_budget, tx_revenue, tx_otherFrom;
    private RecyclerView trailer, similar, otherFrom;

    private void init(View view){
        tx_rate = (TextView)view.findViewById(R.id.tx_rating);
        tx_review = (TextView)view.findViewById(R.id.tx_review);
        tx_releaseDate= (TextView)view.findViewById(R.id.tx_release_date);
        tx_directed = (TextView)view.findViewById(R.id.tx_directed);
        tx_budget = (TextView)view.findViewById(R.id.tx_budget);
        tx_revenue = (TextView)view.findViewById(R.id.tx_revenue);
        tx_otherFrom = (TextView)view.findViewById(R.id.other_from);
        trailer = (RecyclerView)view.findViewById(R.id.list_movie);
        similar = (RecyclerView)view.findViewById(R.id.list_similar);
        otherFrom = (RecyclerView)view.findViewById(R.id.list_other_from);
        trailer.setHasFixedSize(true);
        trailer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        similar.setHasFixedSize(true);
        similar.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        otherFrom.setHasFixedSize(true);
        otherFrom.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
    }

    private void putValue(){
        tx_rate.setText(movie.getRating());
        tx_review.setText(movie.getPlot());
        tx_releaseDate.setText(movie.getReleasedate());
        tx_directed.setText(movie.getDirector());
        double duit= Double.valueOf(movie.getBudget());
        String budget = NumberFormat.getCurrencyInstance(new Locale("en","US")).format(duit);
        tx_budget.setText(budget);
        double duitX= Double.valueOf(movie.getRevenue());
        String re = NumberFormat.getCurrencyInstance(new Locale("en","US")).format(duitX);
        tx_revenue.setText(re);
        adapter = new ListTrailerMovieAdapter(getContext(), movie.getmTrailer());
        trailer.setAdapter(adapter);
        if(movie.getSimilarMovies()!=null) {
            ListSimilarMovies listSimilarMovies = new ListSimilarMovies(getContext(), movie.getSimilarMovies());
            similar.setAdapter(listSimilarMovies);
        }
        tx_otherFrom.setText("Other From "+movie.getDirector());
        ListOtherFrom adap = new ListOtherFrom(getContext(), movie.getFromDirector());
        otherFrom.setAdapter(adap);
    }
}
