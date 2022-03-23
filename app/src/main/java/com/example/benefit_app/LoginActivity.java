package com.example.benefit_app;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    /* PURPOSE:         To access our corresponding items
                        from the activity_login.xml    */
    private ImageButton signUp_email_button, signUp_google_button, signUp_facebook_button;
    private Button login_button;
    private TextView forgotPassword_text;
    private EditText enteredEmail, enteredPassword;

    /* PURPOSE:          🔥 Firebase 🔥 */
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;


    /* ********************************************************************** */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* PURPOSE:          🔥 Firebase 🔥 */
        auth = FirebaseAuth.getInstance();


        /* START: GOOGLE SIGN IN ---------------------------------- */
        // TODO: Google Sign In - Not yet done
        /* PURPOSE:         To Configure Google Sign-In and Client Object
                            ID & basic profile are included in DEFAULT_SIGN_IN
                            ID is found in google-services.json file         */
        GoogleSignInOptions g_signIn_options = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("419699604657-iechlmg61na2u0gppl82no0kql707cqj.apps.googleusercontent.com")
                .requestEmail().build();
        /* PURPOSE:         To build a GoogleSignInClient with the options
                            specified by g_signIn_options               */
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, g_signIn_options);
        /* PURPOSE              To start the Google Sign in process on Google Icon Click
                                Note; deprecated means its not recommended to use, but ok for now */
        //signUp_google_button.setOnClickListener(view -> startActivityForResult(googleSignInClient.getSignInIntent(), 100));
        /* END: GOOGLE SIGN IN ---------------------------------- */


        /* START: FACEBOOK SIGN IN ---------------------------------- */
        // TODO: Facebook Sign In - Not yet done
        /* PURPOSE:             On Facebook Icon SignUp Click, from activity_login.xml
                                Use 🔥 FireBase 🔥 to automate the sign up process  */
        signUp_facebook_button = (ImageButton) findViewById(R.id.signUp_Facebook_ImageButton);
        /* PURPOSE:             On Google Icon SignUp Click, from activity_login.xml
                                Use 🔥 FireBase 🔥 to automate the sign up process  */
        signUp_google_button = (ImageButton) findViewById(R.id.signUp_Google_ImageButton);
        /* END: FACEBOOK SIGN IN ---------------------------------- */


        /* PURPOSE:            Get the text entered by the user for Email & Password,
                               from the activity_login.xml textFields              */
        enteredEmail = (EditText) findViewById(R.id.entered_email_login);
        enteredPassword = (EditText) findViewById(R.id.entered_password_login);



        /* PURPOSE:         To make the transition from activities even smoother
                            Using fade, on background(s) and navigation bar   */
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.loginBackground), true);
        fade.excludeTarget(decor.findViewById(R.id.createAccountBackground), true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        /* PURPOSE:            On Email Icon SignUp Click, from activity_login.xml,
                               Change from Login Layout to Create Account Layout.
                               Using transition on the 'BeneFit+' title         */
        final TextView appTitleText = findViewById(R.id.login_appName);
        signUp_email_button = (ImageButton) findViewById(R.id.signUp_email_ImageButton);
        signUp_email_button.setOnClickListener(view -> {

            /* INTENT:               What we want to accomplish, in this case; change pages/activities
               ACTIVITY_OPTIONS:     How we want to change pages/activities, use transition keeping AppName
               START_ACTIVITY:       Start our transition process with our Intent & option of transitioning */
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, appTitleText, "AppTitle" );
            startActivity(intent, options.toBundle());
        });


        /* PURPOSE:              To call the Function 'signInUser()' when user
                                 Clicks on the Login resetPassword_yes_button                 */
        login_button = (Button) findViewById((R.id.login_button));
        login_button.setOnClickListener(view ->  signInUser());


        /* PURPOSE:              To call the Function 'resetPassword()' when user
                                 Clicks on the forgot password text            */
        forgotPassword_text = (TextView) findViewById(R.id.forgot_password_text);
        forgotPassword_text.setOnClickListener(view -> resetPassword());
    }


    /* ********************************************************************** */
    /* FUNCTION NAME:    signInUser
       INPUT:            N/A
       OUTPUT:           Prompts User to fill out empty/invalid fields, if any
       PURPOSE:          To sign in user with 🔥 FireBase 🔥 and change activity
                         Activates on login_button click                     */
    private void signInUser(){

        /* PURPOSE:         Convert our user's entered values into String(s)
                            These String(s) are are used within this method */
        String email = enteredEmail.getText().toString().trim();
        String password = enteredPassword.getText().toString().trim();


        /* PURPOSE:         To make sure user's enters valid input for logging in account
                            If invalid, prompt user to fix it                          */
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
        if(password.isEmpty()){
            enteredPassword.setError("Please enter your Password.");
            enteredPassword.requestFocus();
            return;
        }


        /* PURPOSE:         Attempt to sign in user with 🔥 FireBase 🔥
                            Display sign in failures/success        */
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, task -> {

                    if(task.isSuccessful()){
                        /* PURPOSE:     If login succeed, display success message
                                        Change activities to MainActivity
                                        Toast provides simple feedback about an,
                                        operation in a small popup            */
                        Toast.makeText(LoginActivity.this, "Login Successful",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    } else {
                        /* PURPOSE:     If login fails, display failed message
                                        Toast provides simple feedback about an,
                                        operation in a small popup            */
                        Toast.makeText(LoginActivity.this, "Login Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /* ********************************************************************** */
    /* FUNCTION NAME:    resetPassword
       INPUT:            n/a
       OUTPUT:           Opens the resetPasswordFragment class
       PURPOSE:          To open the email reset link fragment on the login page
                         , the fragment then handles logic code               */
    private void resetPassword(){

        /* PURPOSE:             To fill the Login Fragment Container View of
                                activity_login.xml with the resetPasswordFragment & layout,
                                Acts like a pop up window                               */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_fragmentContainerView, new resetPasswordFragment());
        fragmentTransaction.commit();
    }





}
