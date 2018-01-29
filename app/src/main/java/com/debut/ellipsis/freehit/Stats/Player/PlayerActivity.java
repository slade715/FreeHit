package com.debut.ellipsis.freehit.Stats.Player;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DecodeFormat;
import com.debut.ellipsis.freehit.Glide.GlideApp;
import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.R;
import com.debut.ellipsis.freehit.Stats.Player.parallaxviewpager.ParallaxFragmentPagerAdapter;
import com.debut.ellipsis.freehit.Stats.Player.parallaxviewpager.ParallaxViewPagerBaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.debut.ellipsis.freehit.MainActivity.key;

public class PlayerActivity extends ParallaxViewPagerBaseActivity {

    private ImageView mTopImage;
    private SlidingTabLayout mNavigBar;
    PlayerItem playerItem;
    ProgressBar mProgressBar;
    TextView emptyview;
    TextView name;
    TextView country;
    TextView age;
    ImageView playerImage;
    RelativeLayout player_info;
    String ImageURL;
    ImageView background_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stats_player_activity);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initValues();

        Intent i = getIntent();
        String player_name = i.getStringExtra("player_name");
        String player_url = i.getStringExtra("player_url");

        View viewToolbarTabs = findViewById(R.id.toolbar_player);

        Toolbar toolbar = viewToolbarTabs.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(player_name);

        View EmptyView = findViewById(R.id.empty);
        emptyview = EmptyView.findViewById(R.id.empty_view);

        View viewProgress = findViewById(R.id.progress);
        mProgressBar = viewProgress.findViewById(R.id.progress_bar);

        mTopImage = findViewById(R.id.background_image);
        mViewPager = findViewById(R.id.view_pager);
        mNavigBar = findViewById(R.id.navig_tab);
        mHeader = findViewById(R.id.header);

        player_info = findViewById(R.id.player_info);
        name = findViewById(R.id.Player_name_stats_info);
        country = findViewById(R.id.player_country);
        age = findViewById(R.id.Age);
        playerImage = findViewById(R.id.Player_image);

        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                mTopImage.setBackgroundColor(getResources().getColor(R.color.dark));
                mViewPager.setBackgroundColor(getResources().getColor(R.color.night_background));
                break;
            default:
                mTopImage.setBackgroundColor(getResources().getColor(R.color.primary));
                break;
        }

        if (savedInstanceState != null) {
            mTopImage.setTranslationY(savedInstanceState.getFloat(IMAGE_TRANSLATION_Y));
            mHeader.setTranslationY(savedInstanceState.getFloat(HEADER_TRANSLATION_Y));
        }

        Call<PlayerItem> call = MainActivity.apiInterface.doGetPlayerInfo(player_url, key);
        call.enqueue(new Callback<PlayerItem>() {
            @Override
            public void onResponse(Call<PlayerItem> call, Response<PlayerItem> response) {
                if (response.isSuccessful()) {
                    emptyview.setVisibility(View.INVISIBLE);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    playerItem = response.body();

                    name.setText(playerItem.getName());

                    country.setText(playerItem.getNationality());

                    age.setText(playerItem.getAge());

                    ImageURL = playerItem.getImg();

                    MainActivity.requestBuilder = GlideApp.with(getApplication()).load(ImageURL).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);

                    MainActivity.requestBuilder.into(playerImage);

                    player_info.setVisibility(View.VISIBLE);

                    setupAdapter();

                } else {
                    emptyview.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    emptyview.setText(R.string.server_issues);
                    Toast.makeText(getBaseContext(), R.string.server_issues, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlayerItem> call, Throwable t) {
                call.cancel();
                emptyview.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                emptyview.setText(R.string.server_issues);
                Toast.makeText(getBaseContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();

            }
        });

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
        PlayerActivity.super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_to_right);
    }

    @Override
    protected void initValues() {
        int tabHeight = getResources().getDimensionPixelSize(R.dimen._42sdp);
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen._225sdp);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen._225sdp);
        mMinHeaderTranslation = -mMinHeaderHeight + tabHeight;

        mNumFragments = 3;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putFloat(IMAGE_TRANSLATION_Y, mTopImage.getTranslationY());
        outState.putFloat(HEADER_TRANSLATION_Y, mHeader.getTranslationY());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void setupAdapter() {
        if (mAdapter == null) {
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mNumFragments);
        }

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mNumFragments);
        mNavigBar.setOnPageChangeListener(getViewPagerChangeListener());
        mNavigBar.setViewPager(mViewPager);

    }

    @Override
    protected void scrollHeader(int scrollY) {
        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
        mHeader.setTranslationY(translationY);
        mTopImage.setTranslationY(-translationY / 3);
    }

//    private int getActionBarHeight() {
//        if (mActionBarHeight != 0) {
//            return mActionBarHeight;
//        }
//
//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
//            getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
//        } else {
//            getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
//        }
//
//        mActionBarHeight = TypedValue.complexToDimensionPixelSize(
//                mTypedValue.data, getResources().getDisplayMetrics());
//
//        return mActionBarHeight;
//    }

    private static class ViewPagerAdapter extends ParallaxFragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm, int numFragments) {
            super(fm, numFragments);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = PlayerInfo.newInstance(0);
                    break;

                case 1:
                    fragment = PlayerBatting.newInstance(1);
                    break;

                case 2:
                    fragment = PlayerBowling.newInstance(2);
                    break;

                default:
                    throw new IllegalArgumentException("Wrong page given " + position);
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Player Info";

                case 1:
                    return "Batting";

                case 2:
                    return "Bowling";

                default:
                    throw new IllegalArgumentException("wrong position for the fragment in vehicle page");
            }
        }
    }

    public PlayerItem getQuery() {
        return playerItem;
    }
}
