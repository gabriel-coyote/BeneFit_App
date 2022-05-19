package com.example.benefit_app.ui.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.benefit_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FoodFragment extends Fragment {


    /* PURPOSE:         To access our corresponding items
                         from the fragment_food.xml    */
    private EditText food_search_textfield;
    private ImageView food_search_button;

    private TextView foodTitle, foodDescription;

    private TextView caloriesDisplay, carbsDisplay, proteinDisplay;
    private TextView caloriesText, carbsText, proteinText, sodiumText, sugarText, fatText, fiberText;


    private View viewer;


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

        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        } else {
            viewer = inflater.inflate(R.layout.fragment_food, container, false);
        }



        food_search_textfield = viewer.findViewById(R.id.food_search_field);

        foodTitle       = viewer.findViewById(R.id.foodName_placeholder);
        //foodDescription = viewer.findViewById(R.id.foodDescription_placeholder);

        caloriesDisplay = viewer.findViewById(R.id.mainCalories);
        carbsDisplay    = viewer.findViewById(R.id.mainCarbs);
        proteinDisplay  = viewer.findViewById(R.id.mainProtein);

        caloriesText = viewer.findViewById(R.id.caloriesText);
        carbsText    = viewer.findViewById(R.id.carbsText);
        proteinText  = viewer.findViewById(R.id.proteinText);
        sodiumText   = viewer.findViewById(R.id.sodiumText);
        sugarText    = viewer.findViewById(R.id.sugarText);
        fatText      = viewer.findViewById(R.id.fatText);
        fiberText    = viewer.findViewById(R.id.fiberText);


        /* PURPOSE:            On Food Search Icon click, from fragment_food.xml,
                               Call the function getfood() */
        food_search_button = viewer.findViewById(R.id.foodSearchIcon);
        food_search_button.setOnClickListener(view -> getFood());



        return viewer;
    }


    /* ********************************************************************** */
    /* FUNCTION NAME:    getFood()
       INPUT:            n/a
       OUTPUT:           n/a
       PURPOSE:          Makes a request to CaloriesNinja Food API
                         Gets JSON with food nutritional values
                         Then set the textview fields accordingly */
    public void getFood() {


        String foodName_input = food_search_textfield.getText().toString().trim();


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://api.calorieninjas.com/v1/nutrition?query="+foodName_input;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

         /* PURPOSE:         Our Needed Nutrition Info from Food */
            String calories, carbs, protein, sodium, sugar, fiber, fat;
            try {




                JSONArray foodItem = response.getJSONArray("items");
                JSONObject nutritionInfo = foodItem.getJSONObject(0);



                /* PURPOSE:     Parse the nutritional info into string variables */
                calories    = Double.toString( nutritionInfo.getDouble("calories") );
                carbs       = Double.toString( nutritionInfo.getDouble("carbohydrates_total_g") );
                protein     = Double.toString( nutritionInfo.getDouble("protein_g") );

                sodium      = Double.toString( nutritionInfo.getDouble("sodium_mg") );
                sugar       = Double.toString( nutritionInfo.getDouble("sugar_g") );
                fiber       = Double.toString( nutritionInfo.getDouble("fiber_g") );
                fat         = Double.toString( nutritionInfo.getDouble("fat_total_g") );



                /* PURPOSE:      Update Food UI with search Results */
                foodTitle.setText(foodName_input);

                caloriesDisplay.setText(calories);
                carbsDisplay.setText(carbs + " g");
                proteinDisplay.setText(protein + " g");

                caloriesText.setText(calories);
                carbsText.setText(carbs + " g");
                proteinText.setText(protein + " g");
                sodiumText.setText(sodium + " mg");
                sugarText.setText(sugar + " g");
                fiberText.setText(fiber + " g");
                fatText.setText(fat + " g");

            } catch (JSONException e){
               food_search_textfield.setError("Check Spelling? 🤨");
               food_search_textfield.requestFocus();
             e.printStackTrace();
            }


        }, error -> error.printStackTrace()

        )
            {
                /* PURPOSE:         Passing our API key for accessing Food Nutrition API */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    //headers.put("Content-Type", "application/json");
                    headers.put("X-Api-Key", "9uNZgAKO3Hb657GW2aYeIA==6QSUhrgPXxiwypMp");
                    return headers;
                }
            };



            queue.add(jsonObjectRequest);


    }


   /* ********************************************************************** */
    /* FUNCTION NAME:    alertDialog
       INPUT:            A String
       OUTPUT:           n/a
       PURPOSE:          To make the code more readable,
                         outputs an alert style text box    */
    private void alertDialog(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}