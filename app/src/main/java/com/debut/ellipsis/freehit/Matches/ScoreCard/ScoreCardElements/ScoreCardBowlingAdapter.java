package com.debut.ellipsis.freehit.Matches.ScoreCard.ScoreCardElements;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.debut.ellipsis.freehit.Matches.ScoreCard.ScoreCardItem;
import com.debut.ellipsis.freehit.R;

import java.util.List;

public class ScoreCardBowlingAdapter extends ArrayAdapter {

    private List<ScoreCardItem.Bowling> BowlingList;


    public ScoreCardBowlingAdapter(Context context, List<ScoreCardItem.Bowling> batting) {
        super(context, 0, batting);
        BowlingList = batting;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_matchscorecard_scorecard_innings_batting_bowling_item, parent, false);
        }

        ScoreCardItem.Bowling currentBowler = BowlingList.get(position);
        TextView batsman_value = listItemView.findViewById(R.id.batsman_value);
        TextView overs_value = listItemView.findViewById(R.id.runs_value);
        TextView maidens_value = listItemView.findViewById(R.id.balls_value);
        TextView runs_value = listItemView.findViewById(R.id.fours_value);
        TextView wickets_value = listItemView.findViewById(R.id.sixes_value);
        wickets_value.setTypeface(null, Typeface.BOLD);
        TextView EconomyRate_value = listItemView.findViewById(R.id.StrikeRate_value);

        batsman_value.setText(currentBowler.getName());
        overs_value.setText(currentBowler.getOvers());
        maidens_value.setText(currentBowler.getMaidens());
        runs_value.setText(currentBowler.getRuns());
        wickets_value.setText(currentBowler.getWickets());
        EconomyRate_value.setText(currentBowler.getEr());

        return listItemView;

    }
}
