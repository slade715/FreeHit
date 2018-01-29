package com.debut.ellipsis.freehit.Matches.PastMatches;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.Matches.ScoreCard.HeadToHeadFragment;
import com.debut.ellipsis.freehit.Matches.ScoreCard.InfoFragment;
import com.debut.ellipsis.freehit.Matches.ScoreCard.ScoreCardFragment;
import com.debut.ellipsis.freehit.Matches.ScoreCard.ScoreCardItem;
import com.debut.ellipsis.freehit.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.debut.ellipsis.freehit.MainActivity.key;

public class PastMatchScoreCard extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView no_data_no_connection;
    ProgressBar mProgressBar;
    public static List<String> commentaryItems;
    public static List<ScoreCardItem> scoreCardItems;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_matches_match_scorecard);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

        String match_name = getIntent().getStringExtra("match_name");
        final String match_id = getIntent().getStringExtra("match_id");

        setTitle(match_name);

        View viewToolbarTabs = findViewById(R.id.toolbar_tabs_matches_scorecard);

        toolbar = viewToolbarTabs.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View viewViewPager = findViewById(R.id.scorecard_viewpager);

        View viewProgress = findViewById(R.id.progress);

        mProgressBar = viewProgress.findViewById(R.id.progress_bar);

        no_data_no_connection = findViewById(R.id.no_data_no_connection);

        viewPager = viewViewPager.findViewById(R.id.viewpager);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            viewPager.setBackgroundColor(getResources().getColor(R.color.night_background));
            no_data_no_connection.setTextColor(getResources().getColor(R.color.tw__composer_white));
        }

        Call<ScoreCardItem> call = MainActivity.apiInterface.doGetMatchScoreCard(match_id, key);
        call.enqueue(new Callback<ScoreCardItem>() {
            @Override
            public void onResponse(Call<ScoreCardItem> call, Response<ScoreCardItem> response) {
                if (response.isSuccessful()) {
                    no_data_no_connection.setVisibility(View.INVISIBLE);
                    scoreCardItems = response.body().getResults();
                    if (scoreCardItems.get(2).getH2h() == null || scoreCardItems.get(1).getInfo() == null || scoreCardItems.get(0).getScorecard() == null) {
                        Toast.makeText(getBaseContext(), "Redirecting you to faster different server", Toast.LENGTH_SHORT).show();
                        Call<ScoreCardItem> call1 = MainActivity.apiInterface.doGetMatchScoreCardCache(match_id, "selfcall", key);
                        call1.enqueue(new Callback<ScoreCardItem>() {
                            @Override
                            public void onResponse(Call<ScoreCardItem> call, Response<ScoreCardItem> response) {
                                if (response.isSuccessful()) {
                                    no_data_no_connection.setVisibility(View.INVISIBLE);
                                    scoreCardItems = response.body().getResults();
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    setupViewPager(viewPager);
                                    viewPager.setOffscreenPageLimit(3);
                                } else {
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    no_data_no_connection.setText(R.string.server_issues);
                                    Toast.makeText(getBaseContext(), R.string.server_issues, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ScoreCardItem> call, Throwable t) {
                                call.cancel();
                                mProgressBar.setVisibility(View.GONE);
                                no_data_no_connection.setText(R.string.no_internet_connection);
                                Toast.makeText(getBaseContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        setupViewPager(viewPager);
                        viewPager.setCurrentItem(1);
                        viewPager.setOffscreenPageLimit(3);
                    }

                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    no_data_no_connection.setText(R.string.server_issues);
                    Toast.makeText(getBaseContext(), R.string.server_issues, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ScoreCardItem> call, Throwable t) {
                call.cancel();
                mProgressBar.setVisibility(View.GONE);
                no_data_no_connection.setText(R.string.no_internet_connection);
                Toast.makeText(getBaseContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });


        tabLayout = viewToolbarTabs.findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(0, R.anim.exit_to_right);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        PastMatchScoreCard.super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_to_right);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new InfoFragment(), "INFO");
        adapter.addFrag(new ScoreCardFragment(), "SCORE CARD");
        adapter.addFrag(new HeadToHeadFragment(), "HEAD-TO-HEAD");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            if (mFragmentTitleList.get(0).equals("INFO")) {
                Bundle bundle = new Bundle();
                bundle.putString("fragment_name", "PAST");
                fragment.setArguments(bundle);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static List<ScoreCardItem> getQList() {
        return scoreCardItems;
    }

}