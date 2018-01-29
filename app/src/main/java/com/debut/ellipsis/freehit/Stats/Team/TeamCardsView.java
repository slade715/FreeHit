package com.debut.ellipsis.freehit.Stats.Team;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.debut.ellipsis.freehit.Glide.GlideApp;
import com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity;
import com.debut.ellipsis.freehit.R;


public class TeamCardsView extends AppCompatActivity implements View.OnClickListener {
    View afghanistanCardView;
    View australiaCardView;
    View bangladeshCardView;
    View canadaCardView;
    View englandCardView;
    View indiaCardView;
    View irelandCardView;
    View netherlandsCardView;
    View newZealandCardView;
    View pakistanCardView;
    View southAfricaCardView;
    View sriLankaCardView;
    View uaeCardView;
    View westIndiesCardView;
    View zimbabweCardView;


    public TeamCardsView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stats_team_list);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

        afghanistanCardView = findViewById(R.id.afghanistan);
        australiaCardView = findViewById(R.id.australia);
        bangladeshCardView = findViewById(R.id.bangladesh);
        canadaCardView = findViewById(R.id.canada);
        englandCardView = findViewById(R.id.england);
        indiaCardView = findViewById(R.id.india);
        irelandCardView = findViewById(R.id.ireland);
        netherlandsCardView = findViewById(R.id.netherlands);
        newZealandCardView = findViewById(R.id.new_zealand);
        pakistanCardView = findViewById(R.id.pakistan);
        southAfricaCardView = findViewById(R.id.south_africa);
        sriLankaCardView = findViewById(R.id.sri_lanka);
        uaeCardView = findViewById(R.id.uae);
        westIndiesCardView = findViewById(R.id.west_indies);
        zimbabweCardView = findViewById(R.id.zimbabwe);


        // Afghanistan
        ImageView country_image = afghanistanCardView.findViewById(R.id.country_image);

        TextView country_name = afghanistanCardView.findViewById(R.id.country_name);

        RequestBuilder requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("AFGHANISTAN")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("AFGHANISTAN")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_Afghanistan_label);


        // Australia
        country_image = australiaCardView.findViewById(R.id.country_image);

        country_name = australiaCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("AUSTRALIA")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("AUSTRALIA")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_Australia_label);

        // Bangladesh
        country_image = bangladeshCardView.findViewById(R.id.country_image);

        country_name = bangladeshCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("BANGLADESH")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("BANGLADESH")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_Bangladesh_label);

        // Canada
        country_image = canadaCardView.findViewById(R.id.country_image);

        country_name = canadaCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("CANADA")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("CANADA")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_Canada_label);

        // England
        country_image = englandCardView.findViewById(R.id.country_image);

        country_name = englandCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("ENGLAND")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("ENGLAND")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_England_label);

        // India
        country_image = indiaCardView.findViewById(R.id.country_image);

        country_name = indiaCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("INDIA")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("INDIA")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_India_label);

        // Ireland
        country_image = irelandCardView.findViewById(R.id.country_image);

        country_name = irelandCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("IRELAND")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("IRELAND")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_Ireland_label);

        // Netherlands
        country_image = netherlandsCardView.findViewById(R.id.country_image);

        country_name = netherlandsCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("NETHERLANDS")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("NETHERLANDS")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_Netherlands_label);

        // New Zealand
        country_image = newZealandCardView.findViewById(R.id.country_image);

        country_name = newZealandCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("NEW ZEALAND")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("NEW ZEALAND")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_NewZealand_label);

        // Pakistan
        country_image = pakistanCardView.findViewById(R.id.country_image);

        country_name = pakistanCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("PAKISTAN")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("PAKISTAN")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_Pakistan_label);

        // South Africa
        country_image = southAfricaCardView.findViewById(R.id.country_image);

        country_name = southAfricaCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("SOUTH AFRICA")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("SOUTH AFRICA")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_SouthAfrica_label);

        // Sri Lanka
        country_image = sriLankaCardView.findViewById(R.id.country_image);

        country_name = sriLankaCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("SRI LANKA")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("SRI LANKA")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_SriLanka_label);

        // UAE
        country_image = uaeCardView.findViewById(R.id.country_image);

        country_name = uaeCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("UNITED ARAB EMIRATES")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("UNITED ARAB EMIRATES")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText("UAE");

        // West Indies
        country_image = westIndiesCardView.findViewById(R.id.country_image);

        country_name = westIndiesCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("WEST INDIES")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("WEST INDIES")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_WestIndies_label);

        // Zimbabwe
        country_image = zimbabweCardView.findViewById(R.id.country_image);

        country_name = zimbabweCardView.findViewById(R.id.country_name);

        requestBuilder = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("ZIMBABWE")).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565) : GlideApp.with(this).load(WelcomeActivity.countryHash.getCountryFlag("ZIMBABWE")).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        requestBuilder.into(country_image);

        country_name.setText(R.string.settings_team_Zimbabwe_label);


        afghanistanCardView.setOnClickListener(this);
        australiaCardView.setOnClickListener(this);
        bangladeshCardView.setOnClickListener(this);
        canadaCardView.setOnClickListener(this);
        englandCardView.setOnClickListener(this);
        indiaCardView.setOnClickListener(this);
        irelandCardView.setOnClickListener(this);
        netherlandsCardView.setOnClickListener(this);
        newZealandCardView.setOnClickListener(this);
        pakistanCardView.setOnClickListener(this);
        southAfricaCardView.setOnClickListener(this);
        sriLankaCardView.setOnClickListener(this);
        uaeCardView.setOnClickListener(this);
        westIndiesCardView.setOnClickListener(this);
        zimbabweCardView.setOnClickListener(this);


        View viewToolbar = findViewById(R.id.team_list_toolbar);
        Toolbar toolbar = viewToolbar.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Teams");


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
        TeamCardsView.super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_to_right);

    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.afghanistan:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_Afghanistan_label);
                startActivity(i);
                break;
            case R.id.australia:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_Australia_label);
                startActivity(i);
                break;
            case R.id.bangladesh:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_Bangladesh_label);
                startActivity(i);
                break;
            case R.id.canada:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_Canada_label);
                startActivity(i);
                break;
            case R.id.england:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_England_label);
                startActivity(i);
                break;
            case R.id.india:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_India_label);
                startActivity(i);
                break;
            case R.id.ireland:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_Ireland_label);
                startActivity(i);
                break;
            case R.id.netherlands:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_Netherlands_label);
                startActivity(i);
                break;
            case R.id.new_zealand:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_NewZealand_label);
                startActivity(i);
                break;
            case R.id.pakistan:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_Pakistan_label);
                startActivity(i);
                break;
            case R.id.south_africa:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_SouthAfrica_label);
                startActivity(i);
                break;
            case R.id.sri_lanka:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_SriLanka_label);
                startActivity(i);
                break;
            case R.id.uae:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_UnitedArabEmirates_label);
                startActivity(i);
                break;
            case R.id.west_indies:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_WestIndies_label);
                startActivity(i);
                break;
            case R.id.zimbabwe:
                i = i = new Intent(getApplicationContext(), TeamActivity.class);
                i.putExtra("CountryName", R.string.settings_team_Zimbabwe_label);
                startActivity(i);
                break;

        }
    }
}