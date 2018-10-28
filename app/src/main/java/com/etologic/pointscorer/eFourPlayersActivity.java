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

import com.etologic.pointscorer.utils.DialogUtils;
import com.etologic.pointscorer.utils.MyAnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_UP;
import static com.etologic.pointscorer.aMainActivity.REP_DELAY;

public class eFourPlayersActivity extends AppCompatActivity {

    //VIEWS
    @BindView(R.id.tvNameP1) TextView tvNameP1;
    @BindView(R.id.tvNameP2) TextView tvNameP2;
    @BindView(R.id.tvNameP3) TextView tvNameP3;
    @BindView(R.id.tvNameP4) TextView tvNameP4;
    @BindView(R.id.ivShieldP1) ImageView ivShieldP1;
    @BindView(R.id.ivShieldP2) ImageView ivShieldP2;
    @BindView(R.id.ivShieldP3) ImageView ivShieldP3;
    @BindView(R.id.ivShieldP4) ImageView ivShieldP4;
    @BindView(R.id.tvPointsP1) TextView tvPointsP1;
    @BindView(R.id.tvPointsP2) TextView tvPointsP2;
    @BindView(R.id.tvPointsP3) TextView tvPointsP3;
    @BindView(R.id.tvPointsP4) TextView tvPointsP4;
    @BindView(R.id.tvPointsP1ForAnimation) TextView tvPointsP1ForAnimation;
    @BindView(R.id.tvPointsP2ForAnimation) TextView tvPointsP2ForAnimation;
    @BindView(R.id.tvPointsP3ForAnimation) TextView tvPointsP3ForAnimation;
    @BindView(R.id.tvPointsP4ForAnimation) TextView tvPointsP4ForAnimation;
    //FIELDS
    private SharedPrefsHelper sharedPrefsHelper;
    private int initialPoints;
    private int pointsP1;
    private int pointsP2;
    private int pointsP3;
    private int pointsP4;
    private Handler repeatUpdateHandlerP1 = new Handler();
    private Handler repeatUpdateHandlerP2 = new Handler();
    private Handler repeatUpdateHandlerP3 = new Handler();
    private Handler repeatUpdateHandlerP4 = new Handler();
    private boolean isAutoIncrement = false;
    private boolean isAutoDecrement = false;

