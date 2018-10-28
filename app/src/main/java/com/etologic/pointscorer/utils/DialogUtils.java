package com.etologic.pointscorer.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import com.etologic.pointscorer.R;

public class DialogUtils {

    public interface NameDialogListener {
        void onNameChanged(String name);
    }

    public static void showNameDialog(Activity activity, NameDialogListener listener, CharSequence currentName) {
        @SuppressLint("InflateParams")
        TextInputLayout til = (TextInputLayout) activity.getLayoutInflater().inflate(R.layout.dialog_edittext, null);
        TextInputEditText tiet = til.findViewById(R.id.tietName);
        tiet.setText(currentName);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    String name = tiet.getText().toString().trim();
                    listener.onNameChanged(name);
                    KeyboardUtils.hideKeyboard(activity, tiet);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(til)
                .create()
                .show();
        KeyboardUtils.showKeyboard(activity);
    }
}
