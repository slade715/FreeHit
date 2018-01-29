package com.debut.ellipsis.freehit.Stats.Player;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.debut.ellipsis.freehit.R;
import com.debut.ellipsis.freehit.Stats.Player.parallaxviewpager.ScrollViewFragment;

import java.util.List;


public class PlayerInfo extends ScrollViewFragment {

    public static final String TAG = PlayerInfo.class.getSimpleName();

    public static Fragment newInstance(int position) {
        PlayerInfo fragment = new PlayerInfo();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public PlayerInfo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPosition = getArguments().getInt(ARG_POSITION);

        View rootView = inflater.inflate(R.layout.fragment_stats_player_info, container, false);
        mScrollView = rootView.findViewById(R.id.scrollview);
        setScrollViewOnScrollListener();

        TextView DOB = rootView.findViewById(R.id.DOB);
        TextView BattingStyle = rootView.findViewById(R.id.battingStyle);
        TextView BowlingStyle = rootView.findViewById((R.id.BowlingStyle));
        TextView TeamsPlayed = rootView.findViewById((R.id.Teams_Played));
        TextView description = rootView.findViewById((R.id.PlayerInfo));

        PlayerItem info = ((PlayerActivity) getActivity()).getQuery();

        DOB.setText(info.getDob());

        BattingStyle.setText(info.getBatstyle());

        BowlingStyle.setText(info.getBowlstyle());

        TeamsPlayed.setText(info.getTeamsplayed());

        TextView odiBat = rootView.findViewById(R.id.odibattingRanking);
        TextView testBat = rootView.findViewById(R.id.testBattingRanking);
        TextView t20Bat = rootView.findViewById(R.id.T20BattingRanking);

        String ODI_Batting_rank = "-";
        String TEST_Batting_rank = "-";
        String T20_Batting_rank = "-";


        List<String> batting_rank = info.getBatrank();
        for (int i = 0; i < batting_rank.size(); i++) {
            String batting_format_rank = batting_rank.get(i);

            if (TEST_Batting_rank.equals("-")) {
                if (batting_format_rank.contains("Test -")) {
                    String[] parts = batting_format_rank.split("Test - ");
                    TEST_Batting_rank = "1";
                    testBat.setText(parts[1]);
                } else {
                    testBat.setText("--");
                }
            }

            if (ODI_Batting_rank.equals("-")) {
                if (batting_format_rank.contains("ODI - ")) {
                    String[] parts = batting_format_rank.split("ODI - ");
                    ODI_Batting_rank = "1";
                    odiBat.setText(parts[1]);
                } else {
                    odiBat.setText("--");
                }
            }

            if (T20_Batting_rank.equals("-")) {
                if (batting_format_rank.contains("T20I - ")) {
                    String[] parts = batting_format_rank.split("T20I - ");
                    T20_Batting_rank = "1";
                    t20Bat.setText(parts[1]);
                } else {
                    t20Bat.setText("--");
                }
            }
        }


        TextView odibowl = rootView.findViewById(R.id.odiBowlingRanking);
        TextView testbowl = rootView.findViewById(R.id.testBowlingRanking);
        TextView t20bowl = rootView.findViewById(R.id.T20BowlingRanking);


        String ODI_Bowling_rank = "-";
        String TEST_Bowling_rank = "-";
        String T20_Bowling_rank = "-";

        List<String> bowling_rank = info.getBowlrank();
        for (int i = 0; i < bowling_rank.size(); i++) {
            String bowling_format_rank = bowling_rank.get(i);

            if (TEST_Bowling_rank.equals("-")) {
                if (bowling_format_rank.contains("Test -")) {
                    String[] parts = bowling_format_rank.split("Test - ");
                    TEST_Bowling_rank = "1";
                    testbowl.setText(parts[1]);
                } else {
                    testbowl.setText("--");
                }
            }

            if (ODI_Bowling_rank.equals("-")) {
                if (bowling_format_rank.contains("ODI - ")) {
                    String[] parts = bowling_format_rank.split("ODI - ");
                    ODI_Bowling_rank = "1";
                    odibowl.setText(parts[1]);
                } else {
                    odibowl.setText("--");
                }
            }

            if (T20_Bowling_rank.equals("-")) {
                if (bowling_format_rank.contains("T20I - ")) {
                    String[] parts = bowling_format_rank.split("T20I - ");
                    T20_Bowling_rank = "1";
                    t20bowl.setText(parts[1]);
                } else {
                    t20bowl.setText("--");
                }
            }
        }


        TextView mom_test = rootView.findViewById(R.id.MOTM_Test);
        TextView mom_odi = rootView.findViewById(R.id.MOTM_Odi);
        TextView mom_t20 = rootView.findViewById(R.id.MOTM_T20);
        TextView mom_wc = rootView.findViewById(R.id.MOTM_WorldCup);
        TextView mom_ipl = rootView.findViewById(R.id.MOTM_IPL);
        TextView mom_cl = rootView.findViewById(R.id.MOTM_CL);

        String ODI_MOM = "-";
        String TEST_MOM = "-";
        String T20_MOM = "-";
        String WorldCup_MOM = "-";
        String IPL_MOM = "-";
        String CL_MOM = "-";

        List<String> MOTM = info.getManofthematch();
        for (int i = 0; i < MOTM.size(); i++) {
            String Motm_format = MOTM.get(i);
            if (TEST_MOM.equals("-")) {
                if (Motm_format.contains("Test -")) {
                    String[] parts = Motm_format.split("Test - ");
                    TEST_MOM = "1";
                    mom_test.setText(parts[1]);
                } else {
                    mom_test.setText("--");
                }
            }

            if (ODI_MOM.equals("-")) {
                if (Motm_format.contains("ODI - ")) {
                    String[] parts = Motm_format.split("ODI - ");
                    ODI_MOM = "1";
                    mom_odi.setText(parts[1]);
                } else {
                    mom_odi.setText("--");
                }
            }

            if (T20_MOM.equals("-")) {
                if (Motm_format.contains("T20I - ")) {
                    String[] parts = Motm_format.split("T20I - ");
                    T20_MOM = "1";
                    mom_t20.setText(parts[1]);
                } else {
                    mom_t20.setText("--");
                }
            }

            if (WorldCup_MOM.equals("-")) {
                if (Motm_format.contains(" World Cup - ")) {
                    String[] parts = Motm_format.split(" World Cup - ");
                    WorldCup_MOM = "1";
                    mom_wc.setText(parts[1]);
                } else {
                    mom_wc.setText("--");
                }
            }

            if (IPL_MOM.equals("-")) {
                if (Motm_format.contains(" IPL - ")) {
                    String[] parts = Motm_format.split(" IPL - ");
                    IPL_MOM = "1";
                    mom_ipl.setText(parts[1]);
                } else {
                    mom_ipl.setText("--");
                }
            }

            if (CL_MOM.equals("-")) {
                if (Motm_format.contains(" CL - ")) {
                    String[] parts = Motm_format.split(" CL - ");
                    CL_MOM = "1";
                    mom_cl.setText(parts[1]);
                } else {
                    mom_cl.setText("--");
                }
            }
        }

        description.setText(info.getDesc());



        return rootView;
    }
}
