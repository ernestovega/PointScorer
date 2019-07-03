package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.etologic.pointscorer.R;
import com.etologic.pointscorer.SharedPrefsHelper;
import com.etologic.pointscorer.utils.DialogUtils;
import com.etologic.pointscorer.utils.MyAnimationUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_UP;
import static com.etologic.pointscorer.screens.aMainActivity.REP_DELAY;

public class cTwoPlayersActivity extends AppCompatActivity {

    //VIEWS
    @BindView(R.id.tvNameP1) AppCompatEditText tvNameP1;
    @BindView(R.id.tvNameP2) AppCompatEditText tvNameP2;
    @BindView(R.id.ivShieldP1) ImageView ivShieldP1;
    @BindView(R.id.ivShieldP2) ImageView ivShieldP2;
    @BindView(R.id.tvPointsP1) TextView tvPointsP1;
    @BindView(R.id.tvPointsP2) TextView tvPointsP2;
    @BindView(R.id.tvPointsP1ForAnimation) TextView tvPointsP1ForAnimation;
    @BindView(R.id.tvPointsP2ForAnimation) TextView tvPointsP2ForAnimation;
    //FIELDS
    private SharedPrefsHelper sharedPrefsHelper;
    private int initialPoints;
    private int pointsP1;
    private int pointsP2;
    private Handler repeatUpdateHandlerP1 = new Handler();
    private Handler repeatUpdateHandlerP2 = new Handler();
    private boolean isAutoIncrement = false;
    private boolean isAutoDecrement = false;

