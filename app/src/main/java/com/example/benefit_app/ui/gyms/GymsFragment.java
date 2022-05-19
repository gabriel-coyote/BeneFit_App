package com.example.benefit_app.ui.gyms;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.benefit_app.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GymsFragment extends Fragment implements OnMapReadyCallback {


    /* PURPOSE:         To access our corresponding items
                        from the fragment_gyms.xml    */
    private MapView mapView;
    private GoogleMap googleMap;


    private PlacesClient placesClient;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location currentLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    /* PURPOSE:             Used for setting current location */
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private CameraPosition cameraPosition;
    private static final int M_MAX_ENTRIES = 5;
    private String[] likelyPlaceNames;
    private String[] likelyPlaceAddresses;
    private List[] likelyPlaceAttributions;
    private LatLng[] likelyPlaceLatLngs;


    /* PURPOSE:     For out Weather Information */
    private TextView weatherLocation;
    private TextView weatherCondition;
    private TextView weatherTemp;


    /* ********************************************************************** */
    public GymsFragment() {
        // Required empty public constructor
    }


    /* ********************************************************************** */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (googleMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, googleMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, currentLocation);
        }
        super.onSaveInstanceState(outState);
    }


    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* PURPOSE:      Setup/Initialize the Places Client SDK */
        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), "AIzaSyCLZuWWw3Rf2Yn5wKRPC2eF9FZGlizVBwA");
        }

        /* PURPOSE:       Create a new PlacesClient instance */
        placesClient = Places.createClient(getActivity());

        /* PURPOSE:       Construct a FusedLocationProviderClient. */
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        /* PURPOSE:       Loads the saved location if it exists */
        if(savedInstanceState != null){
            currentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }



        /*
        // Build the map.
        // [START maps_current_place_map_fragment]
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        */


    }


    /* ********************************************************************** */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* PURPOSE:             To get our items from the fragment_gyms.xml,
                                Also return viewer to 'inflate' into the Fragment container viewer */
        View viewer = inflater.inflate(R.layout.fragment_gyms, container, false);


        weatherLocation = viewer.findViewById(R.id.weatherLocation);
        weatherCondition = viewer.findViewById(R.id.weatherCondition);
        weatherTemp = viewer.findViewById(R.id.weatherTemp);


        //setWeatherData();

        return viewer;
    }


    /* ********************************************************************** */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

    }


    /* ********************************************************************** */
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        getLocationPermission();

        updateLocationUI();

        getDeviceLocation();

        showCurrentPlace();



    }


    /* ********************************************************************** */
    private void getLocationPermission(){
        /* PURPOSE:
         *
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    /* ********************************************************************** */
    /* PURPOSE:
     *
     * Handles the result of the request for location permissions.
     */
    // [START maps_current_place_on_request_permissions_result]
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }
    // [END maps_current_place_on_request_permissions_result]


    /* ********************************************************************** */
    private void updateLocationUI(){
        if(googleMap == null){
            return;
        }
        try{
            if(locationPermissionGranted){
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                currentLocation = null;
                getLocationPermission();
            }


        } catch (SecurityException e){
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    /* ********************************************************************** */
    private void getDeviceLocation(){
        try{
            if (locationPermissionGranted){
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), task -> {

                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        currentLocation = task.getResult();
                        if (currentLocation != null) {
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentLocation.getLatitude(),
                                            currentLocation.getLongitude()), DEFAULT_ZOOM));

                            setWeatherData(currentLocation.getLatitude(), currentLocation.getLongitude());
                        }


                    } else {
                        Log.d("getDeviceLocation_Tag", "Current location is null. Using defaults.");
                        Log.e("getDeviceLocation_Tag", "Exception: %s", task.getException());
                        googleMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }

                });
            }



        } catch (SecurityException e){
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    /* ********************************************************************** */
    private void showCurrentPlace(){
        if (googleMap == null) {
            return;
        }

        if (locationPermissionGranted) {
            // Use fields to define the data types to return.
            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                    Place.Field.LAT_LNG);

            // Use the builder to create a FindCurrentPlaceRequest.
            FindCurrentPlaceRequest request =
                    FindCurrentPlaceRequest.newInstance(placeFields);

            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission") final
            Task<FindCurrentPlaceResponse> placeResult =
                    placesClient.findCurrentPlace(request);
            placeResult.addOnCompleteListener (task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    FindCurrentPlaceResponse likelyPlaces = task.getResult();

                    // Set the count, handling cases where less than 5 entries are returned.
                    int count;
                    if (likelyPlaces.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
                        count = likelyPlaces.getPlaceLikelihoods().size();
                    } else {
                        count = M_MAX_ENTRIES;
                    }

                    int i = 0;
                    likelyPlaceNames = new String[count];
                    likelyPlaceAddresses = new String[count];
                    likelyPlaceAttributions = new List[count];
                    likelyPlaceLatLngs = new LatLng[count];

                    for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {
                        // Build a list of likely places to show the user.
                        likelyPlaceNames[i] = placeLikelihood.getPlace().getName();
                        likelyPlaceAddresses[i] = placeLikelihood.getPlace().getAddress();
                        likelyPlaceAttributions[i] = placeLikelihood.getPlace()
                                .getAttributions();
                        likelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();

                        i++;
                        if (i > (count - 1)) {
                            break;
                        }
                    }

                    // Show a dialog offering the user the list of likely places, and add a
                    // marker at the selected place.

                    //MapsActivityCurrentPlace.this.openPlacesDialog();
                    //TODO: what to do with this ?->
                    this.openPlacesDialog();
                }
                else {
                    Log.e("showCurrentPlace_tag: ", "Exception: %s", task.getException());
                }
            });
        } else {
            // The user has not granted permission.
            Log.i("showCurrentPlace_tag: ", "The user did not grant location permission.");

            // Add a default marker, because the user hasn't selected a place.
            googleMap.addMarker(new MarkerOptions()
                    .title("Default Location")
                    .position(defaultLocation)
                    .snippet("No places found, because location permission is disabled."));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }


    /* ********************************************************************** */
    private void openPlacesDialog() {
        // Ask the user to choose the place where they are now.
        DialogInterface.OnClickListener listener = (dialog, which) -> {
            // The "which" argument contains the position of the selected item.
            LatLng markerLatLng = likelyPlaceLatLngs[which];
            String markerSnippet = likelyPlaceAddresses[which];
            if (likelyPlaceAttributions[which] != null) {
                markerSnippet = markerSnippet + "\n" + likelyPlaceAttributions[which];
            }

            // Add a marker for the selected place, with an info window
            // showing information about that place.
            googleMap.addMarker(new MarkerOptions()
                    .title(likelyPlaceNames[which])
                    .position(markerLatLng)
                    .snippet(markerSnippet));

            // Position the map's camera at the location of the marker.
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
                    DEFAULT_ZOOM));
        };

        // Display the dialog.
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Choose a Place")
                .setItems(likelyPlaceNames, listener)
                .show();
    }


    /* ********************************************************************** */
    /* FUNCTION NAME:    setWeatherData()
       INPUT:            n/a
       OUTPUT:           n/a
       PURPOSE:          Makes a request to Weather API
                         Gets JSON with current location weather data
                         Then set the textview weather fields accordingly */
    private void setWeatherData(double lat, double lon){

        /* **** Start API weather request **** */



        String currLocation = Double.toString(lat) +"," +Double.toString(lon);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://api.weatherapi.com/v1/current.json?key=4688280b3f0c44f9995105842220804&q="+currLocation+"&aqi=no";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            /* PURPOSE:         Our Needed Weather Info from response JSON */
            String location, condition, temperature;
            try {

                JSONObject locationInfo = response.getJSONObject("location");
                JSONObject weatherInfo = response.getJSONObject("current");

                location    = locationInfo.getString("name")+", "+locationInfo.getString("region");
                condition   = weatherInfo.getJSONObject("condition").getString("text");
                temperature = weatherInfo.getString("temp_f");

                weatherLocation.setText(location);
                weatherCondition.setText(condition);
                weatherTemp.setText(temperature+" °F");





            } catch (JSONException e){
                alertDialog("ERROR: Loading Weather Data 😔");
                e.printStackTrace();
            }


        }, error -> error.printStackTrace()

        );



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