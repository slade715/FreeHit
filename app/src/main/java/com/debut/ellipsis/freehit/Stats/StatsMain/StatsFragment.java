package com.debut.ellipsis.freehit.Stats.StatsMain;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.debut.ellipsis.freehit.R;
import com.debut.ellipsis.freehit.Stats.Player.PlayerSearchActivity;
import com.debut.ellipsis.freehit.Stats.Rankings.RankingActivity;
import com.debut.ellipsis.freehit.Stats.Series.SeriesMainActivity;
import com.debut.ellipsis.freehit.Stats.Team.TeamActivity;
import com.debut.ellipsis.freehit.Stats.Team.TeamCardsView;

import static android.content.Context.MODE_PRIVATE;
import static com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity.MY_PREFS_NAME;

public class StatsFragment extends Fragment implements View.OnClickListener {

    public StatsFragment() {
        // Required empty public constructor
    }

    CardView player;
    CardView team;
    CardView series;
    CardView ranking;
    CardView fav_team;
    String CountryName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stats_main, container, false);

        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String CountryName = prefs.getString("country_name", "null");
        this.CountryName = CountryName;
        player = rootView.findViewById(R.id.player);
        team = rootView.findViewById(R.id.team);
        series = rootView.findViewById(R.id.series);
        ranking = rootView.findViewById(R.id.rankings);
        fav_team = rootView.findViewById(R.id.fav_team);

        player.setOnClickListener(this);
        team.setOnClickListener(this);
        series.setOnClickListener(this);
        ranking.setOnClickListener(this);
        fav_team.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.player:
                Intent PlayerIntent = new Intent(getActivity(), PlayerSearchActivity.class);
                startActivity(PlayerIntent);
                break;
            case R.id.team:
                Intent TeamIntent = new Intent(getActivity(), TeamCardsView.class);
                startActivity(TeamIntent);
                break;
            case R.id.series:
                Intent SeriesIntent = new Intent(getActivity(), SeriesMainActivity.class);
                startActivity(SeriesIntent);
                break;
            case R.id.rankings:
                Intent RankingIntent = new Intent(getActivity(), RankingActivity.class);
                startActivity(RankingIntent);
                break;
            case R.id.fav_team:
                if (CountryName.equals("null")) {
                    Toast.makeText(getContext(), "Select A Favourite Team First", Toast.LENGTH_SHORT).show();
                } else {
                    Intent FavouritesIntent = new Intent(getActivity(), TeamActivity.class);
                    FavouritesIntent.putExtra("fav_country", CountryName);
                    startActivity(FavouritesIntent);
                }
                break;

        }
    }
}