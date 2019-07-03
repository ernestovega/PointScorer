package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etologic.pointscorer.R;
import com.etologic.pointscorer.SharedPrefsHelper;
import com.etologic.pointscorer.utils.DialogUtils;
import com.etologic.pointscorer.utils.MyAnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_UP;
import static com.etologic.pointscorer.screens.aMainActivity.REP_DELAY;

public class PlayerFragment extends Fragment {

    private static final int MAX_POINTS = 999;
    private static final int MIN_POINTS = 999;
    //VIEWS
    @BindView(R.id.acetName) AppCompatEditText etName;
    @BindView(R.id.ivShield) ImageView ivShield;
    @BindView(R.id.tvPoints) TextView tvPoints;
    @BindView(R.id.tvPointsForAnimation) TextView tvPointsForAnimation;
    //FIELDS
    private int playerId;
    private SharedPrefsHelper sharedPrefsHelper;
    private int initialPoints;
    private int points;
    private Handler repeatUpdateHandler = new Handler();
    /**
     * https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed
     */
    private boolean isAutoIncrement = false;
    private boolean isAutoDecrement = false;

    //INNER CLASSES
    class RepeatUpdater implements Runnable {
        public void run() {
            if (isAutoIncrement) {
                incrementPoints();
                sharedPrefsHelper.savePlayerPoints(points, playerId);
                updatePoints();
                repeatUpdateHandler.postDelayed(new RepeatUpdater(), REP_DELAY);
            }
            if (isAutoDecrement) {
                decrementPoints();
                sharedPrefsHelper.savePlayerPoints(points, playerId);
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
        if ((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoIncrement) {
            isAutoIncrement = false;
        }
        return false;
    }
    @OnTouch(R.id.btDown) boolean onDownTouch(MotionEvent event) {
        if ((event.getAction() == ACTION_UP || event.getAction() == ACTION_CANCEL) && isAutoDecrement) {
            isAutoDecrement = false;
        }
        return false;
    }
    @OnClick(R.id.btUp) void onUpClickButton() {
        incrementPoints();
        updatePoints();
    }
    private void incrementPoints() { if (points < MAX_POINTS) points++; }
    private void updatePoints() {
        tvPointsForAnimation.startAnimation(
                MyAnimationUtils.getUpdatePointsAnimation(
                        tvPoints,
                        tvPointsForAnimation,
                        points,
                        () -> new Thread(
                                () -> sharedPrefsHelper.savePlayerPoints(points, playerId)
                            ).run()));
    }
    @OnClick(R.id.btDown) void onDownClickButton() {
        decrementPoints();
        updatePoints();
    }
    private void decrementPoints() { if (points > MIN_POINTS) points--; }
    @OnClick(R.id.ibMenu) void onMenuButtonClick(View view) {
        if(getContext() != null) {
            PopupMenu popup = new PopupMenu(getContext(), view);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_restart:
                        restartPlayerPoints();
                        return true;
                    case R.id.menu_edit_name:
                        if(getActivity() != null) {
                            DialogUtils.showNameDialog(getActivity().getLayoutInflater(), getContext(), name -> {
                                sharedPrefsHelper.savePlayerName(name, playerId);
                                etName.setText(name);
                            }, etName.getText());
                        }
                        return true;
                    case R.id.menu_edit_color:
                        if(getActivity() != null) {
                            DialogUtils.showColorDialog(getActivity().getLayoutInflater(), getContext(), color -> {
                                sharedPrefsHelper.savePlayerColor(color, playerId);
                                setTextsColor(color);
                            });
                        }
                        return true;
                    default:
                        return false;
                }
            });
            popup.inflate(R.menu.one_player_menu);
            popup.show();
        }
    }
    private void setTextsColor(int color) {
        etName.setTextColor(color);
        etName.setHintTextColor(color);
        tvPoints.setTextColor(color);
        tvPointsForAnimation.setTextColor(color);
    }
    private void restartPlayerPoints() {
        points = initialPoints;
        updatePoints();
    }

    //PUBLIC
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
        initSharedPrefs(playerId);
        initNames();
        initColors();
        initPoints();
        initShield();
        updatePoints();
    }

    //LIFECYCLE
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.b_one_player_activity, container, true);
        ButterKnife.bind(view);
        return view;
    }
    private void initSharedPrefs(int playerId) {
        new Thread(() -> {
            if (getContext() != null) {
                sharedPrefsHelper = new SharedPrefsHelper(getContext(), playerId);
            }
        }).run();
    }
    private void initNames() {
        etName.setText(sharedPrefsHelper.getPlayerName(playerId));
    }
    private void initColors() {
        setTextsColor(sharedPrefsHelper.getPlayerColor(playerId));
    }
    private void initPoints() {
        initialPoints = sharedPrefsHelper.getInitialPoints();
        points = sharedPrefsHelper.getPlayerPoints(playerId);
    }
    private void initShield() {
        ivShield.startAnimation(MyAnimationUtils.getShieldAnimation());
    }
}
