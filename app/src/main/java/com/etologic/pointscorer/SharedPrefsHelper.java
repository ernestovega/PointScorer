package com.etologic.pointscorer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.annotation.WorkerThread;

@WorkerThread
public class SharedPrefsHelper {

    //CONSTANTS
    private static final int DEFAULT_INITIAL_POINTS = 100;
    private static final String KEY_INITIAL_POINTS = "initial_points";
    private static final String KEY_INITIAL_CHECK_DONE = "initial_check_done";
    private static final String KEY_NAME   = "name_";
    private static final String KEY_POINTS = "points_";
    private static final String KEY_COLOR = "color_";
    private static final String DEFAULT_PLAYER_NAME = "";

    //FIELDS
    private final SharedPreferences sharedPrefs;
    private int initialPoints;

    //CONSTRUCTOR
    public SharedPrefsHelper(Context context, int playerId) {
        sharedPrefs = context.getSharedPreferences(String.valueOf(playerId), Context.MODE_PRIVATE);
        initialPoints = getInitialPoints();
    }

    //METHODS INITIAL POINTS
    public void initRecordsIfProceed() {
        if(!getInitialCheckDone()) {
            resetAll();
            setInitialCheckDone();
        }
    }
    private boolean getInitialCheckDone() { return sharedPrefs.getBoolean(KEY_INITIAL_CHECK_DONE, false); }
    private void setInitialCheckDone() { sharedPrefs.edit().putBoolean(KEY_INITIAL_CHECK_DONE, true).apply(); }
    public int getInitialPoints() {
        return sharedPrefs.getInt(KEY_INITIAL_POINTS, DEFAULT_INITIAL_POINTS);
    }
    public void saveInitialPoints(int points) {
        sharedPrefs.edit().putInt(KEY_INITIAL_POINTS, points).apply();
        initialPoints = points;
    }

    //region METHODS PLAYER
    public int getPlayerPoints(int playerId) {
        return sharedPrefs.getInt(KEY_POINTS + playerId, initialPoints);
    }
    public int getPlayerColor(int playerId) {
        return sharedPrefs.getInt(KEY_COLOR + playerId, Color.TRANSPARENT);
    }
    public String getPlayerName(int playerId) {
        return sharedPrefs.getString(KEY_NAME + playerId, DEFAULT_PLAYER_NAME);
    }
    public void savePlayerName(String name, int playerId) {
        sharedPrefs.edit().putString(KEY_NAME + playerId, name).apply();
    }
    public void savePlayerPoints(int points, int playerId) {
        sharedPrefs.edit().putInt(KEY_POINTS + playerId, points).apply();
    }
    public void savePlayerColor(int color, int playerId) {
        sharedPrefs.edit().putInt(KEY_COLOR + playerId, color).apply();
    }
}
