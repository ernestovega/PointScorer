package com.etologic.pointscorer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.etologic.pointscorer.aMainActivity.DEFAULT_INITIAL_POINTS;
import static com.etologic.pointscorer.aMainActivity.FILE_NAME;
import static com.etologic.pointscorer.aMainActivity.KEY_INITIAL_POINTS;

public class hSevenPlayersActivity extends AppCompatActivity {

    //CONSTANTS
    private static final String KEY_POINTS_P1 = "seven_players_p1_points";
    private static final String KEY_POINTS_P2 = "seven_players_p2_points";
    private static final String KEY_POINTS_P3 = "seven_players_p3_points";
    private static final String KEY_POINTS_P4 = "seven_players_p4_points";
    private static final String KEY_POINTS_P5 = "seven_players_p5_points";
    private static final String KEY_POINTS_P6 = "seven_players_p6_points";
    private static final String KEY_POINTS_P7 = "seven_players_p7_points";
    //VIEWS
    @BindView(R.id.tvPointsP1) TextView tvPointsP1;
    @BindView(R.id.tvPointsP2) TextView tvPointsP2;
    @BindView(R.id.tvPointsP3) TextView tvPointsP3;
    @BindView(R.id.tvPointsP4) TextView tvPointsP4;
    @BindView(R.id.tvPointsP5) TextView tvPointsP5;
    @BindView(R.id.tvPointsP6) TextView tvPointsP6;
    @BindView(R.id.tvPointsP7) TextView tvPointsP7;
    //FIELDS
    private int initialPoints;
    private static SharedPreferences sharedPreferences;
    private int pointsP1;
    private int pointsP2;
    private int pointsP3;
    private int pointsP4;
    private int pointsP5;
    private int pointsP6;
    private int pointsP7;

