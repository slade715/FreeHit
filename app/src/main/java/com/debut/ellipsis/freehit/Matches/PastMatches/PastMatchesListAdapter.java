package com.debut.ellipsis.freehit.Matches.PastMatches;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DecodeFormat;
import com.debut.ellipsis.freehit.Glide.GlideApp;
import com.debut.ellipsis.freehit.IntoSlider.WelcomeActivity;
import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class PastMatchesListAdapter extends RecyclerView.Adapter<PastMatchesListAdapter.PastViewHolder> implements Filterable {
    private List<PastMatchCardItem> pastMatchCardItems;
    private Context context;
    private static final String MATCH_NAME_SEPARATOR = ", ";
    private static final String STADIUM_NAME_SEPARATOR = ", ";
    private List<PastMatchCardItem> pastListFiltered;

    public static class PastViewHolder extends RecyclerView.ViewHolder {
        ImageView team1image;
        ImageView team2image;
        TextView title;
        TextView series;
        TextView stadium;
        TextView sn1;
        TextView t1inn1;
        TextView t1inn2;
        TextView sn2;
        TextView t2inn1;
        TextView t2inn2;
        TextView result;
        TextView date;
        RelativeLayout rlcontainer;
        CardView cardView;

        public PastViewHolder(View v) {
            super(v);
            team1image = v.findViewById(R.id.team_logo_1_past);
            team2image = v.findViewById(R.id.team_logo_2_past);
            title = v.findViewById(R.id.match_name_past);
            series = v.findViewById(R.id.series_name_past);
            stadium = v.findViewById(R.id.stadium_past);
            sn1 = v.findViewById(R.id.sn_team_1_past);
            t1inn1 = v.findViewById(R.id.innings1_team1_past);
            t1inn2 = v.findViewById(R.id.innings2_team1_past);
            sn2 = v.findViewById(R.id.sn_team_2_past);
            t2inn1 = v.findViewById(R.id.innings1_team2_past);
            t2inn2 = v.findViewById(R.id.innings2_team2_past);
            result = v.findViewById(R.id.match_result_past);
            date = v.findViewById(R.id.match_date_past);
            cardView = v.findViewById(R.id.card_view);
            rlcontainer = itemView.findViewById(R.id.rlcontainer);
        }
    }

    public PastMatchesListAdapter(List<PastMatchCardItem> past, Context context) {
        this.pastMatchCardItems = past;
        this.pastListFiltered = past;
        this.context = context;
    }

    @Override
    public PastMatchesListAdapter.PastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fragment_matches_past_match_card, parent, false);

        return new PastViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(PastMatchesListAdapter.PastViewHolder holder, final int position) {
        String originalMatchTime = pastListFiltered.get(position).getDate().getFinaldatetime();


        Date time = null;
        try {
            time = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(originalMatchTime.replaceAll("Z$", "+0000"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat date_format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        date_format.setTimeZone(TimeZone.getTimeZone("IND"));
        date_format.setTimeZone(TimeZone.getDefault());
        String formattedDate = date_format.format(time);

        holder.date.setText(formattedDate);
        holder.result.setText(pastListFiltered.get(position).getResult());
        holder.series.setText(pastListFiltered.get(position).getTour());
        holder.sn1.setText(pastListFiltered.get(position).getTeam1Info().getSn());
        holder.t1inn1.setText(pastListFiltered.get(position).getTeam1Info().getInn1());

        if (!pastListFiltered.get(position).getTeam1Info().getInn2().equals(""))
            holder.t1inn2.setText("& " + pastListFiltered.get(position).getTeam1Info().getInn2());
        else
            holder.t1inn2.setText(pastListFiltered.get(position).getTeam1Info().getInn2());

        holder.sn2.setText(pastListFiltered.get(position).getTeam2Info().getSn());
        holder.t2inn1.setText(pastListFiltered.get(position).getTeam2Info().getInn1());

        if (!pastListFiltered.get(position).getTeam2Info().getInn2().equals(""))
            holder.t2inn2.setText("& " + pastListFiltered.get(position).getTeam2Info().getInn2());
        else
            holder.t2inn2.setText(pastListFiltered.get(position).getTeam2Info().getInn2());

        String stadium_name = pastListFiltered.get(position).getStadium();

        if (pastListFiltered.get(position).getStadium().contains(STADIUM_NAME_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the "," text. We expect an array of 2 Strings, where
            // the first String will be "India Vs Sri Lanka" , second String will be "1st Test".
            String[] parts = pastListFiltered.get(position).getStadium().split(STADIUM_NAME_SEPARATOR);
            // match_name should be "1st Test"
            stadium_name = parts[1];
        }

        holder.stadium.setText(", " + stadium_name);


        String match_name = pastListFiltered.get(position).getTitle();

        if (pastListFiltered.get(position).getTitle().contains(MATCH_NAME_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the "," text. We expect an array of 2 Strings, where
            // the first String will be "India Vs Sri Lanka" , second String will be "1st Test".
            String[] parts = pastListFiltered.get(position).getTitle().split(MATCH_NAME_SEPARATOR);
            // match_name should be "1st Test"
            match_name = parts[1];
        }

        holder.title.setText(match_name);

        String logo_string1 = WelcomeActivity.countryHash.getCountryFlag(WelcomeActivity.countryHash.getCountryName(pastListFiltered.get(position).getTeam1Info().getSn()).toUpperCase());
        String logo_string2 = WelcomeActivity.countryHash.getCountryFlag(WelcomeActivity.countryHash.getCountryName(pastListFiltered.get(position).getTeam2Info().getSn()).toUpperCase());


        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                MainActivity.requestBuilder = GlideApp.with(context).load(logo_string1).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565);
                MainActivity.requestBuilder.into(holder.team1image);
                MainActivity.requestBuilder = GlideApp.with(context).load(logo_string2).placeholder(R.drawable.placeholder_dark).format(DecodeFormat.PREFER_RGB_565);
                MainActivity.requestBuilder.into(holder.team2image);
                break;
            default:
                MainActivity.requestBuilder = GlideApp.with(context).load(logo_string1).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
                MainActivity.requestBuilder.into(holder.team1image);
                MainActivity.requestBuilder = GlideApp.with(context).load(logo_string2).placeholder(R.drawable.placeholder_light).format(DecodeFormat.PREFER_RGB_565);
                MainActivity.requestBuilder.into(holder.team2image);
                break;
        }


        RelativeLayout RLContainer = holder.rlcontainer;

        View.OnClickListener mClickListener;

        mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pastListFiltered.get(position).getTeam1Info().getInn1().equals("") && pastListFiltered.get(position).getTeam2Info().getInn1().equals("")) {
                    Toast.makeText(context, "Match was Abandoned !", Toast.LENGTH_SHORT).show();
                } else {
                    Intent pastMatchScoreCardIntent = new Intent(context, PastMatchScoreCard.class);
                    pastMatchScoreCardIntent.putExtra("match_id", pastListFiltered.get(position).getNdid());
                    pastMatchScoreCardIntent.putExtra("match_name", pastListFiltered.get(position).getTeam1Info().getSn() + " VS " + pastListFiltered.get(position).getTeam2Info().getSn());
                    pastMatchScoreCardIntent.putExtra("match_type", "PAST");
                    pastMatchScoreCardIntent.putExtra("team1", WelcomeActivity.countryHash.getCountryName(pastListFiltered.get(position).getTeam1Info().getSn()));
                    pastMatchScoreCardIntent.putExtra("team2", WelcomeActivity.countryHash.getCountryName(pastListFiltered.get(position).getTeam2Info().getSn()));
                    pastMatchScoreCardIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(pastMatchScoreCardIntent);
                }

            }
        };


        RLContainer.setOnClickListener(mClickListener);


    }

    @Override
    public int getItemCount() {
        return pastListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    pastListFiltered = pastMatchCardItems;
                } else {
                    List<PastMatchCardItem> filteredList = new ArrayList<>();
                    for (PastMatchCardItem row : pastMatchCardItems) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTeam1Info().getSn().toLowerCase().contains(charString.toLowerCase()) || row.getTeam1Info().getSn().toLowerCase().contains(charString.toLowerCase()) || row.getTour().toLowerCase().contains(charString.toLowerCase()) || row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getStadium().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    pastListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = pastListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pastListFiltered = (ArrayList<PastMatchCardItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}