    //INNER CLASSES
    class RepeatUpdaterP1 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP1++; sharedPrefsHelper.saveFourPlayerPointsP1(pointsP1); updatePointsP1(); repeatUpdateHandlerP1.postDelayed(new eFourPlayersActivity.RepeatUpdaterP1(), REP_DELAY); }
            if (isAutoDecrement) { pointsP1--; sharedPrefsHelper.saveFourPlayerPointsP1(pointsP1); updatePointsP1(); repeatUpdateHandlerP1.postDelayed(new eFourPlayersActivity.RepeatUpdaterP1(), REP_DELAY); }
        }
    }
    class RepeatUpdaterP2 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP2++; sharedPrefsHelper.saveFourPlayerPointsP2(pointsP2); updatePointsP2(); repeatUpdateHandlerP2.postDelayed(new eFourPlayersActivity.RepeatUpdaterP2(), REP_DELAY); }
            if (isAutoDecrement) { pointsP2--; sharedPrefsHelper.saveFourPlayerPointsP2(pointsP2); updatePointsP2(); repeatUpdateHandlerP2.postDelayed(new eFourPlayersActivity.RepeatUpdaterP2(), REP_DELAY); }
        }
    }
    class RepeatUpdaterP3 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP3++; sharedPrefsHelper.saveFourPlayerPointsP3(pointsP3); updatePointsP3(); repeatUpdateHandlerP3.postDelayed(new eFourPlayersActivity.RepeatUpdaterP3(), REP_DELAY); }
            if (isAutoDecrement) { pointsP3--; sharedPrefsHelper.saveFourPlayerPointsP3(pointsP3); updatePointsP3(); repeatUpdateHandlerP3.postDelayed(new eFourPlayersActivity.RepeatUpdaterP3(), REP_DELAY); }
        }
    }
    class RepeatUpdaterP4 implements Runnable {
        public void run() {
            if (isAutoIncrement) { pointsP4++; sharedPrefsHelper.saveFourPlayerPointsP4(pointsP4); updatePointsP4(); repeatUpdateHandlerP4.postDelayed(new eFourPlayersActivity.RepeatUpdaterP4(), REP_DELAY); }
            if (isAutoDecrement) { pointsP4--; sharedPrefsHelper.saveFourPlayerPointsP4(pointsP4); updatePointsP4(); repeatUpdateHandlerP4.postDelayed(new eFourPlayersActivity.RepeatUpdaterP4(), REP_DELAY); }
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
    @OnClick(R.id.btUpP1) void onP1UpButtonClick() { if(pointsP1 < 9999) pointsP1++; sharedPrefsHelper.saveFourPlayerPointsP1(pointsP1); updatePointsP1(); }
    @OnClick(R.id.btUpP2) void onP2UpButtonClick() { if(pointsP2 < 9999) pointsP2++; sharedPrefsHelper.saveFourPlayerPointsP2(pointsP2); updatePointsP2(); }
    @OnClick(R.id.btUpP3) void onP3UpButtonClick() { if(pointsP3 < 9999) pointsP3++; sharedPrefsHelper.saveFourPlayerPointsP3(pointsP3); updatePointsP3(); }
    @OnClick(R.id.btUpP4) void onP4UpButtonClick() { if(pointsP4 < 9999) pointsP4++; sharedPrefsHelper.saveFourPlayerPointsP4(pointsP4); updatePointsP4(); }
    @OnClick(R.id.btDownP1) void onP1DownButtonClick() { if(pointsP1 > -999) pointsP1--; sharedPrefsHelper.saveFourPlayerPointsP1(pointsP1); updatePointsP1(); }
    @OnClick(R.id.btDownP2) void onP2DownButtonClick() { if(pointsP2 > -999) pointsP2--; sharedPrefsHelper.saveFourPlayerPointsP2(pointsP2); updatePointsP2(); }
    @OnClick(R.id.btDownP3) void onP3DownButtonClick() { if(pointsP3 > -999) pointsP3--; sharedPrefsHelper.saveFourPlayerPointsP3(pointsP3); updatePointsP3(); }
    @OnClick(R.id.btDownP4) void onP4DownButtonClick() { if(pointsP4 > -999) pointsP4--; sharedPrefsHelper.saveFourPlayerPointsP4(pointsP4); updatePointsP4(); }
    @OnClick(R.id.ibMenuP1) void onP1MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_edit_name:
                    DialogUtils.showNameDialog(this, name -> {
                        sharedPrefsHelper.saveFourPlayerNameP1(name);
                        tvNameP1.setText(name);
                    }, tvNameP1.getText());
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
                        sharedPrefsHelper.saveFourPlayerNameP2(name);
                        tvNameP2.setText(name);
                    }, tvNameP2.getText());
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
    @OnClick(R.id.ibMenuP3) void onP3MenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_edit_name:
                    DialogUtils.showNameDialog(this, name -> {
                        sharedPrefsHelper.saveFourPlayerNameP3(name);
                        tvNameP3.setText(name);
                    }, tvNameP3.getText());
                    return true;
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
                case R.id.menu_edit_name:
                    DialogUtils.showNameDialog(this, name -> {
                        sharedPrefsHelper.saveFourPlayerNameP4(name);
                        tvNameP4.setText(name);
                    }, tvNameP4.getText());
                    return true;
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
    private void restartAllPlayersPoints() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog);
        builder.setMessage(getString(R.string.restart_all_players_points_))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    restartP1Points();
                    restartP2Points();
                    restartP3Points();
                    restartP4Points();
                })
                .create()
                .show();
    }
    private void restartP1Points() { pointsP1 = initialPoints; sharedPrefsHelper.saveFourPlayerPointsP1(pointsP1); updatePointsP1(); }
    private void restartP2Points() { pointsP2 = initialPoints; sharedPrefsHelper.saveFourPlayerPointsP2(pointsP2); updatePointsP2(); }
    private void restartP3Points() { pointsP3 = initialPoints; sharedPrefsHelper.saveFourPlayerPointsP3(pointsP3); updatePointsP3(); }
    private void restartP4Points() { pointsP4 = initialPoints; sharedPrefsHelper.saveFourPlayerPointsP4(pointsP4); updatePointsP4(); }

    //LIFECYCLE
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_four_players_activity);
        ButterKnife.bind(this);
        sharedPrefsHelper = new SharedPrefsHelper(this);
        initNames();
        initPoints();
    }
    private void initNames() {
        tvNameP1.setText(sharedPrefsHelper.getFourPlayerNameP1());
        tvNameP2.setText(sharedPrefsHelper.getFourPlayerNameP2());
        tvNameP3.setText(sharedPrefsHelper.getFourPlayerNameP3());
        tvNameP4.setText(sharedPrefsHelper.getFourPlayerNameP4());
    }
    private void initPoints() {
        new Thread(() -> {
            initialPoints = sharedPrefsHelper.getInitialPoints();
            pointsP1 = sharedPrefsHelper.getFourPlayerPointsP1();
            pointsP2 = sharedPrefsHelper.getFourPlayerPointsP2();
            pointsP3 = sharedPrefsHelper.getFourPlayerPointsP3();
            pointsP4 = sharedPrefsHelper.getFourPlayerPointsP4();
            runOnUiThread(() -> {
                initShields();
                updatePointsP1();
                updatePointsP2();
                updatePointsP3();
                updatePointsP4();
            });
        }).run();
    }
    private void initShields() {
        ivShieldP1.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP1, tvPointsP1ForAnimation, pointsP1));
        ivShieldP2.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP2, tvPointsP2ForAnimation, pointsP2));
        ivShieldP3.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP3, tvPointsP3ForAnimation, pointsP3));
        ivShieldP4.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPointsP4, tvPointsP4ForAnimation, pointsP4));
    }
}
