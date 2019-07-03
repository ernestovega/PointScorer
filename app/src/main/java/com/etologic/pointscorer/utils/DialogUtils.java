package com.etologic.pointscorer.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.LinearLayout;

import com.etologic.pointscorer.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorListener;

import androidx.appcompat.app.AlertDialog;

public class DialogUtils {

    public interface NameDialogListener {
        void onNameChanged(String name);
    }

    public interface ColorDialogListener {
        void onColorChanged(int color);
    }

    public static void showNameDialog(LayoutInflater layoutInflater, Context context, NameDialogListener listener, CharSequence currentName) {
        @SuppressLint("InflateParams")
        TextInputLayout til = (TextInputLayout) layoutInflater.inflate(R.layout._dialog_edittext, null);
        TextInputEditText tiet = til.findViewById(R.id.tietName);
        tiet.setText(currentName);
        tiet.requestFocus();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            if (tiet.getText() != null) {
                String name = tiet.getText().toString().trim();
                listener.onNameChanged(name);
                KeyboardUtils.hideKeyboard(context, tiet);
            }
        })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(til)
                .create()
                .show();
        KeyboardUtils.showKeyboard(context);
    }

    public static void showColorDialog(LayoutInflater layoutInflater, Context context, ColorDialogListener colorListener) {
                @SuppressLint("InflateParams")
                LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout._dialog_color, null);
                ColorPickerView colorPicker = linearLayout.findViewById(R.id.colorPickerView);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
