package com.example.benefit_app.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benefit_app.R;

public class ProfileFragment extends Fragment {





    /* ********************************************************************** */
    public ProfileFragment() {
        // Required empty public constructor
    }


    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /* ********************************************************************** */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* PURPOSE:             To get our items from the fragment_profile.xml,
                                Also return viewer to 'inflate' into the Fragment container viewer */
        View viewer = inflater.inflate(R.layout.fragment_profile, container, false);



        return viewer;
    }




}