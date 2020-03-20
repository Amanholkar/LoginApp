package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


public class ProfileFragment extends Fragment {
    private TextView ID,Name,Email,Password,Phone,Date,Gender,Hobies;
    private Toolbar toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
//        View view1 = inflater.inflate(R.layout.activity_main,container,false);
        Bundle i =getArguments();
        if (i!=null) {
            toolbar = getActivity().findViewById(R.id.toolbarhome);
            toolbar.setTitle("Profile");
            toolbar.setNavigationIcon(R.drawable.ic_back);
            final AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getActivity().onBackPressed();
//

                }
            });


//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            ID = view.findViewById(R.id.id);
            Name = view.findViewById(R.id.name);
            Email = view.findViewById(R.id.email);
            Password = view.findViewById(R.id.pass);
            Phone = view.findViewById(R.id.phone);
            Date = view.findViewById(R.id.date);
            Gender = view.findViewById(R.id.gender);
            Hobies = view.findViewById(R.id.hobies);

            i = getArguments();
            if (i != null) {
                String id = i.getString("id");
                String name = i.getString("name");
                String email = i.getString("email");
                String pass = i.getString("pass");
                String phone = i.getString("phone");
                String date = i.getString("date");
                String gender = i.getString("gender");
                String hobies = " ";
                hobies = i.getString("hobies");

                ID.setText(id);
                Name.setText(name);
                Email.setText(email);
                Password.setText(pass);
                Phone.setText(phone);
                Date.setText(date);
                Gender.setText(gender);
                Hobies.setText(hobies);

            }
        }else{

        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
}
