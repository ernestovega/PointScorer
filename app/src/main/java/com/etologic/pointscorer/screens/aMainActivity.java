package com.etologic.pointscorer.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;

import com.etologic.pointscorer.R;
import com.etologic.pointscorer.SharedPrefsHelper;
import com.etologic.pointscorer.utils.AnalyticsTags;
import com.etologic.pointscorer.utils.KeyboardUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class aMainActivity extends AppCompatActivity {

    //CONSTANTS
    public static final int REP_DELAY = 100;
    //VIEWS
    @BindView(R.id.tietMainInitialPoints) TextInputEditText tietInitialPoints;
    //FIELDS
    private SharedPrefsHelper sharedPrefsHelper;
    private FirebaseAnalytics mFirebaseAnalytics;

    //EVENTS
    @OnClick(R.id.acbMainResetAll) void onResetAllButtonClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog);
        builder.setTitle(R.string.initial_points)
                .setMessage(R.string.restart_all_saved_points_)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    KeyboardUtils.hideKeyboard(this, tietInitialPoints);
                    sharedPrefsHelper.resetAll();
                    Snackbar.make(tietInitialPoints, R.string.all_points_restarted, Snackbar.LENGTH_LONG).show();

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, AnalyticsTags.TAG_EVENT_MAIN_RESETALLPOINTS);
                    Editable points = tietInitialPoints.getText();
                    bundle.putString(FirebaseAnalytics.Param.VALUE, points == null ? "" : points.toString());
                    mFirebaseAnalytics.logEvent(AnalyticsTags.TAG_EVENT_MAIN_RESETALLPOINTS, bundle);
                })
                .create()
                .show();
    }
    @OnTextChanged(R.id.tietMainInitialPoints) void onInitialPointsChanged(CharSequence text) {
        try {
            sharedPrefsHelper.saveInitialPoints(Integer.valueOf(text.toString()));
        } catch (NumberFormatException nfe) {
            tietInitialPoints.setError(getString(R.string.error_initial_points));
        }
    }
    @OnClick(R.id.btOnePlayer)    void onOnePlayerButtonClick()    { startActivity(new Intent(this, bOnePlayerActivity.class    )); }
    @OnClick(R.id.btTwoPlayers)   void onTwoPlayersButtonClick()   { startActivity(new Intent(this, cTwoPlayersActivity.class   )); }
    @OnClick(R.id.btThreePlayers) void onThreePlayersButtonClick() { startActivity(new Intent(this, dThreePlayersActivity.class )); }
    @OnClick(R.id.btFourPlayers)  void onFourPlayersButtonClick()  { startActivity(new Intent(this, eFourPlayersActivity.class  )); }
    @OnClick(R.id.btFivePlayers)  void onFivePlayersButtonClick()  { startActivity(new Intent(this, fFivePlayersActivity.class  )); }
    @OnClick(R.id.btSixPlayers)   void onSixPlayersButtonClick()   { startActivity(new Intent(this, gSixPlayersActivity.class   )); }
    @OnClick(R.id.btSevenPlayers) void onSevenPlayersButtonClick() { startActivity(new Intent(this, hSevenPlayersActivity.class )); }
    @OnClick(R.id.btEightPlayers) void onEightPlayersButtonClick() { startActivity(new Intent(this, iEightPlayersActivity.class )); }
    //LIFECYCLE
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main_activity);
        ButterKnife.bind(this);

        initSharedPrefs();
        initInitialPoints();
        initAnalytics();
    }
    private void initSharedPrefs() {
        sharedPrefsHelper = new SharedPrefsHelper(this);
        sharedPrefsHelper.initRecordsIfProceed();
    }
    private void initInitialPoints() {
        tietInitialPoints.setText(String.valueOf(sharedPrefsHelper.getInitialPoints()));
    }
    private void initAnalytics() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserId(FirebaseInstanceId.getInstance().getId());
        mFirebaseAnalytics.setCurrentScreen(this, AnalyticsTags.TAG_SCREEN_MAIN, null);
    }
    @Override protected void onResume() {
        super.onResume();
        mFirebaseAnalytics.logEvent(AnalyticsTags.TAG_SCREEN_MAIN, new Bundle());
    }
}
