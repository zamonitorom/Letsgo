package com.letsgoapp.Utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.letsgoapp.R;

import java.util.Objects;

import static com.letsgoapp.Utils.ContextUtill.GetTopContext;

public class Dialogs {
    private String title;
    private String content;
    private DialogType dialogType;
    private MaterialDialog.InputCallback inputCallback;
    private boolean active = false;

    public static void ShowMessage(String messageText) {

        if (messageText != null && !Objects.equals(messageText, "")) {
            Toast toast = new Toast((Activity) GetTopContext());
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.makeText((Activity) GetTopContext(), messageText, Toast.LENGTH_LONG).show();
        }
    }

    public void ShowDialogAgree(String title, String content) {
        InitDialog(title, content, DialogType.Agree, null);
        ShowDialog();
    }

    private void InitDialog(String title, String content, DialogType dialogType, final MaterialDialog.InputCallback inputCallback) {
        this.title = title;
        this.content = content;
        this.dialogType = dialogType;
        this.inputCallback = inputCallback;
        active = true;
    }

    public void ShowDialog(String title, String content, DialogType dialogType) {
        InitDialog(title, content, dialogType, null);
        ShowDialog();
    }

    public void ShowDialog(String title, String content, DialogType dialogType, final MaterialDialog.InputCallback inputCallback) {
        InitDialog(title, content, dialogType, inputCallback);
        ShowDialog();
    }

    public void ShowDialog() {
        if (!active) return;
        MaterialDialog.Builder materialDialog = null;
        if ((Activity) GetTopContext() != null) {
            materialDialog = new MaterialDialog.Builder((Activity) GetTopContext())
                    .title(this.title)
                    .positiveText("OK")
                    .linkColorRes(R.color.cast_intro_overlay_button_background_color)
                    //.autoDismiss(false)
                    .iconRes(R.drawable.ic_warning_indigo_500_36dp)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    })
                    .dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            active = false;
                        }
                    })

                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        }
                    });
        }
        if (dialogType == DialogType.CancelAgree) {
            materialDialog.negativeText("Отмена");
        }

        if (dialogType == DialogType.Input) {
//            materialDialog.inputRangeRes(0, 200, R.color.red);
            materialDialog.input(title, content, inputCallback);
        } else {
            materialDialog.content(Html.fromHtml(content));
        }

//        materialDialog.positiveColorRes(R.color.DarkSalmon);
//        materialDialog.negativeColorRes(R.color.DarkSalmon);
        //return materialDialog.build();

        materialDialog.show();
    }

    public enum DialogType {
        CancelAgree,
        Agree,
        Input
    }

}
