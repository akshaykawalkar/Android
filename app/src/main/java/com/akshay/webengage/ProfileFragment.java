package com.akshay.webengage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {
    TextView firstNameText,lastNameText, emailText;
    DatabaseReference reference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstNameText=view.findViewById(R.id.profile_first_name_value);
        lastNameText=view.findViewById(R.id.profile_last_name_value);
        emailText=view.findViewById(R.id.profile_email_value);
        if (Login.loginUserEmail!=null) {
            readData(Login.loginUserEmail.replace("@", "").replace(".", ""));
        }
    }
    private void readData(String userEmail)
    {
        reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userEmail).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                     if (task.getResult().exists())
                     {
                         Toast.makeText(getActivity(),"successfully read",Toast.LENGTH_SHORT).show();
                         DataSnapshot dataSnapshot=task.getResult();
                         String firstName=String.valueOf(dataSnapshot.child("firstName").getValue());
                         String lastName=String.valueOf(dataSnapshot.child("lastName").getValue());
                         String email=String.valueOf(dataSnapshot.child("email").getValue());
                         if (firstName!=null&&lastName!=null&&emailText!=null) {
                             firstNameText.setText(firstName);
                             lastNameText.setText(lastName);
                             emailText.setText(Login.loginUserEmail);
                         }
                     }
                     else {
                         Toast.makeText(getActivity(),"user does not exists",Toast.LENGTH_SHORT).show();
                     }
                }
                else {
                    Toast.makeText(getActivity(),"fail to read the data",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}