package com.iota.eshopping.widget;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.service.datahelper.datasource.online.ForgetPasswordService;
import com.iota.eshopping.util.ExceptionUtils;
import com.iota.eshopping.util.LoggerHelper;

/**
 * @author yeakleang.ay on 8/1/18.
 */

public class ForgetPasswordAlertDialog extends DialogFragment implements View.OnClickListener {

    private EditText edtEmail;
    private Button btnSubmit;

    private FrameLayout submitProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_forget_password, container, false);

        edtEmail = view.findViewById(R.id.edt_forget_email);

        btnSubmit = view.findViewById(R.id.btn_forget_password_submit);
        btnSubmit.setOnClickListener(this);

        submitProgressBar = view.findViewById(R.id.submit_progress_bar);

        return view;
    }


    @Override
    public void onClick(View view) {
        if (btnSubmit.equals(view)) {
            btnSubmit.setVisibility(View.GONE);
            submitProgressBar.setVisibility(View.VISIBLE);
            processForgetPasswordWithEmail(edtEmail.getText().toString());
        }
        else {
            dismiss();
        }
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        if (window != null) {
            ViewGroup.LayoutParams params = window.getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        }
        super.onResume();
    }

    /**
     * process forget password
     * @param email String
     */
    private void processForgetPasswordWithEmail(String email) {
        new ForgetPasswordService(email, new ForgetPasswordService.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(Boolean status) {
                dismiss();
                if (status) {
                    showMessageDialog("Email Sent", "Please check your email to reset password.");
                }
                else {
                    showMessageDialog("Message", "We cannot send reset password in the moment. Please try again.");
                }
            }

            @Override
            public void onError(Throwable e) {
                dismiss();
                Toast.makeText(getContext(), "Error: "  + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                LoggerHelper.showErrorLog("Error: ", e);
            }
        });
    }

    /**
     * show alert dialog
     * @param message message
     */
    private void showMessageDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> dismiss());
        builder.create().show();
    }
}
