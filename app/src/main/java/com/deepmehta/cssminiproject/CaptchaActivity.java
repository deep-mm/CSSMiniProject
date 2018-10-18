package com.deepmehta.cssminiproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import test.jinesh.captchaimageviewlib.CaptchaImageView;

public class CaptchaActivity extends AppCompatActivity {

    private EditText captchaInput;
    private Button submitButton;
    private ImageButton reloadCaptcha;
    private CaptchaImageView captchaImageView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private int RC_SIGN_IN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);


        getSupportActionBar().setTitle("Verify Captcha");
        initialize();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String captchaCode =  captchaImageView.getCaptchaCode();
                String editTextVal = captchaInput.getText().toString();
                if(captchaCode.equals(editTextVal)){
                    loginOTP();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Captcha Mismatch",Toast.LENGTH_LONG).show();
                }
            }
        });

        reloadCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captchaImageView.regenerate();
            }
        });
    }

    public void initialize(){
        captchaInput = (EditText) findViewById(R.id.captha_input);
        submitButton = (Button) findViewById(R.id.submit_button);
        reloadCaptcha = (ImageButton) findViewById(R.id.regen);
        captchaImageView = (CaptchaImageView) findViewById(R.id.image);
    }

    public void loginOTP(){

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build());

        FirebaseApp.initializeApp(this);
// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                finishAffinity();
            }
        }
    }
}
