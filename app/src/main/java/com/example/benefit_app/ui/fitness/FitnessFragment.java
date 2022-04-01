package com.example.benefit_app.ui.fitness;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.benefit_app.LoginActivity;
import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;
import com.google.firebase.auth.FirebaseAuth;

public class FitnessFragment extends Fragment {


    private Button logoutButton;



    public FitnessFragment() {}



    public static FitnessFragment newInstance(String param1, String param2) {
        FitnessFragment fragment = new FitnessFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.fragment_fitness, container, false);


        logoutButton = viewer.findViewById(R.id.test_fragment_logout_button);
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            com.facebook.login.LoginManager.getInstance().logOut();
           startActivity(new Intent(getActivity(), LoginActivity.class));
        });


        return viewer;
    }




}