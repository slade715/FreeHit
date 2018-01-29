package com.debut.ellipsis.freehit.IntoSlider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.debut.ellipsis.freehit.CountryHash;
import com.debut.ellipsis.freehit.Glide.GlideApp;
import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.R;


public class WelcomeActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static CountryHash countryHash = new CountryHash();
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private ImageView[] dots;
    private int[] layouts;
    private Button btnNext;
    private PrefManager prefManager;
    private boolean clicked = false;
    AnimatedColor oneToTwo = null;
    AnimatedColor TwoToThree = null;
    AnimatedColor ThreeToFour = null;
    AnimatedColor FourToFive = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);


        View viewViewPager = findViewById(R.id.welcome_viewpager);

        viewPager = viewViewPager.findViewById(R.id.viewpager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnNext = findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide5_country_picker};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();

        viewPager.setAdapter(myViewPagerAdapter);

        // Set PageTransformer on ViewPager
        viewPager.setPageTransformer(false, new OnboardingPageTransformer());

        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    if (clicked) {
                        launchHomeScreen();
                    } else {
                        Toast.makeText(getBaseContext(), R.string.select_team_alert, Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new ImageView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        int width = getResources().getDimensionPixelSize(R.dimen._15sdp);
        int height = getResources().getDimensionPixelSize(R.dimen._15sdp);
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            dots[i].setLayoutParams(params);
            params.setMargins(15, 15, 0, 0);
            //parms.setMargins(left, top, right, bottom);
            dots[i].setImageResource(R.drawable.circle_vector);
            dots[i].setColorFilter(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            dots[currentPage].setLayoutParams(params);
            params.setMargins(15, 15, 0, 0);
            dots[currentPage].setImageResource(R.drawable.ball);
            dots[currentPage].setColorFilter(colorsActive[currentPage]);
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));

            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
            }
        }

        @Override
        // arg0 - position , arg1 - positionOffset arg2 - positionOffsetPixels
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            switch (arg0) {
                case 0:
                    viewPager.setBackgroundColor(oneToTwo.with(arg1));
                    break;
                case 1:
                    viewPager.setBackgroundColor(TwoToThree.with(arg1));
                    break;
                case 2:
                    viewPager.setBackgroundColor(ThreeToFour.with(arg1));
                    break;
                case 3:
                    viewPager.setBackgroundColor(FourToFive.with(arg1));
                    break;
                case 4:
                    viewPager.setBackgroundColor(getResources().getColor( R.color.bg_screen5));
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            oneToTwo = new AnimatedColor(getResources().getColor( R.color.bg_screen1), getResources().getColor( R.color.bg_screen2));
            TwoToThree = new AnimatedColor(getResources().getColor( R.color.bg_screen2), getResources().getColor(R.color.bg_screen3));
            ThreeToFour = new AnimatedColor(getResources().getColor( R.color.bg_screen3), getResources().getColor( R.color.bg_screen4));
            FourToFive = new AnimatedColor(getResources().getColor( R.color.bg_screen4), getResources().getColor( R.color.bg_screen5));


            if (position == 4) {

                Switch NightMode = findViewById(R.id.switch_night_mode);
                final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

                NightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // do something, the isChecked will be
                        // true if the switch is in the On position
                        if (isChecked) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            editor.putBoolean("switch_state", true);

                        } else if (!isChecked) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            editor.putBoolean("switch_state", false);

                        }
                        editor.apply();
                    }
                });


                ImageView country_flag = findViewById(R.id.country_flag);

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String name = prefs.getString("country_name", "SELECT COUNTRY");

                TextView country_name = findViewById(R.id.country_name);
                country_name.setText(name.toUpperCase());


                String TeamLogo = countryHash.getCountryFlag(name.toUpperCase());
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    MainActivity.requestBuilder = GlideApp.with(getBaseContext()).load(TeamLogo).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565);
                } else {
                    MainActivity.requestBuilder = GlideApp.with(getBaseContext()).load(TeamLogo).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
                }


                MainActivity.requestBuilder.into(country_flag);

                Boolean NightModeState = prefs.getBoolean("switch_state", false);

                if (NightModeState) {
                    NightMode.setChecked(true);
                } else {
                    NightMode.setChecked(false);
                }

            }
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    public void select_country(View view) {

        final CountryPicker picker = CountryPicker.newInstance();  // dialog title
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String flagURLID) {
                // Implement your code here
                TextView country_name = findViewById(R.id.country_name);
                country_name.setText(name.toUpperCase());

                ImageView before = findViewById(R.id.country_flag);

                RequestBuilder requestBuilder;

                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    requestBuilder = GlideApp.with(getBaseContext()).load(flagURLID).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565);
                } else {
                    requestBuilder = GlideApp.with(getBaseContext()).load(flagURLID).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
                }
                requestBuilder.into(before);

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("country_name", name);
                editor.apply();

                clicked = true;
                picker.dismiss();

            }
        });
        picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

    }


}
