package com.example.benefit_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.benefit_app.ui.fitness.FitnessFragment;
import com.example.benefit_app.ui.food.FoodFragment;
import com.example.benefit_app.ui.gyms.GymsFragment;
import com.example.benefit_app.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        // TODO: Defining our fragments here
        final FragmentManager fragmentManager = getSupportFragmentManager();

        final Fragment fragmentGyms = new GymsFragment();
        final Fragment fragmentFood = new FoodFragment();
        final Fragment fragmentFitness = new FitnessFragment();
        final Fragment fragmentProfile = new ProfileFragment();


        /* PURPOSE:     To handle the navigation selection
                        Switches fragments/page based on current selection */
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                //Fragment fragment = fragmentFitness;
                switch (item.getItemId()){
                    case R.id.menu_Geo:
                        loadFragment(fragmentGyms);
                        break;
                    case R.id.menu_Food:
                        loadFragment(fragmentFood);
                        break;
                    case R.id.menu_Fitness:
                        loadFragment(fragmentFitness);
                        break;
                    case R.id.menu_Profile:
                        loadFragment(fragmentProfile);
                        break;
                    default: break;
                }

                return true;


            }
        });



        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.menu_Fitness);
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_Container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}