package com.movieapp.cwe.movieapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CustomDialogFragment extends DialogFragment {

    private DialogClickListener callback;

    public interface DialogClickListener {
        public void onOkClick();
        public void onCancleClick();
    }

    @Override
    public void onAttach(Activity activity) {
        callback = (DialogClickListener) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog imageRenamedialog = new Dialog(getActivity());
        imageRenamedialog.setContentView(R.layout.custom_dialog_fragment);

        Button dialogButtonOk = (Button) imageRenamedialog.findViewById(R.id.btnOk);
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onOkClick();
                imageRenamedialog.dismiss();
            }
        });
        Button dialogButtonCancle = (Button) imageRenamedialog.findViewById(R.id.btnCancle);
        dialogButtonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCancleClick();
                imageRenamedialog.dismiss();
            }
        });

        return imageRenamedialog;
    }
}
