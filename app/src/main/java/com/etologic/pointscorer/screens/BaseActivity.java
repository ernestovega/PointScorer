package com.etologic.pointscorer.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
class BaseActivity extends AppCompatActivity {

    protected void initPlayer(int playerId, int frameLayout, int nameSize, int playerNameMarginTop, int pointsSize) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PlayerFragment.KEY_PLAYER_ID, playerId);
        bundle.putInt(PlayerFragment.KEY_PLAYER_NAME_SIZE, nameSize);
        bundle.putInt(PlayerFragment.KEY_PLAYER_NAME_MARGIN_TOP, playerNameMarginTop);
        bundle.putInt(PlayerFragment.KEY_PLAYER_POINTS_SIZE, pointsSize);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .add(frameLayout, fragment)
                .commit();
    }
}
