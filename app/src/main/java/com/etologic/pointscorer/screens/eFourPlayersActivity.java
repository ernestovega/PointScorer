package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;

public class eFourPlayersActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_four_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PlayerFragment player41Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer41);
        PlayerFragment player42Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer42);
        PlayerFragment player43Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer43);
        PlayerFragment player44Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer44);
        if (player41Fragment != null) player41Fragment.setPlayerId(41);
        if (player42Fragment != null) player42Fragment.setPlayerId(42);
        if (player43Fragment != null) player43Fragment.setPlayerId(43);
        if (player44Fragment != null) player44Fragment.setPlayerId(44);
    }
}
