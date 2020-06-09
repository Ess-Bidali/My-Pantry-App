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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Register extends AppCompatActivity {
    //Variables
    ImageView logoImageView;
    TextView registerWelcomeTextView, registerTitleTextView;
    TextInputLayout fullNameTextInputLayout,usernameTextInputLayout, phoneNumberTextInputLayout, emailTextInputLayout, passwordTextInputLayout;
    Button signInButton, registerButton;
    EditText nameEditText, usernameEditText, phoneEditText, emailEditText, passwordEditText;
    ProgressBar registerProgressBar;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hooks
        logoImageView = findViewById(R.id.logoImageView);
        registerWelcomeTextView = findViewById(R.id.registerWelcomeTextView);
        registerTitleTextView = findViewById(R.id.registerTitleTextView);
        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        fullNameTextInputLayout = findViewById(R.id.fullNameTextInputLayout);
        phoneNumberTextInputLayout = findViewById(R.id.phoneNumberTextInputLayout);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        registerProgressBar = findViewById(R.id.registerProgressBar);
        registerButton = findViewById(R.id.signUpButton);
        signInButton = findViewById(R.id.signInButton);
        nameEditText = Objects.requireNonNull(fullNameTextInputLayout.getEditText());
        usernameEditText =  Objects.requireNonNull(usernameTextInputLayout.getEditText());
        phoneEditText =  Objects.requireNonNull(phoneNumberTextInputLayout.getEditText());
        emailEditText =  Objects.requireNonNull(emailTextInputLayout.getEditText());
        passwordEditText =  Objects.requireNonNull(passwordTextInputLayout.getEditText());


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString().trim();
                String username =usernameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
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
                    //initialize firebase auth
                    mAuth = FirebaseAuth.getInstance();

                    //if user is authenticated, move along
                    if (mAuth.getCurrentUser() != null){
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();
                    }
                    else{
                        registerProgressBar.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Toast.makeText(Register.this, "User successfully created", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Register.this, Login.class));
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(Register.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        registerProgressBar.setVisibility(View.INVISIBLE);
                                    }
                                });
                    }
                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                //Clear previous tasks and activities and start afresh with Login activity as base activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                Pair[] pairs = new Pair[7];
                pairs[0] = Pair.create((View)logoImageView, "logo_image");
                pairs[1] = Pair.create((View)registerWelcomeTextView, "logo_text");
                pairs[2] = Pair.create((View)registerTitleTextView, "text_desc");
                pairs[3] = Pair.create((View)usernameTextInputLayout, "email_trans");
                pairs[4] = Pair.create((View)passwordTextInputLayout, "password_trans");
                pairs[5] = Pair.create((View)registerButton, "btn_trans");
                pairs[6] = Pair.create((View)signInButton, "new_user_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Register.this, pairs);
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
