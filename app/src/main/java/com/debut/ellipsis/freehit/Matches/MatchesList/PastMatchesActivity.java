package com.debut.ellipsis.freehit.Matches.MatchesList;

import android.app.SearchManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.Matches.PastMatches.PastMatchCardItem;
import com.debut.ellipsis.freehit.Matches.PastMatches.PastMatchesListAdapter;
import com.debut.ellipsis.freehit.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.debut.ellipsis.freehit.MainActivity.key;

public class PastMatchesActivity extends AppCompatActivity {

    Toolbar toolbar;
    PastMatchesListAdapter PastMatchListAdapter;
    com.debut.ellipsis.freehit.Matches.UpcomingMatches.UpcomingMatchListAdapter UpcomingMatchListAdapter;
    ProgressBar mProgressBar;
    RecyclerView rv;
    SwipeRefreshLayout refresh_layout;
    FloatingActionButton fab;
    TextView mEmptyView;
    LinearLayoutManager mLinearLayoutManager;
    private SearchView searchView;
    boolean set = false;

    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_matches_complete_match_list);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View viewProgress = findViewById(R.id.progress);
        mProgressBar = viewProgress.findViewById(R.id.progress_bar);

        View viewFAB = findViewById(R.id.fab);
        fab = viewFAB.findViewById(R.id.common_fab);
        fab.hide();
        fab.setImageResource(R.drawable.arrow_up_vector);

        rv = findViewById(R.id.recycler_list);
        refresh_layout = findViewById(R.id.refresh_layout);

        mLinearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLinearLayoutManager);

        View viewToolbar = findViewById(R.id.toolbar_matches_list);

        toolbar = viewToolbar.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View viewEmpty = findViewById(R.id.empty);
        mEmptyView = viewEmpty.findViewById(R.id.empty_view);

        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                refresh_layout.setColorSchemeColors(Color.BLACK);
                break;
            default:
                refresh_layout.setColorSchemeResources(R.color.orange);
                fab.setColorFilter(Color.WHITE);
                break;
        }


        setTitle(R.string.past_list);
        PastMatchesList();
        refresh_layout.setColorSchemeResources(R.color.orange);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                @Override
                                                public void onRefresh() {
                                                    // Checking if connected or not on refresh
                                                    refresh_layout.setRefreshing(true);
                                                    PastMatchesList();
                                                    refresh_layout.setRefreshing(false);
                                                }
                                            }
        );


        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    fab.hide();

                } else {
                    fab.show();
                }
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int firstVisibleItemIndex = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemIndex > 0) {
                    mLinearLayoutManager.smoothScrollToPosition(rv, null, 0);
                }
            }
        });

    }

    void PastMatchesList() {
        Call<PastMatchCardItem> call = MainActivity.apiInterface.doGetCompletePastCardResources(key);
        call.enqueue(new Callback<PastMatchCardItem>() {
            @Override
            public void onResponse(Call<PastMatchCardItem> call, Response<PastMatchCardItem> response) {
                if (response.isSuccessful()) {
                    mProgressBar.setVisibility(View.GONE);
                    List<PastMatchCardItem> pastMatchCardItems = response.body().getResults();
                    if (pastMatchCardItems.size() == 0) {
                        mEmptyView.setVisibility(View.VISIBLE);
                        mEmptyView.setText(R.string.EmptyMatches);
                    }

                    PastMatchListAdapter = new PastMatchesListAdapter(pastMatchCardItems, getApplicationContext());
                    rv.setAdapter(PastMatchListAdapter);
                    set = true;
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mEmptyView.setVisibility(View.VISIBLE);
                    mEmptyView.setText(R.string.server_issues);
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.server_issues, Toast.LENGTH_SHORT);
                    toast.show();
                    set = true;
                }
            }

            @Override
            public void onFailure(Call<PastMatchCardItem> call, Throwable t) {
                mProgressBar.setVisibility(View.INVISIBLE);
                mEmptyView.setVisibility(View.VISIBLE);
                mEmptyView.setText(R.string.no_internet_connection);
                Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT);
                toast.show();
                call.cancel();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.matches_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                if (PastMatchListAdapter.getFilter() == null) {
                    final Toast toast = Toast.makeText(getApplicationContext(), "Wait for list to be populated", Toast.LENGTH_SHORT);
                    toast.show();
                    //For custom 1 second toast , rather than the regular 2 seconds toast
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 1000);
                } else {
                    PastMatchListAdapter.getFilter().filter(query);
                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                if (!set) {
                    final Toast toast = Toast.makeText(getApplicationContext(), "Wait for list to be populated", Toast.LENGTH_SHORT);
                    toast.show();
                    //For custom 1 second toast , rather than the regular 2 seconds toast
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 1000);
                } else {
                    PastMatchListAdapter.getFilter().filter(query);
                }
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(0, R.anim.exit_to_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        PastMatchesActivity.super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_to_right);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    private Rect mRect = new Rect();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        int[] location = new int[2];
        searchView.getLocationOnScreen(location);
        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + searchView.getWidth();
        mRect.bottom = location[1] + searchView.getHeight();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if (action == MotionEvent.ACTION_DOWN && !mRect.contains(x, y)) {
            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
