package com.akshay.webengage;

import static com.akshay.webengage.R.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import com.akshay.webengage.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
   ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId()== id.bottom_home)
            {
                binding.topViewTitle.setText("Home");
                replaceFragment(new HomeFragment());
            }
            else if (item.getItemId()== id.bottom_person)
            {
                binding.topViewTitle.setText("Profile");
                replaceFragment(new ProfileFragment());
            }
           else if (item.getItemId()== id.bottom_settings)
            {
                binding.topViewTitle.setText("Settings");
                replaceFragment(new SettingsFragment());
            }

            return true;
        });


        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if (user==null)
        {
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        }
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(id.viewPager2,fragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}