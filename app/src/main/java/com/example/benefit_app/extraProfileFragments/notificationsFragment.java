package com.example.benefit_app.extraProfileFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;


public class notificationsFragment extends Fragment {




    private Button notification_doneButton;

    /* ********************************************************************** */
    public notificationsFragment() {
        // Required empty public constructor
    }

    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /* ********************************************************************** */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewer = inflater.inflate(R.layout.fragment_notifications, container, false);

        notification_doneButton = viewer.findViewById(R.id.notification_DoneButton);
        notification_doneButton.setOnClickListener(view -> MainActivity.bottomNavigationView.setSelectedItemId(R.id.menu_Profile));




        return viewer;
    }




    /* ********************************************************************** */
    /* FUNCTION NAME:    loadFragment
       INPUT:            A Fragment
       OUTPUT:           N/A
       PURPOSE:          Switches/loads a fragment into the main fragment container */
    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_Container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}