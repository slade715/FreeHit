package com.debut.ellipsis.freehit;


import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestBuilder;
import com.debut.ellipsis.freehit.About.AboutActivity;
import com.debut.ellipsis.freehit.Glide.GlideApp;
import com.debut.ellipsis.freehit.Home.HomeFragment;
import com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity;
import com.debut.ellipsis.freehit.Matches.MatchesFragment;
import com.debut.ellipsis.freehit.News.NewsFragment;
import com.debut.ellipsis.freehit.Settings.CustomSettings;
import com.debut.ellipsis.freehit.Social.SocialMainFragment;
import com.debut.ellipsis.freehit.Stats.StatsMain.StatsFragment;
import com.debut.ellipsis.freehit.Stats.Team.TeamActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.twitter.sdk.android.core.Twitter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity.MY_PREFS_NAME;
import static com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity.countryHash;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.home_vector,
            R.drawable.matches_vector,
            R.drawable.news_vector,
            R.drawable.social_vector,
            R.drawable.stats_vector,
    };
    private Boolean exit = false;
    public static RequestBuilder requestBuilder;
    public static APIInterface apiInterface;
    public static String key;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    public static String FACEBOOK_URL = "https://www.facebook.com/freehit.cricketapp";
    public static String FACEBOOK_PAGE_ID = "1934240016895387";

    public native String getNativeKey();

    static {
        System.loadLibrary("keys");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        key = new String(Base64.decode(getNativeKey(), Base64.DEFAULT));

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        Boolean NightModeState = prefs.getBoolean("switch_state", false);
        AppCompatDelegate.setDefaultNightMode(NightModeState ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }

        final String CountryName = prefs.getString("country_name", "all");
        String TeamLogoURL = WelcomeActivity.countryHash.getCountryFlag(CountryName.toUpperCase());

        FirebaseMessaging.getInstance().subscribeToTopic(countryHash.getCountrySN(CountryName.toUpperCase()));

        apiInterface = ApiClient.getClient().create(APIInterface.class);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //  If we have to define custom configuration, uncomment next line and don't forget to add consumer Key and Secret Key.
        //TwitterConfig config = new TwitterConfig.Builder(this).logger(new DefaultLogger(Log.DEBUG)).twitterAuthConfig(new TwitterAuthConfig("FREEHIT_CONSUMER_KEY","FREEHIT_CONSUMER_SECRET")).debug(true).build();

        // Initializing Twitter Kit
        Twitter.initialize(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        final String app_ver = BuildConfig.VERSION_NAME;
        final float version_name = Float.parseFloat(app_ver);

        final Dialog dialog = new Dialog(MainActivity.this, R.style.PopTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.update_dialog);

        RelativeLayout changelog_layout = dialog.findViewById(R.id.changelog_layout);
        final TextView changelog = dialog.findViewById(R.id.changelog);
        final Button Positive_button = dialog.findViewById(R.id.positiveBtn);
        final Button Negative_button = dialog.findViewById(R.id.negativeBtn);

        changelog_layout.setBackgroundColor(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? getResources().getColor(R.color.match_pager) : getResources().getColor(R.color.match_cards));


        Call<AppVersion> call = MainActivity.apiInterface.doGetAppVersion();
        call.enqueue(new Callback<AppVersion>() {
            @Override
            public void onResponse(Call<AppVersion> call, Response<AppVersion> response) {
                if (response.isSuccessful()) {
                    if (version_name < Float.parseFloat(response.body().getCurrver())) {
                        changelog.setText(response.body().getChangelog());
                        if (version_name < Float.parseFloat(response.body().minver)) {
                            dialog.setCancelable(false);
                        }
                        Positive_button.setOnClickListener(v -> {
                            String appPackageName = getPackageName();
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException exception) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        });

                        if (version_name < Float.parseFloat(response.body().minver)) {
                            Negative_button.setOnClickListener(v ->
                                    finish());
                        } else {
                            Negative_button.setOnClickListener(v ->
                                    dialog.dismiss());
                        }

                        dialog.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppVersion> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                call.cancel();

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        final View viewMainPager = findViewById(R.id.main_viewpager);

        viewPager = viewMainPager.findViewById(R.id.viewpager);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            viewPager.setBackgroundColor(getResources().getColor(R.color.night_background));
        }
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(5);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            DrawerImageLoader.init(new AbstractDrawerImageLoader() {
                @Override
                public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                    GlideApp.with(imageView.getContext()).load(uri).placeholder(R.drawable.placeholder_dark).into(imageView);
                }
            });
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            DrawerImageLoader.init(new AbstractDrawerImageLoader() {
                @Override
                public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                    GlideApp.with(imageView.getContext()).load(uri).placeholder(R.drawable.placeholder_light).into(imageView);
                }
            });
        }

        final IProfile profile = new ProfileDrawerItem().withIcon(TeamLogoURL).withIdentifier(100);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.nav_header)
                .withSelectionListEnabledForSingleProfile(false)
                .withTextColor(getResources().getColor(R.color.tw__composer_white))
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        Intent i = new Intent(getApplicationContext(), TeamActivity.class);
                        i.putExtra("CountryName_main", CountryName);
                        startActivity(i);
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .addProfiles(
                        profile
                )
                .withSavedInstance(savedInstanceState)
                .build();


        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.home_vector).withIdentifier(1).withIconTintingEnabled(true),
                        new PrimaryDrawerItem().withName("Matches").withIcon(R.drawable.matches_vector).withIdentifier(2).withIconTintingEnabled(true),
                        new PrimaryDrawerItem().withName("News").withIcon(R.drawable.news_vector).withIdentifier(3).withIconTintingEnabled(true),
                        new PrimaryDrawerItem().withName("Social").withIcon(R.drawable.social_vector).withIdentifier(4).withIconTintingEnabled(true),
                        new PrimaryDrawerItem().withName("Stats").withIcon(R.drawable.stats_vector).withIdentifier(5).withIconTintingEnabled(true),

                        new ExpandableDrawerItem().withName("Follow us").withIcon(R.drawable.heart_vector).withIdentifier(10).withIconTintingEnabled(true).withSubItems(
                                new SecondaryDrawerItem().withName(R.string.fb_share).withLevel(2).withIcon(R.drawable.facebook_vector).withIdentifier(2002).withIconTintingEnabled(true),
                                new SecondaryDrawerItem().withName(R.string.insta_share).withLevel(2).withIcon(R.drawable.instagram_vector).withIdentifier(2003).withIconTintingEnabled(true)
                        ),

                        new SectionDrawerItem().withName("More"),
                        new SecondaryDrawerItem().withName(R.string.app_info).withIcon(R.drawable.info_vector).withIdentifier(24).withIconTintingEnabled(true),
                        new SecondaryDrawerItem().withName(R.string.review).withIcon(R.drawable.review_vector).withIdentifier(21).withIconTintingEnabled(true),
                        new SecondaryDrawerItem().withName(R.string.share).withIcon(R.drawable.share_vector).withIdentifier(22).withIconTintingEnabled(true),
                        new SecondaryDrawerItem().withName(R.string.open).withIcon(R.drawable.github_vector).withIdentifier(23).withIconTintingEnabled(true),

                        new SectionDrawerItem().withName("Settings"),
                        new SecondaryDrawerItem().withName(R.string.settings_menu_item).withIcon(R.mipmap.ic_settings_white_24dp).withIdentifier(30).withIconTintingEnabled(true)

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                viewPager.setCurrentItem(0);
                            } else if (drawerItem.getIdentifier() == 2) {
                                viewPager.setCurrentItem(1);
                            } else if (drawerItem.getIdentifier() == 3) {
                                viewPager.setCurrentItem(2);
                            } else if (drawerItem.getIdentifier() == 4) {
                                viewPager.setCurrentItem(3);
                            } else if (drawerItem.getIdentifier() == 5) {
                                viewPager.setCurrentItem(4);
                            } else if (drawerItem.getIdentifier() == 2002) {
                                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                                String facebookUrl = getFacebookPageURL(getApplicationContext());
                                facebookIntent.setData(Uri.parse(facebookUrl));
                                startActivity(facebookIntent);
                            } else if (drawerItem.getIdentifier() == 2003) {
                                Intent instaIntent;
                                String instagramUrl = "https://www.instagram.com/freehit_/";
                                instaIntent = newInstagramProfileIntent(getApplicationContext(), instagramUrl);
                                startActivity(instaIntent);
                            } else if (drawerItem.getIdentifier() == 21) {
                                String appPackageName = getPackageName();
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            } else if (drawerItem.getIdentifier() == 22) {
                                try {
                                    Intent i = new Intent(Intent.ACTION_SEND);
                                    i.setType("text/plain");
                                    i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                                    String sAux = "Check out this all in one Ad Free Cricket Application \n\n";
                                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.debut.ellipsis.freehit \n";
                                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                                    startActivity(Intent.createChooser(i, "choose one"));
                                } catch (Exception e) {
                                    //e.toString();
                                }
                            } else if (drawerItem.getIdentifier() == 23) {
                                String url = "https://github.com/aseef17/FreeHit";
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            } else if (drawerItem.getIdentifier() == 24) {
                                startActivity(new Intent(MainActivity.this, AboutActivity.class),
                                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                            } else if (drawerItem.getIdentifier() == 30) {
                                intent = new Intent(MainActivity.this, CustomSettings.class);
                            }
                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                result.setSelection(tab.getPosition() + 1);
                tab.getIcon().setColorFilter(Color.parseColor("#f5f5f5"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    tab.getIcon().setColorFilter(Color.parseColor("#6c6c6d"), PorterDuff.Mode.SRC_IN);
                } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                    tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        setupTabIcons();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#f5f5f5"), PorterDuff.Mode.SRC_IN);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#6c6c6d"), PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#6c6c6d"), PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#6c6c6d"), PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(4).getIcon().setColorFilter(Color.parseColor("#6c6c6d"), PorterDuff.Mode.SRC_IN);
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            tabLayout.getTabAt(1).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(2).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(3).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(4).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "HOME");
        adapter.addFrag(new MatchesFragment(), "MATCHES");
        adapter.addFrag(new NewsFragment(), "NEWS");
        adapter.addFrag(new SocialMainFragment(), "SOCIAL");
        adapter.addFrag(new StatsFragment(), "STATS");
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
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, CustomSettings.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
        overridePendingTransition(0, R.anim.exit_to_right);
    }

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public static Intent newInstagramProfileIntent(Context context, String url) {
        PackageManager pm = context.getPackageManager();
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                final String username = url.substring(url.lastIndexOf("/") + 1);
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(url));
        return intent;
    }

}
