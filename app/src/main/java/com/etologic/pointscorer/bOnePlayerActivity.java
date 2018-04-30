package com.etologic.pointscorer;

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

public class bOnePlayerActivity extends AppCompatActivity {

    //CONSTANTS
    private static final String KEY_POINTS_PLAYER = "one_player_points";
    //VIEWS
    @BindView(R.id.tvPoints) TextView tvPoints;
    //FIELDS
    private static SharedPreferences sharedPreferences;
    private int initialPoints;
    private int points;

    //EVENTS
    @OnClick(R.id.btUp) void onUpClickButton() { points++; savePoints(); updatePoints(); }
    private void savePoints() { sharedPreferences.edit().putInt(KEY_POINTS_PLAYER, points).apply(); }
    private void updatePoints() { tvPoints.setText(String.format(Locale.getDefault(), "%d", points)); }
    @OnClick(R.id.btDown) void onDownClickButton() { points--; savePoints(); updatePoints(); }
    @OnClick(R.id.ibMenu) void onMenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().findItem(R.id.menu_restart_all).setVisible(false);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_restart:
                    restartPlayerPoints();
                    return true;
                default:
                    return false;
            }
        });
        popup.inflate(R.menu.player_menu);
        popup.show();
    }
    private void restartPlayerPoints() {
        points = initialPoints;
        savePoints();
        updatePoints();
    }
    //LIFECYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_one_player_activity);
        ButterKnife.bind(this);
        initPoints();
    }
    private void initPoints() {
        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        initialPoints = sharedPreferences.getInt(KEY_INITIAL_POINTS, DEFAULT_INITIAL_POINTS);
        points = getSavedPoints();
        updatePoints();
    }
    private int getSavedPoints() { return sharedPreferences.getInt(KEY_POINTS_PLAYER, initialPoints); }
}
