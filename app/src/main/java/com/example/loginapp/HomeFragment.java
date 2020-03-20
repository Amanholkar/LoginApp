package com.example.loginapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loginapp.Adapter.RecyclerViewAdapter;
import com.example.loginapp.data.DatabaseHandler;
import com.example.loginapp.modal.User;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class HomeFragment extends Fragment implements RecyclerViewAdapter.OnClickRecyclerView {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<User> users;
    DatabaseHandler db ;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    private Toolbar toolbar1;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        toolbar1 =getActivity().findViewById(R.id.toolbarhome);
        toolbar1.setTitle("Home");

        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar1);
        drawerLayout =getActivity().findViewById(R.id.drawerlayout);

        navigationView =getActivity().findViewById(R.id.nav_view);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                drawerLayout,
                toolbar1,
                R.string.nav_open,
                R.string.nav_close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        actionBarDrawerToggle.syncState();



        Log.i(getClass().getName(),"rrrrrrrr");

        recyclerView =view.findViewById(R.id.recycleview);
        db=new DatabaseHandler(getActivity());
         users =db.getAllContacts();

        layoutManager = new LinearLayoutManager(getActivity());

        adapter = new RecyclerViewAdapter(users,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onDelete(int position) {

        final int currentposition = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete");
        builder.setMessage("Do You Want to Delete ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user =users.get(currentposition);
                db.deleteContact(user);
                users.remove(user);
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Successfully Delete", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog =builder.create();
        alertDialog.show();

    }

    @Override
    public void onEdit(int position) {

        User user =users.get(position);
        Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();

        EditUserFragment editUserFragment = new EditUserFragment();
        Bundle bundle = new Bundle();
        User user1=user;
        bundle.putSerializable("user",user1);
        editUserFragment.setArguments(bundle);
//               fragmentManager = getFragmentManager();
//        fragmentTransaction =fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.frame_contener,editUserFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        replaceFragmentWithBack(R.id.frame_contener, editUserFragment, "EditUserFragment", "HomeFragment");
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(int position) {
        User user = users.get(position);

        String id = String.valueOf(user.getId());
        String name=user.getName();
        String email =user.getEmail();
        String password=user.getPassword();
        String phone=user.getPhoneNumber();
        String date=user.getDate();
        String gender=user.getGender();
        String hobies = user.getHobbies();


      ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle =new Bundle();
        bundle.putString("id",id);
        bundle.putString("name",name);
        bundle.putString("email",email);
        bundle.putString("pass",password);
        bundle.putString("phone",phone);
        bundle.putString("date",date);
        bundle.putString("gender",gender);
        bundle.putString("hobies",hobies);
        profileFragment.setArguments(bundle);


//       fragmentManager = getFragmentManager();
//       fragmentTransaction=fragmentManager.beginTransaction();
//       fragmentTransaction.add(R.id.frame_contener,profileFragment);
//       fragmentTransaction.addToBackStack(null);
//       fragmentTransaction.commit();

        replaceFragmentWithBack(R.id.frame_contener, profileFragment, "ProfileFragment", "HomeFragment");


    }

    public void replaceFragmentWithBack(int containerViewId, Fragment fragment,
                                        String fragmentTag, String backStackStateName) {
        getFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commitAllowingStateLoss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(getClass().getName(),"sasasas ");
    }
}
