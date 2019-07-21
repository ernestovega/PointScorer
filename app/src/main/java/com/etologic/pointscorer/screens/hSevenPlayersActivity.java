package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;

public class hSevenPlayersActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_seven_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initPlayer(71, R.id.flPlayer71);
        initPlayer(72, R.id.flPlayer72);
        initPlayer(73, R.id.flPlayer73);
        initPlayer(74, R.id.flPlayer74);
        initPlayer(75, R.id.flPlayer75);
        initPlayer(76, R.id.flPlayer76);
        initPlayer(77, R.id.flPlayer77);
    }
}