package com.essbidali.pantrywatcher.ui.authentication;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {
    //Variables
    ImageView logoImageView;
    TextView loginWelcomeTextView, loginTitleTextView;
    Button newUserButton, loginButton;
    TextInputLayout usernameTextInputLayout, passwordTextInputLayout;
    EditText emailEditText, passwordEditText;
    ProgressBar loginProgressBar;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        logoImageView = findViewById(R.id.logoImageView);
        loginWelcomeTextView = findViewById(R.id.loginWelcomeTextView);
        loginTitleTextView = findViewById(R.id.loginTitleTextView);
        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        loginButton = findViewById(R.id.loginButton);
        newUserButton = findViewById(R.id.newUserButton);
        emailEditText =  Objects.requireNonNull(usernameTextInputLayout.getEditText());
        passwordEditText =  Objects.requireNonNull(passwordTextInputLayout.getEditText());

        //initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
        //if there's already an authenticated user
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        loginProgressBar.setVisibility(View.GONE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    emailEditText.setError("Email is required!");
                }
                else if(TextUtils.isEmpty(password)){
                    passwordEditText.setError("Password is required!");
                }
                else if((password.length()) < 6){
                    passwordEditText.setError("Password must be at least 6 characters!");
                }
                else{
                    loginProgressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        mUser = mAuth.getCurrentUser();
                                        Toast.makeText(Login.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, MainActivity.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(Login.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    loginProgressBar.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });


        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = Pair.create((View)logoImageView, "logo_image");
                pairs[1] = Pair.create((View)loginWelcomeTextView, "logo_text");
                pairs[2] = Pair.create((View)loginTitleTextView, "text_desc");
                pairs[3] = Pair.create((View)usernameTextInputLayout, "email_trans");
                pairs[4] = Pair.create((View)passwordTextInputLayout, "password_trans");
                pairs[5] = Pair.create((View)loginButton, "btn_trans");
                pairs[6] = Pair.create((View)newUserButton, "new_user_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        finish();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
