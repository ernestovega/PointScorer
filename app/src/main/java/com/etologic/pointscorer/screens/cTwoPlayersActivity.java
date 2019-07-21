package com.etologic.pointscorer.screens;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.annotation.NonNull;

public class cTwoPlayersActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_two_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initPlayer(21, R.id.flPlayer21, 24, 2, 80);
        initPlayer(22, R.id.flPlayer22, 24, 2, 80);
    }
}
