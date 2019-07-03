package com.etologic.pointscorer.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.LinearLayout;

import com.etologic.pointscorer.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DialogUtils {

    public interface NameDialogListener {
        void onNameChanged(String name);
    }

    public interface ColorDialogListener {
        void onColorChanged(int color);
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

    public static void showColorDialog(AppCompatActivity activity, ColorDialogListener colorListener) {
                @SuppressLint("InflateParams")
                LinearLayout linearLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout._dialog_color, null);
                ColorPickerView colorPicker = linearLayout.findViewById(R.id.colorPickerView);
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                AlertDialog alertDialog = builder.setView(linearLayout)
                        .setPositiveButton(android.R.string.ok, null)
                        .create();
                colorPicker.setColorListener((ColorListener) (color, fromUser) -> colorListener.onColorChanged(color));
                Window window = alertDialog.getWindow();
                if(window != null) {
                    ColorDrawable drawable = new ColorDrawable(Color.TRANSPARENT);
                    window.setBackgroundDrawable(drawable);
                }
                alertDialog.show();
    }
}
