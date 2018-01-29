package com.debut.ellipsis.freehit.Stats.Series;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesItem {
    @SerializedName("id")
    private String id;
    @SerializedName("title") 
    private String title;
    @SerializedName("date")
    private String date;
    @SerializedName("team1")
    private String team1;
    @SerializedName("team2")
    private String team2;
    @SerializedName("matches")
    private String matches;
    @SerializedName("result")
    private List<SeriesItem> result;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getMatches() {
        return matches;
    }

    public List<SeriesItem> getResults(){
        return result;
    }
}
