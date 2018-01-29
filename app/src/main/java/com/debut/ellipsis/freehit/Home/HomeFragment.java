package com.debut.ellipsis.freehit.Home;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.News.NewsItem;
import com.debut.ellipsis.freehit.R;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity.MY_PREFS_NAME;
import static com.debut.ellipsis.freehit.MainActivity.key;

public class HomeFragment extends Fragment {
    ProgressBar mProgressBar;
    PageIndicatorView indicator;
    RecentMatchAdapter mAdapter;
    ViewPager vp;
    Button NoConnectionButton;
    TextView NoConnectionText;
    View no_internet_connection;
    View common_match_cards;
    ShimmerRecyclerView recyclerView;
    LinearLayoutManager mLinearLayoutManager;
    String CountryName;
    SharedPreferences prefs;
    View No_Recent_matches_news;
    TextView NoRecentMatchesText;
    TextView NoRecentMatchesButton;
    TextView home_news_heading;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        CountryName = prefs.getString("country_name", "india");
        Boolean AutoRereshState = prefs.getBoolean("auto_refresh", false);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        View viewProgress = rootView.findViewById(R.id.progress);
        mProgressBar = viewProgress.findViewById(R.id.progress_bar);

        common_match_cards = rootView.findViewById(R.id.common_match_cards);

        vp = common_match_cards.findViewById(R.id.viewpager);

