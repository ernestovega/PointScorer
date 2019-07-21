package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.etologic.pointscorer.R;
import com.etologic.pointscorer.SharedPrefsHelper;
import com.etologic.pointscorer.utils.MyAnimationUtils;
import com.etologic.pointscorer.utils.MySizeUtils;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_UP;
import static com.etologic.pointscorer.screens.aMainActivity.REP_DELAY;

public class PlayerFragment extends Fragment {

    //CONSTANTS
    private static final int MAX_POINTS = 9999;
    private static final int MIN_POINTS = -999;
    static final String KEY_PLAYER_ID = "key_playerId";
    static final String KEY_PLAYER_NAME_SIZE = "key_player_name_size";
    static final String KEY_PLAYER_NAME_MARGIN_TOP = "key_player_name_margin_top";
    static final String KEY_PLAYER_POINTS_SIZE = "key_player_points_size";

    //FIELDS
    private Unbinder unbinder;
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
    private int downCount, upCount;

    //VIEWS
    @BindView(R.id.etName) AppCompatEditText etName;
    @BindView(R.id.ivShield) AppCompatImageView ivShield;
    @BindView(R.id.tvPointsPlayer) AppCompatTextView tvPoints;
    @BindView(R.id.tvPointsForAnimation) AppCompatTextView tvPointsForAnimation;
    @BindView(R.id.tvUpCount) AppCompatTextView tvUpCount;
    @BindView(R.id.tvDownCount) AppCompatTextView tvDownCount;
    @BindColor(R.color.gray_text) int defaultTextColor;
    private int playerNameSize, playerNameMarginTop, playerPointsSize, playerPointsMarginTop;

