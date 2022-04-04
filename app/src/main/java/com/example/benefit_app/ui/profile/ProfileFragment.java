package com.example.benefit_app.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;

public class ProfileFragment extends Fragment {




private Button profile_editButton;
final Fragment fragmentProfileEdit = new ProfileEditFragment();

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

        profile_editButton = viewer.findViewById(R.id.profile_editButton);
        profile_editButton.setOnClickListener(view -> loadFragment(fragmentProfileEdit));

        return viewer;
    }

    /* ********************************************************************** */

    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_Container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}