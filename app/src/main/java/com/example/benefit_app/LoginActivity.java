package com.example.benefit_app;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {

    /* PURPOSE:         To access our corresponding items
                        from the activity_login.xml    */
    private ImageButton signUp_button;
    private Button login_button;
    private TextView forgotPassword_text;
    private EditText enteredEmail, enteredPassword;

    // 🔥 Firebase 🔥
    // private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 🔥 Firebase 🔥
        // auth = FirebaseAuth.getInstance();


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
        final ImageButton signUp_button = (ImageButton) findViewById(R.id.signUp_email_ImageButton);
        signUp_button.setOnClickListener(view -> {

            /* INTENT:               What we want to accomplish, in this case; change pages/activities
               ACTIVITY_OPTIONS:     How we want to change pages/activities, use transition keeping AppName
               START_ACTIVITY:       Start our transition process with our Intent & option of transitioning */
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, appTitleText, "AppTitle" );
            startActivity(intent, options.toBundle());
        });


    }
}
