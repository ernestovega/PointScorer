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

public class fFivePlayersActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_five_players_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PlayerFragment player51Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer51);
        PlayerFragment player52Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer52);
        PlayerFragment player53Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer53);
        PlayerFragment player54Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer54);
        PlayerFragment player55Fragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPlayer55);
        if (player51Fragment != null) player51Fragment.setPlayerId(51);
        if (player52Fragment != null) player52Fragment.setPlayerId(52);
        if (player53Fragment != null) player53Fragment.setPlayerId(53);
        if (player54Fragment != null) player54Fragment.setPlayerId(54);
        if (player55Fragment != null) player55Fragment.setPlayerId(55);
    }
}
