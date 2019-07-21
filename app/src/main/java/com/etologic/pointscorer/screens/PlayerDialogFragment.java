package com.etologic.pointscorer.screens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etologic.pointscorer.R;
import com.etologic.pointscorer.utils.StringUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorListener;
import com.skydoves.colorpickerview.preference.ColorPickerPreferenceManager;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PlayerDialogFragment extends DialogFragment {

    public interface PlayerDialogListener {
        void onColorChanged(int color);
        void onNameChanged(String name);
    }
    static final String TAG = "PlayerDialogFragment";
    static final String KEY_INITIAL_COLOR = "key_initial_color";
    static final String KEY_INITIAL_NAME = "key_initial_name";
    @BindView(R.id.tilName) TextInputLayout tilName;
    @BindView(R.id.tietName) TextInputEditText tietName;
    @BindView(R.id.colorPickerView) ColorPickerView colorPicker;
    @BindView(R.id.brightnessSlideBar) BrightnessSlideBar brightnessSlideBar;
    @BindColor(R.color.gray_text) int defaultColor;
    @BindString(R.string.player_name) String defaultName;
    private Unbinder viewUnbinder;
    private PlayerDialogListener playerDialogListener;
    private String initialName;
    private int initialColor;

    void setPlayerDialogListener(PlayerDialogListener playerDialogListener) {
        this.playerDialogListener = playerDialogListener;
    }

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout._dialog_name_and_color, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewUnbinder = ButterKnife.bind(this, view);
        defaultColor = ContextCompat.getColor(view.getContext(), R.color.gray_text);
        initValues();
        initColorPicker();
        initName();
    }
    private void initValues() {
        if (getArguments() != null) {
            initialColor = getArguments().getInt(KEY_INITIAL_COLOR, defaultColor);
            String argName = getArguments().getString(KEY_INITIAL_NAME, defaultName);
            initialName = StringUtils.isEmpty(argName) ? defaultName : argName;
        }
    }
    private void initColorPicker() {
        colorPicker.attachBrightnessSlider(brightnessSlideBar);
        colorPicker.setColorListener((ColorListener) (color, fromUser) -> {
            if (fromUser) {
                tietName.setTextColor(color);
                if (playerDialogListener != null) playerDialogListener.onColorChanged(color);
            }
        });
    }
    private void initName() {
        tietName.setText(initialName);
        tietName.setTextColor(initialColor);
    }
    @Override public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) getDialog().getWindow().setBackgroundDrawableResource(R.color.colorPrimaryDark);
    }
    @Override public void onResume() {
        super.onResume();
        if (!isAdded()) dismiss();
    }
    @Override public void onDismiss(@NonNull DialogInterface dialog) {
        if (playerDialogListener != null)
            playerDialogListener.onNameChanged(tietName.getText() != null ? tietName.getText().toString() : "");
        super.onDismiss(dialog);
    }
    @Override public void onDestroyView() {
        if (viewUnbinder != null) {
            viewUnbinder.unbind();
        }
        super.onDestroyView();
    }

    @OnClick(R.id.btOk) void onOkClick() {
        dismiss();
    }
    @OnClick(R.id.btCancel) void onCancelClick() {
        if (playerDialogListener != null) {
            playerDialogListener.onColorChanged(initialColor);
            playerDialogListener.onNameChanged(initialName);
        }
        dismiss();
    }
}
