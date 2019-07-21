package com.etologic.pointscorer.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
class BaseActivity extends AppCompatActivity {

    protected void initPlayer(int playerId, int frameLayout) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PlayerFragment.KEY_PLAYER_ID, playerId);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .add(frameLayout, fragment)
                .commit();
    }
}
