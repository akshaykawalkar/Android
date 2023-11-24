package com.akshay.webengage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akshay.webengage.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {
    private TextView textView;
    private Button createAccountButton;
    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private FirebaseDatabase db;
    private DatabaseReference reference;
    private ActivityMainBinding binding;
    private String firstName, lastName, email;
    static String createdUserEmail;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_create_account);
        textView = findViewById(R.id.signin_text);
        firstNameEditText = findViewById(R.id.create_account_first_name_input);
        lastNameEditText = findViewById(R.id.create_account_last_name_input);
        passwordEditText = findViewById(R.id.acreate_account_password_input);
        emailEditText = findViewById(R.id.create_account_email_input);
        createAccountButton = findViewById(R.id.create_account_button);
        progressBar = findViewById(R.id.progressBar);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = firstNameEditText.getText().toString();
                lastName = lastNameEditText.getText().toString();
                createdUserEmail = emailEditText.getText().toString();
                email = emailEditText.getText().toString().replace("@", "").replace(".", "");

                if (firstName.isEmpty())
                {
                    firstNameEditText.setError("Please enter first name");
                }
                if (lastName.isEmpty())
                {
                    lastNameEditText.setError("Please enter last name");
                }
                if (email.isEmpty())
                {
                    emailEditText.setError("Please enter email");
                }

                if (!firstName.isEmpty() && !lastName.isEmpty()) {
                    Users users = new Users(firstName, lastName, email);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");
                    reference.child(email).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Data is stored", Toast.LENGTH_SHORT).show();
                            firstNameEditText.setText("");
                            lastNameEditText.setText("");
                            emailEditText.setText("");
                        }
                    });
                    progressBar.setVisibility(View.VISIBLE);
                }

                String emailValue, passwordValue;
                emailValue = emailEditText.getText().toString();
                passwordValue = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(emailValue)) {
                    Toast.makeText(CreateAccount.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordValue)) {
                    Toast.makeText(CreateAccount.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailValue, passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccount.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateAccount.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(CreateAccount.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccount.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}