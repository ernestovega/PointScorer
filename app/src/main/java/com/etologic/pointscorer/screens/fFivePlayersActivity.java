package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;

public class fFivePlayersActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_five_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initPlayer(51, R.id.flPlayer51, 16, 8, 48);
        initPlayer(52, R.id.flPlayer52, 16, 8, 48);
        initPlayer(53, R.id.flPlayer53, 16, 8, 48);
        initPlayer(54, R.id.flPlayer54, 16, 8, 48);
        initPlayer(55, R.id.flPlayer55, 16, 8, 48);
    }
}
