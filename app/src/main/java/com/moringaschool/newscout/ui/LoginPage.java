package com.moringaschool.newscout.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moringaschool.newscout.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.TextViewSignUp)
    TextView mTextViewSignUp;

    @BindView(R.id.editTextEmailAddress)
    EditText mEditTextEmailAddress;

    @BindView(R.id.editTextPassword)
    EditText
            mEditTextPassword;

    @BindView(R.id.loginButton)
    Button mLoginButton;

    @BindView(R.id.firebaseProgressBar)
    ProgressBar mSignInProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LoginPage.this, dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        };

        mTextViewSignUp.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mTextViewSignUp) {
            Intent intent = new Intent(LoginPage.this, CreateAccountActivity.class);
            startActivity(intent);
        }


        if (v == mLoginButton) {
            loginWithPassword();
            showProgressBar();
        }
    }


    private void showProgressBar() {
        mSignInProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mSignInProgressBar.setVisibility(View.GONE);
    }


    private void loginWithPassword() {

        String email = mEditTextEmailAddress.getText().toString().trim();

        String password = mEditTextPassword.getText().toString().trim();


        if (email.equals("")) {
            mEditTextEmailAddress.setError("please Enter your email");

            return;
        }
        if (password.equals("")) {
            mEditTextPassword.setError("Password cannot be blank");

            return;
        }


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            hideProgressBar();

                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginPage.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(LoginPage.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }
}