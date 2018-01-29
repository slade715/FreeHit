package com.debut.ellipsis.freehit.Matches.LiveMatches;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity.MY_PREFS_NAME;
import static com.debut.ellipsis.freehit.MainActivity.key;


public class LiveMatchCard extends Fragment {

    ProgressBar mProgressBar;
    LiveMatchCardAdapter mAdapter;
    Button NoConnectionButton;
    TextView NoLiveMatchesText;
    TextView NoConnectionText;
    Button NoLiveMatchesButton;
    View no_internet_connection;
    View No_live_matches;
    RecyclerView recyclerView;
    LinearLayoutManager mLinearLayoutManager;

    public LiveMatchCard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {


        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        View  rootView = inflater.inflate(R.layout.fragment_matches_live, container, false);

        Boolean AutoRereshState = prefs.getBoolean("auto_refresh", false);

        View viewProgress = rootView.findViewById(R.id.progress);
        mProgressBar = viewProgress.findViewById(R.id.progress_bar);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = rootView.findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        final PullRefreshLayout refreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);

        no_internet_connection = rootView.findViewById(R.id.Unavailable_connection);

        NoConnectionText = no_internet_connection.findViewById(R.id.no_internet_connection_text);
        NoConnectionButton = no_internet_connection.findViewById(R.id.no_internet_refresh_button);

        No_live_matches = rootView.findViewById(R.id.No_Live_Matches);

        NoLiveMatchesText = No_live_matches.findViewById(R.id.empty_view);
        NoLiveMatchesButton = No_live_matches.findViewById(R.id.No_Live_Matches_button);


        liveCall();

        if (AutoRereshState) {
            //Auto Refresh after every 60 seconds
            final Handler refreshHandler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    liveCall();
                    refreshHandler.postDelayed(this, 60 * 1000);
                }
            };
            refreshHandler.postDelayed(runnable, 60 * 1000);
        }

        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                refreshLayout.setRefreshing(true);
                liveCall();
                refreshLayout.setRefreshing(false);
            }

        });


        return rootView;
    }

    void liveCall() {
        Call<LiveMatchCardItem> call = MainActivity.apiInterface.doGetLiveMatchResources(key);
        call.enqueue(new Callback<LiveMatchCardItem>() {
            @Override
            public void onResponse(Call<LiveMatchCardItem> call, Response<LiveMatchCardItem> response) {
                if (response.isSuccessful()) {
                    no_internet_connection.setVisibility(View.INVISIBLE);
                    List<LiveMatchCardItem> LiveMatches = response.body().getResults();
                    mProgressBar.setVisibility(View.INVISIBLE);

                    if (getActivity() != null) {
                        if (LiveMatches.size() == 0) {
                            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                                NoLiveMatchesText.setTextColor(Color.WHITE);
                                NoLiveMatchesButton.setBackgroundResource(R.drawable.button_shape_dark);
                                NoLiveMatchesButton.setTextColor(Color.BLACK);
                            }
                            No_live_matches.setVisibility(View.VISIBLE);
                            NoLiveMatchesButton.setOnClickListener(new View.OnClickListener() {

                                public void onClick(View v) {

                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.detach(LiveMatchCard.this).attach(LiveMatchCard.this).commit();
                                }
                            });

                        }
                        mAdapter = new LiveMatchCardAdapter(LiveMatches,getActivity());
                        recyclerView.setAdapter(mAdapter);
                    }
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    no_internet_connection.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    NoConnectionText.setText(R.string.server_issues);
                    NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(LiveMatchCard.this).attach(LiveMatchCard.this).commit();

                        }
                    });
                    call.cancel();

                }
            }

            @Override
            public void onFailure(Call<LiveMatchCardItem> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
                no_internet_connection.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(LiveMatchCard.this).attach(LiveMatchCard.this).commit();

                    }
                });
                call.cancel();
            }
        });

    }

}
