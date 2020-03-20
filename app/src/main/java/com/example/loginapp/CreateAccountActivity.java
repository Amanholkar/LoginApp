package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.loginapp.data.DatabaseHandler;
import com.example.loginapp.modal.User;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener, CountryCodePicker.OnCountryChangeListener {
    private Toolbar toolbar;
    private TextInputEditText Name;
    private TextInputEditText Email;
    private TextInputEditText Password;
    private TextInputEditText phoneNumber;
    private TextInputEditText Date;
    private RadioGroup radioGroup;
    private RadioButton Gender;
    private Chip Gym,Guitar,Dance,Sing;
    private RadioButton male , female;
    private Button createAcccount;
    private CountryCodePicker ccp;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String gender;
    private String Hobies=" ";
    private String date;
    private String ccpcode;
    String gym=" ",dance=" ",guitar=" ",sing=" ";
    String phone;
    DatabaseHandler db ;
    User user;
    int mDay,mMonth,mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Insilize();
        toolbar =findViewById(R.id.createToolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("Create Account");
        setSupportActionBar(toolbar);

        Gym.setOnClickListener(this);
        Guitar.setOnClickListener(this);
        Dance.setOnClickListener(this);
        Sing.setOnClickListener(this);
        ccp.setOnCountryChangeListener(this);
    }

    private void Insilize() {
        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Password =findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phone_number);
        Date = findViewById(R.id.date);
        Gym =findViewById(R.id.Egym);
        Guitar = findViewById(R.id.Eguitar);
        Dance = findViewById(R.id.Edance);
        Sing = findViewById(R.id.Esing);
        createAcccount =findViewById(R.id.Eupdate);
        radioGroup =findViewById(R.id.Eradiogroup);
        male =findViewById(R.id.radioMale);
        female =findViewById(R.id.radioFemale);
        ccp =findViewById(R.id.countryCodePicker);


    }

    // onclick method for createaccount sign in
    public void createaccount(View view) {
        startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
    }

    // onclick method for famela
    public void RadioMethod(View view) {
        int radioId=radioGroup.getCheckedRadioButtonId();
        Gender =findViewById(radioId);

        gender=Gender.getText().toString();
        Toast.makeText(this, ""+gender, Toast.LENGTH_SHORT).show();
    }

    // onclick method for submitButton
    public void CreateAccountSubmit(View view) {
        String name =Name.getText().toString().trim();
        String email =Email.getText().toString().trim();
        String password =Password.getText().toString().trim();
        phone = "+"+ccpcode+phoneNumber.getText().toString();

         date = Date.getText().toString();


        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(email) &&email.matches(emailPattern) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phone) &&!TextUtils.isEmpty(date)){
          user = new User();
          db=new DatabaseHandler(CreateAccountActivity.this);
            Hobies=gym+guitar+sing+dance;
          user.setName(name);
          user.setEmail(email);
          user.setPassword(password);
          user.setPhoneNumber(phone);
          user.setDate(date);
          user.setGender(gender);
          user.setHobbies(Hobies);
          db.addContact(user);
          db.close();
            startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));


        }else if (TextUtils.isEmpty(name)){
            Name.setError("Enter the Name");
        }else if (TextUtils.isEmpty(email)){
            Email.setError("Enter the Email");
        }else if (!email.matches(emailPattern)){
            Email.setError("Email Wrrong");
        }else if (TextUtils.isEmpty(password)){
            Password.setError("Enter the Password");
        }else if (password.length()>8){
            Password.setError("Password is Long");
        }else if (phone.length() > 12){
            phoneNumber.setError("Phone number is long ");
        }
    }


    // onclick method for chip button
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.Egym:
                gym ="Gym,";
                break;
            case R.id.Eguitar:
                guitar="Guitar,";
                break;
            case R.id.Edance:
                dance="Dance,";
                break;
            case R.id.Esing:
                sing="Sing,";
                break;
        }
    }

    // setOnSelectfor CountryCode
    @Override
    public void onCountrySelected() {
        ccpcode=ccp.getSelectedCountryCode();
        Toast.makeText(CreateAccountActivity.this, ""+ccpcode, Toast.LENGTH_SHORT).show();


    }

    public void datePick(View view) {
        Calendar c =Calendar.getInstance();
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth=c.get(Calendar.MONTH);
        mYear=c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                date=Date.getText().toString();

            }

        },mYear,mMonth,mDay);
        datePickerDialog.show();
    }
}
