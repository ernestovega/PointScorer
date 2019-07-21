package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;

public class cTwoPlayersActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_two_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PlayerFragment player21Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer21);
        PlayerFragment player22Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer22);
        if (player21Fragment != null) player21Fragment.setPlayerId(21);
        if (player22Fragment != null) player22Fragment.setPlayerId(22);
    }
}
