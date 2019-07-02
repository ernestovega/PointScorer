package com.etologic.pointscorer.utils;

import android.annotation.SuppressLint;

import com.etologic.pointscorer.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DialogUtils {

    public interface NameDialogListener {
        void onNameChanged(String name);
    }

    public static void showNameDialog(AppCompatActivity activity, NameDialogListener listener, CharSequence currentName) {
        @SuppressLint("InflateParams")
        TextInputLayout til = (TextInputLayout) activity.getLayoutInflater().inflate(R.layout._dialog_edittext, null);
        TextInputEditText tiet = til.findViewById(R.id.tietName);
        tiet.setText(currentName);
        tiet.requestFocus();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            if (tiet.getText() != null) {
                String name = tiet.getText().toString().trim();
                listener.onNameChanged(name);
                KeyboardUtils.hideKeyboard(activity, tiet);
            }
        })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(til)
                .create()
                .show();
        KeyboardUtils.showKeyboard(activity);
    }
}
