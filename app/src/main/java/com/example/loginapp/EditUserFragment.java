package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.loginapp.data.DatabaseHandler;
import com.example.loginapp.modal.User;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;


public class EditUserFragment extends Fragment implements View.OnClickListener {



     private TextInputEditText Name;
     private TextInputEditText Email;
    private TextInputEditText Password;
    private TextInputEditText Phone;
    private TextInputEditText Date;
    private RadioGroup radioGroup;
    private RadioButton Gender;
    private Chip Gym,Guitar,Dance,Sing;
    private RadioButton male , female;
    private Button updateButton;
    String hobiesarray[];
    String gender =" ";
    String Hobies =" ";
    String gym=" ",dance=" ",guitar=" ",sing=" ";
    User user;
    View view;
    DatabaseHandler db ;
    Toolbar toolbar1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_edit_user, container, false);

        toolbar1 =getActivity().findViewById(R.id.toolbarhome);
        toolbar1.setTitle("Profile");
        toolbar1.setNavigationIcon(R.drawable.ic_back);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();


            }
        });

        Name =view.findViewById(R.id.Ename);
        Email=view.findViewById(R.id.Eemail);
        Password=view.findViewById(R.id.Epassword);
        Phone = view.findViewById(R.id.Ephone_number);
        Date = view.findViewById(R.id.Edate);
        radioGroup =view.findViewById(R.id.Eradiogroup);

        male=view.findViewById(R.id.EradioMale);
        female=view.findViewById(R.id.EradioFemale);
        Gym=view.findViewById(R.id.Egym);
        Guitar=view.findViewById(R.id.Eguitar);
        Dance=view.findViewById(R.id.Edance);
        Sing=view.findViewById(R.id.Esing);
        updateButton = view.findViewById(R.id.Eupdate);

        db = new DatabaseHandler(getActivity());





         Bundle i =getArguments();
         user = (User) i.getSerializable("user");
        Name.setText(user.getName());
        Email.setText(user.getEmail());
        Password.setText(user.getPassword());
        Phone.setText(user.getPhoneNumber());
        Date.setText(user.getDate());
        Hobies=user.getHobbies();
        hobiesarray=Hobies.split(",");
        for (String name:hobiesarray)
        {

            for (int p=0;p<=hobiesarray.length;p++){

                if (name.equalsIgnoreCase("gym")){
                   Gym.setChecked(true);

                }
                if (name.equalsIgnoreCase("guitar")){
                    Guitar.setChecked(true);

                }
                if (name.equalsIgnoreCase("dance")){
                    Dance.setChecked(true);

                }
                if (name.equalsIgnoreCase("sing")){
                    Sing.setChecked(true);

                }

            }
        }

        Log.i(getClass().getName(), "onCreateView: "+user.getGender());
        if (user.getGender().equalsIgnoreCase("male")){
            male.setChecked(true);
        }else  if (user.getGender().equalsIgnoreCase("female")){
            female.setChecked(true);
        }

        Gym.setOnClickListener(this);
        Guitar.setOnClickListener(this);
        Dance.setOnClickListener(this);
        Sing.setOnClickListener(this);




       updateButton.setOnClickListener(this);
        return view;
    }




    @Override
    public void onClick(View v) {
       switch (v.getId()){

           case R.id.Egym:
               if (Gym.isChecked()) {
                   gym ="Gym,";
               }else{
                   gym=" ";
               }
               break;
           case R.id.Eguitar:

               if (Guitar.isChecked()) {
                   guitar = "Guitar,";
               }else{
                   guitar=" ";
               }
               break;
           case R.id.Edance:
               if (Dance.isChecked()){
                   dance="Dance,";
               }else {
                   dance=" ";
               }

               break;
           case R.id.Esing:
               if (Sing.isChecked()){
                   sing="Sing,";
               }else{
                   sing=" ";
               }

               break;

           case R.id.Eupdate:

               String name =Name.getText().toString();
               String email=Email.getText().toString();
               String pass=Password.getText().toString();
               String phone=Phone.getText().toString();
               String date=Date.getText().toString();
               int selectedId=radioGroup.getCheckedRadioButtonId();
              RadioButton local=(RadioButton)view.findViewById(selectedId);

               Log.i(getClass().getName(), "onClick: "+local.getText());
               Toast.makeText(getActivity(),"fdskfksdf  "+local.getText(),Toast.LENGTH_SHORT).show();
//               Toast.makeText(getActivity(), "Update Complete", Toast.LENGTH_SHORT).show();
               gender=local.getText().toString();
               user.setName(name);
               user.setEmail(email);
               user.setPassword(pass);
               user.setPhoneNumber(phone);
               user.setDate(date);
               user.setGender(gender);
               Hobies=gym+guitar+sing+dance;
               user.setHobbies(Hobies);


               db.updateContact(user);
//               Toast.makeText(getActivity(), "Update Complete", Toast.LENGTH_SHORT).show();

               FragmentManager fragmentManager = getFragmentManager();
               FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.frame_contener,new HomeFragment());
               fragmentTransaction.commit();
               break;


       }





    }

}
