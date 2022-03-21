package com.example.benefit_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class resetPasswordFragment extends Fragment {

    /* PURPOSE:         To access our corresponding items
                        from the fragment_reset_password.xml */
    private Button resetPasswordButton_Yes, resetPasswordButton_Back;
    private EditText resetPassword_enteredEmail;

    /* PURPOSE:          🔥 Firebase 🔥 */
    private FirebaseAuth auth;


    /* ********************************************************************** */
    public resetPasswordFragment() {
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

        /* PURPOSE:             To get our items from the fragment_reset_password.xml,
                                Also return to 'inflate' into the Fragment container viewer */
        View viewer = inflater.inflate(R.layout.fragment_reset_password, container, false);

        /* PURPOSE:          🔥 Firebase 🔥 */
        auth = FirebaseAuth.getInstance();


        /* PURPOSE:             Get the text entered by the user for Email
                                From fragment_reset_password.xml, use 'viewer' */
        resetPassword_enteredEmail = (EditText) viewer.findViewById(R.id.resetPassword_enteredEmail);

        /* PURPOSE:             To close the reset password dialog/fragment on BACK button click */
        resetPasswordButton_Back = (Button) viewer.findViewById(R.id.resetPassword_back_button);
        resetPasswordButton_Back.setOnClickListener(view -> getActivity().getSupportFragmentManager()
                .beginTransaction().remove(resetPasswordFragment.this).commit());

        /* PURPOSE:             To call the Function 'resetPassword_SendEmail()' when user clicks
                                Clicks on the YES button of fragment_reset_password.xml   */
        resetPasswordButton_Yes = (Button) viewer.findViewById(R.id.resetPassword_yes_button);
        resetPasswordButton_Yes.setOnClickListener( view -> resetPassword_SendEmail());

        /* PURPOSE:         Inflate the layout for this fragment */
        return viewer;
    }


    /* ********************************************************************** */
    /* FUNCTION NAME:    resetPassword_SendEmail
       INPUT:            N/A
       OUTPUT:           Prompts User to fill out empty/invalid fields, if any
       PURPOSE:          To send password reset email with 🔥 FireBase 🔥 and
                         Prompt email sent to inputted email
                         Activates on YES button click                     */
    private void resetPassword_SendEmail(){

        /* PURPOSE:         Convert our user's entered values into String(s)
                            These String(s) are are used within this method */
        String email = resetPassword_enteredEmail.getText().toString().trim();


        /* PURPOSE:         To make sure user's enters valid input for email account
                            If invalid, prompt user to fix it                     */
        if(email.isEmpty()){
            resetPassword_enteredEmail.setError("Pleaser enter your Email.");
            resetPassword_enteredEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            resetPassword_enteredEmail.setError("Must enter a valid Email.");
            resetPassword_enteredEmail.requestFocus();
            return;
        }


        /* PURPOSE:         Attempt to send reset password email with 🔥 FireBase 🔥
                            Display sending email failures/success               */
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(unused ->

                        /* PURPOSE:     If email valid, send a resent link to that Email address
                                        Using Toast to provide a simple feedback message      */
                        Toast.makeText(getActivity(), "Resent Link sent to Email.",
                                Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->

                        /* PURPOSE:     If error, doesn't send a resent link to that Email address
                                        Using Toast to provide a simple feedback message        */
                        Toast.makeText(getActivity(), "Error! Reset Link not sent!",
                                Toast.LENGTH_SHORT).show());

    }
}