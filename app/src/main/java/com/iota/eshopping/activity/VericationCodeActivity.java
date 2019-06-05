package com.iota.eshopping.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.iota.eshopping.R;
import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.PhoneNumber;
import com.iota.eshopping.service.datahelper.datasource.online.FetchCustomer;

public class VericationCodeActivity extends AppCompatActivity implements TextWatcher {


    private Toolbar toolbar;
    private EditText etCode;

    private String mPhoneNumber;
    private String mVerificationId;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verication_code);

        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        etCode = findViewById(R.id.edit_text_pin_code);
        etCode.addTextChangedListener(this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.confirm_sms_code);
        }

        mVerificationId = getIntent().getStringExtra(ApplicationConfiguration.VERIFICATION_ID);
        mPhoneNumber = getIntent().getStringExtra(ApplicationConfiguration.PHONE_NUMBER);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (charSequence.length() == 6) {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, charSequence.toString());
            signInWithPhoneAuthCredential(credential);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VericationCodeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            PhoneNumber phoneNumber = new PhoneNumber();
                            phoneNumber.setPhoneNumber(mPhoneNumber);
                            new FetchCustomer("sldfjsldkjf", new FetchCustomer.InvokeOnCompleteAsync() {
                                @Override
                                public void onComplete(Customer customerInfo) {
//                                    Log.d(ApplicationConfiguration)
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                        } else {

                        }
                    }
                });
    }
}
