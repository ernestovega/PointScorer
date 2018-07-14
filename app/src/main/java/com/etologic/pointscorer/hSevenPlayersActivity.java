package com.etologic.pointscorer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_UP;
import static com.etologic.pointscorer.aMainActivity.REP_DELAY;

public class hSevenPlayersActivity extends AppCompatActivity {

    //VIEWS
    @BindView(R.id.ivShieldP1) ImageView ivShieldP1;
    @BindView(R.id.ivShieldP2) ImageView ivShieldP2;
    @BindView(R.id.ivShieldP3) ImageView ivShieldP3;
    @BindView(R.id.ivShieldP4) ImageView ivShieldP4;
    @BindView(R.id.ivShieldP5) ImageView ivShieldP5;
    @BindView(R.id.ivShieldP6) ImageView ivShieldP6;
    @BindView(R.id.ivShieldP7) ImageView ivShieldP7;
    @BindView(R.id.tvPointsP1) TextView tvPointsP1;
    @BindView(R.id.tvPointsP2) TextView tvPointsP2;
    @BindView(R.id.tvPointsP3) TextView tvPointsP3;
    @BindView(R.id.tvPointsP4) TextView tvPointsP4;
    @BindView(R.id.tvPointsP5) TextView tvPointsP5;
    @BindView(R.id.tvPointsP6) TextView tvPointsP6;
    @BindView(R.id.tvPointsP7) TextView tvPointsP7;
    @BindView(R.id.tvPointsP1ForAnimation) TextView tvPointsP1ForAnimation;
    @BindView(R.id.tvPointsP2ForAnimation) TextView tvPointsP2ForAnimation;
    @BindView(R.id.tvPointsP3ForAnimation) TextView tvPointsP3ForAnimation;
    @BindView(R.id.tvPointsP4ForAnimation) TextView tvPointsP4ForAnimation;
    @BindView(R.id.tvPointsP5ForAnimation) TextView tvPointsP5ForAnimation;
    @BindView(R.id.tvPointsP6ForAnimation) TextView tvPointsP6ForAnimation;
    @BindView(R.id.tvPointsP7ForAnimation) TextView tvPointsP7ForAnimation;
    //FIELDS
    private SharedPrefsHelper sharedPrefsHelper;
    private int initialPoints;
    private int pointsP1;
    private int pointsP2;
    private int pointsP3;
    private int pointsP4;
    private int pointsP5;
    private int pointsP6;
    private int pointsP7;
    private Handler repeatUpdateHandlerP1 = new Handler();
    private Handler repeatUpdateHandlerP2 = new Handler();
    private Handler repeatUpdateHandlerP3 = new Handler();
    private Handler repeatUpdateHandlerP4 = new Handler();
    private Handler repeatUpdateHandlerP5 = new Handler();
    private Handler repeatUpdateHandlerP6 = new Handler();
    private Handler repeatUpdateHandlerP7 = new Handler();
    private boolean isAutoIncrement = false;
    private boolean isAutoDecrement = false;

