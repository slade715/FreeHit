package com.debut.ellipsis.freehit.Stats.StatsMain;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.debut.ellipsis.freehit.R;

import java.util.ArrayList;

public class StatsAdapter extends ArrayAdapter<StatsItem> {

    public StatsAdapter(Context context, ArrayList<StatsItem> items) {

        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_stats_main_item, parent, false);
        }

        StatsItem currentItem = getItem(position);

        ImageView moreArrowIcon = listItemView.findViewById(R.id.arrow_icon);

        TextView moreNameTextView = listItemView.findViewById(R.id.row_title);
        moreNameTextView.setText(currentItem.getmMoreName());

        ImageView moreIcon = listItemView.findViewById(R.id.row_icon);
        moreIcon.setImageResource(currentItem.getmMoreIcon());

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
              moreArrowIcon.setColorFilter(Color.WHITE);
              moreIcon.setColorFilter(Color.WHITE);
              moreNameTextView.setTextColor(Color.WHITE);
        }


        return listItemView;
    }
}
