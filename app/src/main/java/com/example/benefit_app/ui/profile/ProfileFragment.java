package com.example.benefit_app.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.benefit_app.LoginActivity;
import com.example.benefit_app.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {




    private Button profile_editButton;
    private Button sign_out_button;
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

        //SIGN-OUT FEATURE---------------->
        sign_out_button = viewer.findViewById(R.id.sign_out_button_layout);
        sign_out_button.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            com.facebook.login.LoginManager.getInstance().logOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
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