package com.debut.ellipsis.freehit.Matches.PastMatches;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.Matches.MatchesList.PastMatchesActivity;
import com.debut.ellipsis.freehit.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.debut.ellipsis.freehit.MainActivity.key;

public class PastMatchCard extends Fragment {

    ProgressBar mProgressBar;
    Button NoConnectionButton;
    TextView NoConnectionText;
    TextView viewMore;
    View no_internet_connection;
    ImageView NoConnectionImage;
    RecyclerView recyclerView;
    ImageView viewMoreImage;
    LinearLayoutManager mLinearLayoutManager;

    public PastMatchCard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_matches_past_upcoming, container, false);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = rootView.findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        View viewProgress = rootView.findViewById(R.id.progress);
        mProgressBar = viewProgress.findViewById(R.id.progress_bar);

        final PullRefreshLayout refreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);


        no_internet_connection = rootView.findViewById(R.id.Unavailable_connection);

        NoConnectionText = no_internet_connection.findViewById(R.id.no_internet_connection_text);
        NoConnectionButton = no_internet_connection.findViewById(R.id.no_internet_refresh_button);
        NoConnectionImage = no_internet_connection.findViewById(R.id.no_internet_connection);

        viewMore = rootView.findViewById(R.id.click_to_view_more);
        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pastMatchesIntent = new Intent(getActivity(), PastMatchesActivity.class);
                // Start the new activity
                getActivity().startActivity(pastMatchesIntent);
            }
        });

        viewMoreImage = rootView.findViewById(R.id.viewMore_image);

        viewMore.setVisibility(View.INVISIBLE);
        viewMoreImage.setVisibility(View.INVISIBLE);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            NoConnectionButton.setTextColor(Color.BLACK);
            NoConnectionText.setTextColor(Color.WHITE);
            NoConnectionImage.setColorFilter(Color.WHITE);
        }

        PastCall();

        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                                               @Override
                                               public void onRefresh() {
                                                   // Checking if connected or not on refresh
                                                   refreshLayout.setRefreshing(true);
                                                   PastCall();
                                                   refreshLayout.setRefreshing(false);
                                               }
                                           }
        );


        return rootView;
    }

    void PastCall() {
        Call<PastMatchCardItem> call = MainActivity.apiInterface.doGetPastCardResources("6", key);
        call.enqueue(new Callback<PastMatchCardItem>() {
            @Override
            public void onResponse(Call<PastMatchCardItem> call, Response<PastMatchCardItem> response) {
                if (response.isSuccessful()) {
                    no_internet_connection.setVisibility(View.INVISIBLE);
                    List<PastMatchCardItem> pastMatchCardItems = response.body().getResults();
                    if (getActivity() != null) {
                        viewMore.setVisibility(View.VISIBLE);
                        viewMoreImage.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new PastMatchCardItemAdapter(pastMatchCardItems, getContext()));
                        mProgressBar.setVisibility(View.GONE);
                    }
                } else {
                    NoConnectionText.setText(R.string.server_issues);
                    mProgressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    viewMore.setVisibility(View.GONE);
                    viewMoreImage.setVisibility(View.GONE);
                    no_internet_connection.setVisibility(View.VISIBLE);
                    NoConnectionButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(PastMatchCard.this).attach(PastMatchCard.this).commit();

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<PastMatchCardItem> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                no_internet_connection.setVisibility(View.VISIBLE);
                viewMore.setVisibility(View.GONE);
                viewMoreImage.setVisibility(View.GONE);
                recyclerView.setVisibility(View.INVISIBLE);
                NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(PastMatchCard.this).attach(PastMatchCard.this).commit();

                    }
                });
                call.cancel();
            }
        });
    }

}
