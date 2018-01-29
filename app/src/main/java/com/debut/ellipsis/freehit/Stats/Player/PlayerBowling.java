package com.debut.ellipsis.freehit.Stats.Player;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debut.ellipsis.freehit.R;
import com.debut.ellipsis.freehit.Stats.Player.parallaxviewpager.NotifyingScrollView;
import com.debut.ellipsis.freehit.Stats.Player.parallaxviewpager.ScrollViewFragment;

public class PlayerBowling extends ScrollViewFragment {

    public static final String TAG = PlayerBowling.class.getSimpleName();

    public static Fragment newInstance(int position) {
        PlayerBowling fragment = new PlayerBowling();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public PlayerBowling() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPosition = getArguments().getInt(ARG_POSITION);

        View rootView = inflater.inflate(R.layout.fragment_stats_player_batting_bowling, container, false);
        mScrollView = (NotifyingScrollView) rootView.findViewById(R.id.scrollview);
        setScrollViewOnScrollListener();

        TextView Batting_Label = rootView.findViewById(R.id.Batting_Label);
        Batting_Label.setText(R.string.bowling);

        TextView Matches_Innings_label = rootView.findViewById(R.id.Matches_Innings_label);
        TextView m_i_test = rootView.findViewById(R.id.m_i_test);
        TextView m_i_odi = rootView.findViewById(R.id.m_i_odi);
        TextView m_i_t20 = rootView.findViewById(R.id.m_i_t20);
        TextView m_i_ipl = rootView.findViewById(R.id.m_i_ipl);

        TextView Innings_Overs_label = rootView.findViewById(R.id.Innings_Overs_label);
        TextView i_o_test = rootView.findViewById(R.id.i_o_test);
        TextView i_o_odi = rootView.findViewById(R.id.i_o_odi);
        TextView i_o_t20 = rootView.findViewById(R.id.i_o_t20);
        TextView i_o_ipl = rootView.findViewById(R.id.i_o_ipl);

        TextView NotOut_Maidens_Label = rootView.findViewById(R.id.NotOut_Maidens_Label);
        TextView no_m_test = rootView.findViewById(R.id.no_m_test);
        TextView no_m_odi = rootView.findViewById(R.id.no_m_odi);
        TextView no_m_t20 = rootView.findViewById(R.id.no_m_t20);
        TextView no_m_ipl = rootView.findViewById(R.id.no_m_ipl);


        TextView r_r_test = rootView.findViewById(R.id.r_r_test);
        TextView r_r_odi = rootView.findViewById(R.id.r_r_odi);
        TextView r_r_t20 = rootView.findViewById(R.id.r_r_t20);
        TextView r_r_ipl = rootView.findViewById(R.id.r_r_ipl);


        TextView highest_Wickets_label = rootView.findViewById(R.id.highest_Wickets_label);
        TextView h_w_test = rootView.findViewById(R.id.h_w_test);
        TextView h_w_odi = rootView.findViewById(R.id.h_w_odi);
        TextView h_w_t20 = rootView.findViewById(R.id.h_w_t20);
        TextView h_w_ipl = rootView.findViewById(R.id.h_w_ipl);

        TextView hundreds_best_label = rootView.findViewById(R.id.hundreds_best_label);
        TextView h_b_test = rootView.findViewById(R.id.h_b_test);
        TextView h_b_odi = rootView.findViewById(R.id.h_b_odi);
        TextView h_b_t20 = rootView.findViewById(R.id.h_b_t20);
        TextView h_b_ipl = rootView.findViewById(R.id.h_b_ipl);

        TextView fifties_3w_label = rootView.findViewById(R.id.fifties_3w_label);
        TextView f_3w_test = rootView.findViewById(R.id.f_3w_test);
        TextView f_3w_odi = rootView.findViewById(R.id.f_3w_odi);
        TextView f_3w_t20 = rootView.findViewById(R.id.f_3w_t20);
        TextView f_3w_ipl = rootView.findViewById(R.id.f_3w_ipl);

        TextView fours_5wickets_label = rootView.findViewById(R.id.fours_5wickets_label);
        TextView f_5w_test = rootView.findViewById(R.id.f_5w_test);
        TextView f_5w_odi = rootView.findViewById(R.id.f_5w_odi);
        TextView f_5w_t20 = rootView.findViewById(R.id.f_5w_t20);
        TextView f_5w_ipl = rootView.findViewById(R.id.f_5w_ipl);

        TextView sixes_average_label = rootView.findViewById(R.id.sixes_average_label);
        TextView s_avg_test = rootView.findViewById(R.id.s_avg_test);
        TextView s_avg_odi = rootView.findViewById(R.id.s_avg_odi);
        TextView s_avg_t20 = rootView.findViewById(R.id.s_avg_t20);
        TextView s_avg_ipl = rootView.findViewById(R.id.s_avg_ipl);

        TextView average_economy_label = rootView.findViewById(R.id.average_economy_label);
        TextView avg_eco_test = rootView.findViewById(R.id.avg_eco_test);
        TextView avg_eco_odi = rootView.findViewById(R.id.avg_eco_odi);
        TextView avg_eco_t20 = rootView.findViewById(R.id.avg_eco_t20);
        TextView avg_eco_ipl = rootView.findViewById(R.id.avg_eco_ipl);

        TextView sr_test = rootView.findViewById(R.id.sr_test);
        TextView sr_odi = rootView.findViewById(R.id.sr_odi);
        TextView sr_t20 = rootView.findViewById(R.id.sr_t20);
        TextView sr_ipl = rootView.findViewById(R.id.sr_ipl);


        PlayerItem info = ((PlayerActivity) getActivity()).getQuery();

        //Number Of Innings in Test,Odi,T20,IPL
        Matches_Innings_label.setText(R.string.innings);
        m_i_test.setText(info.getBowlstats().getTest().getinnbowled());
        m_i_odi.setText(info.getBowlstats().getOdi().getinnbowled());
        m_i_t20.setText(info.getBowlstats().getT20().getinnbowled());
        m_i_ipl.setText(info.getBowlstats().getIpl().getinnbowled());

        //Number Of Overs Bowled in Test,Odi,T20,IPL
        Innings_Overs_label.setText(R.string.overs);
        i_o_test.setText(info.getBowlstats().getTest().getoversbowled());
        i_o_odi.setText(info.getBowlstats().getOdi().getoversbowled());
        i_o_t20.setText(info.getBowlstats().getT20().getoversbowled());
        i_o_ipl.setText(info.getBowlstats().getIpl().getoversbowled());

        //Number Of Maidens in Test,Odi,T20,IPL
        NotOut_Maidens_Label.setText(R.string.maidens);
        no_m_test.setText(info.getBowlstats().getTest().getmaidens());
        no_m_odi.setText(info.getBowlstats().getOdi().getmaidens());
        no_m_t20.setText(info.getBowlstats().getT20().getmaidens());
        no_m_ipl.setText(info.getBowlstats().getIpl().getmaidens());

        //Number of Runs Conceded in Test,Odi,T20,IPL
        r_r_test.setText(info.getBowlstats().getTest().getrunsgiven());
        r_r_odi.setText(info.getBowlstats().getOdi().getrunsgiven());
        r_r_t20.setText(info.getBowlstats().getT20().getrunsgiven());
        r_r_ipl.setText(info.getBowlstats().getIpl().getrunsgiven());

        //Number Of Wickets taken in Test,Odi,T20,IPL
        highest_Wickets_label.setText(R.string.wickets);
        h_w_test.setText(info.getBowlstats().getTest().getwicktaken());
        h_w_odi.setText(info.getBowlstats().getOdi().getwicktaken());
        h_w_t20.setText(info.getBowlstats().getT20().getwicktaken());
        h_w_ipl.setText(info.getBowlstats().getIpl().getwicktaken());

        //Bes innings in Test,Odi,T20,IPL
        hundreds_best_label.setText(R.string.best);
        h_b_test.setText(info.getBowlstats().getTest().getbestinn());
        h_b_odi.setText(info.getBowlstats().getOdi().getbestinn());
        h_b_t20.setText(info.getBowlstats().getT20().getbestinn());
        h_b_ipl.setText(info.getBowlstats().getIpl().getbestinn());

        //Number Of 3 Wicket hauls in Test,Odi,T20,IPL
        fifties_3w_label.setText(R.string.wickets_3);
        f_3w_test.setText(info.getBowlstats().getTest().getthreewick());
        f_3w_odi.setText(info.getBowlstats().getOdi().getthreewick());
        f_3w_t20.setText(info.getBowlstats().getT20().getthreewick());
        f_3w_ipl.setText(info.getBowlstats().getIpl().getthreewick());

        //Number of 5 Wicket hauls in Test,Odi,T20,IPL
        fours_5wickets_label.setText(R.string.wickets_5);
        f_5w_test.setText(info.getBowlstats().getTest().getfivewick());
        f_5w_odi.setText(info.getBowlstats().getOdi().getfivewick());
        f_5w_t20.setText(info.getBowlstats().getT20().getfivewick());
        f_5w_ipl.setText(info.getBowlstats().getIpl().getfivewick());

        //Bowling Average in Test,Odi,T20,IPL
        sixes_average_label.setText(R.string.average);
        s_avg_test.setText(info.getBowlstats().getTest().getbowlingavg());
        s_avg_odi.setText(info.getBowlstats().getOdi().getbowlingavg());
        s_avg_t20.setText(info.getBowlstats().getT20().getbowlingavg());
        s_avg_ipl.setText(info.getBowlstats().getIpl().getbowlingavg());

        //Economy in Test,Odi,T20,IPL
        average_economy_label.setText(R.string.economy);
        avg_eco_test.setText(info.getBowlstats().getTest().geteconomy());
        avg_eco_odi.setText(info.getBowlstats().getOdi().geteconomy());
        avg_eco_t20.setText(info.getBowlstats().getT20().geteconomy());
        avg_eco_ipl.setText(info.getBowlstats().getIpl().geteconomy());

        //Strike Rate in Test,Odi,T20,IPL
        sr_test.setText(info.getBowlstats().getTest().getstrrate());
        sr_odi.setText(info.getBowlstats().getOdi().getstrrate());
        sr_t20.setText(info.getBowlstats().getT20().getstrrate());
        sr_ipl.setText(info.getBowlstats().getIpl().getstrrate());

        return rootView;
    }
}