package com.moringaschool.newscout.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.moringaschool.newscout.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = CreateAccountActivity.class.getSimpleName();

    private FirebaseAuth.AuthStateListener mAuthListener;

     private String mName;


    //initialize firebase tool

    private FirebaseAuth mAuth;



   //binding button and textViews

    @BindView(R.id.editTextCreateName)
    EditText mEditTextCreateName;

    @BindView( R.id.editTextCreateEmailAddress)
    EditText mEditTextCreateEmailAddress;

    @BindView(R.id.editTextCreatePassword)
    EditText mEditTextCreatePassword;

    @BindView(R.id.editTextConfirmPassword)
    EditText mEditTextConfirmPassword;

    @BindView(R.id.CreateUserButton)
    Button mCreateUserButton;

    @BindView(R.id.GotoLogin)
    TextView mGotoLogin;

    @BindView(R.id.firebaseProgressBar)
    ProgressBar mSignInProgressBar;

    @BindView(R.id.loadingTextView)
    TextView mLoadingSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        mGotoLogin.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        createAuthStateListener();
    }


    private void showProgressBar() {
        mSignInProgressBar.setVisibility(View.VISIBLE);
        mLoadingSignUp.setVisibility(View.VISIBLE);
        mLoadingSignUp.setText("Sign Up process in Progress");
    }

    private void hideProgressBar() {
        mSignInProgressBar.setVisibility(View.GONE);
        mLoadingSignUp.setVisibility(View.GONE);
    }
    // listens and confirms to application if an Authentication was made

    private void createAuthStateListener(){

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){
                    Intent intent = new Intent (CreateAccountActivity.this,dashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();;
                }
            }
        };

    }


    //method to create new user

    private void createNewUser(){

        mName = mEditTextCreateName.getText().toString().trim();

        final String name =  mEditTextCreateName.getText().toString().trim();
        final String email = mEditTextCreateEmailAddress.getText().toString().trim();
        String password = mEditTextCreatePassword.getText().toString().trim();
        String confirmPassword = mEditTextConfirmPassword.getText().toString().trim();


        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(mName);

        boolean validPassword = isValidPassword(password,confirmPassword);


        if(!validEmail || !validName || !validPassword) return;

        showProgressBar();
        // create user with email and password using firebase Object;

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {

                    hideProgressBar();

                    if(task.isSuccessful()){
                        Log.d(TAG, "User was Created");
                        createFirebaseUserProfile(Objects.requireNonNull(task.getResult().getUser()));
                    }   else{
                        Toast.makeText(CreateAccountActivity.this,"Failed to Authenticate",Toast.LENGTH_SHORT).show();

                    }
                });



    }


    @Override
    public void onClick(View v) {
        if (v == mGotoLogin){
            Intent intent = new Intent (CreateAccountActivity.this,LoginPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        if(v == mCreateUserButton){
            createNewUser();
        }

    }



    private boolean isValidEmail (String email){
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isGoodEmail){
            mEditTextCreateEmailAddress.setError("Please Enter a valid email Address");

            return false;
        }
        return  isGoodEmail;
    }


    private  boolean isValidName (String name){
        if(name.equals("")){
            mEditTextCreateName.setError("please Enter Your Name");
        }
        return true;
    }


    private  boolean isValidPassword(String password,String confirmPassword){
        if(password.length() < 6){
            mEditTextCreatePassword.setError("please create a password containing at least 6 characters");
            return  false;
        } else if(!password.equals(confirmPassword)) {
            mEditTextCreatePassword.setError("passwords do not Match");
            return  false;
        }
        return true;
    }

    private void createFirebaseUserProfile(final FirebaseUser user){
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, Objects.requireNonNull(user.getDisplayName()));
                            Toast.makeText(CreateAccountActivity.this, "The display name has ben set", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public  void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public  void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}