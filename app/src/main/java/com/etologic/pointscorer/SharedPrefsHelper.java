package com.etologic.pointscorer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;

@WorkerThread
public class SharedPrefsHelper {
    //region CONSTANTS
    private static final int DEFAULT_INITIAL_POINTS = 100;
    private static final String FILE_NAME = "points_scorer_shared_prefs";
    private static final String KEY_INITIAL_POINTS = "initial_points";
    private static final String KEY_INITIAL_CHECK_DONE = "initial_check_done";
    private static final String KEY_POINTS_ONE_PLAYER = "one_player_points";
    private static final String KEY_POINTS_TWO_PLAYERS_P1 = "two_players_p1_points";
    private static final String KEY_POINTS_TWO_PLAYERS_P2 = "two_players_p2_points";
    private static final String KEY_POINTS_THREE_PLAYERS_P1 = "three_players_p1_points";
    private static final String KEY_POINTS_THREE_PLAYERS_P2 = "three_players_p2_points";
    private static final String KEY_POINTS_THREE_PLAYERS_P3 = "three_players_p3_points";
    private static final String KEY_POINTS_FOUR_PLAYERS_P1 = "four_players_p1_points";
    private static final String KEY_POINTS_FOUR_PLAYERS_P2 = "four_players_p2_points";
    private static final String KEY_POINTS_FOUR_PLAYERS_P3 = "four_players_p3_points";
    private static final String KEY_POINTS_FOUR_PLAYERS_P4 = "four_players_p4_points";
    private static final String KEY_POINTS_FIVE_PLAYERS_P1 = "five_players_p1_points";
    private static final String KEY_POINTS_FIVE_PLAYERS_P2 = "five_players_p2_points";
    private static final String KEY_POINTS_FIVE_PLAYERS_P3 = "five_players_p3_points";
    private static final String KEY_POINTS_FIVE_PLAYERS_P4 = "five_players_p4_points";
    private static final String KEY_POINTS_FIVE_PLAYERS_P5 = "five_players_p5_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P1 = "six_players_p1_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P2 = "six_players_p2_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P3 = "six_players_p3_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P4 = "six_players_p4_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P5 = "six_players_p5_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P6 = "six_players_p6_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P1 = "seven_players_p1_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P2 = "seven_players_p2_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P3 = "seven_players_p3_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P4 = "seven_players_p4_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P5 = "seven_players_p5_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P6 = "seven_players_p6_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P7 = "seven_players_p7_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P1 = "eight_players_p1_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P2 = "eight_players_p2_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P3 = "eight_players_p3_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P4 = "eight_players_p4_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P5 = "eight_players_p5_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P6 = "eight_players_p6_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P7 = "eight_players_p7_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P8 = "eight_players_p8_points";
    //endregion
    //region FIELDS
    private final SharedPreferences sharedPrefs;
    private int initialPoints;
    //endregion
    //region CONSTRUCTOR
    SharedPrefsHelper(Context context) {
        sharedPrefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        initialPoints = getInitialPoints();
    }
    //endregion
    // region METHODS INITIAL_POINTS
    void initRecordsIfProceed() {
        if(!getInitialCheckDone()) {
            resetAll();
            setInitialCheckDone();
        }
    }
    private boolean getInitialCheckDone() { return sharedPrefs.getBoolean(KEY_INITIAL_CHECK_DONE, false); }
    private void setInitialCheckDone() { sharedPrefs.edit().putBoolean(KEY_INITIAL_CHECK_DONE, true).apply(); }
    public void resetAll() {
        new Thread(() -> {
            saveOnePlayerPoints(initialPoints);

            saveTwoPlayerPointsP1(initialPoints);
            saveTwoPlayerPointsP2(initialPoints);

            saveThreePlayerPointsP1(initialPoints);
            saveThreePlayerPointsP2(initialPoints);
            saveThreePlayerPointsP3(initialPoints);

            saveFourPlayerPointsP1(initialPoints);
            saveFourPlayerPointsP2(initialPoints);
            saveFourPlayerPointsP3(initialPoints);
            saveFourPlayerPointsP4(initialPoints);

            saveFivePlayerPointsP1(initialPoints);
            saveFivePlayerPointsP2(initialPoints);
            saveFivePlayerPointsP3(initialPoints);
            saveFivePlayerPointsP4(initialPoints);
            saveFivePlayerPointsP5(initialPoints);

            saveSixPlayerPointsP1(initialPoints);
            saveSixPlayerPointsP2(initialPoints);
            saveSixPlayerPointsP3(initialPoints);
            saveSixPlayerPointsP4(initialPoints);
            saveSixPlayerPointsP5(initialPoints);
            saveSixPlayerPointsP6(initialPoints);

            saveSevenPlayerPointsP1(initialPoints);
            saveSevenPlayerPointsP2(initialPoints);
            saveSevenPlayerPointsP3(initialPoints);
            saveSevenPlayerPointsP4(initialPoints);
            saveSevenPlayerPointsP5(initialPoints);
            saveSevenPlayerPointsP6(initialPoints);
            saveSevenPlayerPointsP7(initialPoints);

            saveEightPlayerPointsP1(initialPoints);
            saveEightPlayerPointsP2(initialPoints);
            saveEightPlayerPointsP3(initialPoints);
            saveEightPlayerPointsP4(initialPoints);
            saveEightPlayerPointsP5(initialPoints);
            saveEightPlayerPointsP6(initialPoints);
            saveEightPlayerPointsP7(initialPoints);
            saveEightPlayerPointsP8(initialPoints);
        }).run();
    }
    public int getInitialPoints() {
        return sharedPrefs.getInt(KEY_INITIAL_POINTS, DEFAULT_INITIAL_POINTS);
    }
    public void saveInitialPoints(int points) {
        sharedPrefs.edit().putInt(KEY_INITIAL_POINTS, points).apply();
        initialPoints = points;
    }
    //endregion
    //region METHODS ONE PLAYER
    public int getOnePlayerPoints() { return sharedPrefs.getInt(KEY_POINTS_ONE_PLAYER, initialPoints); }
    public void saveOnePlayerPoints(int points) { sharedPrefs.edit().putInt(KEY_POINTS_ONE_PLAYER, points).apply(); }
    //endregion
    //region METHODS TWO PLAYERS
    public int getTwoPlayerPointsP1() { return sharedPrefs.getInt(KEY_POINTS_TWO_PLAYERS_P1, initialPoints); }
    public int getTwoPlayerPointsP2() { return sharedPrefs.getInt(KEY_POINTS_TWO_PLAYERS_P2, initialPoints); }
    public void saveTwoPlayerPointsP1(int points) { sharedPrefs.edit().putInt(KEY_POINTS_TWO_PLAYERS_P1, points).apply(); }
    public void saveTwoPlayerPointsP2(int points) { sharedPrefs.edit().putInt(KEY_POINTS_TWO_PLAYERS_P2, points).apply(); }
    //endregion
    //region METHODS THREE PLAYERS
    public int getThreePlayerPointsP1() { return sharedPrefs.getInt(KEY_POINTS_THREE_PLAYERS_P1, initialPoints); }
    public int getThreePlayerPointsP2() { return sharedPrefs.getInt(KEY_POINTS_THREE_PLAYERS_P2, initialPoints); }
    public int getThreePlayerPointsP3() { return sharedPrefs.getInt(KEY_POINTS_THREE_PLAYERS_P3, initialPoints); }
    public void saveThreePlayerPointsP1(int points) { sharedPrefs.edit().putInt(KEY_POINTS_THREE_PLAYERS_P1, points).apply(); }
    public void saveThreePlayerPointsP2(int points) { sharedPrefs.edit().putInt(KEY_POINTS_THREE_PLAYERS_P2, points).apply(); }
    public void saveThreePlayerPointsP3(int points) { sharedPrefs.edit().putInt(KEY_POINTS_THREE_PLAYERS_P3, points).apply(); }
    //endregion
    //region METHODS FOUR PLAYERS
    public int getFourPlayerPointsP1() { return sharedPrefs.getInt(KEY_POINTS_FOUR_PLAYERS_P1, initialPoints); }
    public int getFourPlayerPointsP2() { return sharedPrefs.getInt(KEY_POINTS_FOUR_PLAYERS_P2, initialPoints); }
    public int getFourPlayerPointsP3() { return sharedPrefs.getInt(KEY_POINTS_FOUR_PLAYERS_P3, initialPoints); }
    public int getFourPlayerPointsP4() { return sharedPrefs.getInt(KEY_POINTS_FOUR_PLAYERS_P4, initialPoints); }
    public void saveFourPlayerPointsP1(int points) { sharedPrefs.edit().putInt(KEY_POINTS_FOUR_PLAYERS_P1, points).apply(); }
    public void saveFourPlayerPointsP2(int points) { sharedPrefs.edit().putInt(KEY_POINTS_FOUR_PLAYERS_P2, points).apply(); }
    public void saveFourPlayerPointsP3(int points) { sharedPrefs.edit().putInt(KEY_POINTS_FOUR_PLAYERS_P3, points).apply(); }
    public void saveFourPlayerPointsP4(int points) { sharedPrefs.edit().putInt(KEY_POINTS_FOUR_PLAYERS_P4, points).apply(); }
    //endregion
    //region METHODS FIVE PLAYERS
    public int getFivePlayerPointsP1() { return sharedPrefs.getInt(KEY_POINTS_FIVE_PLAYERS_P1, initialPoints); }
    public int getFivePlayerPointsP2() { return sharedPrefs.getInt(KEY_POINTS_FIVE_PLAYERS_P2, initialPoints); }
    public int getFivePlayerPointsP3() { return sharedPrefs.getInt(KEY_POINTS_FIVE_PLAYERS_P3, initialPoints); }
    public int getFivePlayerPointsP4() { return sharedPrefs.getInt(KEY_POINTS_FIVE_PLAYERS_P4, initialPoints); }
    public int getFivePlayerPointsP5() { return sharedPrefs.getInt(KEY_POINTS_FIVE_PLAYERS_P5, initialPoints); }
    public void saveFivePlayerPointsP1(int points) { sharedPrefs.edit().putInt(KEY_POINTS_FIVE_PLAYERS_P1, points).apply(); }
    public void saveFivePlayerPointsP2(int points) { sharedPrefs.edit().putInt(KEY_POINTS_FIVE_PLAYERS_P2, points).apply(); }
    public void saveFivePlayerPointsP3(int points) { sharedPrefs.edit().putInt(KEY_POINTS_FIVE_PLAYERS_P3, points).apply(); }
    public void saveFivePlayerPointsP4(int points) { sharedPrefs.edit().putInt(KEY_POINTS_FIVE_PLAYERS_P4, points).apply(); }
    public void saveFivePlayerPointsP5(int points) { sharedPrefs.edit().putInt(KEY_POINTS_FIVE_PLAYERS_P5, points).apply(); }
    //endregion
    //region METHODS SIX PLAYERS
    public int getSixPlayerPointsP1() { return sharedPrefs.getInt(KEY_POINTS_SIX_PLAYERS_P1, initialPoints); }
    public int getSixPlayerPointsP2() { return sharedPrefs.getInt(KEY_POINTS_SIX_PLAYERS_P2, initialPoints); }
    public int getSixPlayerPointsP3() { return sharedPrefs.getInt(KEY_POINTS_SIX_PLAYERS_P3, initialPoints); }
    public int getSixPlayerPointsP4() { return sharedPrefs.getInt(KEY_POINTS_SIX_PLAYERS_P4, initialPoints); }
    public int getSixPlayerPointsP5() { return sharedPrefs.getInt(KEY_POINTS_SIX_PLAYERS_P5, initialPoints); }
    public int getSixPlayerPointsP6() { return sharedPrefs.getInt(KEY_POINTS_SIX_PLAYERS_P6, initialPoints); }
    public void saveSixPlayerPointsP1(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SIX_PLAYERS_P1, points).apply(); }
    public void saveSixPlayerPointsP2(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SIX_PLAYERS_P2, points).apply(); }
    public void saveSixPlayerPointsP3(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SIX_PLAYERS_P3, points).apply(); }
    public void saveSixPlayerPointsP4(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SIX_PLAYERS_P4, points).apply(); }
    public void saveSixPlayerPointsP5(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SIX_PLAYERS_P5, points).apply(); }
    public void saveSixPlayerPointsP6(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SIX_PLAYERS_P6, points).apply(); }
    //endregion
    //region METHODS SEVEN PLAYERS
    public int getSevenPlayerPointsP1() { return sharedPrefs.getInt(KEY_POINTS_SEVEN_PLAYERS_P1, initialPoints); }
    public int getSevenPlayerPointsP2() { return sharedPrefs.getInt(KEY_POINTS_SEVEN_PLAYERS_P2, initialPoints); }
    public int getSevenPlayerPointsP3() { return sharedPrefs.getInt(KEY_POINTS_SEVEN_PLAYERS_P3, initialPoints); }
    public int getSevenPlayerPointsP4() { return sharedPrefs.getInt(KEY_POINTS_SEVEN_PLAYERS_P4, initialPoints); }
    public int getSevenPlayerPointsP5() { return sharedPrefs.getInt(KEY_POINTS_SEVEN_PLAYERS_P5, initialPoints); }
    public int getSevenPlayerPointsP6() { return sharedPrefs.getInt(KEY_POINTS_SEVEN_PLAYERS_P6, initialPoints); }
    public int getSevenPlayerPointsP7() { return sharedPrefs.getInt(KEY_POINTS_SEVEN_PLAYERS_P7, initialPoints); }
    public void saveSevenPlayerPointsP1(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SEVEN_PLAYERS_P1, points).apply(); }
    public void saveSevenPlayerPointsP2(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SEVEN_PLAYERS_P2, points).apply(); }
    public void saveSevenPlayerPointsP3(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SEVEN_PLAYERS_P3, points).apply(); }
    public void saveSevenPlayerPointsP4(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SEVEN_PLAYERS_P4, points).apply(); }
    public void saveSevenPlayerPointsP5(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SEVEN_PLAYERS_P5, points).apply(); }
    public void saveSevenPlayerPointsP6(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SEVEN_PLAYERS_P6, points).apply(); }
    public void saveSevenPlayerPointsP7(int points) { sharedPrefs.edit().putInt(KEY_POINTS_SEVEN_PLAYERS_P7, points).apply(); }
    //endregion
    //region METHODS EIGHT PLAYERS
    public int getEightPlayerPointsP1() { return sharedPrefs.getInt(KEY_POINTS_EIGHT_PLAYERS_P1, initialPoints); }
    public int getEightPlayerPointsP2() { return sharedPrefs.getInt(KEY_POINTS_EIGHT_PLAYERS_P2, initialPoints); }
    public int getEightPlayerPointsP3() { return sharedPrefs.getInt(KEY_POINTS_EIGHT_PLAYERS_P3, initialPoints); }
    public int getEightPlayerPointsP4() { return sharedPrefs.getInt(KEY_POINTS_EIGHT_PLAYERS_P4, initialPoints); }
    public int getEightPlayerPointsP5() { return sharedPrefs.getInt(KEY_POINTS_EIGHT_PLAYERS_P5, initialPoints); }
    public int getEightPlayerPointsP6() { return sharedPrefs.getInt(KEY_POINTS_EIGHT_PLAYERS_P6, initialPoints); }
    public int getEightPlayerPointsP7() { return sharedPrefs.getInt(KEY_POINTS_EIGHT_PLAYERS_P7, initialPoints); }
    public int getEightPlayerPointsP8() { return sharedPrefs.getInt(KEY_POINTS_EIGHT_PLAYERS_P8, initialPoints); }
    public void saveEightPlayerPointsP1(int points) { sharedPrefs.edit().putInt(KEY_POINTS_EIGHT_PLAYERS_P1, points).apply(); }
    public void saveEightPlayerPointsP2(int points) { sharedPrefs.edit().putInt(KEY_POINTS_EIGHT_PLAYERS_P2, points).apply(); }
    public void saveEightPlayerPointsP3(int points) { sharedPrefs.edit().putInt(KEY_POINTS_EIGHT_PLAYERS_P3, points).apply(); }
    public void saveEightPlayerPointsP4(int points) { sharedPrefs.edit().putInt(KEY_POINTS_EIGHT_PLAYERS_P4, points).apply(); }
    public void saveEightPlayerPointsP5(int points) { sharedPrefs.edit().putInt(KEY_POINTS_EIGHT_PLAYERS_P5, points).apply(); }
    public void saveEightPlayerPointsP6(int points) { sharedPrefs.edit().putInt(KEY_POINTS_EIGHT_PLAYERS_P6, points).apply(); }
    public void saveEightPlayerPointsP7(int points) { sharedPrefs.edit().putInt(KEY_POINTS_EIGHT_PLAYERS_P7, points).apply(); }
    public void saveEightPlayerPointsP8(int points) { sharedPrefs.edit().putInt(KEY_POINTS_EIGHT_PLAYERS_P8, points).apply(); }
    //endregion
}
