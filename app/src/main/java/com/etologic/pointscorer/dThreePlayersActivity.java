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

public class dThreePlayersActivity extends AppCompatActivity {

    //CONSTANTS
    private static final String KEY_POINTS_P1 = "three_players_p1_points";
    private static final String KEY_POINTS_P2 = "three_players_p2_points";
    private static final String KEY_POINTS_P3 = "three_players_p3_points";
    //VIEWS
    @BindView(R.id.tvPointsP1) TextView tvPointsP1;
    @BindView(R.id.tvPointsP2) TextView tvPointsP2;
    @BindView(R.id.tvPointsP3) TextView tvPointsP3;
    @BindView(R.id.tvPointsP1ForAnimation) TextView tvPointsP1ForAnimation;
    @BindView(R.id.tvPointsP2ForAnimation) TextView tvPointsP2ForAnimation;
    @BindView(R.id.tvPointsP3ForAnimation) TextView tvPointsP3ForAnimation;
    //FIELDS
    private int initialPoints;
    private static SharedPreferences sharedPreferences;
    private int pointsP1;
    private int pointsP2;
    private int pointsP3;

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
    private void restartAllPlayersPoints() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog);
        builder.setMessage("Restart all players points?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    restartP1Points();
                    restartP2Points();
                    restartP3Points();
                })
                .create()
                .show();
    }
    private void restartP1Points() {
        tvPointsP1ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP1, tvPointsP1ForAnimation, pointsP1));
    }
    private void restartP2Points() { pointsP2 = initialPoints; savePoints(KEY_POINTS_P2, pointsP2); updatePointsP2(); }
    private void updatePointsP2() {
        tvPointsP2ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP2, tvPointsP2ForAnimation, pointsP2));
    }
    private void updatePointsP3() {
        tvPointsP3ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP3, tvPointsP3ForAnimation, pointsP3));
    }
    private void restartP3Points() { pointsP3 = initialPoints; savePoints(KEY_POINTS_P3, pointsP3); updatePointsP3(); }
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
    //LIFECYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_three_players_activity);
        ButterKnife.bind(this);
        initPoints();
    }
    private void initPoints() {
        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        initialPoints = sharedPreferences.getInt(KEY_INITIAL_POINTS, DEFAULT_INITIAL_POINTS);
        pointsP1 = getSavedPoints(KEY_POINTS_P1);
        pointsP2 = getSavedPoints(KEY_POINTS_P2);
        pointsP3 = getSavedPoints(KEY_POINTS_P3);
        updatePointsP1();
        updatePointsP2();
        updatePointsP3();
    }
    private int getSavedPoints(String key) { return sharedPreferences.getInt(key, initialPoints); }
}
