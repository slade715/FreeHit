package com.debut.ellipsis.freehit;

import com.google.gson.annotations.SerializedName;

public class AppVersion {

    @SerializedName("minver")
    String minver;

    @SerializedName("currver")
    String currver;

    @SerializedName("changelog")
    String changelog;

    public String getChangelog() {
        return changelog;
    }

    public String getCurrver() {
        return currver;
    }

    public String getMinver() {
        return minver;
    }
}
