package com.example.benefit_app.fitnessFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benefit_app.R;

public class waterFragment extends Fragment{

    private ImageView bottle_size_plus;
    private ImageView bottle_size_minus;
    private ImageView todays_goal_plus;
    private ImageView todays_goal_minus;
    private ImageView progress_plus;
    private ImageView progress_minus;

    private TextView todays_progress_text;
    private TextView bottle_size_text;
    private TextView todays_goal_text;

    //golabal variables for counting --------------->
    private int bottle_size_count;
    private int todays_goal_count;
    private int todays_progress_count;

    //buttons------------>
    private Button save_bottle_size_button;
    private Button save_todays_goal_button;
    private Button save_todays_progress_button;

    private View viewer;

    /* ********************************************************************** */
    public waterFragment(){
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

        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        } else{
            viewer = inflater.inflate(R.layout.fragment_water, container, false);
        }

        //buttons--------->
        bottle_size_plus = viewer.findViewById(R.id.Bottle_size_plus);
        bottle_size_minus = viewer.findViewById(R.id.bottle_size_minus);
        todays_goal_plus = viewer.findViewById(R.id.todays_goal_plus);
        todays_goal_minus = viewer.findViewById(R.id.todays_goal_minus);
        progress_plus = viewer.findViewById(R.id.progress_plus);
        progress_minus = viewer.findViewById(R.id.progress_minus);

        //save buttons------------>
        save_bottle_size_button = viewer.findViewById(R.id.save_bottle_size_button);
        save_todays_goal_button = viewer.findViewById(R.id.save_todays_goal_button);
        save_todays_progress_button = viewer.findViewById(R.id.save_todays_progress_button);

        //text displayed when buttons are pressed in ounces.
        todays_progress_text = viewer.findViewById(R.id.todays_progress_text);
        todays_goal_text = viewer.findViewById(R.id.todays_goal_text);
        bottle_size_text = viewer.findViewById(R.id.bottle_size_text);

        //Bottle size listeners
        bottle_size_minus.setOnClickListener(view -> changeWaterValue(1,0,0,0,0,0));
        bottle_size_plus.setOnClickListener(view -> changeWaterValue(0,1,0,0,0,0));

        //goal listeners
        todays_goal_plus.setOnClickListener(view -> changeWaterValue(0,0,0,1,0,0));
        todays_goal_minus.setOnClickListener(view -> changeWaterValue(0,0,1,0,0,0));

        //progress listeners
        progress_plus.setOnClickListener(view -> changeWaterValue(0,0,0,0,1,0));
        progress_minus.setOnClickListener(view -> changeWaterValue(0,0,0,0,0,1));




        return viewer;
    }

    /* ********************************************************************** */

    private void changeWaterValue(int bsm, int bsp, int tgm, int tgp, int pp, int pm){
        String bottle_size_temp;
        String todays_goal_temp;
        String progress_temp;

        if(bsp == 1){
            bottle_size_count += 1;
            bottle_size_temp = Integer.toString(bottle_size_count);
            bottle_size_text.setText(bottle_size_temp+" "+"Oz.");
        }
        else if(bsm == 1){
            bottle_size_count -= 1;
            bottle_size_temp = Integer.toString(bottle_size_count);
            bottle_size_text.setText(bottle_size_temp+" "+"Oz.");
        }
        else if(tgm == 1){
            todays_goal_count -= 1;
            todays_goal_temp = Integer.toString(todays_goal_count);
            todays_goal_text.setText(todays_goal_temp+" "+"Oz.");

        }
        else if(tgp == 1){
            todays_goal_count += 1;
            todays_goal_temp = Integer.toString(todays_goal_count);
            todays_goal_text.setText(todays_goal_temp+" "+"Oz.");
        }
        else if(pp == 1){
            todays_progress_count += 1;
            progress_temp = Integer.toString(todays_progress_count);
            todays_progress_text.setText(progress_temp+" "+"Oz.");
        }
        else if(pm == 1){
            todays_progress_count -= 1;
            progress_temp = Integer.toString(todays_progress_count);
            todays_progress_text.setText(progress_temp+" "+"Oz.");
        }


    }

}