        indicator = common_match_cards.findViewById(R.id.indicator);
        final PullRefreshLayout refreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);

        no_internet_connection = rootView.findViewById(R.id.Unavailable_connection);

        NoConnectionText = no_internet_connection.findViewById(R.id.no_internet_connection_text);
        NoConnectionButton = no_internet_connection.findViewById(R.id.no_internet_refresh_button);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = rootView.findViewById(R.id.fav_team_news_list);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);

        No_Recent_matches_news = rootView.findViewById(R.id.No_recent_Matches_News);

        NoRecentMatchesText = No_Recent_matches_news.findViewById(R.id.empty_view);
        NoRecentMatchesButton = No_Recent_matches_news.findViewById(R.id.No_Live_Matches_button);

        home_news_heading = rootView.findViewById(R.id.home_news);
        home_news_heading.setVisibility(View.VISIBLE);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            NoRecentMatchesButton.setBackgroundResource(R.drawable.button_shape_dark);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            homeCall();
            home_news_heading.setText("NEWS ABOUT TEAM " + CountryName);
            if (AutoRereshState) {
                //Auto Refresh after every 60 seconds
                final Handler refreshHandler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        homeMatchesCall();
                        refreshHandler.postDelayed(this, 60 * 1000);
                    }
                };
                refreshHandler.postDelayed(runnable, 60 * 1000);
            }
        } else {
            home_news_heading.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            common_match_cards.setVisibility(View.GONE);
            no_internet_connection.setVisibility(View.VISIBLE);
            NoConnectionText.setText(R.string.no_internet_connection);
            NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();

                }
            });
        }

        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                                               @Override
                                               public void onRefresh() {
                                                   // Checking if connected or not on refresh
                                                   refreshLayout.setRefreshing(true);
                                                   homeCall();
                                                   refreshLayout.setRefreshing(false);
                                               }
                                           }
        );

        return rootView;
    }

    private void IndicatorConfig() {
        indicator.setVisibility(View.VISIBLE);
        indicator.setAnimationType(AnimationType.WORM);
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                indicator.setUnselectedColor(Color.parseColor("#5e5e6a"));
                indicator.setSelectedColor(Color.parseColor("#0e0d19"));
                break;
            default:
                indicator.setUnselectedColor(getResources().getColor(R.color.colorPrimaryLight));
                indicator.setSelectedColor(getResources().getColor(R.color.colorPrimary));
                break;
        }
        indicator.setInteractiveAnimation(true);
        indicator.setAnimationDuration(500);

    }

    void homeMatchesCall() {
        Call<RecentItem> call = MainActivity.apiInterface.doGetRecentResources(key);
        call.enqueue(new Callback<RecentItem>() {
            @Override
            public void onResponse(Call<RecentItem> call, Response<RecentItem> response) {
                if (response.isSuccessful()) {
                    common_match_cards.setVisibility(View.VISIBLE);
                    no_internet_connection.setVisibility(View.INVISIBLE);
                    indicator.setVisibility(View.VISIBLE);
                    indicator.setViewPager(vp);
                    List<RecentItem> RecentMatches = response.body().getResults();
                    mProgressBar.setVisibility(View.INVISIBLE);
                    if (getActivity() != null) {
                        if (RecentMatches.size() == 0) {
                            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                                NoRecentMatchesText.setTextColor(Color.WHITE);
                                NoRecentMatchesButton.setBackgroundResource(R.drawable.button_shape_dark);
                                NoRecentMatchesButton.setTextColor(Color.BLACK);
                            }
                            No_Recent_matches_news.setVisibility(View.VISIBLE);
                            NoRecentMatchesButton.setOnClickListener(new View.OnClickListener() {

                                public void onClick(View v) {

                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();
                                }
                            });

                        }
                        mAdapter = new RecentMatchAdapter(getActivity(), RecentMatches);
                        indicator.setViewPager(vp);
                        indicator.setCount(mAdapter.getCount());
                        IndicatorConfig();
                        vp.setAdapter(mAdapter);
                    }
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    no_internet_connection.setVisibility(View.VISIBLE);
                    common_match_cards.setVisibility(View.INVISIBLE);
                    NoConnectionText.setText(R.string.server_issues);
                    NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();

                        }
                    });
                    call.cancel();

                }
            }

            @Override
            public void onFailure(Call<RecentItem> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
                home_news_heading.setVisibility(View.INVISIBLE);
                no_internet_connection.setVisibility(View.VISIBLE);
                common_match_cards.setVisibility(View.INVISIBLE);
                NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();

                    }
                });
                call.cancel();
            }
        });

    }

    void homeCall() {
        Call<RecentItem> call = MainActivity.apiInterface.doGetRecentResources(key);
        call.enqueue(new Callback<RecentItem>() {
            @Override
            public void onResponse(Call<RecentItem> call, Response<RecentItem> response) {
                if (response.isSuccessful()) {
                    common_match_cards.setVisibility(View.VISIBLE);
                    no_internet_connection.setVisibility(View.INVISIBLE);
                    indicator.setVisibility(View.VISIBLE);
                    indicator.setViewPager(vp);
                    List<RecentItem> RecentMatches = response.body().getResults();
                    FavNewsCall();
                    mProgressBar.setVisibility(View.INVISIBLE);
                    if (getActivity() != null) {
                        if (RecentMatches.size() == 0) {
                            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                                NoRecentMatchesText.setTextColor(Color.WHITE);
                                NoRecentMatchesButton.setBackgroundResource(R.drawable.button_shape_dark);
                                NoRecentMatchesButton.setTextColor(Color.BLACK);
                            }
                            No_Recent_matches_news.setVisibility(View.VISIBLE);
                            NoRecentMatchesButton.setOnClickListener(new View.OnClickListener() {

                                public void onClick(View v) {

                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();
                                }
                            });

                        }
                        mAdapter = new RecentMatchAdapter(getActivity(), RecentMatches);
                        indicator.setViewPager(vp);
                        indicator.setCount(mAdapter.getCount());
                        IndicatorConfig();
                        vp.setAdapter(mAdapter);
                    }
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    no_internet_connection.setVisibility(View.VISIBLE);
                    common_match_cards.setVisibility(View.INVISIBLE);
                    NoConnectionText.setText(R.string.server_issues);
                    NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();

                        }
                    });
                    call.cancel();

                }
            }

            @Override
            public void onFailure(Call<RecentItem> call, Throwable t) {
                home_news_heading.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
                no_internet_connection.setVisibility(View.VISIBLE);
                common_match_cards.setVisibility(View.INVISIBLE);
                NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();

                    }
                });
                call.cancel();
            }
        });

    }

    void FavNewsCall() {
        Call<NewsItem> call = MainActivity.apiInterface.doGetHomeFavNews(8, CountryName.toLowerCase(), key);
        call.enqueue(new Callback<NewsItem>() {
            @Override
            public void onResponse(Call<NewsItem> call, Response<NewsItem> response) {
                if (response.isSuccessful()) {
                    List<NewsItem> news = response.body().getResults();
                    if (news.size() == 0) {
                        No_Recent_matches_news.setVisibility(View.INVISIBLE);
                        NoRecentMatchesButton.setVisibility(View.INVISIBLE);
                        home_news_heading.setText("No news about "+CountryName+" found");
                    }
                    no_internet_connection.setVisibility(View.INVISIBLE);
                    recyclerView.showShimmerAdapter();
                    recyclerView.setAdapter(new HomeNewsItemAdapter(news, R.layout.fragment_home_news_list_item, getContext()));
                }
            }

            @Override
            public void onFailure(Call<NewsItem> call, Throwable t) {
                no_internet_connection.setVisibility(View.VISIBLE);
                NoConnectionButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();

                    }
                });

                call.cancel();
            }
        });
    }
}
