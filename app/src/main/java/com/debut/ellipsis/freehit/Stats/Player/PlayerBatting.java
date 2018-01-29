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

public class PlayerBatting extends ScrollViewFragment {

    public static final String TAG = PlayerBatting.class.getSimpleName();

    public static Fragment newInstance(int position) {
        PlayerBatting fragment = new PlayerBatting();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public PlayerBatting() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPosition = getArguments().getInt(ARG_POSITION);

        View rootView = inflater.inflate(R.layout.fragment_stats_player_batting_bowling, container, false);
        mScrollView = (NotifyingScrollView) rootView.findViewById(R.id.scrollview);
        setScrollViewOnScrollListener();

        TextView m_i_test = rootView.findViewById(R.id.m_i_test);
        TextView m_i_odi = rootView.findViewById(R.id.m_i_odi);
        TextView m_i_t20 = rootView.findViewById(R.id.m_i_t20);
        TextView m_i_ipl = rootView.findViewById(R.id.m_i_ipl);

        TextView i_o_test = rootView.findViewById(R.id.i_o_test);
        TextView i_o_odi = rootView.findViewById(R.id.i_o_odi);
        TextView i_o_t20 = rootView.findViewById(R.id.i_o_t20);
        TextView i_o_ipl = rootView.findViewById(R.id.i_o_ipl);

        TextView no_m_test = rootView.findViewById(R.id.no_m_test);
        TextView no_m_odi = rootView.findViewById(R.id.no_m_odi);
        TextView no_m_t20 = rootView.findViewById(R.id.no_m_t20);
        TextView no_m_ipl = rootView.findViewById(R.id.no_m_ipl);


        TextView r_r_test = rootView.findViewById(R.id.r_r_test);
        TextView r_r_odi = rootView.findViewById(R.id.r_r_odi);
        TextView r_r_t20 = rootView.findViewById(R.id.r_r_t20);
        TextView r_r_ipl = rootView.findViewById(R.id.r_r_ipl);


        TextView h_w_test = rootView.findViewById(R.id.h_w_test);
        TextView h_w_odi = rootView.findViewById(R.id.h_w_odi);
        TextView h_w_t20 = rootView.findViewById(R.id.h_w_t20);
        TextView h_w_ipl = rootView.findViewById(R.id.h_w_ipl);

        TextView h_b_test = rootView.findViewById(R.id.h_b_test);
        TextView h_b_odi = rootView.findViewById(R.id.h_b_odi);
        TextView h_b_t20 = rootView.findViewById(R.id.h_b_t20);
        TextView h_b_ipl = rootView.findViewById(R.id.h_b_ipl);

        TextView f_3w_test = rootView.findViewById(R.id.f_3w_test);
        TextView f_3w_odi = rootView.findViewById(R.id.f_3w_odi);
        TextView f_3w_t20 = rootView.findViewById(R.id.f_3w_t20);
        TextView f_3w_ipl = rootView.findViewById(R.id.f_3w_ipl);

        TextView f_5w_test = rootView.findViewById(R.id.f_5w_test);
        TextView f_5w_odi = rootView.findViewById(R.id.f_5w_odi);
        TextView f_5w_t20 = rootView.findViewById(R.id.f_5w_t20);
        TextView f_5w_ipl = rootView.findViewById(R.id.f_5w_ipl);

        TextView s_avg_test = rootView.findViewById(R.id.s_avg_test);
        TextView s_avg_odi = rootView.findViewById(R.id.s_avg_odi);
        TextView s_avg_t20 = rootView.findViewById(R.id.s_avg_t20);
        TextView s_avg_ipl = rootView.findViewById(R.id.s_avg_ipl);

        TextView avg_eco_test = rootView.findViewById(R.id.avg_eco_test);
        TextView avg_eco_odi = rootView.findViewById(R.id.avg_eco_odi);
        TextView avg_eco_t20 = rootView.findViewById(R.id.avg_eco_t20);
        TextView avg_eco_ipl = rootView.findViewById(R.id.avg_eco_ipl);

        TextView sr_test = rootView.findViewById(R.id.sr_test);
        TextView sr_odi = rootView.findViewById(R.id.sr_odi);
        TextView sr_t20 = rootView.findViewById(R.id.sr_t20);
        TextView sr_ipl = rootView.findViewById(R.id.sr_ipl);


        PlayerItem info = ((PlayerActivity) getActivity()).getQuery();


        //Number Of Matches in Test,Odi,T20,IPL
        m_i_test.setText(info.getBatstats().getTest().getMatches());
        m_i_odi.setText(info.getBatstats().getOdi().getMatches());
        m_i_t20.setText(info.getBatstats().getT20().getMatches());
        m_i_ipl.setText(info.getBatstats().getIpl().getMatches());

        //Number Of Innings Played in Test,Odi,T20,IPL
        i_o_test.setText(info.getBatstats().getTest().getInnbat());
        i_o_odi.setText(info.getBatstats().getOdi().getInnbat());
        i_o_t20.setText(info.getBatstats().getT20().getInnbat());
        i_o_ipl.setText(info.getBatstats().getIpl().getInnbat());

        //Number of Not Outs in Test,Odi,T20,IPL
        no_m_test.setText(info.getBatstats().getTest().getNotout());
        no_m_odi.setText(info.getBatstats().getOdi().getNotout());
        no_m_t20.setText(info.getBatstats().getT20().getNotout());
        no_m_ipl.setText(info.getBatstats().getIpl().getNotout());

        //Number Of Runs in Test,Odi,T20,IPL
        r_r_test.setText(info.getBatstats().getTest().getRuns());
        r_r_odi.setText(info.getBatstats().getOdi().getRuns());
        r_r_t20.setText(info.getBatstats().getT20().getRuns());
        r_r_ipl.setText(info.getBatstats().getIpl().getRuns());

        //Highest Score in Test,Odi,T20,IPL
        h_w_test.setText(info.getBatstats().getTest().getHighestinn());
        h_w_odi.setText(info.getBatstats().getOdi().getHighestinn());
        h_w_t20.setText(info.getBatstats().getT20().getHighestinn());
        h_w_ipl.setText(info.getBatstats().getIpl().getHighestinn());

        //Centuries in Test,Odi,T20,IPL
        h_b_test.setText(info.getBatstats().getTest().getHundreds());
        h_b_odi.setText(info.getBatstats().getOdi().getHundreds());
        h_b_t20.setText(info.getBatstats().getT20().getHundreds());
        h_b_ipl.setText(info.getBatstats().getIpl().getHundreds());

        //Fifties in Test,Odi,T20,IPL
        f_3w_test.setText(info.getBatstats().getTest().getFifties());
        f_3w_odi.setText(info.getBatstats().getOdi().getFifties());
        f_3w_t20.setText(info.getBatstats().getT20().getFifties());
        f_3w_ipl.setText(info.getBatstats().getIpl().getFifties());

        //Fours in Test,Odi,T20,IPL
        f_5w_test.setText(info.getBatstats().getTest().getFours());
        f_5w_odi.setText(info.getBatstats().getOdi().getFours());
        f_5w_t20.setText(info.getBatstats().getT20().getFours());
        f_5w_ipl.setText(info.getBatstats().getIpl().getFours());

        //Sixes in Test,Odi,T20,IPL
        s_avg_test.setText(info.getBatstats().getTest().getSixes());
        s_avg_odi.setText(info.getBatstats().getOdi().getSixes());
        s_avg_t20.setText(info.getBatstats().getT20().getSixes());
        s_avg_ipl.setText(info.getBatstats().getIpl().getSixes());

        //batting Average in Test,Odi,T20,IPL
        avg_eco_test.setText(info.getBatstats().getTest().getBatavg());
        avg_eco_odi.setText(info.getBatstats().getOdi().getBatavg());
        avg_eco_t20.setText(info.getBatstats().getT20().getBatavg());
        avg_eco_ipl.setText(info.getBatstats().getIpl().getBatavg());

        //Batting Strike Rate in Test,Odi,T20,IPL
        sr_test.setText(info.getBatstats().getTest().getBatstr());
        sr_odi.setText(info.getBatstats().getOdi().getBatstr());
        sr_t20.setText(info.getBatstats().getT20().getBatstr());
        sr_ipl.setText(info.getBatstats().getIpl().getBatstr());


        return rootView;
    }
}