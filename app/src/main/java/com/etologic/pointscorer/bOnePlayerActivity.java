package com.etologic.pointscorer;

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

public class bOnePlayerActivity extends AppCompatActivity {

    //VIEWS
    @BindView(R.id.ivShield) ImageView ivShield;
    @BindView(R.id.tvPoints) TextView tvPoints;
    @BindView(R.id.tvPointsForAnimation) TextView tvPointsForAnimation;
    //FIELDS
    private SharedPrefsHelper sharedPrefsHelper;
    private int initialPoints;
    private int points;
    private Handler repeatUpdateHandler = new Handler();
    private boolean isAutoIncrement = false;
    private boolean isAutoDecrement = false;

    //INNER CLASSES
    class RepeatUpdater implements Runnable {
        public void run() {
            if (isAutoIncrement) { points++; sharedPrefsHelper.saveOnePlayerPoints(points); updatePoints(); repeatUpdateHandler.postDelayed(new RepeatUpdater(), REP_DELAY); }
            if (isAutoDecrement) { points--; sharedPrefsHelper.saveOnePlayerPoints(points); updatePoints(); repeatUpdateHandler.postDelayed(new RepeatUpdater(), REP_DELAY); }
        }
    }

    //EVENTS
    @OnLongClick(R.id.btUp) boolean onUpLongClickButton() {
        isAutoIncrement = true; repeatUpdateHandler.post(new RepeatUpdater()); return false;
    }
    @OnLongClick(R.id.btDown) boolean onDownLongClickButton() {
        isAutoDecrement = true; repeatUpdateHandler.post(new RepeatUpdater()); return false;
    }
    @OnTouch(R.id.btUp) boolean onUpTouch(MotionEvent event) {
        if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) { isAutoIncrement = false; } return false;
    }
    @OnTouch(R.id.btDown) boolean onDownTouch(MotionEvent event) {
        if((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) { isAutoDecrement = false; } return false;
    }
    @OnClick(R.id.btUp) void onUpClickButton() {
        points++; sharedPrefsHelper.saveOnePlayerPoints(points); updatePoints();
    }
    @OnClick(R.id.btDown) void onDownClickButton() {
        points--; sharedPrefsHelper.saveOnePlayerPoints(points); updatePoints();
    }
    @OnClick(R.id.ibMenu)
    void onMenuButtonClick(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_restart:
                    restartPlayerPoints();
                    return true;
                default:
                    return false;
            }
        });
        popup.inflate(R.menu.one_player_menu);
        popup.show();
    }
    private void updatePoints() { tvPointsForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPoints, tvPointsForAnimation, points)); }
    private void restartPlayerPoints() { points = initialPoints; sharedPrefsHelper.saveOnePlayerPoints(points); updatePoints(); }

    //LIFECYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_one_player_activity);
        ButterKnife.bind(this);
        sharedPrefsHelper = new SharedPrefsHelper(this);
        initShield();
        initPoints();
    }
    private void initShield() { ivShield.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPoints, tvPointsForAnimation, points)); }
    private void initPoints() {
        initialPoints = sharedPrefsHelper.getInitialPoints();
        points = sharedPrefsHelper.getOnePlayerPoints();
        updatePoints();
    }
}