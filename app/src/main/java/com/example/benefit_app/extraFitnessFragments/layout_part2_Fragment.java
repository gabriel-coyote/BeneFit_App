package com.example.benefit_app.extraFitnessFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class layout_part2_Fragment extends Fragment {



    private TextView date_text;
    private ImageButton backButton;
    View viewer;


    /* ********************************************************************** */
    public layout_part2_Fragment() {
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
        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("dd");
        // Inflate the layout for this fragment
        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        }else {
            viewer = inflater.inflate(R.layout.fragment_layout_part2, container, false);
        }
        date_text = viewer.findViewById(R.id.textViewWorkout);
        date_text.setText(dateForm.format(thisDate));
        // Opening the specific workout


        backButton = viewer.findViewById(R.id.back_button_calories_part2);
        backButton.setOnClickListener(view -> {getActivity().onBackPressed(); });


     return viewer;
    }



    /* ********************************************************************** */
    /* FUNCTION NAME:    loadFragment
       INPUT:            A Fragment
       OUTPUT:           n/a
       PURPOSE:          Switches/loads a fragment into the main fragment container */
    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_Container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}