package com.etologic.pointscorer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class aMainActivity extends AppCompatActivity {

    //CONSTANTS
    public static final int DEFAULT_INITIAL_POINTS = 50;
    public static final String FILE_NAME = "points_scorer_shared_prefs";
    public static final String KEY_INITIAL_POINTS = "initial_points";

    //VIEWS
    @BindView(R.id.tietMainInitialPoints) TextInputEditText tietInitialPoints;

    //FIELDS
    private SharedPreferences sharedPreferences;

    //EVENTS
    @OnTextChanged(R.id.tietMainInitialPoints) void onInitialPointsChanged(CharSequence text) {
        try {
            int iNewInitialPoints = Integer.valueOf(text.toString());
            saveInitialPoints(iNewInitialPoints);
        } catch (NumberFormatException nfe) {
            tietInitialPoints.setError(getString(R.string.error_initial_points));
        }
    }
    private void saveInitialPoints(int newInitialPoints) { sharedPreferences.edit().putInt(KEY_INITIAL_POINTS, newInitialPoints).apply(); }
    private int getInitialPoints() { return sharedPreferences.getInt(KEY_INITIAL_POINTS, DEFAULT_INITIAL_POINTS); }
    @OnClick(R.id.btOnePlayer) void onOnePlayerButtonClick() { startActivity(new Intent(this, bOnePlayerActivity.class)); }
    @OnClick(R.id.btTwoPlayers) void onTwoPlayersButtonClick() { startActivity(new Intent(this, cTwoPlayersActivity.class)); }
    @OnClick(R.id.btThreePlayers) void onThreePlayersButtonClick() { startActivity(new Intent(this, dThreePlayersActivity.class)); }
    @OnClick(R.id.btFourPlayers) void onFourPlayersButtonClick() { startActivity(new Intent(this, eFourPlayersActivity.class)); }
    @OnClick(R.id.btFivePlayers) void onFivePlayersButtonClick() { startActivity(new Intent(this, fFivePlayersActivity.class)); }
    @OnClick(R.id.btSixPlayers) void onSixPlayersButtonClick() { startActivity(new Intent(this, gSixPlayersActivity.class)); }
    @OnClick(R.id.btSevenPlayers) void onSevenPlayersButtonClick() { startActivity(new Intent(this, hSevenPlayersActivity.class)); }
    @OnClick(R.id.btEightPlayers) void onEightPlayersButtonClick() { startActivity(new Intent(this, iEightPlayersActivity.class)); }
    //LIFECYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main_activity);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        getInitialPoints();
    }
}
