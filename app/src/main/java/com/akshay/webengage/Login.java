package com.akshay.webengage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class Login extends AppCompatActivity {
    private TextView createAccount, forgotPassword;
    private Button loginButton;
    private EditText email, password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    static String loginUserEmail;
    static String currentPassword;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            loginUserEmail = CreateAccount.createdUserEmail;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createAccount = findViewById(R.id.sign_create_account_button);
        email = findViewById(R.id.sign_email_input);
        password = findViewById(R.id.sign_password_input);
        progressBar = findViewById(R.id.sign_progressBar);
        loginButton = findViewById(R.id.sign_button);
        forgotPassword = findViewById(R.id.sign_forgot_password_button);
        mAuth = FirebaseAuth.getInstance();
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
                Log.d("Clicked", "Create Account");
                Toast.makeText(Login.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String emailValue, passwordValue;
                emailValue = email.getText().toString();
                loginUserEmail = email.getText().toString();
                passwordValue = password.getText().toString();
                currentPassword=password.getText().toString();
                if (emailValue.isEmpty())
                {
                    email.setError("Please enter first name");
                    progressBar.setVisibility(View.GONE);
                }
                if (passwordValue.isEmpty()) {
                    password.setError("Please enter last name");
                    progressBar.setVisibility(View.GONE);
                }
                if (!emailValue.isEmpty() && !passwordValue.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login successful.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
