package com.example.benefit_app.ui.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.benefit_app.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class FoodFragment extends Fragment {

    private EditText food_search_textfield;
    private ImageView food_search_button;



    private TextView response_box;


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
        response_box = viewer.findViewById(R.id.foodDescription_placeholder);


        food_search_button = viewer.findViewById(R.id.foodSearchIcon);
        //food_search_button.setOnClickListener(view -> {
           // try {
                //TODO: Fix food functions -> getFood();
           // } catch (IOException e) {
           ///     e.printStackTrace();
         //   }
        //});




        return viewer;
    }


    /* ********************************************************************** */
    public void getFood() throws IOException {

        String food_name_input = food_search_textfield.getText().toString().trim();


        URL url = new URL("https://api.api-ninjas.com/v1/nutrition?query=1lb brisket and fries");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseStream);

        System.out.println(root.path("fact").asText());



        /*
        // From foodninji website
        Response response = Request.Get("https://api.calorieninjas.com/v1/nutrition?query="+query)
                .addHeader("X-Api-Key", "YOUR_API_KEY")
                .execute();


         */


        /*

        String myUrl = "https://api.calorieninjas.com/v1/nutrition?query="+foodtext;
        StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
                response -> {
                    try{
                        //Create a JSON object containing information from the API.
                        JSONObject myJsonObject = new JSONObject(response);
                        food_search_response = myJsonObject.getString("items");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                volleyError -> Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_SHORT).show()
        );


         */


        /*
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet("https://api.calorieninjas.com/v1/nutrition?query="+foodtext);
        request.addHeader("x-api-key", "9uNZgAKO3Hb657GW2aYeIA==6QSUhrgPXxiwypMp");
        HttpResponse food_search_response = httpclient.execute(request);
*/

        //System.out.println(food_search_response);


        //Response response =

       /*


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url("https://api.calorieninjas.com/v1/nutrition?query="+foodtext).header("X-Api-Key", "9uNZgAKO3Hb657GW2aYeIA==6QSUhrgPXxiwypMp").build();


        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // ... handle failed request
            }

            String responseBody = response.body().string();
            response_box.setText(responseBody);// ... do something with response
            
        } catch (IOException e) {
            // ... handle IO exception
        }


        */


        response_box.setText(root.path("fact").asText());


    }


}