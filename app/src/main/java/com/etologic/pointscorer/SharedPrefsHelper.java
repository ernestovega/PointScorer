package com.etologic.pointscorer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import java.util.Locale;

import androidx.annotation.WorkerThread;

@WorkerThread
public class SharedPrefsHelper {

    //CONSTANTS
    private static final String FILE_NAME = "points_scorer_shared_prefs";
    private static final int MAX_PLAYERS = 8;
    private static final int DEFAULT_INITIAL_POINTS = 100;
    private static final String KEY_INITIAL_POINTS = "initial_points";
    private static final String KEY_INITIAL_CHECK_DONE = "initial_check_done";
    private static final String KEY_NAME = "name_";
    private static final String KEY_POINTS = "points_";
    private static final String KEY_COLOR = "color_";
    private static final String DEFAULT_PLAYER_NAME = "";

    //FIELDS
    private final SharedPreferences sharedPrefs;
    private int initialPoints;

    //CONSTRUCTOR
    public SharedPrefsHelper(Context context) {
        sharedPrefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        initialPoints = getInitialPoints();
    }
    public int getInitialPoints() {
        return sharedPrefs.getInt(KEY_INITIAL_POINTS, DEFAULT_INITIAL_POINTS);
    }

    public void initRecordsIfProceed() {
        new Thread(() -> {
            if (!sharedPrefs.getBoolean(KEY_INITIAL_CHECK_DONE, false)) {
                resetAll();
                sharedPrefs.edit().putBoolean(KEY_INITIAL_CHECK_DONE, true).apply();
            }
        }).run();
    }
    public void resetAll() {
        for (int i = 1; i <= MAX_PLAYERS; i++) {
            for (int x = 1; x <= i; x++) {
                String sId = String.format(Locale.ENGLISH, "%d%d", i, x);
                resetPlayer(Integer.valueOf(sId));
            }
        }
    }
    private void resetPlayer(int playerId) {
        savePlayerPoints(playerId, initialPoints);
        savePlayerColor(playerId, Color.WHITE);
        savePlayerName("", playerId);
    }
    private void resetPlayers(int[] playerIds) {
        for (int id : playerIds) resetPlayer(id);
    }

    public void saveInitialPoints(int points) {
        sharedPrefs.edit().putInt(KEY_INITIAL_POINTS, points).apply();
        initialPoints = points;
    }

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
