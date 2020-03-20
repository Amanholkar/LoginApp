package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar1;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout frameLayout;
    TextView Name;
    TextView Email;
    String name;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar1 = findViewById(R.id.toolbarhome);
        toolbar1.setTitle("Home");
        setSupportActionBar(toolbar1);
        toolbar1.setTitleTextColor(0xFFFFFFFF);
        drawerLayout =findViewById(R.id.drawerlayout);
        frameLayout = findViewById(R.id.frame_contener);

        navigationView =findViewById(R.id.nav_view);
        gattingDataSharedPreferens();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar1,
                R.string.nav_open,
                R.string.nav_close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        actionBarDrawerToggle.syncState();


//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.frame_contener, new HomeFragment());
//        ft.commit();

        replaceFragment(new HomeFragment(), "HomeFragment");
        showdataInNaviDrawable();

        navigationView.setNavigationItemSelectedListener(this);




    }



    private void replaceFragment(Fragment fragment, String fragmentName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_contener, fragment, fragmentName)
                //.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss();
    }
    // set data to navigation to header
    private void showdataInNaviDrawable() {
        View haderView =navigationView.getHeaderView(0);
        Name =haderView.findViewById(R.id.hname);
        Email=haderView.findViewById(R.id.hemail);
        Name.setText(name);
        Email.setText(email);
    }
//   methd for geting data from the  sharedprefe
    private void gattingDataSharedPreferens() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.loginapp",MODE_PRIVATE);
        name=sharedPreferences.getString("nameKey"," ");
        email=sharedPreferences.getString("emailKey"," ");

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //creating fragment object
        Fragment fragment = null;




        //initializing the fragment object which is selected
        switch (item.getItemId()) {
            case R.id.home:
                HomeFragment homeFragment = new HomeFragment();


                fragment = homeFragment;
                toolbar1.setTitle("Home");
//                replaceFragment(new HomeFragment(), "HomeFragment");
                break;
            case R.id.profile:
                ProfileFragment profileFragment = new ProfileFragment();
                fragment = profileFragment;
                toolbar1.setTitle("Profile");
//                replaceFragment(new ProfileFragment(), "ProfileFragment");
                break;
            case R.id.setting:
                SettingFragment settingFragment = new SettingFragment();
                fragment = settingFragment;
                toolbar1.setTitle("Setting");
//                replaceFragment(new SettingFragment(), "SettingFragment");
                break;

            case R.id.logout:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;

            case R.id.exit:
                AlertDialog.Builder builder  = new AlertDialog.Builder(this);
                builder.setTitle("Exit");
                builder.setMessage("Do you want to exit ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                        System.exit(1);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog =builder.create();
                alertDialog.show();
                break;

            default: fragment = new HomeFragment();

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_contener, fragment);
            ft.commit();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentLast = fragmentManager.findFragmentById(R.id.frame_contener);
        String tag = (String) fragmentLast.getTag();

        Log.i(getClass().getName(),"tag  "+tag);
      if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
          Log.i(getClass().getName(),"tag1111  "+tag);
//            dialogExit(this, getResources().getString(R.string.exit_app),
//                    getResources().getString(R.string.exit_app_message));

          AlertDialog.Builder builder  = new AlertDialog.Builder(this);
          builder.setTitle("Exit");
          builder.setMessage("Do you want to exit ?");
          builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

                 finish();
                System.exit(1);
              }
          });
          builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
              }
          });
         AlertDialog alertDialog =builder.create();
         alertDialog.show();
//          super.onBackPressed();
      }
    }
}
