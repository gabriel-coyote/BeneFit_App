package com.example.benefit_app.ui.food;

import android.net.http.Request;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


public class FoodFragment extends Fragment {

    private EditText food_search_textfield;
    private Button food_search_button;
    private String food_search_response;

    private TextView response;




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
        response = viewer.findViewById(R.id.website_response);
        food_search_button.setOnClickListener(view -> {
            try {
                getFood();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });




        return viewer;
    }

    public void getFood() throws IOException {
        String foodtext = food_search_textfield.getText().toString().trim();


        String query = foodtext;

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
        response.setText("result");

    }


}