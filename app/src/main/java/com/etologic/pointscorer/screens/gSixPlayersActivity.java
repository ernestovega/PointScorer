package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;

public class gSixPlayersActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g_six_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PlayerFragment player61Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer61);
        PlayerFragment player62Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer62);
        PlayerFragment player63Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer63);
        PlayerFragment player64Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer64);
        PlayerFragment player65Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer65);
        PlayerFragment player66Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer66);
        if (player61Fragment != null) player61Fragment.setPlayerId(61);
        if (player62Fragment != null) player62Fragment.setPlayerId(62);
        if (player63Fragment != null) player63Fragment.setPlayerId(63);
        if (player64Fragment != null) player64Fragment.setPlayerId(64);
        if (player65Fragment != null) player65Fragment.setPlayerId(65);
        if (player66Fragment != null) player66Fragment.setPlayerId(66);
    }
}
