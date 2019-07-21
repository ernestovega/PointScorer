package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;

public class hSevenPlayersActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_seven_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PlayerFragment player71Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer71);
        PlayerFragment player72Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer72);
        PlayerFragment player73Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer73);
        PlayerFragment player74Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer74);
        PlayerFragment player75Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer75);
        PlayerFragment player76Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer76);
        PlayerFragment player77Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer77);
        if (player71Fragment != null) player71Fragment.setPlayerId(71);
        if (player72Fragment != null) player72Fragment.setPlayerId(72);
        if (player73Fragment != null) player73Fragment.setPlayerId(73);
        if (player74Fragment != null) player74Fragment.setPlayerId(74);
        if (player75Fragment != null) player75Fragment.setPlayerId(75);
        if (player76Fragment != null) player76Fragment.setPlayerId(76);
        if (player77Fragment != null) player77Fragment.setPlayerId(77);
    }
}