package com.debut.ellipsis.freehit.Stats.Team;


public class TeamCardItem {

    private String mTeamIconURL;

    private int mTeamName;


    public TeamCardItem(String TeamIconURL, int TeamName) {
        mTeamIconURL = TeamIconURL;
        mTeamName = TeamName;

    }

    public String getmTeamIconURL() {
        return mTeamIconURL;
    }

    public int getmTeamName() {
        return mTeamName;
    }

}