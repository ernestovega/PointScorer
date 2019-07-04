package com.etologic.pointscorer.screens;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.etologic.pointscorer.R;
import com.etologic.pointscorer.SharedPrefsHelper;
import com.etologic.pointscorer.utils.DialogUtils;
import com.etologic.pointscorer.utils.MyAnimationUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.PopupMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTouch;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_UP;
import static com.etologic.pointscorer.screens.aMainActivity.REP_DELAY;

public class iEightPlayersActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.i_eight_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PlayerFragment player81Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer81);
        PlayerFragment player82Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer82);
        PlayerFragment player83Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer83);
        PlayerFragment player84Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer84);
        PlayerFragment player85Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer85);
        PlayerFragment player86Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer86);
        PlayerFragment player87Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer87);
        PlayerFragment player88Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer88);
        if (player81Fragment != null) player81Fragment.setPlayerId(81);
        if (player82Fragment != null) player82Fragment.setPlayerId(82);
        if (player83Fragment != null) player83Fragment.setPlayerId(83);
        if (player84Fragment != null) player84Fragment.setPlayerId(84);
        if (player85Fragment != null) player85Fragment.setPlayerId(85);
        if (player86Fragment != null) player86Fragment.setPlayerId(86);
        if (player87Fragment != null) player87Fragment.setPlayerId(87);
        if (player88Fragment != null) player88Fragment.setPlayerId(88);
    }
}
