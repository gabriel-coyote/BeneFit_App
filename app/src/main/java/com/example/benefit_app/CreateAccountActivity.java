package com.example.benefit_app;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class CreateAccountActivity extends AppCompatActivity {

    /* PURPOSE:         To access our corresponding items from
                        the activity_create_account.xml     */
    private TextView backLogin_text;
    private Button createAccount_button;
    private EditText enteredUsername, enteredEmail,
                     enteredPassword, enteredConfirmPassword;

    /* PURPOSE:          🔥 Firebase 🔥 */
    private FirebaseAuth mAuth;


    /* ********************************************************************** */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        /* PURPOSE:          🔥 Firebase 🔥 */
        mAuth = FirebaseAuth.getInstance();

        /* PURPOSE:            Get the text entered by the user for creating account
                               from the activity_create_account.xml textFields    */
        enteredUsername = (EditText) findViewById(R.id.entered_username_create);
        enteredEmail = (EditText) findViewById(R.id.entered_email_create);
        enteredPassword = (EditText) findViewById(R.id.entered_password_create);
        enteredConfirmPassword = (EditText) findViewById(R.id.entered_confirmPassword_create);


        /* PURPOSE:             To call the Function 'createAccountUser()' when user
                                Clicks on the createAccount resetPassword_yes_button                */
        createAccount_button = (Button) findViewById(R.id.createAccount_button);
        createAccount_button.setOnClickListener(view -> createAccountUser());


        /* PURPOSE:         To make the transition from activities even smoother
                            Using fade, on background(s) and navigation bar   */
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.loginBackground), true);
        fade.excludeTarget(decor.findViewById(R.id.createAccountBackground), true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        /* Purpose:         On Back text click, from activity_create_account.xml,
                            Change from Create Account Layout to Login Layout
                            Using transition on the 'BeneFit+' title           */
        final TextView appTitleText = findViewById(R.id.login_appName);
        backLogin_text = (TextView) findViewById(R.id.back_login_text);
        backLogin_text.setOnClickListener(view -> {

            /* INTENT:               What we want to accomplish, in this case; change pages/activities
               ACTIVITY_OPTIONS:     How we want to change pages/activities, use transition keeping AppName
               START_ACTIVITY:       Start our transition process with our Intent & option of transitioning */
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CreateAccountActivity.this, appTitleText, "AppTitle" );
            startActivity(intent, options.toBundle());
        });

    }


    /* ********************************************************************** */
    /* FUNCTION NAME:    createAccountUser
       INPUT:            N/A
       OUTPUT:           Prompts User to fill out empty fields, if any
       PURPOSE:          To register/create user for use with 🔥 FireBase 🔥
                         Activates on createAccount_button click         */
    private void createAccountUser(){

        /* PURPOSE:        Convert our user's entered values into String(s)
                           These String(s) are used within this method   */
        String userName = enteredUsername.getText().toString().trim();
        String email = enteredEmail.getText().toString().trim();
        String password = enteredPassword.getText().toString().trim();
        String confirmPassword = enteredConfirmPassword.getText().toString().trim();


        /* PURPOSE:         To make sure user's enters valid input for creating account
                            If invalid, prompt user to fix it                        */
        if(userName.isEmpty()){
            enteredUsername.setError("Please enter a UserName.");
            enteredUsername.requestFocus();
            return;
        }
        if(email.isEmpty()){
            enteredEmail.setError("Please enter your Email.");
            enteredEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            enteredEmail.setError("Must enter a valid Email.");
            enteredEmail.requestFocus();
            return;
        }
        if(password.length() < 8){
            enteredPassword.setError("Password must be minimum 8 characters long.");
            enteredPassword.requestFocus();
            return;
        }
        if(confirmPassword.isEmpty()){
            enteredConfirmPassword.setError("Please re-enter your Password.");
            enteredConfirmPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            enteredConfirmPassword.setError("Passwords do not match.");
            enteredConfirmPassword.requestFocus();
            return;
        }


        /* PURPOSE:         Create a user account with 🔥 FireBase 🔥
                            Display creation failures/successes    */
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(CreateAccountActivity.this, task -> {

                    if(task.isSuccessful()){
                        /* PURPOSE:     If registration succeed, display success message

                                        Change activities to LoginActivity
                                        Using alertDialog function for message        */
                        alertDialog("Registration Successful!");
                        startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                    } else {
                        /* PURPOSE:     If registration fails, display failed message
                                        Using alertDialog function for message     */
                        alertDialog("Registration Failed.");
                    }
                });

    }


    /* ********************************************************************** */
    /* FUNCTION NAME:    alertDialog
       INPUT:            A String
       OUTPUT:           n/a
       PURPOSE:          To make the code more readable,
                         outputs an alert style text box    */
    private void alertDialog(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }



}