    //LIFECYCLE
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_fragment, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initSharedPrefs();
        initValues();
        if (playerId > 0) {
            initName();
            initPoints();
            initColors();
            initShieldAndPoints();
        }
    }
    private void initValues() {
        if (getArguments() == null) {
            playerId = 0;
            playerNameSize = 16;
            playerPointsSize = 48;
        } else {
            playerId = getArguments().getInt(KEY_PLAYER_ID);
            playerNameSize = getArguments().getInt(KEY_PLAYER_NAME_SIZE);
            playerNameMarginTop = getArguments().getInt(KEY_PLAYER_NAME_MARGIN_TOP, 8);
            playerPointsSize = getArguments().getInt(KEY_PLAYER_POINTS_SIZE);
            playerPointsMarginTop = 0;
        }
    }
    private void initSharedPrefs() {
        new Thread(() -> {
            if (getContext() != null) {
                sharedPrefsHelper = new SharedPrefsHelper(getContext());
            }
        }).run();
    }
    private void initPoints() {
        initialPoints = sharedPrefsHelper.getInitialPoints();
        points = sharedPrefsHelper.getPlayerPoints(playerId);
        tvPoints.setTextSize(playerPointsSize);
        tvPointsForAnimation.setTextSize(playerPointsSize);
        tvPoints.setPadding(0, MySizeUtils.dp2Px(tvPoints.getContext(), playerPointsMarginTop), 0, 0);
        tvPointsForAnimation.setPadding(0, MySizeUtils.dp2Px(tvPoints.getContext(), playerPointsMarginTop), 0, 0);
    }
    private void initName() {
        etName.setPadding(0, MySizeUtils.dp2Px(tvPoints.getContext(), playerNameMarginTop), 0, 0);
        etName.setTextSize(playerNameSize);
        etName.setText(sharedPrefsHelper.getPlayerName(playerId));
    }
    private void initColors() { setTextsColor(sharedPrefsHelper.getPlayerColor(playerId)); }
    private void setTextsColor(int color) {
        etName.setTextColor(color);
        etName.setHintTextColor(color);
        tvPoints.setTextColor(color);
        tvPointsForAnimation.setTextColor(color);
    }
    private void initShieldAndPoints() {
        ivShield.startAnimation(MyAnimationUtils.getShieldAnimation());
        tvPointsForAnimation.startAnimation(MyAnimationUtils.getUpdatePointsAnimation(tvPoints, tvPointsForAnimation, points));
    }
    @Override public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    //INNER CLASSES
    class RepeatUpdater implements Runnable {
        public void run() {
            if (isAutoIncrement)
                incrementPoints();
            else if (isAutoDecrement)
                decrementPoints();
            else {
                tvUpCount.startAnimation(MyAnimationUtils.getFadeOutAnimation(() -> {
                    tvUpCount.setText("");
                    upCount = 0;
                    Log.d("WARNING_COUNTER_UP", "tvUpCount.endAnimation -> upText: \"\", upCount: 0");
                }));
                tvDownCount.startAnimation(MyAnimationUtils.getFadeOutAnimation(() -> {
                    tvDownCount.setText("");
                    downCount = 0;
                    Log.d("WARNING_COUNTER_DOWN", "tvDownCount.endAnimation -> downText: \"\", downCount: 0");
                }));
                return;
            }
            sharedPrefsHelper.savePlayerPoints(points, playerId);
            updatePoints();
            repeatUpdateHandler.postDelayed(new RepeatUpdater(), REP_DELAY);
        }
    }
    //EVENTS
    @OnLongClick(R.id.btUp) boolean onUpLongClickButton() {
        isAutoIncrement = true;
        upCount = 0;
        Log.d("WARNING_COUNTER_UP", "onUpLongClickButton -> upCount: 0");
        repeatUpdateHandler.post(new RepeatUpdater());
        return false;
    }
    @OnLongClick(R.id.btDown) boolean onDownLongClickButton() {
        isAutoDecrement = true;
        downCount = 0;
        Log.d("WARNING_COUNTER_DOWN", "onDownLongClickButton -> downCount: 0");
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
        tvUpCount.startAnimation(MyAnimationUtils.getFadeOutAnimation(() -> {
            tvUpCount.setText("");
            upCount = 0;
            Log.d("WARNING_COUNTER_UP", "onUpClickButton -> upText: \"\", upCount: 0");
        }));
    }
    private void incrementPoints() {
        if (points < MAX_POINTS) {
            points++;
            upCount++;
            Log.d("WARNING_COUNTER_UP", String.format(Locale.ENGLISH, "incrementPoints -> upCount: %d", upCount));
        }
    }
    private void updatePoints() {
        String formattedUpCount = "";
        String formattedDownCount = "";
        if(upCount != 0) formattedUpCount = String.format(Locale.ENGLISH, "%+d", upCount);
        if(downCount != 0) formattedDownCount = String.format(Locale.ENGLISH, "%+d", downCount);
        tvUpCount.setText(formattedUpCount);
        tvDownCount.setText(formattedDownCount);
        Log.d("WARNING_COUNTER_UP", String.format(Locale.ENGLISH, "updatePoints -> upText: %s", formattedUpCount.isEmpty() ? "\"\"" : formattedUpCount));
        Log.d("WARNING_COUNTER_DOWN", String.format(Locale.ENGLISH, "updatePoints -> downText: %s", formattedDownCount.isEmpty() ? "\"\"" : formattedDownCount));

        MyAnimationUtils.AnimationEndListener animationEndListener = () -> new Thread(() -> sharedPrefsHelper.savePlayerPoints(points, playerId)).run();
        Animation updatePointsAnimation = MyAnimationUtils.getUpdatePointsAnimation(
                tvPoints, tvPointsForAnimation, points, animationEndListener);

        tvPointsForAnimation.startAnimation(updatePointsAnimation);
    }
    @OnClick(R.id.btDown) void onDownClickButton() {
        decrementPoints();
        updatePoints();
        tvDownCount.startAnimation(MyAnimationUtils.getFadeOutAnimation(() -> {
            tvDownCount.setText("");
            downCount = 0;
            Log.d("WARNING_COUNTER_DOWN", "onDownClickButton -> downText: \"\", downCount: 0");
        }));
    }
    private void decrementPoints() {
        if (points > MIN_POINTS) {
            points--;
            downCount--;
            Log.d("WARNING_COUNTER_DOWN", String.format(Locale.ENGLISH, "decrementPoints -> downCount: %d", downCount));
        }
    }
    @OnClick(R.id.ibMenu) void onMenuButtonClick(View view) {
        if (getContext() != null) {
            PopupMenu popup = new PopupMenu(getContext(), view);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_edit_player:
                        showPlayerDialog();
                        return true;
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
    }
    private void showPlayerDialog() {
        if (getActivity() != null) {
            PlayerDialogFragment playerDialogFragment = new PlayerDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(PlayerDialogFragment.KEY_INITIAL_COLOR, etName.getCurrentTextColor());
            String name = etName.getText() == null ? sharedPrefsHelper.getPlayerName(playerId) : etName.getText().toString();
            bundle.putString(PlayerDialogFragment.KEY_INITIAL_NAME, name);
            playerDialogFragment.setArguments(bundle);
            playerDialogFragment.show(getActivity().getSupportFragmentManager(), PlayerDialogFragment.TAG);
            playerDialogFragment.setPlayerDialogListener(new PlayerDialogFragment.PlayerDialogListener() {
                @Override public void onColorChanged(int color) {
                    sharedPrefsHelper.savePlayerColor(color, playerId);
                    setTextsColor(color == 0 ? defaultTextColor : color);
                    Log.d("WARNING_COLOR", String.format(Locale.ENGLISH, "PlayerFragment -> PlayerDialogListener.onColorChanged: %d", color));
                }
                @Override public void onNameChanged(String name) {
                    sharedPrefsHelper.savePlayerName(name, playerId);
                    etName.setText(name);
                    Log.d("WARNING_NAME", String.format("PlayerFragment -> PlayerDialogListener.onNameChanged: %s", name));
                }
            });
        }
    }
    private void restartPlayerPoints() {
        points = initialPoints;
        updatePoints();
    }
}
