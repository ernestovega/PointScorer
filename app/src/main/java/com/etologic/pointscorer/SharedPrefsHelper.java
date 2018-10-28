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


    private static final String KEY_NAME_ONE_PLAYER   = "one_player_name";
    private static final String KEY_POINTS_ONE_PLAYER = "one_player_points";

    private static final String KEY_NAME_TWO_PLAYER_1 = "two_player_1_name";
    private static final String KEY_NAME_TWO_PLAYER_2 = "two_player_2_name";
    private static final String KEY_POINTS_TWO_PLAYERS_P1 = "two_players_p1_points";
    private static final String KEY_POINTS_TWO_PLAYERS_P2 = "two_players_p2_points";

    private static final String KEY_NAME_THREE_PLAYER_1 = "three_player_1_name";
    private static final String KEY_NAME_THREE_PLAYER_2 = "three_player_2_name";
    private static final String KEY_NAME_THREE_PLAYER_3 = "three_player_3_name";
    private static final String KEY_POINTS_THREE_PLAYERS_P1 = "three_players_p1_points";
    private static final String KEY_POINTS_THREE_PLAYERS_P2 = "three_players_p2_points";
    private static final String KEY_POINTS_THREE_PLAYERS_P3 = "three_players_p3_points";

    private static final String KEY_NAME_FOUR_PLAYER_1 = "four_player_1_name";
    private static final String KEY_NAME_FOUR_PLAYER_2 = "four_player_2_name";
    private static final String KEY_NAME_FOUR_PLAYER_3 = "four_player_3_name";
    private static final String KEY_NAME_FOUR_PLAYER_4 = "four_player_4_name";
    private static final String KEY_POINTS_FOUR_PLAYERS_P1 = "four_players_p1_points";
    private static final String KEY_POINTS_FOUR_PLAYERS_P2 = "four_players_p2_points";
    private static final String KEY_POINTS_FOUR_PLAYERS_P3 = "four_players_p3_points";
    private static final String KEY_POINTS_FOUR_PLAYERS_P4 = "four_players_p4_points";

    private static final String KEY_NAME_FIVE_PLAYER_1 = "five_player_1_name";
    private static final String KEY_NAME_FIVE_PLAYER_2 = "five_player_2_name";
    private static final String KEY_NAME_FIVE_PLAYER_3 = "five_player_3_name";
    private static final String KEY_NAME_FIVE_PLAYER_4 = "five_player_4_name";
    private static final String KEY_NAME_FIVE_PLAYER_5 = "five_player_5_name";
    private static final String KEY_POINTS_FIVE_PLAYERS_P1 = "five_players_p1_points";
    private static final String KEY_POINTS_FIVE_PLAYERS_P2 = "five_players_p2_points";
    private static final String KEY_POINTS_FIVE_PLAYERS_P3 = "five_players_p3_points";
    private static final String KEY_POINTS_FIVE_PLAYERS_P4 = "five_players_p4_points";
    private static final String KEY_POINTS_FIVE_PLAYERS_P5 = "five_players_p5_points";

    private static final String KEY_NAME_SIX_PLAYER_1 = "six_player_1_name";
    private static final String KEY_NAME_SIX_PLAYER_2 = "six_player_2_name";
    private static final String KEY_NAME_SIX_PLAYER_3 = "six_player_3_name";
    private static final String KEY_NAME_SIX_PLAYER_4 = "six_player_4_name";
    private static final String KEY_NAME_SIX_PLAYER_5 = "six_player_5_name";
    private static final String KEY_NAME_SIX_PLAYER_6 = "six_player_6_name";
    private static final String KEY_POINTS_SIX_PLAYERS_P1 = "six_players_p1_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P2 = "six_players_p2_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P3 = "six_players_p3_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P4 = "six_players_p4_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P5 = "six_players_p5_points";
    private static final String KEY_POINTS_SIX_PLAYERS_P6 = "six_players_p6_points";

    private static final String KEY_NAME_SEVEN_PLAYER_1 = "seven_player_1_name";
    private static final String KEY_NAME_SEVEN_PLAYER_2 = "seven_player_2_name";
    private static final String KEY_NAME_SEVEN_PLAYER_3 = "seven_player_3_name";
    private static final String KEY_NAME_SEVEN_PLAYER_4 = "seven_player_4_name";
    private static final String KEY_NAME_SEVEN_PLAYER_5 = "seven_player_5_name";
    private static final String KEY_NAME_SEVEN_PLAYER_6 = "seven_player_6_name";
    private static final String KEY_NAME_SEVEN_PLAYER_7 = "seven_player_7_name";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P1 = "seven_players_p1_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P2 = "seven_players_p2_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P3 = "seven_players_p3_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P4 = "seven_players_p4_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P5 = "seven_players_p5_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P6 = "seven_players_p6_points";
    private static final String KEY_POINTS_SEVEN_PLAYERS_P7 = "seven_players_p7_points";

    private static final String KEY_NAME_EIGHT_PLAYER_1 = "eight_player_1_name";
    private static final String KEY_NAME_EIGHT_PLAYER_2 = "eight_player_2_name";
    private static final String KEY_NAME_EIGHT_PLAYER_3 = "eight_player_3_name";
    private static final String KEY_NAME_EIGHT_PLAYER_4 = "eight_player_4_name";
    private static final String KEY_NAME_EIGHT_PLAYER_5 = "eight_player_5_name";
    private static final String KEY_NAME_EIGHT_PLAYER_6 = "eight_player_6_name";
    private static final String KEY_NAME_EIGHT_PLAYER_7 = "eight_player_7_name";
    private static final String KEY_NAME_EIGHT_PLAYER_8 = "eight_player_8_name";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P1 = "eight_players_p1_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P2 = "eight_players_p2_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P3 = "eight_players_p3_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P4 = "eight_players_p4_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P5 = "eight_players_p5_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P6 = "eight_players_p6_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P7 = "eight_players_p7_points";
    private static final String KEY_POINTS_EIGHT_PLAYERS_P8 = "eight_players_p8_points";

    private static final String DEFAULT_PLAYER_NAME = "";

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
    public String getOnePlayerName() { return sharedPrefs.getString(KEY_NAME_ONE_PLAYER, DEFAULT_PLAYER_NAME); }
    public void saveOnePlayerName(String name) { sharedPrefs.edit().putString(KEY_NAME_ONE_PLAYER, name).apply(); }
    public int getOnePlayerPoints() { return sharedPrefs.getInt(KEY_POINTS_ONE_PLAYER, initialPoints); }
    public void saveOnePlayerPoints(int points) { sharedPrefs.edit().putInt(KEY_POINTS_ONE_PLAYER, points).apply(); }
    //endregion
    //region METHODS TWO PLAYERS
    public String getTwoPlayerNameP1() { return sharedPrefs.getString(KEY_NAME_TWO_PLAYER_1, DEFAULT_PLAYER_NAME); }
    public String getTwoPlayerNameP2() { return sharedPrefs.getString(KEY_NAME_TWO_PLAYER_2, DEFAULT_PLAYER_NAME); }
    public void saveTwoPlayerNameP1(String name) { sharedPrefs.edit().putString(KEY_NAME_TWO_PLAYER_1, name).apply(); }
    public void saveTwoPlayerNameP2(String name) { sharedPrefs.edit().putString(KEY_NAME_TWO_PLAYER_2, name).apply(); }
    public int getTwoPlayerPointsP1() { return sharedPrefs.getInt(KEY_POINTS_TWO_PLAYERS_P1, initialPoints); }
    public int getTwoPlayerPointsP2() { return sharedPrefs.getInt(KEY_POINTS_TWO_PLAYERS_P2, initialPoints); }
    public void saveTwoPlayerPointsP1(int points) { sharedPrefs.edit().putInt(KEY_POINTS_TWO_PLAYERS_P1, points).apply(); }
    public void saveTwoPlayerPointsP2(int points) { sharedPrefs.edit().putInt(KEY_POINTS_TWO_PLAYERS_P2, points).apply(); }
    //endregion
    //region METHODS THREE PLAYERS
    public String getThreePlayerNameP1() { return sharedPrefs.getString(KEY_NAME_THREE_PLAYER_1, DEFAULT_PLAYER_NAME); }
    public String getThreePlayerNameP2() { return sharedPrefs.getString(KEY_NAME_THREE_PLAYER_2, DEFAULT_PLAYER_NAME); }
    public String getThreePlayerNameP3() { return sharedPrefs.getString(KEY_NAME_THREE_PLAYER_3, DEFAULT_PLAYER_NAME); }
    public void saveThreePlayerNameP1(String name) { sharedPrefs.edit().putString(KEY_NAME_THREE_PLAYER_1, name).apply(); }
    public void saveThreePlayerNameP2(String name) { sharedPrefs.edit().putString(KEY_NAME_THREE_PLAYER_2, name).apply(); }
    public void saveThreePlayerNameP3(String name) { sharedPrefs.edit().putString(KEY_NAME_THREE_PLAYER_3, name).apply(); }
    public int getThreePlayerPointsP1() { return sharedPrefs.getInt(KEY_POINTS_THREE_PLAYERS_P1, initialPoints); }
    public int getThreePlayerPointsP2() { return sharedPrefs.getInt(KEY_POINTS_THREE_PLAYERS_P2, initialPoints); }
    public int getThreePlayerPointsP3() { return sharedPrefs.getInt(KEY_POINTS_THREE_PLAYERS_P3, initialPoints); }
    public void saveThreePlayerPointsP1(int points) { sharedPrefs.edit().putInt(KEY_POINTS_THREE_PLAYERS_P1, points).apply(); }
    public void saveThreePlayerPointsP2(int points) { sharedPrefs.edit().putInt(KEY_POINTS_THREE_PLAYERS_P2, points).apply(); }
    public void saveThreePlayerPointsP3(int points) { sharedPrefs.edit().putInt(KEY_POINTS_THREE_PLAYERS_P3, points).apply(); }
    //endregion
    //region METHODS FOUR PLAYERS
    public String getFourPlayerNameP1() { return sharedPrefs.getString(KEY_NAME_FOUR_PLAYER_1, DEFAULT_PLAYER_NAME); }
    public String getFourPlayerNameP2() { return sharedPrefs.getString(KEY_NAME_FOUR_PLAYER_2, DEFAULT_PLAYER_NAME); }
    public String getFourPlayerNameP3() { return sharedPrefs.getString(KEY_NAME_FOUR_PLAYER_3, DEFAULT_PLAYER_NAME); }
    public String getFourPlayerNameP4() { return sharedPrefs.getString(KEY_NAME_FOUR_PLAYER_4, DEFAULT_PLAYER_NAME); }
    public void saveFourPlayerNameP1(String name) { sharedPrefs.edit().putString(KEY_NAME_FOUR_PLAYER_1, name).apply(); }
    public void saveFourPlayerNameP2(String name) { sharedPrefs.edit().putString(KEY_NAME_FOUR_PLAYER_2, name).apply(); }
    public void saveFourPlayerNameP3(String name) { sharedPrefs.edit().putString(KEY_NAME_FOUR_PLAYER_3, name).apply(); }
    public void saveFourPlayerNameP4(String name) { sharedPrefs.edit().putString(KEY_NAME_FOUR_PLAYER_4, name).apply(); }
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
    public String getFivePlayerNameP1() { return sharedPrefs.getString(KEY_NAME_FIVE_PLAYER_1, DEFAULT_PLAYER_NAME); }
    public String getFivePlayerNameP2() { return sharedPrefs.getString(KEY_NAME_FIVE_PLAYER_2, DEFAULT_PLAYER_NAME); }
    public String getFivePlayerNameP3() { return sharedPrefs.getString(KEY_NAME_FIVE_PLAYER_3, DEFAULT_PLAYER_NAME); }
    public String getFivePlayerNameP4() { return sharedPrefs.getString(KEY_NAME_FIVE_PLAYER_4, DEFAULT_PLAYER_NAME); }
    public String getFivePlayerNameP5() { return sharedPrefs.getString(KEY_NAME_FIVE_PLAYER_5, DEFAULT_PLAYER_NAME); }
    public void saveFivePlayerNameP1(String name) { sharedPrefs.edit().putString(KEY_NAME_FIVE_PLAYER_1, name).apply(); }
    public void saveFivePlayerNameP2(String name) { sharedPrefs.edit().putString(KEY_NAME_FIVE_PLAYER_2, name).apply(); }
    public void saveFivePlayerNameP3(String name) { sharedPrefs.edit().putString(KEY_NAME_FIVE_PLAYER_3, name).apply(); }
    public void saveFivePlayerNameP4(String name) { sharedPrefs.edit().putString(KEY_NAME_FIVE_PLAYER_4, name).apply(); }
    public void saveFivePlayerNameP5(String name) { sharedPrefs.edit().putString(KEY_NAME_FIVE_PLAYER_5, name).apply(); }
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
    public String getSixPlayerNameP1() { return sharedPrefs.getString(KEY_NAME_SIX_PLAYER_1, DEFAULT_PLAYER_NAME); }
    public String getSixPlayerNameP2() { return sharedPrefs.getString(KEY_NAME_SIX_PLAYER_2, DEFAULT_PLAYER_NAME); }
    public String getSixPlayerNameP3() { return sharedPrefs.getString(KEY_NAME_SIX_PLAYER_3, DEFAULT_PLAYER_NAME); }
    public String getSixPlayerNameP4() { return sharedPrefs.getString(KEY_NAME_SIX_PLAYER_4, DEFAULT_PLAYER_NAME); }
    public String getSixPlayerNameP5() { return sharedPrefs.getString(KEY_NAME_SIX_PLAYER_5, DEFAULT_PLAYER_NAME); }
    public String getSixPlayerNameP6() { return sharedPrefs.getString(KEY_NAME_SIX_PLAYER_6, DEFAULT_PLAYER_NAME); }
    public void saveSixPlayerNameP1(String name) { sharedPrefs.edit().putString(KEY_NAME_SIX_PLAYER_1, name).apply(); }
    public void saveSixPlayerNameP2(String name) { sharedPrefs.edit().putString(KEY_NAME_SIX_PLAYER_2, name).apply(); }
    public void saveSixPlayerNameP3(String name) { sharedPrefs.edit().putString(KEY_NAME_SIX_PLAYER_3, name).apply(); }
    public void saveSixPlayerNameP4(String name) { sharedPrefs.edit().putString(KEY_NAME_SIX_PLAYER_4, name).apply(); }
    public void saveSixPlayerNameP5(String name) { sharedPrefs.edit().putString(KEY_NAME_SIX_PLAYER_5, name).apply(); }
    public void saveSixPlayerNameP6(String name) { sharedPrefs.edit().putString(KEY_NAME_SIX_PLAYER_6, name).apply(); }
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
    public String getSevenPlayerNameP1() { return sharedPrefs.getString(KEY_NAME_SEVEN_PLAYER_1, DEFAULT_PLAYER_NAME); }
    public String getSevenPlayerNameP2() { return sharedPrefs.getString(KEY_NAME_SEVEN_PLAYER_2, DEFAULT_PLAYER_NAME); }
    public String getSevenPlayerNameP3() { return sharedPrefs.getString(KEY_NAME_SEVEN_PLAYER_3, DEFAULT_PLAYER_NAME); }
    public String getSevenPlayerNameP4() { return sharedPrefs.getString(KEY_NAME_SEVEN_PLAYER_4, DEFAULT_PLAYER_NAME); }
    public String getSevenPlayerNameP5() { return sharedPrefs.getString(KEY_NAME_SEVEN_PLAYER_5, DEFAULT_PLAYER_NAME); }
    public String getSevenPlayerNameP6() { return sharedPrefs.getString(KEY_NAME_SEVEN_PLAYER_6, DEFAULT_PLAYER_NAME); }
    public String getSevenPlayerNameP7() { return sharedPrefs.getString(KEY_NAME_SEVEN_PLAYER_7, DEFAULT_PLAYER_NAME); }
    public void saveSevenPlayerNameP1(String name) { sharedPrefs.edit().putString(KEY_NAME_SEVEN_PLAYER_1, name).apply(); }
    public void saveSevenPlayerNameP2(String name) { sharedPrefs.edit().putString(KEY_NAME_SEVEN_PLAYER_2, name).apply(); }
    public void saveSevenPlayerNameP3(String name) { sharedPrefs.edit().putString(KEY_NAME_SEVEN_PLAYER_3, name).apply(); }
    public void saveSevenPlayerNameP4(String name) { sharedPrefs.edit().putString(KEY_NAME_SEVEN_PLAYER_4, name).apply(); }
    public void saveSevenPlayerNameP5(String name) { sharedPrefs.edit().putString(KEY_NAME_SEVEN_PLAYER_5, name).apply(); }
    public void saveSevenPlayerNameP6(String name) { sharedPrefs.edit().putString(KEY_NAME_SEVEN_PLAYER_6, name).apply(); }
    public void saveSevenPlayerNameP7(String name) { sharedPrefs.edit().putString(KEY_NAME_SEVEN_PLAYER_7, name).apply(); }
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
    public String getEightPlayerNameP1() { return sharedPrefs.getString(KEY_NAME_EIGHT_PLAYER_1, DEFAULT_PLAYER_NAME); }
    public String getEightPlayerNameP2() { return sharedPrefs.getString(KEY_NAME_EIGHT_PLAYER_2, DEFAULT_PLAYER_NAME); }
    public String getEightPlayerNameP3() { return sharedPrefs.getString(KEY_NAME_EIGHT_PLAYER_3, DEFAULT_PLAYER_NAME); }
    public String getEightPlayerNameP4() { return sharedPrefs.getString(KEY_NAME_EIGHT_PLAYER_4, DEFAULT_PLAYER_NAME); }
    public String getEightPlayerNameP5() { return sharedPrefs.getString(KEY_NAME_EIGHT_PLAYER_5, DEFAULT_PLAYER_NAME); }
    public String getEightPlayerNameP6() { return sharedPrefs.getString(KEY_NAME_EIGHT_PLAYER_6, DEFAULT_PLAYER_NAME); }
    public String getEightPlayerNameP7() { return sharedPrefs.getString(KEY_NAME_EIGHT_PLAYER_7, DEFAULT_PLAYER_NAME); }
    public String getEightPlayerNameP8() { return sharedPrefs.getString(KEY_NAME_EIGHT_PLAYER_8, DEFAULT_PLAYER_NAME); }
    public void saveEightPlayerNameP1(String name) { sharedPrefs.edit().putString(KEY_NAME_EIGHT_PLAYER_1, name).apply(); }
    public void saveEightPlayerNameP2(String name) { sharedPrefs.edit().putString(KEY_NAME_EIGHT_PLAYER_2, name).apply(); }
    public void saveEightPlayerNameP3(String name) { sharedPrefs.edit().putString(KEY_NAME_EIGHT_PLAYER_3, name).apply(); }
    public void saveEightPlayerNameP4(String name) { sharedPrefs.edit().putString(KEY_NAME_EIGHT_PLAYER_4, name).apply(); }
    public void saveEightPlayerNameP5(String name) { sharedPrefs.edit().putString(KEY_NAME_EIGHT_PLAYER_5, name).apply(); }
    public void saveEightPlayerNameP6(String name) { sharedPrefs.edit().putString(KEY_NAME_EIGHT_PLAYER_6, name).apply(); }
    public void saveEightPlayerNameP7(String name) { sharedPrefs.edit().putString(KEY_NAME_EIGHT_PLAYER_7, name).apply(); }
    public void saveEightPlayerNameP8(String name) { sharedPrefs.edit().putString(KEY_NAME_EIGHT_PLAYER_8, name).apply(); }
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
