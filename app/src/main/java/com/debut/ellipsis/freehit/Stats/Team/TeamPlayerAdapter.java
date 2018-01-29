package com.debut.ellipsis.freehit.Stats.Team;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.DecodeFormat;
import com.debut.ellipsis.freehit.Glide.GlideApp;
import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.PlayerCountryItem;
import com.debut.ellipsis.freehit.R;
import com.debut.ellipsis.freehit.Stats.Player.PlayerActivity;

import java.util.List;

public class TeamPlayerAdapter extends RecyclerView.Adapter<TeamPlayerAdapter.TeamPlayerViewHolder> {

    private List<PlayerCountryItem> playerCountryItems;
    private int rowLayout;
    private Context context;


    public static class TeamPlayerViewHolder extends RecyclerView.ViewHolder {
        ImageView PlayerImage;
        TextView PlayerName;
        LinearLayout rlcontainer;


        public TeamPlayerViewHolder(View v) {
            super(v);
            PlayerImage = v.findViewById(R.id.row_icon);
            PlayerName = v.findViewById(R.id.row_title);
            rlcontainer = v.findViewById(R.id.row_layout);
        }
    }

    public TeamPlayerAdapter(List<PlayerCountryItem> playerCountryItems, int rowLayout, Context context) {
        this.playerCountryItems = playerCountryItems;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public TeamPlayerAdapter.TeamPlayerViewHolder onCreateViewHolder(ViewGroup parent,
                                                                            int viewType) {
        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        }
        return new TeamPlayerAdapter.TeamPlayerViewHolder(view);

    }


    @Override
    public void onBindViewHolder(TeamPlayerAdapter.TeamPlayerViewHolder holder, final int position) {
        holder.PlayerName.setText(playerCountryItems.get(position).getName());

        String PlayerURL = playerCountryItems.get(position).getImage();

        LinearLayout RLContainer = holder.rlcontainer;

        View.OnClickListener mClickListener;

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            holder.PlayerName.setTextColor(Color.WHITE);
            MainActivity.requestBuilder = GlideApp.with(context).load(PlayerURL).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565);
        }
        else
        {
            MainActivity.requestBuilder = GlideApp.with(context).load(PlayerURL).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
        }

        MainActivity.requestBuilder.into(holder.PlayerImage);

        mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent PlayerBioIntent = new Intent(context, PlayerActivity.class);
                PlayerBioIntent.putExtra("player_url", playerCountryItems.get(position).getLink());
                PlayerBioIntent.putExtra("player_name",playerCountryItems.get(position).getName());
                PlayerBioIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(PlayerBioIntent);

            }
        };
        RLContainer.setOnClickListener(mClickListener);


    }

    @Override
    public int getItemCount() {
        return playerCountryItems.size();
    }
}
