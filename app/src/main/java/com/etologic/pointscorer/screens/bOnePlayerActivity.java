package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etologic.pointscorer.R;
import com.etologic.pointscorer.SharedPrefsHelper;
import com.etologic.pointscorer.utils.AnalyticsTags;
import com.etologic.pointscorer.utils.DialogUtils;
import com.etologic.pointscorer.utils.MyAnimationUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_UP;
import static com.etologic.pointscorer.screens.aMainActivity.REP_DELAY;

public class bOnePlayerActivity extends AppCompatActivity {

    //VIEWS
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.ivShield) ImageView ivShield;
    @BindView(R.id.tvPoints) TextView tvPoints;
    @BindView(R.id.tvPointsForAnimation) TextView tvPointsForAnimation;
    //FIELDS
    private SharedPrefsHelper sharedPrefsHelper;
    private FirebaseAnalytics mFirebaseAnalytics;
    private int initialPoints;
    private int points;
    private Handler repeatUpdateHandler = new Handler();/**https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed*/
    private boolean isAutoIncrement = false;
    private boolean isAutoDecrement = false;

    //INNER CLASSES
    class RepeatUpdater implements Runnable {
        public void run() {
            if (isAutoIncrement) {
                incrementPoints();
                sharedPrefsHelper.saveOnePlayerPoints(points);
                updatePoints();
                repeatUpdateHandler.postDelayed(new RepeatUpdater(), REP_DELAY);
            }
            if (isAutoDecrement) {
                decrementPoints();
                sharedPrefsHelper.saveOnePlayerPoints(points);
                updatePoints();
                repeatUpdateHandler.postDelayed(new RepeatUpdater(), REP_DELAY);
            }
        }
    }

    //EVENTS
    @OnLongClick(R.id.btUp) boolean onUpLongClickButton() {
        isAutoIncrement = true;
        repeatUpdateHandler.post(new RepeatUpdater());
        return false;
    }
    @OnLongClick(R.id.btDown) boolean onDownLongClickButton() {
        isAutoDecrement = true;
        repeatUpdateHandler.post(new RepeatUpdater());
        return false;
    }
    @OnTouch(R.id.btUp) boolean onUpTouch(MotionEvent event) {
        if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) {
            isAutoIncrement = false;
            mFirebaseAnalytics.logEvent(AnalyticsTags.TAG_EVENT_1_PLAYER_POINTS_UP_LONGCLICK, null);
        }
        return false;
    }
    @OnTouch(R.id.btDown) boolean onDownTouch(MotionEvent event) {
        if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) {
            isAutoDecrement = false;
            mFirebaseAnalytics.logEvent(AnalyticsTags.TAG_EVENT_1_PLAYER_POINTS_DOWN_LONGCLICK, null);
        }
        return false;
    }
    @OnClick(R.id.btUp) void onUpClickButton() {
        incrementPoints();
        updatePoints();
        mFirebaseAnalytics.logEvent(AnalyticsTags.TAG_EVENT_1_PLAYER_POINTS_UP_CLICK, null);
    }
    private void incrementPoints() { if (points < 9999) points++; }
    @OnClick(R.id.btDown) void onDownClickButton() {
        decrementPoints();
        updatePoints();
        mFirebaseAnalytics.logEvent(AnalyticsTags.TAG_EVENT_1_PLAYER_POINTS_DOWN_CLICK, null);
    }
    private void decrementPoints() { if(points > -999) points--; }
    @OnClick(R.id.ibMenu) void onMenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_restart:
                    restartPlayerPoints();
                    return true;
                case R.id.menu_edit_name:
                    DialogUtils.showNameDialog(this, name -> {
                        sharedPrefsHelper.saveOnePlayerName(name);
                        tvName.setText(name);
                    }, tvName.getText());
                    return true;
                default:
                    return false;
            }
        });
        popup.inflate(R.menu.one_player_menu);
        popup.show();
    }
    private void restartPlayerPoints() { points = initialPoints; updatePoints(); }

    //LIFECYCLE
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_one_player_activity);
        ButterKnife.bind(this);
        initSharedPrefs();
        initNames();
        initPoints();
        initShield();
        updatePoints();
        initAnalytics();
    }
    private void initSharedPrefs() {
        sharedPrefsHelper = new SharedPrefsHelper(this);
    }
    private void initNames() {
        tvName.setText(sharedPrefsHelper.getOnePlayerName());
    }
    private void initPoints() {
        initialPoints = sharedPrefsHelper.getInitialPoints();
        points = sharedPrefsHelper.getOnePlayerPoints();
    }
    private void initShield() {
        ivShield.startAnimation(MyAnimationUtils.getShieldAnimation());
    }
    private void updatePoints() {
        tvPointsForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPoints, tvPointsForAnimation, points, () -> new Thread(() -> sharedPrefsHelper.saveOnePlayerPoints(points)).run() ));
    }
    private void initAnalytics() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserId(FirebaseInstanceId.getInstance().getId());
        mFirebaseAnalytics.setCurrentScreen(this, AnalyticsTags.TAG_SCREEN_1_PLAYER, null);
    }
    @Override protected void onResume() {
        super.onResume();
        mFirebaseAnalytics.logEvent(AnalyticsTags.TAG_SCREEN_1_PLAYER, new Bundle());
    }
}