    //INNER CLASSES
    class RepeatUpdaterP1 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP1++; sharedPrefsHelper.saveSevenPlayerPointsP1(pointsP1); updatePointsP1(); repeatUpdateHandlerP1.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP1(), REP_DELAY); }
            if (isAutoDecrement) { pointsP1--; sharedPrefsHelper.saveSevenPlayerPointsP1(pointsP1); updatePointsP1(); repeatUpdateHandlerP1.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP1(), REP_DELAY); }
        }
    }
    class RepeatUpdaterP2 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP2++; sharedPrefsHelper.saveSevenPlayerPointsP2(pointsP2); updatePointsP2(); repeatUpdateHandlerP2.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP2(), REP_DELAY); }
            if (isAutoDecrement) { pointsP2--; sharedPrefsHelper.saveSevenPlayerPointsP2(pointsP2); updatePointsP2(); repeatUpdateHandlerP2.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP2(), REP_DELAY); }
        }
    }
    class RepeatUpdaterP3 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP3++; sharedPrefsHelper.saveSevenPlayerPointsP3(pointsP3); updatePointsP3(); repeatUpdateHandlerP3.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP3(), REP_DELAY); }
            if (isAutoDecrement) { pointsP3--; sharedPrefsHelper.saveSevenPlayerPointsP3(pointsP3); updatePointsP3(); repeatUpdateHandlerP3.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP3(), REP_DELAY); }
        }
    }
    class RepeatUpdaterP4 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP4++; sharedPrefsHelper.saveSevenPlayerPointsP4(pointsP4); updatePointsP4(); repeatUpdateHandlerP4.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP4(), REP_DELAY); }
            if (isAutoDecrement) { pointsP4--; sharedPrefsHelper.saveSevenPlayerPointsP4(pointsP4); updatePointsP4(); repeatUpdateHandlerP4.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP4(), REP_DELAY); }
        }
    }
    class RepeatUpdaterP5 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP5++; sharedPrefsHelper.saveSevenPlayerPointsP5(pointsP5); updatePointsP5(); repeatUpdateHandlerP5.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP5(), REP_DELAY); }
            if (isAutoDecrement) { pointsP5--; sharedPrefsHelper.saveSevenPlayerPointsP5(pointsP5); updatePointsP5(); repeatUpdateHandlerP5.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP5(), REP_DELAY); }
        }
    }
    class RepeatUpdaterP6 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP6++; sharedPrefsHelper.saveSevenPlayerPointsP6(pointsP6); updatePointsP6(); repeatUpdateHandlerP6.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP6(), REP_DELAY); }
            if (isAutoDecrement) { pointsP6--; sharedPrefsHelper.saveSevenPlayerPointsP6(pointsP6); updatePointsP6(); repeatUpdateHandlerP6.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP6(), REP_DELAY); }
        }
    }
    class RepeatUpdaterP7 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP7++; sharedPrefsHelper.saveSevenPlayerPointsP7(pointsP7); updatePointsP7(); repeatUpdateHandlerP7.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP7(), REP_DELAY); }
            if (isAutoDecrement) { pointsP7--; sharedPrefsHelper.saveSevenPlayerPointsP7(pointsP7); updatePointsP7(); repeatUpdateHandlerP7.postDelayed(new hSevenPlayersActivity.RepeatUpdaterP7(), REP_DELAY); }
        }
    }

    //EVENTS
    @OnLongClick(R.id.btUpP1) boolean onUpP1LongClickButton() { isAutoIncrement = true; repeatUpdateHandlerP1.post(new RepeatUpdaterP1()); return false; }
    @OnLongClick(R.id.btDownP1) boolean onDownP1LongClickButton() { isAutoDecrement = true; repeatUpdateHandlerP1.post(new RepeatUpdaterP1()); return false; }
    @OnTouch(R.id.btUpP1) boolean onUpP1Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) { isAutoIncrement = false; } return false; }
    @OnTouch(R.id.btDownP1) boolean onDownP1Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) { isAutoDecrement = false; } return false; }
    @OnLongClick(R.id.btUpP2) boolean onUpP2LongClickButton() { isAutoIncrement = true; repeatUpdateHandlerP2.post(new RepeatUpdaterP2()); return false; }
    @OnLongClick(R.id.btDownP2) boolean onDownP2LongClickButton() { isAutoDecrement = true; repeatUpdateHandlerP2.post(new RepeatUpdaterP2()); return false; }
    @OnTouch(R.id.btUpP2) boolean onUpP2Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) { isAutoIncrement = false; } return false; }
    @OnTouch(R.id.btDownP2) boolean onDownP2Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) { isAutoDecrement = false; } return false; }
    @OnLongClick(R.id.btUpP3) boolean onUpP3LongClickButton() { isAutoIncrement = true; repeatUpdateHandlerP3.post(new RepeatUpdaterP3()); return false; }
    @OnLongClick(R.id.btDownP3) boolean onDownP3LongClickButton() { isAutoDecrement = true; repeatUpdateHandlerP3.post(new RepeatUpdaterP3()); return false; }
    @OnTouch(R.id.btUpP3)   boolean onUpP3Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) { isAutoIncrement = false; } return false; }
    @OnTouch(R.id.btDownP3) boolean onDownP3Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) { isAutoDecrement = false; } return false; }
    @OnLongClick(R.id.btUpP4) boolean onUpP4LongClickButton() { isAutoIncrement = true; repeatUpdateHandlerP4.post(new RepeatUpdaterP4()); return false; }
    @OnLongClick(R.id.btDownP4) boolean onDownP4LongClickButton() { isAutoDecrement = true; repeatUpdateHandlerP4.post(new RepeatUpdaterP4()); return false; }
    @OnTouch(R.id.btUpP4)   boolean onUpP4Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) { isAutoIncrement = false; } return false; }
    @OnTouch(R.id.btDownP4) boolean onDownP4Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) { isAutoDecrement = false; } return false; }
    @OnLongClick(R.id.btUpP5) boolean onUpP5LongClickButton() { isAutoIncrement = true; repeatUpdateHandlerP5.post(new RepeatUpdaterP5()); return false; }
    @OnLongClick(R.id.btDownP5) boolean onDownP5LongClickButton() { isAutoDecrement = true; repeatUpdateHandlerP5.post(new RepeatUpdaterP5()); return false; }
    @OnTouch(R.id.btUpP5)   boolean onUpP5Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) { isAutoIncrement = false; } return false; }
    @OnTouch(R.id.btDownP5) boolean onDownP5Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) { isAutoDecrement = false; } return false; }
    @OnLongClick(R.id.btUpP6) boolean onUpP6LongClickButton() { isAutoIncrement = true; repeatUpdateHandlerP6.post(new RepeatUpdaterP6()); return false; }
    @OnLongClick(R.id.btDownP6) boolean onDownP6LongClickButton() { isAutoDecrement = true; repeatUpdateHandlerP6.post(new RepeatUpdaterP6()); return false; }
    @OnTouch(R.id.btUpP6)   boolean onUpP6Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) { isAutoIncrement = false; } return false; }
    @OnTouch(R.id.btDownP6) boolean onDownP6Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) { isAutoDecrement = false; } return false; }
    @OnLongClick(R.id.btUpP7) boolean onUpP7LongClickButton() { isAutoIncrement = true; repeatUpdateHandlerP7.post(new RepeatUpdaterP7()); return false; }
    @OnLongClick(R.id.btDownP7) boolean onDownP7LongClickButton() { isAutoDecrement = true; repeatUpdateHandlerP7.post(new RepeatUpdaterP7()); return false; }
    @OnTouch(R.id.btUpP7)   boolean onUpP7Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) { isAutoIncrement = false; } return false; }
    @OnTouch(R.id.btDownP7) boolean onDownP7Touch(MotionEvent event) { if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) { isAutoDecrement = false; } return false; }
    @OnClick(R.id.btUpP1) void onP1UpButtonClick() { pointsP1++; sharedPrefsHelper.saveSevenPlayerPointsP1(pointsP1); updatePointsP1(); }
    @OnClick(R.id.btUpP2) void onP2UpButtonClick() { pointsP2++; sharedPrefsHelper.saveSevenPlayerPointsP2(pointsP2); updatePointsP2(); }
    @OnClick(R.id.btUpP3) void onP3UpButtonClick() { pointsP3++; sharedPrefsHelper.saveSevenPlayerPointsP3(pointsP3); updatePointsP3(); }
    @OnClick(R.id.btUpP4) void onP4UpButtonClick() { pointsP4++; sharedPrefsHelper.saveSevenPlayerPointsP4(pointsP4); updatePointsP4(); }
    @OnClick(R.id.btUpP5) void onP5UpButtonClick() { pointsP5++; sharedPrefsHelper.saveSevenPlayerPointsP5(pointsP5); updatePointsP5(); }
    @OnClick(R.id.btUpP6) void onP6UpButtonClick() { pointsP6++; sharedPrefsHelper.saveSevenPlayerPointsP6(pointsP6); updatePointsP6(); }
    @OnClick(R.id.btUpP7) void onP7UpButtonClick() { pointsP7++; sharedPrefsHelper.saveSevenPlayerPointsP7(pointsP7); updatePointsP7(); }
    @OnClick(R.id.btDownP1) void onP1DownButtonClick() { pointsP1--; sharedPrefsHelper.saveSevenPlayerPointsP1(pointsP1); updatePointsP1(); }
    @OnClick(R.id.btDownP2) void onP2DownButtonClick() { pointsP2--; sharedPrefsHelper.saveSevenPlayerPointsP2(pointsP2); updatePointsP2(); }
    @OnClick(R.id.btDownP3) void onP3DownButtonClick() { pointsP3--; sharedPrefsHelper.saveSevenPlayerPointsP3(pointsP3); updatePointsP3(); }
    @OnClick(R.id.btDownP4) void onP4DownButtonClick() { pointsP4--; sharedPrefsHelper.saveSevenPlayerPointsP4(pointsP4); updatePointsP4(); }
    @OnClick(R.id.btDownP5) void onP5DownButtonClick() { pointsP5--; sharedPrefsHelper.saveSevenPlayerPointsP5(pointsP5); updatePointsP5(); }
    @OnClick(R.id.btDownP6) void onP6DownButtonClick() { pointsP6--; sharedPrefsHelper.saveSevenPlayerPointsP6(pointsP6); updatePointsP6(); }
    @OnClick(R.id.btDownP7) void onP7DownButtonClick() { pointsP7--; sharedPrefsHelper.saveSevenPlayerPointsP7(pointsP7); updatePointsP7(); }
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
    private void updatePointsP1() {
        tvPointsP1ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP1, tvPointsP1ForAnimation, pointsP1));
    }
    private void updatePointsP2() {
        tvPointsP2ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP2, tvPointsP2ForAnimation, pointsP2));
    }
    private void updatePointsP3() {
        tvPointsP3ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP3, tvPointsP3ForAnimation, pointsP3));
    }
    private void updatePointsP4() {
        tvPointsP4ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP4, tvPointsP4ForAnimation, pointsP4));
    }
    private void updatePointsP5() {
        tvPointsP5ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP5, tvPointsP5ForAnimation, pointsP5));
    }
    private void updatePointsP6() {
        tvPointsP6ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP6, tvPointsP6ForAnimation, pointsP6));
    }
    private void updatePointsP7() {
        tvPointsP7ForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP7, tvPointsP7ForAnimation, pointsP7));
    }
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
    private void restartP1Points() { pointsP1 = initialPoints; sharedPrefsHelper.saveSevenPlayerPointsP1(pointsP1); updatePointsP1(); }
    private void restartP2Points() { pointsP2 = initialPoints; sharedPrefsHelper.saveSevenPlayerPointsP2(pointsP2); updatePointsP2(); }
    private void restartP3Points() { pointsP3 = initialPoints; sharedPrefsHelper.saveSevenPlayerPointsP3(pointsP3); updatePointsP3(); }
    private void restartP4Points() { pointsP4 = initialPoints; sharedPrefsHelper.saveSevenPlayerPointsP4(pointsP4); updatePointsP4(); }
    private void restartP5Points() { pointsP5 = initialPoints; sharedPrefsHelper.saveSevenPlayerPointsP5(pointsP5); updatePointsP5(); }
    private void restartP6Points() { pointsP6 = initialPoints; sharedPrefsHelper.saveSevenPlayerPointsP6(pointsP6); updatePointsP6(); }
    private void restartP7Points() { pointsP7 = initialPoints; sharedPrefsHelper.saveSevenPlayerPointsP7(pointsP7); updatePointsP7(); }

    //LIFECYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_seven_players_activity);
        ButterKnife.bind(this);
        sharedPrefsHelper = new SharedPrefsHelper(this);
        initPoints();
    }
    private void initShields() {
        ivShieldP1.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP1, tvPointsP1ForAnimation, pointsP1));
        ivShieldP2.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP2, tvPointsP2ForAnimation, pointsP2));
        ivShieldP3.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP3, tvPointsP3ForAnimation, pointsP3));
        ivShieldP4.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP4, tvPointsP4ForAnimation, pointsP4));
        ivShieldP5.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP5, tvPointsP5ForAnimation, pointsP5));
        ivShieldP6.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP6, tvPointsP6ForAnimation, pointsP6));
        ivShieldP7.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP7, tvPointsP7ForAnimation, pointsP7));
    }
    private void initPoints() {
        new Thread(() -> {
            initialPoints = sharedPrefsHelper.getInitialPoints();
            pointsP1 = sharedPrefsHelper.getSevenPlayerPointsP1();
            pointsP2 = sharedPrefsHelper.getSevenPlayerPointsP2();
            pointsP3 = sharedPrefsHelper.getSevenPlayerPointsP3();
            pointsP4 = sharedPrefsHelper.getSevenPlayerPointsP4();
            pointsP5 = sharedPrefsHelper.getSevenPlayerPointsP5();
            pointsP6 = sharedPrefsHelper.getSevenPlayerPointsP6();
            pointsP7 = sharedPrefsHelper.getSevenPlayerPointsP7();
            runOnUiThread(() -> {
                initShields();
                updatePointsP1();
                updatePointsP2();
                updatePointsP3();
                updatePointsP4();
                updatePointsP5();
                updatePointsP6();
                updatePointsP7();
            });
        }).run();
    }
}
