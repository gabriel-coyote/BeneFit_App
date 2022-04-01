package com.example.benefit_app.ui.gyms;

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

public class GymsFragment extends Fragment {






    public GymsFragment() {}



    public static com.example.benefit_app.ui.gyms.GymsFragment newInstance(String param1, String param2) {
        com.example.benefit_app.ui.gyms.GymsFragment fragment = new com.example.benefit_app.ui.gyms.GymsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.fragment_gyms, container, false);



        return viewer;
    }




}