    //EVENTS
    @OnClick(R.id.btUpP1) void onP1UpButtonClick() { pointsP1++; savePoints(KEY_POINTS_P1, pointsP1); updatePointsP1(); }
    private void savePoints(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value).apply();
    }
    private void updatePointsP1() { tvPointsP1.setText(String.format(Locale.getDefault(), "%d", pointsP1)); }
    @OnClick(R.id.btDownP1) void onP1DownButtonClick() { pointsP1--; savePoints(KEY_POINTS_P1, pointsP1); updatePointsP1(); }
    @OnClick(R.id.ibMenuP1) void onP1MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_restart:
                    restartP1Points();
                    return true;
                case R.id.menu_restart_all:
                    restartAllPlayersPoints();
                    return true;
                default:
                    return false;
            }
        });
        popup.inflate(R.menu.player_menu);
        popup.show();
    }
    private void restartP1Points() { pointsP1 = initialPoints; savePoints(KEY_POINTS_P1, pointsP1); updatePointsP1(); }
    private void restartAllPlayersPoints() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog);
        builder.setMessage("Restart all players points?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    restartP1Points();
                    restartP2Points();
                    restartP3Points();
                    restartP4Points();
                    restartP5Points();
                    restartP6Points();
                    restartP7Points();
                })
                .create()
                .show();
    }
    private void restartP2Points() { pointsP2 = initialPoints; savePoints(KEY_POINTS_P2, pointsP2); updatePointsP2(); }
    private void updatePointsP2() { tvPointsP2.setText(String.format(Locale.getDefault(), "%d", pointsP2)); }
    private void restartP3Points() { pointsP3 = initialPoints; savePoints(KEY_POINTS_P3, pointsP3); updatePointsP3(); }
    private void updatePointsP3() { tvPointsP3.setText(String.format(Locale.getDefault(), "%d", pointsP3)); }
    private void restartP4Points() { pointsP4 = initialPoints; savePoints(KEY_POINTS_P4, pointsP4); updatePointsP4(); }
    private void updatePointsP4() { tvPointsP4.setText(String.format(Locale.getDefault(), "%d", pointsP4)); }
    private void restartP5Points() { pointsP5 = initialPoints; savePoints(KEY_POINTS_P5, pointsP5); updatePointsP5(); }
    private void updatePointsP5() { tvPointsP5.setText(String.format(Locale.getDefault(), "%d", pointsP5)); }
    private void restartP6Points() { pointsP6 = initialPoints; savePoints(KEY_POINTS_P6, pointsP6); updatePointsP6(); }
    private void updatePointsP6() { tvPointsP6.setText(String.format(Locale.getDefault(), "%d", pointsP6)); }
    private void restartP7Points() { pointsP7 = initialPoints; savePoints(KEY_POINTS_P7, pointsP7); updatePointsP7(); }
    private void updatePointsP7() { tvPointsP7.setText(String.format(Locale.getDefault(), "%d", pointsP7)); }
    @OnClick(R.id.btUpP2) void onP2UpButtonClick() { pointsP2++; savePoints(KEY_POINTS_P2, pointsP2); updatePointsP2(); }
    @OnClick(R.id.btDownP2) void onP2DownButtonClick() { pointsP2--; savePoints(KEY_POINTS_P2, pointsP2); updatePointsP2(); }
    @OnClick(R.id.ibMenuP2) void onP2MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_restart:
                    restartP2Points();
                    return true;
                case R.id.menu_restart_all:
                    restartAllPlayersPoints();
                    return true;
                default:
                    return false;
            }
        });
        popup.inflate(R.menu.player_menu);
        popup.show();
    }
    @OnClick(R.id.btUpP3) void onP3UpButtonClick() { pointsP3++; savePoints(KEY_POINTS_P3, pointsP3); updatePointsP3(); }
    @OnClick(R.id.btDownP3) void onP3DownButtonClick() { pointsP3--; savePoints(KEY_POINTS_P3, pointsP3); updatePointsP3(); }
    @OnClick(R.id.ibMenuP3) void onP3MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_restart:
                    restartP3Points();
                    return true;
                case R.id.menu_restart_all:
                    restartAllPlayersPoints();
                    return true;
                default:
                    return false;
            }
        });
        popup.inflate(R.menu.player_menu);
        popup.show();
    }
    @OnClick(R.id.btUpP4) void onP4UpButtonClick() { pointsP4++; savePoints(KEY_POINTS_P4, pointsP4); updatePointsP4(); }
    @OnClick(R.id.btDownP4) void onP4DownButtonClick() { pointsP4--; savePoints(KEY_POINTS_P4, pointsP4); updatePointsP4(); }
    @OnClick(R.id.ibMenuP4) void onP4MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_restart:
                    restartP4Points();
                    return true;
                case R.id.menu_restart_all:
                    restartAllPlayersPoints();
                    return true;
                default:
                    return false;
            }
        });
        popup.inflate(R.menu.player_menu);
        popup.show();
    }
    @OnClick(R.id.btUpP5) void onP5UpButtonClick() { pointsP5++; savePoints(KEY_POINTS_P5, pointsP5); updatePointsP5(); }
    @OnClick(R.id.btDownP5) void onP5DownButtonClick() { pointsP5--; savePoints(KEY_POINTS_P5, pointsP5); updatePointsP5(); }
    @OnClick(R.id.ibMenuP5) void onP5MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_restart:
                    restartP5Points();
                    return true;
                case R.id.menu_restart_all:
                    restartAllPlayersPoints();
                    return true;
                default:
                    return false;
            }
        });
        popup.inflate(R.menu.player_menu);
        popup.show();
    }
    @OnClick(R.id.btUpP6) void onP6UpButtonClick() { pointsP6++; savePoints(KEY_POINTS_P6, pointsP6); updatePointsP6(); }
    @OnClick(R.id.btDownP6) void onP6DownButtonClick() { pointsP6--; savePoints(KEY_POINTS_P6, pointsP6); updatePointsP6(); }
    @OnClick(R.id.ibMenuP6) void onP6MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_restart:
                    restartP6Points();
                    return true;
                case R.id.menu_restart_all:
                    restartAllPlayersPoints();
                    return true;
                default:
                    return false;
            }
        });
        popup.inflate(R.menu.player_menu);
        popup.show();
    }
    @OnClick(R.id.btUpP7) void onP7UpButtonClick() { pointsP7++; savePoints(KEY_POINTS_P7, pointsP7); updatePointsP7(); }
    @OnClick(R.id.btDownP7) void onP7DownButtonClick() { pointsP7--; savePoints(KEY_POINTS_P7, pointsP7); updatePointsP7(); }
    @OnClick(R.id.ibMenuP7) void onP7MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_restart:
                    restartP7Points();
                    return true;
                case R.id.menu_restart_all:
                    restartAllPlayersPoints();
                    return true;
                default:
                    return false;
            }
        });
        popup.inflate(R.menu.player_menu);
        popup.show();
    }
    //LIFECYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_seven_players_activity);
        ButterKnife.bind(this);
        initPoints();
    }
    private void initPoints() {
        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        initialPoints = sharedPreferences.getInt(KEY_INITIAL_POINTS, DEFAULT_INITIAL_POINTS);
        pointsP1 = getSavedPoints(KEY_POINTS_P1);
        pointsP2 = getSavedPoints(KEY_POINTS_P2);
        pointsP3 = getSavedPoints(KEY_POINTS_P3);
        pointsP4 = getSavedPoints(KEY_POINTS_P4);
        pointsP5 = getSavedPoints(KEY_POINTS_P5);
        pointsP6 = getSavedPoints(KEY_POINTS_P6);
        pointsP7 = getSavedPoints(KEY_POINTS_P7);
        updatePointsP1();
        updatePointsP2();
        updatePointsP3();
        updatePointsP4();
        updatePointsP5();
        updatePointsP6();
        updatePointsP7();
    }
    private int getSavedPoints(String key) { return sharedPreferences.getInt(key, initialPoints); }
}
