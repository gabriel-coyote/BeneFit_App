package com.example.benefit_app.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benefit_app.R;
import com.example.benefit_app.users.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileEditFragment extends Fragment {


    private ImageView backButton;
    private Button finish_edit_button;
    private TextView first_name_text;
    private TextView last_name_text;
    private TextView email_text;
    private TextView pnum_text;
    private TextView username_text;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

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
        View viewer = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        /* On back button click of the Profile Edit Page go back to main Profile Page */
        backButton = viewer.findViewById(R.id.editProfileBackArrow);
        backButton.setOnClickListener(view -> getActivity().onBackPressed());


        first_name_text = viewer.findViewById(R.id.first_name_text);
        last_name_text = viewer.findViewById(R.id.last_name_text);
        email_text = viewer.findViewById(R.id.email_text);
        pnum_text = viewer.findViewById(R.id.pnum_text);
        username_text = viewer.findViewById(R.id.username_text);
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

            User user = new User(fname_input,lname_input,email_input,pnum_input,username_input);


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


        }






    }

    /* ********************************************************************** */
    private void alertDialog(String text){ Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

}