    //INNER CLASSES
    class RepeatUpdaterP1 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP1++; sharedPrefsHelper.saveTwoPlayerPointsP1(pointsP1); updatePointsP1(); repeatUpdateHandlerP1.postDelayed(new cTwoPlayersActivity.RepeatUpdaterP1(), REP_DELAY); }
            if (isAutoDecrement) { pointsP1--; sharedPrefsHelper.saveTwoPlayerPointsP1(pointsP1); updatePointsP1(); repeatUpdateHandlerP1.postDelayed(new cTwoPlayersActivity.RepeatUpdaterP1(), REP_DELAY); }
        }
    }
    class RepeatUpdaterP2 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP2++; sharedPrefsHelper.saveTwoPlayerPointsP2(pointsP2); updatePointsP2(); repeatUpdateHandlerP2.postDelayed(new cTwoPlayersActivity.RepeatUpdaterP2(), REP_DELAY); }
            if (isAutoDecrement) { pointsP2--; sharedPrefsHelper.saveTwoPlayerPointsP2(pointsP2); updatePointsP2(); repeatUpdateHandlerP2.postDelayed(new cTwoPlayersActivity.RepeatUpdaterP2(), REP_DELAY); }
        }
    }

    //EVENTS
    @OnLongClick(R.id.btUpP1)   boolean onUpP1LongClickButton()   { isAutoIncrement = true; repeatUpdateHandlerP1.post(new RepeatUpdaterP1()); return false; }
    @OnLongClick(R.id.btDownP1) boolean onDownP1LongClickButton() { isAutoDecrement = true; repeatUpdateHandlerP1.post(new RepeatUpdaterP1()); return false; }
    @OnTouch(R.id.btUpP1)   boolean onUpP1Touch(MotionEvent event)   { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) { isAutoIncrement = false; } return false; }
    @OnTouch(R.id.btDownP1) boolean onDownP1Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) { isAutoDecrement = false; } return false; }
    @OnLongClick(R.id.btUpP2)   boolean onUpP2LongClickButton()   { isAutoIncrement = true; repeatUpdateHandlerP2.post(new RepeatUpdaterP2()); return false; }
    @OnLongClick(R.id.btDownP2) boolean onDownP2LongClickButton() { isAutoDecrement = true; repeatUpdateHandlerP2.post(new RepeatUpdaterP2()); return false; }
    @OnTouch(R.id.btUpP2)   boolean onUpP2Touch(MotionEvent event)   { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) { isAutoIncrement = false; } return false; }
    @OnTouch(R.id.btDownP2) boolean onDownP2Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) { isAutoDecrement = false; } return false; }
    @OnClick(R.id.btUpP1) void onP1UpButtonClick() { if(pointsP1 < 9999) pointsP1++; sharedPrefsHelper.saveTwoPlayerPointsP1(pointsP1); updatePointsP1(); }
    @OnClick(R.id.btUpP2) void onP2UpButtonClick() { if(pointsP2 < 9999) pointsP2++; sharedPrefsHelper.saveTwoPlayerPointsP2(pointsP2); updatePointsP2(); }
    @OnClick(R.id.btDownP1) void onP1DownButtonClick() { if(pointsP1 > -999) pointsP1--; sharedPrefsHelper.saveTwoPlayerPointsP1(pointsP1); updatePointsP1(); }
    @OnClick(R.id.btDownP2) void onP2DownButtonClick() { if(pointsP2 > -999) pointsP2--; sharedPrefsHelper.saveTwoPlayerPointsP2(pointsP2); updatePointsP2(); }
    @OnClick(R.id.ibMenuP1) void onP1MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_edit_name:
                    DialogUtils.showNameDialog(this, name -> {
                        sharedPrefsHelper.saveTwoPlayerNameP1(name);
                        tvNameP1.setText(name);
                    }, tvNameP1.getText());
                    return true;
                case R.id.menu_edit_color:
                    DialogUtils.showColorDialog(this, color -> {
                        sharedPrefsHelper.saveOnePlayerColor(color);
                        setTextsColorP1(color);
                    });
                    return true;
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
    @OnClick(R.id.ibMenuP2) void onP2MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_edit_name:
                    DialogUtils.showNameDialog(this, name -> {
                        sharedPrefsHelper.saveTwoPlayerColorP1(name);
                        tvNameP2.setText(name);
                    }, tvNameP2.getText());
                    return true;
                case R.id.menu_edit_color:
                    DialogUtils.showColorDialog(this, color -> {
                        sharedPrefsHelper.saveTwoPlayerColorP2(color);
                        setTextsColorP2(color);
                    });
                    return true;
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
    private void setTextsColorP1(int color) {
        tvNameP1.setTextColor(color);
        tvNameP1.setHintTextColor(color);
        tvPointsP1.setTextColor(color);
        tvPointsP1ForAnimation.setTextColor(color);
    }
    private void setTextsColorP2(int color) {
        tvNameP2.setTextColor(color);
        tvNameP2.setHintTextColor(color);
        tvPointsP2.setTextColor(color);
        tvPointsP2ForAnimation.setTextColor(color);
    }
    private void updatePointsP1() { tvPointsP1ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP1, tvPointsP1ForAnimation, pointsP1)); }
    private void updatePointsP2() { tvPointsP2ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP2, tvPointsP2ForAnimation, pointsP2)); }
    private void restartAllPlayersPoints() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog);
        builder.setMessage(getString(R.string.restart_all_players_points_))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    restartP1Points();
                    restartP2Points();
                })
                .create()
                .show();
    }
    private void restartP1Points() { pointsP1 = initialPoints; sharedPrefsHelper.saveTwoPlayerPointsP1(pointsP1); updatePointsP1(); }
    private void restartP2Points() { pointsP2 = initialPoints; sharedPrefsHelper.saveTwoPlayerPointsP2(pointsP2); updatePointsP2(); }
    //LIFECYCLE
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_two_players_activity);
        ButterKnife.bind(this);
        initSharedPrefs();
        initNames();
        initColors();
        initPoints();
    }
    private void initSharedPrefs() {
        new Thread(() -> sharedPrefsHelper = new SharedPrefsHelper(cTwoPlayersActivity.this, playerId)).run();
    }
    private void initNames() {
        tvNameP1.setText(sharedPrefsHelper.getTwoPlayerNameP1());
        tvNameP2.setText(sharedPrefsHelper.getTwoPlayerNameP2());
    }
    private void initColors() {
        setTextsColorP1(sharedPrefsHelper.getTwoPlayerColorP1());
        setTextsColorP2(sharedPrefsHelper.getTwoPlayerColorP2());
    }
    private void initPoints() {
        new Thread(() -> {
            initialPoints = sharedPrefsHelper.getInitialPoints();
            pointsP1 = sharedPrefsHelper.getTwoPlayerPointsP1();
            pointsP2 = sharedPrefsHelper.getTwoPlayerPointsP2();
            runOnUiThread(() -> {
                initShields();
                updatePointsP1();
                updatePointsP2();
            });
        }).run();
    }
    private void initShields() {
        ivShieldP1.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP1, tvPointsP1ForAnimation, pointsP1));
        ivShieldP2.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP2, tvPointsP2ForAnimation, pointsP2));
    }
}
