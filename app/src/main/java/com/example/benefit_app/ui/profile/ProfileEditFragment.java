package com.example.benefit_app.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.benefit_app.Objects.User;
import com.example.benefit_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ProfileEditFragment extends Fragment {


    private ImageView backButton;
    private Button finish_edit_button;
    private TextView first_name_text;
    private TextView last_name_text;
    private TextView email_text;
    private TextView pnum_text;
    private TextView username_text;




    private TextView edit_profile_display_name ;
    private TextView edit_profile_display_username;

    // For use of uploading & setting profile image
    //Create a reference to upload, download, or delete a file, or to get or update its metadata
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference firebaseStorageReference = firebaseStorage.getReference();

    private  View viewer;



    // For uploading profile image1
    private ImageView profilePic;
    public Uri imageUri;



    /* ********************************************************************** */
    public ProfileEditFragment() {
        // Required empty public constructor
    }



    /* ********************************************************************** */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /* ********************************************************************** */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* PURPOSE:             To get our items from the fragment_profile_edit.xml,
                                Also return viewer to 'inflate' into the Fragment container viewer */
        // IF the viewer doesn't exist then make one
        // Else keep the same viewer
        if (viewer != null) {
            if ((ViewGroup)viewer.getParent() != null)
                ((ViewGroup)viewer.getParent()).removeView(viewer);
            return viewer;
        } else{
            viewer = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        }



        /* On back button click of the Profile Edit Page go back to main Profile Page */
        backButton = viewer.findViewById(R.id.editProfileBackArrow);
        backButton.setOnClickListener(view -> getActivity().onBackPressed());

        // Our textfields for input
        first_name_text = viewer.findViewById(R.id.first_name_text);
        last_name_text = viewer.findViewById(R.id.last_name_text);
        email_text = viewer.findViewById(R.id.email_text);
        pnum_text = viewer.findViewById(R.id.pnum_text);
        username_text = viewer.findViewById(R.id.username_text);



        edit_profile_display_name = viewer.findViewById(R.id.edit_profile_display_name);
        edit_profile_display_username = viewer.findViewById(R.id.edit_profile_display_username);

        updateUI();




        finish_edit_button = viewer.findViewById(R.id.finish_edit_button);
        finish_edit_button.setOnClickListener(view -> changeInfo());





        return viewer;
    }

    /* ********************************************************************** */

    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_Container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /* ********************************************************************** */
    private void changeInfo(){


        String fname_input = first_name_text.getText().toString().trim();
        String lname_input = last_name_text.getText().toString().trim();
        String email_input = email_text.getText().toString().trim();
        String pnum_input = pnum_text.getText().toString().trim();
        String username_input = username_text.getText().toString().trim();


        if(fname_input.isEmpty() || lname_input.isEmpty() || email_input.isEmpty() || pnum_input.isEmpty() || username_input.isEmpty()){
          alertDialog("Please fill out all entries.");
        } else {
            // Everything is filled in

            // Creating Object User to setvalue in the object oriented database in firebase
            User user = new User(fname_input,lname_input,email_input,pnum_input,username_input);

            // Updating our Names & Username
            //edit_profile_display_name.setText(user.getFirstName()+" "+user.getLastName());
            //edit_profile_display_username.setText(user.getUsername());



            // Adding user info to Firebase database
            FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){

                        alertDialog("Saving Complete 😌");







                        getActivity().onBackPressed();

                    }else if(!task.isSuccessful()){
                        alertDialog("Saving Failed... :(");
                    }
                }
            });


            updateUI();
            ProfileFragment.updateUI();
        }






    }

    /* ********************************************************************** */
    // Check if the logged in user has a saved profile
    // If so update UI to display name & username
    private void updateUI(){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userNameRef = rootRef.child("Users").child(currentUser.getUid());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //create new user
                    edit_profile_display_name.setText(dataSnapshot.child("firstName").getValue(String.class));
                    edit_profile_display_username.setText(dataSnapshot.child("username").getValue(String.class));
                } else{
                    // User doesn't have a saved profile so use default placeholder for name & username.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("EditProfileUpdate_TAG:", databaseError.getMessage()); //Don't ignore errors!
            }
        };

        userNameRef.addListenerForSingleValueEvent(eventListener);

    }



    /* ********************************************************************** */
    private void alertDialog(String text){ Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

}