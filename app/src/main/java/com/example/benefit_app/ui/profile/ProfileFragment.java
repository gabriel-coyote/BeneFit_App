package com.example.benefit_app.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benefit_app.R;

public class ProfileFragment extends Fragment {






    public ProfileFragment() {}



    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.fragment_profile, container, false);



        return viewer;
    }




}