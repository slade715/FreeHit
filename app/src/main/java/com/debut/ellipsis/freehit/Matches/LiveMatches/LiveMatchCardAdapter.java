package com.debut.ellipsis.freehit.Matches.LiveMatches;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.DecodeFormat;
import com.debut.ellipsis.freehit.Glide.GlideApp;
import com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity;
import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity.countryHash;

public class LiveMatchCardAdapter extends RecyclerView.Adapter<LiveMatchCardAdapter.LiveViewHolder> {
    private List<LiveMatchCardItem> liveMatchCardItems;
    private Context context;
    private static final String DATE_SEPARATOR = "T";
    private static final String MATCH_SERIES_SEPARATOR = ", ";
    private static final String STADIUM_NAME_SEPARATOR = ", ";
    private String logo_string1;
    private String logo_string2;

    public static class LiveViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewTeam1Logo;
        ImageView imageViewTeam2Logo;
        TextView textViewMatchName;
        TextView textViewSeriesName;
        TextView textViewStadiumName;
        TextView shortName1;
        TextView team1Innings1;
        TextView team1Innings2;
        TextView shortName2;
        TextView team2Innings1;
        TextView team2Innings2;
        TextView MatchResult;
        TextView MatchDate;
        RelativeLayout rlcontainer;

