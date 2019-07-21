package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;

public class gSixPlayersActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g_six_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initPlayer(61, R.id.flPlayer61, 16, 8, 48);
        initPlayer(62, R.id.flPlayer62, 16, 8, 48);
        initPlayer(63, R.id.flPlayer63, 16, 8, 48);
        initPlayer(64, R.id.flPlayer64, 16, 8, 48);
        initPlayer(65, R.id.flPlayer65, 16, 8, 48);
        initPlayer(66, R.id.flPlayer66, 16, 8, 48);
    }
}
