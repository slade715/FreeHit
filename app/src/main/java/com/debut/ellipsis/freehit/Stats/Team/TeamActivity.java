package com.debut.ellipsis.freehit.Stats.Team;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
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
import android.view.Window;
import android.view.WindowManager;

import com.debut.ellipsis.freehit.R;
import com.debut.ellipsis.freehit.Stats.TeamSeriesMatchesFragment;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity {

    public static int Team = 0;
    public static String favTeam = null;
    public static String tempTeamName = null;
    public static String Team_Main = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        setContentView(R.layout.fragment_stats_team_activity);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent i = getIntent();
        Team = i.getIntExtra("CountryName", 0);
        favTeam = i.getStringExtra("fav_country");
        Team_Main = i.getStringExtra("CountryName_main");

        if (Team_Main == null) {
            if (Team == 0) {
                tempTeamName = favTeam;
            } else {
                tempTeamName = this.getApplicationContext().getString(Team);
            }
        } else {
            tempTeamName = Team_Main;
        }
        View viewToolbarTabs = findViewById(R.id.team_toolbar_tabs);

        Toolbar toolbar = viewToolbarTabs.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(tempTeamName);

        View viewTeamPager = findViewById(R.id.teams_viewpager);

        ViewPager viewPager = viewTeamPager.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        viewPager.setOffscreenPageLimit(3);

        TabLayout tabLayout = viewToolbarTabs.findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.dark));
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
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
        TeamActivity.super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_to_right);

    }

    private void setupViewPager(ViewPager viewPager) {
        TeamActivity.ViewPagerAdapter adapter = new TeamActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TeamPlayers(), "PLAYERS");
        adapter.addFrag(new TeamSeriesMatchesFragment(), "SCHEDULE");
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

            if (mFragmentTitleList.size() == 2) {
                if (mFragmentTitleList.get(1).equals("SCHEDULE")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("fragment_name", "TEAM SCHEDULE");
                    fragment.setArguments(bundle);
                }
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
