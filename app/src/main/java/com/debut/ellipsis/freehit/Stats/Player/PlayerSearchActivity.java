package com.debut.ellipsis.freehit.Stats.Player;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.debut.ellipsis.freehit.MainActivity;
import com.debut.ellipsis.freehit.PlayerCountryItem;
import com.debut.ellipsis.freehit.R;
import com.debut.ellipsis.freehit.Stats.Team.TeamPlayerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.debut.ellipsis.freehit.MainActivity.key;

public class PlayerSearchActivity extends AppCompatActivity {

    EditText playerSearchEdit;
    RecyclerView recyclerView;
    TextView emptyview;
    ProgressBar mProgressBar;

    protected void onCreate(Bundle savedInstanceState) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        setContentView(R.layout.fragment_stats_player_search_fragment);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        playerSearchEdit = findViewById(R.id.editText_player);
        VectorDrawableCompat drawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.ic_search, playerSearchEdit.getContext().getTheme());
        playerSearchEdit.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableCompat, null, null, null);

        View viewToolbar = findViewById(R.id.toolbar_player_search);

        Toolbar toolbar = viewToolbar.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Player Search");

        RelativeLayout parentLayout = findViewById(R.id.parent_layout);

        RelativeLayout relativeLayout = findViewById(R.id.player_list_layout);

        View EmptyView = findViewById(R.id.empty);
        emptyview = EmptyView.findViewById(R.id.empty_view);

        View viewProgress = findViewById(R.id.progress);
        mProgressBar = viewProgress.findViewById(R.id.progress_bar);

        recyclerView = findViewById(R.id.recycler_list);
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_YES:
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.night_background));
                parentLayout.setBackgroundColor(getResources().getColor(R.color.night_background));
                toolbar.setBackgroundColor(getResources().getColor(R.color.dark));
                emptyview.setTextColor(Color.WHITE);
                recyclerView.setBackgroundColor(getResources().getColor(R.color.night_background));
                Window window = getWindow();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.BLACK);
                }

                break;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            playerSearchEdit.addTextChangedListener(new TextWatcher() {
                int length = 0;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    String str = playerSearchEdit.getText().toString();
                    length = str.length();
                    if (length >= 3) {
                        PlayerSearch(s);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            playerSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        // do something, e.g. set your TextView here via .setText()
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        return true;
                    }
                    return false;
                }
            });
        } else {
            emptyview.setVisibility(View.VISIBLE);
            emptyview.setText(R.string.no_internet_connection);
            Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    void PlayerSearch(final CharSequence s) {
        Call<PlayerCountryItem> playerInfo = MainActivity.apiInterface.doGetPlayerList(s.toString(),key);
        playerInfo.enqueue(new Callback<PlayerCountryItem>() {
            @Override
            public void onResponse(Call<PlayerCountryItem> call, Response<PlayerCountryItem> response) {
                if (response.isSuccessful()) {
                    List<PlayerCountryItem> playerCountryItems = response.body().getResults();

                    for (int i = 0; i < playerCountryItems.size(); i++) {
                        recyclerView.setAdapter(new TeamPlayerAdapter(playerCountryItems, R.layout.country_picker_row, getApplicationContext()));
                    }

                    //When not found in player list
                    if (playerCountryItems.isEmpty()) {

                        Call<PlayerCountryItem> call1 = MainActivity.apiInterface.doGetTeamPlayers(s.toString(),key);
                        call1.enqueue(new Callback<PlayerCountryItem>() {
                            @Override
                            public void onResponse(Call<PlayerCountryItem> call, Response<PlayerCountryItem> response) {
                                if (response.isSuccessful()) {
                                    List<PlayerCountryItem> playerCountryItems = response.body().getResults();
                                    for (int i = 0; i < playerCountryItems.size(); i++) {
                                        recyclerView.setAdapter(new TeamPlayerAdapter(playerCountryItems, R.layout.country_picker_row, getApplicationContext()));
                                    }
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onFailure(Call<PlayerCountryItem> call, Throwable t) {

                            }
                        });
                    }
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), R.string.server_issues, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlayerCountryItem> call, Throwable t) {
                Toast.makeText(getApplicationContext(),R.string.no_internet_connection,Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        PlayerSearchActivity.super.onBackPressed();
        overridePendingTransition(0, R.anim.exit_to_right);

    }

    private Rect mRect = new Rect();
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        int[] location = new int[2];
        playerSearchEdit.getLocationOnScreen(location);
        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + playerSearchEdit.getWidth();
        mRect.bottom = location[1] + playerSearchEdit.getHeight();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if (action == MotionEvent.ACTION_DOWN && !mRect.contains(x, y)) {
            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(playerSearchEdit.getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
