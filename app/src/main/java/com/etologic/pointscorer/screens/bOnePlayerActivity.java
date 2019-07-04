package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.view.WindowManager;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class bOnePlayerActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_one_player_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        PlayerFragment player11Fragment = new PlayerFragment(11);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.add(R.id.flPlayer11, player11Fragment);
        fragmentTransaction.commit();
    }

    @Override protected void onResume() {
        super.onResume();
    }
}