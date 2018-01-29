package com.debut.ellipsis.freehit.News;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.debut.ellipsis.freehit.MainActivity.key;


public class NewsFragment extends Fragment {

    ProgressBar mProgressBar;
    Button NoConnectionButton;
    TextView NoConnection;
    TextView NoNewsText;
    Button NoNewsButton;
    ImageView NoConnectionImage;
    FloatingActionButton fab;
    LinearLayoutManager mLinearLayoutManager;
    View no_internet_connection;
    RecyclerView recyclerView;
    View No_news;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        View viewRecycler = rootView.findViewById(R.id.news_list);

        View viewFAB = rootView.findViewById(R.id.fab);
        fab = viewFAB.findViewById(R.id.common_fab);
        fab.hide();
        fab.setImageResource(R.drawable.arrow_up_vector);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = viewRecycler.findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        no_internet_connection = rootView.findViewById(R.id.Unavailable_connection);
        NoConnectionButton = no_internet_connection.findViewById(R.id.no_internet_refresh_button);
        NoConnection = no_internet_connection.findViewById(R.id.no_internet_connection_text);
        NoConnectionImage = no_internet_connection.findViewById(R.id.no_internet_connection);

        No_news = rootView.findViewById(R.id.No_news);
        NoNewsText = No_news.findViewById(R.id.empty_view);
        NoNewsButton = No_news.findViewById(R.id.No_Live_Matches_button);

        View viewProgress = rootView.findViewById(R.id.progress);
        mProgressBar = viewProgress.findViewById(R.id.progress_bar);

        final SwipeRefreshLayout refLayout = viewRecycler.findViewById(R.id.refresh_layout);

        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                recyclerView.setBackgroundColor(getResources().getColor(R.color.match_pager));
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                refLayout.setColorSchemeColors(Color.BLACK);
                NoConnectionButton.setTextColor(Color.BLACK);
                NoConnection.setTextColor(Color.WHITE);
                NoConnectionImage.setColorFilter(Color.WHITE);
                break;
            default:
                refLayout.setColorSchemeResources(R.color.orange);
                fab.setColorFilter(Color.WHITE);
                break;
        }

        NewsCall();

        refLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                           @Override
                                           public void onRefresh() {
                                               // Checking if connected or not on refresh
                                               refLayout.setRefreshing(true);
                                               fab.hide();
                                               NewsCall();
                                               refLayout.setRefreshing(false);
                                           }
                                       }
        );

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (mLinearLayoutManager.findFirstVisibleItemPosition() == 0) {
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
                    mLinearLayoutManager.smoothScrollToPosition(recyclerView, null, 0);
                }
            }
        });


        return rootView;
    }

    void NewsCall() {
        Call<NewsItem> call = MainActivity.apiInterface.doGetNewsListResources(key);
        call.enqueue(new Callback<NewsItem>() {
            @Override
            public void onResponse(Call<NewsItem> call, Response<NewsItem> response) {
                if (response.isSuccessful()) {
                    List<NewsItem> news = response.body().getResults();
                    if (news.size() == 0) {
                        No_news.setVisibility(View.VISIBLE);
                        NoNewsText.setText(R.string.EmptyNews);
                        NoNewsButton.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {

                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(NewsFragment.this).attach(NewsFragment.this).commit();
                            }
                        });
                    }
                    if (getActivity() != null) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        no_internet_connection.setVisibility(View.INVISIBLE);
                        recyclerView.setAdapter(new NewsItemAdapter(news, R.layout.fragment_news_list_item, getContext()));
                    }
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    no_internet_connection.setVisibility(View.VISIBLE);
                    NoConnection.setText(R.string.server_issues);
                    NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(NewsFragment.this).attach(NewsFragment.this).commit();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<NewsItem> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                no_internet_connection.setVisibility(View.VISIBLE);
                NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(NewsFragment.this).attach(NewsFragment.this).commit();

                    }
                });

                call.cancel();
            }
        });
    }

}