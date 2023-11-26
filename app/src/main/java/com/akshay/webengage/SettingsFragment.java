package com.akshay.webengage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button logoutButton, resetPassword;
    private EditText currentPassword, newPassword, renewPassword;
    private MyViewModel myViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logoutButton = view.findViewById(R.id.settings_logout_button);
        currentPassword = view.findViewById(R.id.settings_enter_password_input);
        newPassword = view.findViewById(R.id.settings_enter_new_password_input);
        renewPassword = view.findViewById(R.id.settings_re_enter_new_passsword_input);
        resetPassword = view.findViewById(R.id.settings_reset_password_button);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

    }

    @Override
    public void onResume() {
        super.onResume();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPasswordValue, newPasswordValue, reEnterNewPasswordValue;
                currentPasswordValue = currentPassword.getText().toString();
                newPasswordValue = newPassword.getText().toString();
                reEnterNewPasswordValue = renewPassword.getText().toString();
                if (currentPasswordValue.isEmpty()) {
                    currentPassword.setError("Please enter current password");
                }
                if (newPasswordValue.isEmpty()) {
                    newPassword.setError("Please enter new password");
                }
                if (reEnterNewPasswordValue.isEmpty()) {
                    renewPassword.setError("Please re-enter new password");
                }
                if (!currentPasswordValue.isEmpty() && !newPasswordValue.isEmpty() && !reEnterNewPasswordValue.isEmpty()) {
                    if (myViewModel.getUserEmail().isEmpty()) {
                        Toast.makeText(getActivity(), "Please logout and login to change the password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (newPasswordValue.equalsIgnoreCase(reEnterNewPasswordValue)) {
                        auth.signInWithEmailAndPassword(myViewModel.getUserEmail(), currentPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                user.updatePassword(newPassword.getText().toString());
                                Toast.makeText(getActivity(), "Password is updated!!", Toast.LENGTH_SHORT).show();
                                currentPassword.setText("");
                                newPassword.setText("");
                                renewPassword.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Please check current password", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "New password and re-enter password is not same", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}