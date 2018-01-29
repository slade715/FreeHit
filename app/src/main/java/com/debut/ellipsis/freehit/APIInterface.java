package com.debut.ellipsis.freehit;

import com.debut.ellipsis.freehit.Home.RecentItem;
import com.debut.ellipsis.freehit.Matches.LiveMatches.LiveMatchCardItem;
import com.debut.ellipsis.freehit.Matches.PastMatches.PastMatchCardItem;
import com.debut.ellipsis.freehit.Matches.ScoreCard.CommentaryElements.CommentaryItem;
import com.debut.ellipsis.freehit.Matches.ScoreCard.ScoreCardItem;
import com.debut.ellipsis.freehit.Matches.UpcomingMatches.UpcomingMatchCardItem;
import com.debut.ellipsis.freehit.News.NewsArticleItem;
import com.debut.ellipsis.freehit.News.NewsItem;
import com.debut.ellipsis.freehit.Social.Polls.PollCardItem;
import com.debut.ellipsis.freehit.Stats.Player.PlayerItem;
import com.debut.ellipsis.freehit.Stats.Rankings.RankingItem;
import com.debut.ellipsis.freehit.Stats.Series.PerformanceItem;
import com.debut.ellipsis.freehit.Stats.Series.SeriesItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {


    @GET("version")
    Call<AppVersion> doGetAppVersion();

    @GET("news?")
    Call<NewsItem> doGetNewsListResources(@Query("key") String key);

    @GET("news?")
    Call<NewsArticleItem> doGetNewsArticle(@Query("id") String id,@Query("key") String key);

    @GET("playerbio?")
    Call<PlayerItem> doGetPlayerInfo(@Query("url") String url,@Query("key") String key);

    @GET("upcoming?")
    Call<UpcomingMatchCardItem> doGetUpcomingMatchListResources(@Query("max") String max,@Query("key") String key);

    @GET("upcoming?")
    Call<UpcomingMatchCardItem> doGetUpcomingCompleteMatchListResources(@Query("key") String key);

    @GET("playerlist?")
    Call<PlayerCountryItem> doGetPlayerList(@Query("fav") String search,@Query("key") String key);

    @GET("live?")
    Call<LiveMatchCardItem> doGetLiveMatchResources(@Query("key") String key);

    @GET("polls?")
    Call<PollCardItem> doGetPollsListResources(@Query("key") String key);

    @GET("polls?")
    Call<PollCardItem> doVotePollListResources(@Query("id") String id, @Query("cid") String cid,@Query("key") String key);

    @GET("past?")
    Call<PastMatchCardItem> doGetPastCardResources(@Query("max") String max, @Query("key") String key);

    @GET("past?")
    Call<PastMatchCardItem> doGetCompletePastCardResources(@Query("key") String key);

    @GET("news?")
    Call<NewsItem> doGetNewsArticleTeam(@Query("fav") String team,@Query("key") String key);

    @GET("upcoming?")
    Call<UpcomingMatchCardItem> doGetUpcomingFavTeam(@Query("fav") String team,@Query("key") String key);

    @GET("past?")
    Call<PastMatchCardItem> doGetPastFavTeam(@Query("fav") String team,@Query("key") String key);

    @GET("country?")
    Call<CountryItem> doGetCountryResources(@Query("key") String key);

    @GET("player?")
    Call<PlayerCountryItem> doGetFavTeamPlayers(@Query ("fav") String Team,@Query("key") String key);

    @GET("player?")
    Call<PlayerCountryItem> doGetTeamPlayers(@Query("fav") String PlayerName,@Query("key") String key);

    @GET("series?")
    Call<SeriesItem> doGetSeries(@Query("key") String key);

    @GET("past?")
    Call<PastMatchCardItem> doGetPastSeriesMatches(@Query("sfav") String team1, @Query("date") String date,@Query("key") String key);

    @GET("upcoming?")
    Call<UpcomingMatchCardItem> doGetUpComingSeriesMatches(@Query("sfav") String team1,@Query("date") String date,@Query("key") String key);

    @GET("scorecard?")
    Call<ScoreCardItem> doGetMatchScoreCard(@Query("ndid") String match_id,@Query("key") String key);

    @GET("scorecard?")
    Call<ScoreCardItem> doGetMatchScoreCardCache(@Query("ndid") String match_id,@Query("cache") String cahceCall,@Query("key") String key);

    @GET("scorecardlive?")
    Call<ScoreCardItem> doGetLiveMatchScoreCard(@Query("ndid") String match_id,@Query("key") String key);

    @GET("scorecardlive?")
    Call<ScoreCardItem> doGetLiveMatchScoreCardCache(@Query("ndid") String match_id,@Query("cache") String cacheCall,@Query("key") String key);

    @GET("ranking?")
    Call<RankingItem> doGetRankingResources(@Query("key") String key);

    @GET("ranking?")
    Call<RankingItem> doGetRankingCache(@Query("cache") String cacheCall,@Query("key") String key);

    @GET("series?")
    Call<PerformanceItem> doGetSeriesPerformance(@Query("id") String id,@Query("key") String key);

    @GET("comm?")
    Call<CommentaryItem> doGetLiveMatchCommentary(@Query("ndid") String match_id,@Query("key") String key);

    @GET("home?")
    Call<RecentItem> doGetRecentResources(@Query("key") String key);

    @GET("news?")
    Call<NewsItem> doGetHomeFavNews(@Query("max") int value,@Query("fav") String favTeam,@Query("key") String key);

    @GET("news?")
    Call<NewsItem> doGetHomeNews(@Query("max") String max,@Query("key") String key);


}