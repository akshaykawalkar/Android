package com.akshay.webengage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {
   EditText passwordInput;
   Button resetPasswordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        passwordInput=findViewById(R.id.forgot_email_input);
        resetPasswordButton=findViewById(R.id.forgot_password_button);
    }
    @Override
    protected void onResume() {
        super.onResume();
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordInput.getText().toString().isEmpty())
                {
                    passwordInput.setError("Please enter email");
                }
                else {
                    passwordInput.setText("");
                    Toast.makeText(getApplicationContext(),"Please verify your emsil",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}