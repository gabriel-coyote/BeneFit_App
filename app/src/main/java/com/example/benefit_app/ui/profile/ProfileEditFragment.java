package com.example.benefit_app.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.benefit_app.R;

public class ProfileEditFragment extends Fragment {


    private ImageView backButton;

    /* ********************************************************************** */
    public ProfileEditFragment() {
        // Required empty public constructor
    }



    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /* ********************************************************************** */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* PURPOSE:             To get our items from the fragment_profile_edit.xml,
                                Also return viewer to 'inflate' into the Fragment container viewer */
        View viewer = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        /* On back button click of the Profile Edit Page go back to main Profile Page */
        backButton = viewer.findViewById(R.id.editProfileBackArrow);
        backButton.setOnClickListener(view -> getActivity().onBackPressed());


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