package com.example.benefit_app.ui.food;

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

public class FoodFragment extends Fragment {






    public FoodFragment() {}



    public static com.example.benefit_app.ui.food.FoodFragment newInstance(String param1, String param2) {
        com.example.benefit_app.ui.food.FoodFragment fragment = new com.example.benefit_app.ui.food.FoodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.fragment_food, container, false);



        return viewer;
    }




}