package com.example.benefit_app.ui.food;

import android.app.DownloadManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.benefit_app.R;

import java.net.URL;


public class FoodFragment extends Fragment {

    private EditText food_search_textfield;
    private Button food_search_button;


    /* ********************************************************************** */
    public FoodFragment() {
        // Required empty public constructor

    }


    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /* ********************************************************************** */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* PURPOSE:             To get our items from the fragment_food.xml,
                                Also return viewer to 'inflate' into the Fragment container viewer */
        View viewer = inflater.inflate(R.layout.fragment_food, container, false);
        food_search_textfield = viewer.findViewById(R.id.food_search_field);
        food_search_button = viewer.findViewById(R.id.food_search_button);
        food_search_button.setOnClickListener(view -> getFood());




        return viewer;
    }

    public void getFood(){
        String foodtext = food_search_textfield.getText().toString().trim();


    }


}