package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.loginapp.data.DatabaseHandler;
import com.example.loginapp.modal.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextInputEditText Email;
    private TextInputEditText Password;
    private Button Login;
    private DatabaseHandler db ;
    User user;


    public static final String SMyPREFERENCES = "MyPrefs" ;
    public static final String SName = "nameKey";
    public static final String SEmail = "emailKey";
    public static final String SPassword ="passwordKey";
    public static final String SPhoneNumber ="phonenumberKey";
    public static final String SDate ="dateKey";
    public static final String SGender="genderKey";
    public static final String SHobies = "hobiesKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar =findViewById(R.id.toolbar);
        Email=findViewById(R.id.Lemail);
        Password =findViewById(R.id.Lpassword);
        Login = findViewById(R.id.Eupdate);
        toolbar.setTitle("Login");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);

        Login.setOnClickListener(this);

    }

    private void SharedPreferencesMethod() {

        String name=user.getName();
        String email=user.getEmail();
        String password=user.getPassword();
        String phoneNumber=user.getPhoneNumber();
        String date=user.getDate();
        String gender=user.getGender();
        String hobies =user.getHobbies();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.loginapp",MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(SName,name);
        editor.putString(SEmail,email);
        editor.putString(SPassword,password);
        editor.putString(SPhoneNumber,phoneNumber);
        editor.putString(SDate,date);
        editor.putString(SGender,gender);
        editor.putString(SHobies,hobies);
        editor.apply();
    }

    public void signup(View view) {
        startActivity( new Intent(LoginActivity.this,CreateAccountActivity.class));
    }

    @Override
    public void onClick(View v) {
        String email =Email.getText().toString().trim();
        String password=Password.getText().toString().trim();
        db= new DatabaseHandler(LoginActivity.this);
        user =db.Authenticate(new User(email,password));

        //Check Authentication is successful or not
        if (user != null) {
            Snackbar.make(Login, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();
            SharedPreferencesMethod();
//            Intent i = new Intent(LoginActivity.this,MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            startActivity(i);
           startActivity(new Intent(LoginActivity.this,MainActivity.class));
        } else {

            //User Logged in Failed
            Snackbar.make(Login, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

        }
    }
}
