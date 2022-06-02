package com.example.myfirstandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {
    //pattern for password check
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^"+
                    "(?=.*[0-9])"+  //at least 1 digit
                    "(?=.*[a-z])"+  //at least 1 lower case
                    "(?=.*[A-Z])"+  //at least 1 upper case
                    ".{8}"+         //at least 8 characters
                    "$");

    //creating objects for editText and button
    private EditText emailText, passwordText, reenterPasswordText;
    private Button loginBtn;

    //creating sharePref object
    private SharedPreferences shPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailText=findViewById(R.id.email_address);
        passwordText=findViewById(R.id.enter_password);
        reenterPasswordText=findViewById(R.id.reenter_password);
        loginBtn=findViewById(R.id.create_account_button);

        shPrefs=getSharedPreferences("MY_SHARED_PREFS",MODE_PRIVATE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        emailText.setFocusable(true);
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isValid = Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches();
                if(!isValid)
                {
                    emailText.setError("Invalid Email");
                }
                else
                {
                    emailText.setError(null);
                }

            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = passwordText.getText().toString();
                if(!PASSWORD_PATTERN.matcher(password).matches())
                {
                    passwordText.setError("INCORRECT FORMAT");
                }
                else
                {
                    passwordText.setError(null);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }

        });

        reenterPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //getting the fields and putting them into a string
                String password = passwordText.getText().toString();
                String reEnterPassword = reenterPasswordText.getText().toString();
                String email = emailText.getText().toString();
                //check if email matches the emailPatter
                boolean isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();


                if(reEnterPassword.equals(password) && isValid)
                {
                    loginBtn.setEnabled(true);
                    reenterPasswordText.setError(null);
                }
                else
                {
                    loginBtn.setEnabled(false);
                    reenterPasswordText.setError("PASSWORD DOESN'T MATCH");
                }

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting emailText to string and adding it to sharePref
                String email = emailText.getText().toString();
                String registerEmail = shPrefs.getString("EMAIL", "");


                if(email.equals(registerEmail))
                {
                    emailText.setError("EMAIL NOT AVAILABLE");
                }
                else {
                    shPrefs.edit().putString("EMAIL", email)
                            .apply();

                    //re-directing back to mainActivity
                    Intent intent = new Intent(CreateAccount.this, MainActivity.class);
                    startActivity(intent);

                    //let user know account was created
                    Toast.makeText(CreateAccount.this, "ACCOUNT CREATED", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}