package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;

public class iEightPlayersActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.i_eight_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initPlayer(81, R.id.flPlayer81);
        initPlayer(82, R.id.flPlayer82);
        initPlayer(83, R.id.flPlayer83);
        initPlayer(84, R.id.flPlayer84);
        initPlayer(85, R.id.flPlayer85);
        initPlayer(86, R.id.flPlayer86);
        initPlayer(87, R.id.flPlayer87);
        initPlayer(88, R.id.flPlayer88);
    }
}
