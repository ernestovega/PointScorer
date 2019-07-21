package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;

public class dThreePlayersActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_three_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PlayerFragment player31Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer31);
        PlayerFragment player32Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer32);
        PlayerFragment player33Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer33);
        if (player31Fragment != null) player31Fragment.setPlayerId(31);
        if (player32Fragment != null) player32Fragment.setPlayerId(32);
        if (player33Fragment != null) player33Fragment.setPlayerId(33);
    }
}


