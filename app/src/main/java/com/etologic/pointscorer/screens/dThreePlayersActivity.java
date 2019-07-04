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


