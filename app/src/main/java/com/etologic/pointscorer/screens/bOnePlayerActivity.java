package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.etologic.pointscorer.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class bOnePlayerActivity extends AppCompatActivity {

    @BindView(R.id.flPlayer11) FrameLayout player11Container;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_one_player_activity);
        ButterKnife.bind(this);
    }

    @Override protected void onResume() {
        super.onResume();
        PlayerFragment player11Fragment = new PlayerFragment();
        player11Fragment.setPlayerId(11);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.replace(R.id.flPlayer11, player11Fragment);
        fragmentTransaction.commit();
    }
}