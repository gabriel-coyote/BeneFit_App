package com.example.benefit_app.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.benefit_app.LoginActivity;
import com.example.benefit_app.MainActivity;
import com.example.benefit_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {




    private Button profile_notifications;
    private Button profile_editButton;
    private Button sign_out_button;


    public static TextView profile_display_name;
    public static TextView profile_display_username;

    private View viewer;


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

        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        }else {
            viewer = inflater.inflate(R.layout.fragment_profile, container, false);
        }

        profile_editButton = viewer.findViewById(R.id.profile_editButton);
        profile_editButton.setOnClickListener(view -> loadFragment(MainActivity.fragmentProfileEdit));

        profile_notifications = viewer.findViewById(R.id.notification_profileButton);
        profile_notifications.setOnClickListener(view -> loadFragment(MainActivity.fragmentNotifications));


        profile_display_name = viewer.findViewById(R.id.profile_display_name);
        profile_display_username = viewer.findViewById(R.id.profile_display_username);


        // Updates Name and Username when the user logs in; if exist
        updateUI();

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

    /* ********************************************************************** */
    // Check if the logged in user has a saved profile
    // If so update UI to display name & username
    public static void updateUI(){

       FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("Users").child(currentUser.getUid());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //create new user
                    profile_display_name.setText(dataSnapshot.child("firstName").getValue(String.class));
                    profile_display_username.setText(dataSnapshot.child("username").getValue(String.class));
                } else{
                    // User doesn't have a saved profile so use default placeholder for name & username.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ProfileUpdate_TAG:", databaseError.getMessage()); //Don't ignore errors!
            }
        };

        userNameRef.addListenerForSingleValueEvent(eventListener);

    }


}