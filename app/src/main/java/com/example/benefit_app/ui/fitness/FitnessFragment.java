package com.example.benefit_app.ui.fitness;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.benefit_app.R;


public class FitnessFragment extends Fragment {


    private Button logoutButton;


    /* ********************************************************************** */
    public FitnessFragment() {
        // Required empty public constructor
    }


    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /* ********************************************************************** */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        /* PURPOSE:             To get our items from the fragment_fitness.xml,
                                Also return viewer to 'inflate' into the Fragment container viewer */
        View viewer = inflater.inflate(R.layout.fragment_fitness, container, false);


        /* TODO: Delete this or reuse somewhere else.
        logoutButton = viewer.findViewById(R.id.test_fragment_logout_button);
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            com.facebook.login.LoginManager.getInstance().logOut();
           startActivity(new Intent(getActivity(), LoginActivity.class));
        });
        */


        return viewer;
    }




}