        public LiveViewHolder(View view) {
            super(view);
            textViewMatchName = view.findViewById(R.id.match_name_live);
            textViewSeriesName = view.findViewById(R.id.series_name_live);
            textViewStadiumName = view.findViewById(R.id.stadium_live);
            imageViewTeam1Logo = view.findViewById(R.id.team_logo_1_live);
            imageViewTeam2Logo = view.findViewById(R.id.team_logo_2_live);
            shortName1 = view.findViewById(R.id.sn_team_1_live);
            team1Innings1 = view.findViewById(R.id.innings1_team1_live);
            team1Innings2 = view.findViewById(R.id.innings2_team1_live);
            shortName2 = view.findViewById(R.id.sn_team_2_live);
            team2Innings1 = view.findViewById(R.id.innings1_team2_live);
            team2Innings2 = view.findViewById(R.id.innings2_team2_live);
            MatchResult = view.findViewById(R.id.match_target_trail_leadBy_live);
            MatchDate = view.findViewById(R.id.match_date_live);
            rlcontainer = itemView.findViewById(R.id.rlcontainer);
        }
    }

    public LiveMatchCardAdapter(List<LiveMatchCardItem> past, Context context) {
        this.liveMatchCardItems = past;
        this.context = context;
    }

    @Override
    public LiveMatchCardAdapter.LiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fragment_matches_live_match_card, parent, false);

        return new LiveMatchCardAdapter.LiveViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(LiveMatchCardAdapter.LiveViewHolder holder, final int position) {

        String originalMatchName = liveMatchCardItems.get(position).getTour();

        String match_name = null;
        String series_name = null;
        // Check whether the originalLocation string contains the " of " text
        if (originalMatchName.contains(MATCH_SERIES_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the "," text. We expect an array of 4 Strings, where
            // the first String will be "2nd Test" , second String will be " Australia in Bangladesh", third String will be " 2 Test Series", fourth String will be " 2017".
            String[] parts = originalMatchName.split(MATCH_SERIES_SEPARATOR);
            // match_name should be "2nd Test"
            // series_name should be " Australia in Bangladesh, 2 Test Series, 2017"
            match_name = parts[0];
            series_name = parts[1] + MATCH_SERIES_SEPARATOR;
            for (int i = 2; i < parts.length; i++) {
                series_name += parts[i];
            }
        }

        holder.textViewMatchName.setText(match_name);
        holder.textViewSeriesName.setText(series_name);

        String stadium_name = liveMatchCardItems.get(position).getStadium();

        if (liveMatchCardItems.get(position).getStadium().contains(STADIUM_NAME_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the "," text. We expect an array of 2 Strings, where
            // the first String will be "India Vs Sri Lanka" , second String will be "1st Test".
            String[] parts = liveMatchCardItems.get(position).getStadium().split(STADIUM_NAME_SEPARATOR);
            // match_name should be "1st Test"
            stadium_name = parts[1];
        }

        final String ShortNameTeam1 = countryHash.getCountrySN((liveMatchCardItems.get(position).getTeam1().getName()).toUpperCase());

        holder.shortName1.setText(ShortNameTeam1);

        holder.textViewStadiumName.setText(", " + stadium_name);

        holder.team1Innings1.setText(liveMatchCardItems.get(position).getTeam1().getInn1());


        if (!liveMatchCardItems.get(position).getTeam1().getInn1().equals("") && !liveMatchCardItems.get(position).getTeam1().getInn2().equals(""))
            holder.team1Innings2.setText("& " + liveMatchCardItems.get(position).getTeam1().getInn2());
        else
            holder.team1Innings2.setText(liveMatchCardItems.get(position).getTeam1().getInn2());


        final String ShortNameTeam2 = countryHash.getCountrySN((liveMatchCardItems.get(position).getTeam2().getName()).toUpperCase());

        holder.shortName2.setText(ShortNameTeam2);

        holder.team2Innings1.setText(liveMatchCardItems.get(position).getTeam2().getInn1());

        if (!liveMatchCardItems.get(position).getTeam2().getInn1().equals("") && !liveMatchCardItems.get(position).getTeam2().getInn2().equals(""))
            holder.team2Innings2.setText("& " + liveMatchCardItems.get(position).getTeam2().getInn2());
        else
            holder.team2Innings2.setText(liveMatchCardItems.get(position).getTeam2().getInn2());


        holder.MatchResult.setText(liveMatchCardItems.get(position).getMresult());

        String originalMatchDate = liveMatchCardItems.get(position).getDate().getFinaldate();

        // Check whether the originalLocation string contains the " of " text
        if (originalMatchDate.contains(DATE_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the "T" text. We expect an array of 2 Strings, where
            // the first String will be "2017-09-04" and the second String will be "04:00:00.000Z".
            String[] parts = originalMatchDate.split(DATE_SEPARATOR);
            // originalMatchDate should be "2017-09-04"--> "04 Sep 2017"
            originalMatchDate = parts[0];

        }

        final String finalMatch_name = match_name;

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy , E");
        Date date = null;
        try {
            date = inputFormat.parse(originalMatchDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);

        holder.MatchDate.setText(outputDateStr);

        logo_string1 = WelcomeActivity.countryHash.getCountryFlag(liveMatchCardItems.get(position).getTeam1().getName().toUpperCase());
        logo_string2 = WelcomeActivity.countryHash.getCountryFlag(liveMatchCardItems.get(position).getTeam2().getName().toUpperCase());

        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                MainActivity.requestBuilder = GlideApp.with(context).load(logo_string1).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565);
                MainActivity.requestBuilder.into(holder.imageViewTeam1Logo);
                MainActivity.requestBuilder = GlideApp.with(context).load(logo_string2).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565);
                MainActivity.requestBuilder.into(holder.imageViewTeam2Logo);
                break;
            default:
                MainActivity.requestBuilder = GlideApp.with(context).load(logo_string1).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
                MainActivity.requestBuilder.into(holder.imageViewTeam1Logo);
                MainActivity.requestBuilder = GlideApp.with(context).load(logo_string2).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
                MainActivity.requestBuilder.into(holder.imageViewTeam2Logo);
                break;
        }


        RelativeLayout RLContainer = holder.rlcontainer;

        View.OnClickListener mClickListener;

        mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LiveMatchScoreCardIntent = new Intent(context, LiveMatchScoreCard.class);
                LiveMatchScoreCardIntent.putExtra("match_id", liveMatchCardItems.get(position).getNdid());
                LiveMatchScoreCardIntent.putExtra("match_name", finalMatch_name + "( " + ShortNameTeam1 + " vs " + ShortNameTeam2 + " )");
                LiveMatchScoreCardIntent.putExtra("team1", WelcomeActivity.countryHash.getCountryName(ShortNameTeam1));
                LiveMatchScoreCardIntent.putExtra("team2", WelcomeActivity.countryHash.getCountryName(ShortNameTeam2));
                LiveMatchScoreCardIntent.putExtra("match_type", "LIVE");
                LiveMatchScoreCardIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(LiveMatchScoreCardIntent);
            }
        };

        RLContainer.setOnClickListener(mClickListener);


    }

    @Override
    public int getItemCount() {
        return liveMatchCardItems.size();
    }
}
