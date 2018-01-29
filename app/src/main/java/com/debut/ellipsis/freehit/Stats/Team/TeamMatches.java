package com.debut.ellipsis.freehit.Stats.Team;

import android.content.Intent;
import android.content.res.ColorStateList;
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

import com.debut.ellipsis.freehit.APIInterface;
import com.debut.ellipsis.freehit.ApiClient;
import com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity;
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

public class TeamMatches extends Fragment {

    private UpcomingMatchListAdapter UpcomingMatchListAdapter;
    private PastMatchesListAdapter PastMatchListAdapter;
    private ProgressBar mProgressBar;
    private RecyclerView rv;
    public SwipeRefreshLayout refresh_layout;
    public TextView mEmptyView;
    private FloatingActionButton fab;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_stats_team_complete_match_list, container, false);
        String fragment_name = getArguments().getString("fragment_name");

        View viewFAB = rootView.findViewById(R.id.fab);
        fab = viewFAB.findViewById(R.id.common_fab);
        fab.hide();
        fab.setImageResource(R.drawable.arrow_up_vector);

        Intent i = getActivity().getIntent();
        TeamActivity.Team = i.getIntExtra("CountryName", 0);
        TeamActivity.favTeam = i.getStringExtra("fav_country");
        TeamActivity.Team_Main = i.getStringExtra("CountryName_main");

        if(TeamActivity.Team_Main==null) {
            if (TeamActivity.Team == 0) {
                TeamActivity.tempTeamName = TeamActivity.favTeam;
            } else {
                TeamActivity.tempTeamName = this.getContext().getString(TeamActivity.Team);
            }
        }
        else
        {
            TeamActivity.tempTeamName =TeamActivity.Team_Main;
        }
        TeamActivity.tempTeamName = WelcomeActivity.countryHash.getCountrySN(TeamActivity.tempTeamName.toUpperCase());

        MainActivity.apiInterface = ApiClient.getClient().create(APIInterface.class);

        View viewProgress = rootView.findViewById(R.id.progress);
        mProgressBar = viewProgress.findViewById(R.id.progress_bar);

        rv = rootView.findViewById(R.id.recycler_list);

        mLinearLayoutManager = new LinearLayoutManager(getContext());

        rv.setLayoutManager(mLinearLayoutManager);

        refresh_layout = rootView.findViewById(R.id.refresh_layout);

        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                rv.setBackgroundColor(getResources().getColor(R.color.night_background));
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                refresh_layout.setColorSchemeColors(Color.BLACK);
                break;
            default:
                refresh_layout.setColorSchemeResources(R.color.orange);
                fab.setColorFilter(Color.WHITE);
                break;
        }

        View viewEmpty = rootView.findViewById(R.id.empty);
        mEmptyView = viewEmpty.findViewById(R.id.empty_view);

        switch (fragment_name) {
            case "UPCOMING": {
                TeamUpcomingMatches();

                refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                        @Override
                                                        public void onRefresh() {
                                                            // Checking if connected or not on refresh
                                                            refresh_layout.setRefreshing(true);
                                                            TeamUpcomingMatches();
                                                            refresh_layout.setRefreshing(false);
                                                        }
                                                    }
                );
                break;
            }
            case "PAST": {
                TeamPastMatches();

                refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                        @Override
                                                        public void onRefresh() {
                                                            // Checking if connected or not on refresh
                                                            refresh_layout.setRefreshing(true);
                                                            TeamPastMatches();
                                                            refresh_layout.setRefreshing(false);
                                                        }
                                                    }
                );
                break;
            }
        }

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    fab.hide();

                } else {
                    fab.show();
                }
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int firstVisibleItemIndex = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemIndex > 0) {
                    mLinearLayoutManager.smoothScrollToPosition(rv, null, 0);
                }
            }
        });


        return rootView;
    }

    void TeamPastMatches() {
        Call<PastMatchCardItem> call = MainActivity.apiInterface.doGetPastFavTeam(TeamActivity.tempTeamName,key);
        call.enqueue(new Callback<PastMatchCardItem>() {
            @Override
            public void onResponse(Call<PastMatchCardItem> call, Response<PastMatchCardItem> response) {
                if (response.isSuccessful()) {
                    List<PastMatchCardItem> pastMatchesList = response.body().getResults();
                    mProgressBar.setVisibility(View.GONE);
                    if (pastMatchesList.size() == 0) {
                        mEmptyView.setText(R.string.EmptyMatches);
                        mEmptyView.setVisibility(View.VISIBLE);
                    }

                    PastMatchListAdapter = new PastMatchesListAdapter(pastMatchesList, getContext());
                    rv.setAdapter(PastMatchListAdapter);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mEmptyView.setText(R.string.server_issues);
                    mEmptyView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), R.string.server_issues, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PastMatchCardItem> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
                mEmptyView.setText(R.string.no_internet_connection);
                mEmptyView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }

    void TeamUpcomingMatches() {
        Call<UpcomingMatchCardItem> call = MainActivity.apiInterface.doGetUpcomingFavTeam(TeamActivity.tempTeamName,key);
        call.enqueue(new Callback<UpcomingMatchCardItem>() {
            @Override
            public void onResponse(Call<UpcomingMatchCardItem> call, Response<UpcomingMatchCardItem> response) {
                if (response.isSuccessful()) {
                    List<UpcomingMatchCardItem> upcomingMatchesList = response.body().getResults();
                    mProgressBar.setVisibility(View.GONE);
                    if (upcomingMatchesList.size() == 0) {
                        mEmptyView.setVisibility(View.VISIBLE);
                        mEmptyView.setText(R.string.EmptyMatches);
                    }

                    UpcomingMatchListAdapter = new UpcomingMatchListAdapter(getContext(), upcomingMatchesList);
                    rv.setAdapter(UpcomingMatchListAdapter);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mEmptyView.setText(R.string.server_issues);
                    mEmptyView.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), R.string.server_issues, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpcomingMatchCardItem> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
                mEmptyView.setText(R.string.no_internet_connection);
                mEmptyView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
}
