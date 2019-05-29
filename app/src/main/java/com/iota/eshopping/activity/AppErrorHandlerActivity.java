package com.iota.eshopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.iota.eshopping.R;
import com.iota.eshopping.constant.ConstantValue;

/**
 * @author yeakleang.ay
 */
public class AppErrorHandlerActivity extends AppCompatActivity {

    private TextView txt_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_error_handler);
        txt_message = findViewById(R.id.txt_message);
        getDataIntent();
    }

    /**
     *
     */
    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(ConstantValue.TAG)) {
            txt_message.setText(intent.getStringExtra(ConstantValue.TAG));
        }
    }
}
