package com.iota.eshopping.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iota.eshopping.R;

/**
 * @author yeakleang.ay on 6/27/18.
 */
public class AlertUtils {

    /**
     * Show confirm dialog
     * @param context Context
     * @param title String
     * @param message String
     * @param actionName String
     * @param listener DialogInterface.OnClickListener
     */
    public static void showConfirmDialog(Context context, String title, String message, String actionName, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogPrimary);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(actionName, listener)
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss() );

        builder.create().show();
    }

    /**
     *
     * @param context context
     * @param title title
     * @param message message
     * @param duration duration
     * @param gravity gravity
     * @param alertType alertType
     */
    public static void showMessage(Context context, String title, String message, int duration, int gravity, AlertType alertType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater != null ? inflater.inflate(R.layout.custom_toast, null, false) : null;

        if (view == null) {
            return;
        }

        LinearLayout linearLayout = view.findViewById(R.id.toast_layout);
        linearLayout.setBackground(ContextCompat.getDrawable(context, alertType.getValue()));

        TextView txtTitle = view.findViewById(R.id.toast_title);
        TextView txtMessage = view.findViewById(R.id.toast_body);

        if (title != null) {
            txtTitle.setText(title);
        }
        else {
            txtTitle.setText(alertType.getTitle());
        }
        txtMessage.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setGravity(gravity, 0, 0);
        toast.setView(view);
        toast.show();
    }

    /**
     * Show default toast
     * @param context Context
     * @param title title
     * @param message message
     * @param duration duration
     */
    public static void showMessage(Context context, String title, String message, int duration, AlertType alertType) {
        showMessage(context, title, message, duration, Gravity.BOTTOM, alertType);
    }

    /**
     * Show toast with default duration
     * @param context Context
     * @param title title
     * @param message message
     */
    public static void showMessage(Context context, String title, String message, AlertType alertType) {
        showMessage(context, title, message, Toast.LENGTH_SHORT, alertType);
    }

    /**
     *
     * @param context Context
     * @param message String
     * @param gravity int
     * @param alertType AlertType
     */
    public static void showMessage(Context context, String message, int gravity, AlertType alertType) {
        showMessage(context, null, message, Toast.LENGTH_SHORT, gravity, alertType);
    }

    /**
     *
     * @param context Context
     * @param message String
     * @param alertType AlertType
     */
    public static void showMessage(Context context, String message, AlertType alertType) {
        showMessage(context, null, message, alertType);
    }

    /**
     * Show toast with default duration
     * @param context Context
     * @param title title
     * @param message message
     */
    public static void showMessage(Context context, String title, String message) {
        showMessage(context, title, message, AlertType.INFO);
    }

    /**
     * @author yeakleang.ay
     */
    public enum AlertType {

        INFO(R.string.alert_info, R.drawable.background_primary_color_with_radius_5dp),

        WARNING(R.string.alert_warning, R.drawable.background_primary_color_with_radius_5dp),

        ERROR(R.string.alert_error, R.drawable.background_error_color_with_radius_5dp);

        int title;
        int value;

        /**
         * Constructor
         * @param type String
         * @param value int
         */
        AlertType(int type, int value) {
            this.title = type;
            this.value = value;
        }

        /**
         *
         * @return title
         */
        public int getTitle() {
            return title;
        }

        /**
         *
         * @return value
         */
        public int getValue() {
            return value;
        }
    }

}
