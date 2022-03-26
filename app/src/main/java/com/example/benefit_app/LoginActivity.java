package com.example.benefit_app;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;


import java.util.Arrays;


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
    private CallbackManager callbackManager;


    /* ********************************************************************** */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /* PURPOSE:          🔥 Firebase 🔥 */
        auth = FirebaseAuth.getInstance();



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





        /* PURPOSE:             On Facebook Icon SignUp Click, from activity_login.xml
                                Call the facebookSignUp function                    */
        signUp_facebook_button = (ImageButton) findViewById(R.id.signUp_Facebook_ImageButton);
        signUp_facebook_button.setOnClickListener(view -> facebookSignUp());



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
                                        Using alertDialog function for message */
                        alertDialog("Login Successful!");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    } else {
                        /* PURPOSE:     If login fails, display failed message
                                        Using the alertDialog function      */
                        alertDialog("Login Failed.");
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



    /* ********************************************************************** */
    /* FUNCTION NAME:    facebookSignUp
       INPUT:            n/a
       OUTPUT:           Logs Success, Cancel, and Error
                            of signing up with Facebook account
       PURPOSE:          Initialize the Facebook LoginButton & CallbackManager
                         Register user with facebook account through 🔥 Firebase 🔥
                         Calls handleFacebookAccessToken() to use 🔥 Firebase 🔥 */
    private void facebookSignUp(){

        /* PURPOSE:         Initializing the Facebook Login Button
                            With permissions to read email & profile */
        callbackManager = CallbackManager.Factory.create();
        LoginButton FB_button = findViewById(R.id.FBlogin_button);
        FB_button.setPermissions(Arrays.asList("email", "public_profile"));

        FB_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookSignUp", "OnSuccess: " + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FacebookSignUp", "OnCancel: ");
            }

            @Override
            public void onError(@NonNull FacebookException error) {
                Log.d("FacebookSignUp", "OnError: " + error);
            }
        });


        FB_button.performClick();
    }



    /* ********************************************************************** */
    /* FUNCTION NAME:    handleFacebookAccessToken
       INPUT:            AccessToken
       OUTPUT:           n/a
       PURPOSE:          Using Access Token for the signed in user,
                         exchange for a 🔥 FireBase 🔥 credential
                         Authenticate using the 🔥 FireBase 🔥 credential
                         On success, change activities to MainActivity*/
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, task -> {

                    if (task.isSuccessful()) {
                        alertDialog("Authentication Success!");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        alertDialog("Authentication Failed.");
                    }
                });
    }



    /* ********************************************************************** */
    /* FUNCTION NAME:    OnActivityResult
       INPUT:            int, int, Intent
       OUTPUT:           n/a
       PURPOSE:          Pass the activity result back to Facebook SDK
                         Used for login/registration using Facebook  */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    /* ********************************************************************** */
    /* FUNCTION NAME:    onStart
       INPUT:            n/a
       OUTPUT:           n/a
       PURPOSE:          Checks if user is currently logged in, if so
                         Change activity to Main Activity           */
    @Override
    public void onStart() {
        super.onStart();

        /* Check if user is signed in (non-null) and update UI accordingly. */
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else{
            /* User is not signed in */
        }

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
