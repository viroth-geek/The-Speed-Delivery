package com.iota.eshopping.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iota.eshopping.R;

/**
 * Created by channarith.bong on 12/20/17.
 *
 * @author channarith.bong
 */

public class BottomSheetDialog extends BottomSheetDialogFragment {

    /**
     * @return
     */
    public static BottomSheetDialog getInstance() {
        return new BottomSheetDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_time_picker, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        View view = getView();
    }
}
