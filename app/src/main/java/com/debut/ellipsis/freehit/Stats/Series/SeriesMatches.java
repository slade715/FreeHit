package com.debut.ellipsis.freehit.Stats.Series;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.Matches.PastMatches.PastMatchCardItem;
import com.debut.ellipsis.freehit.Matches.PastMatches.PastMatchesListAdapter;
import com.debut.ellipsis.freehit.Matches.UpcomingMatches.UpcomingMatchCardItem;
import com.debut.ellipsis.freehit.Matches.UpcomingMatches.UpcomingMatchListAdapter;
import com.debut.ellipsis.freehit.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.debut.ellipsis.freehit.MainActivity.key;

public class SeriesMatches extends Fragment {

    public SwipeRefreshLayout refresh_layout;
    private ProgressBar mProgressBar;
    private RecyclerView rv;
    View viewEmpty;
    TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stats_team_complete_match_list, container, false);
        String fragment_name = getArguments().getString("fragment_name");


        View viewFAB = rootView.findViewById(R.id.fab);
        FloatingActionButton fab = viewFAB.findViewById(R.id.common_fab);
        fab.setVisibility(View.GONE);

        Intent i = getActivity().getIntent();
        SeriesActivity.date = i.getStringExtra("date");
        SeriesActivity.Teams = i.getStringExtra("Teams");

        final View viewProgress = rootView.findViewById(R.id.progress);
        mProgressBar = viewProgress.findViewById(R.id.progress_bar);

        rv = rootView.findViewById(R.id.recycler_list);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        refresh_layout = rootView.findViewById(R.id.refresh_layout);

        viewEmpty = rootView.findViewById(R.id.empty);
        emptyView = viewEmpty.findViewById(R.id.empty_view);

        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                rv.setBackgroundColor(getResources().getColor(R.color.night_background));
                refresh_layout.setColorSchemeColors(Color.BLACK);
                break;
            default:
                refresh_layout.setColorSchemeResources(R.color.orange);
                fab.setColorFilter(Color.WHITE);
                break;
        }

        switch (fragment_name) {
            case "UPCOMING":
                UpcomingSeriesMatches();
                refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                        @Override
                                                        public void onRefresh() {
                                                            // Checking if connected or not on refresh
                                                            refresh_layout.setRefreshing(true);
                                                            UpcomingSeriesMatches();
                                                            refresh_layout.setRefreshing(false);
                                                        }
                                                    }
                );

                break;
        }
        if (fragment_name.equals("PAST")) {
            PastSeriesMatches();
            refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                    @Override
                                                    public void onRefresh() {
                                                        // Checking if connected or not on refresh
                                                        refresh_layout.setRefreshing(true);

                                                        PastSeriesMatches();
                                                        refresh_layout.setRefreshing(false);
                                                    }
                                                }
            );
        }

        return rootView;
    }

    void PastSeriesMatches() {
        Call<PastMatchCardItem> call = MainActivity.apiInterface.doGetPastSeriesMatches(SeriesActivity.Teams, SeriesActivity.date,key);
        call.enqueue(new Callback<PastMatchCardItem>() {
            @Override
            public void onResponse(Call<PastMatchCardItem> call, Response<PastMatchCardItem> response) {
                if (response.isSuccessful()) {
                    List<PastMatchCardItem> pastMatchesList = response.body().getResults();
                    mProgressBar.setVisibility(View.GONE);
                    if (pastMatchesList.size() == 0) {
                        emptyView.setText(R.string.EmptyMatches);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    rv.setAdapter(new PastMatchesListAdapter(pastMatchesList, getContext()));
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast toast = Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<PastMatchCardItem> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Toast toast = Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT);
                toast.show();
                call.cancel();
            }
        });
    }

    void UpcomingSeriesMatches() {
        Call<UpcomingMatchCardItem> call = MainActivity.apiInterface.doGetUpComingSeriesMatches(SeriesActivity.Teams, SeriesActivity.date,key);
        call.enqueue(new Callback<UpcomingMatchCardItem>() {
            @Override
            public void onResponse(Call<UpcomingMatchCardItem> call, Response<UpcomingMatchCardItem> response) {
                if (response.isSuccessful()) {
                    List<UpcomingMatchCardItem> upcomingMatchesList = response.body().getResults();
                    mProgressBar.setVisibility(View.GONE);
                    if (upcomingMatchesList.size() == 0) {
                        emptyView.setText(R.string.EmptyMatches);
                        emptyView.setVisibility(View.VISIBLE);
                    }

                    rv.setAdapter(new UpcomingMatchListAdapter(getContext(), upcomingMatchesList));
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast toast = Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<UpcomingMatchCardItem> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
                emptyView.setVisibility(View.INVISIBLE);
                Toast toast = Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT);
                toast.show();
                call.cancel();
            }
        });
    }
}

