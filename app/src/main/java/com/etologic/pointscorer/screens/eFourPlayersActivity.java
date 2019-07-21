package com.etologic.pointscorer.screens;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

public class eFourPlayersActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_four_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int nameMarginTop = this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 4 : 40;

        initPlayer(41, R.id.flPlayer41, 16, nameMarginTop, 48);
        initPlayer(42, R.id.flPlayer42, 16, nameMarginTop, 48);
        initPlayer(43, R.id.flPlayer43, 16, nameMarginTop, 48);
        initPlayer(44, R.id.flPlayer44, 16, nameMarginTop, 48);
    }
}
