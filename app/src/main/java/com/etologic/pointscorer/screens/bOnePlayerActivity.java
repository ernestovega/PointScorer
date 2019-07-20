package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;

public class bOnePlayerActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_one_player_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle bundle = new Bundle();
        bundle.putInt(PlayerFragment.KEY_PLAYER_ID, PLAYER_1_ID);

        PlayerFragment player11Fragment = new PlayerFragment();
        player11Fragment.setArguments(bundle);

        addFragment(player11Fragment, R.id.flPlayer11);
    }

    @SuppressWarnings("SameParameterValue")
    private void addFragment(PlayerFragment fragment, int frameLayout) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .add(frameLayout, fragment)
                .commit();
    }

    @Override protected void onResume() {
        super.onResume();
    }
    public static final int PLAYER_1_ID = 11;
}