package com.kingkingyyk.uhunter.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.kingkingyyk.uhunter.Config;
import com.kingkingyyk.uhunter.DataManager;
import com.kingkingyyk.uhunter.R;
import com.kingkingyyk.uhunter.databinding.ActivityHomeBinding;

public class ActivityHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityHomeBinding binding;
    private int lastNavigationMenuId=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ((TextView)binding.navView.getHeaderView(0).findViewById(R.id.textViewName)).setText(Config.CURRENT_USER.getName());
        ((TextView)binding.navView.getHeaderView(0).findViewById(R.id.textViewUsername)).setText(Config.CURRENT_USER.getUsername());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.navView.setCheckedItem(R.id.nav_profile);
                onNavigationItemSelected(binding.navView.getMenu().getItem(0));
            }
        }, 500);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

/*
        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } */

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        if (lastNavigationMenuId != id) {
            if (id == R.id.nav_profile) {
                Fragment fragment=(Fragment) FragmentProfile.newInstance(Config.CURRENT_USER);
                FragmentManager fman = getSupportFragmentManager();
                fman.beginTransaction().replace(R.id.frameContent, fragment).commit();
                item.setChecked(true);
                setTitle(item.getTitle());
            } else if (id == R.id.nav_logout) {
                finish();
                DataManager.clearData(this,"username");
                Intent intent = new Intent(ActivityHome.this, EnterUsername.class);
                startActivity(intent);
            }
            lastNavigationMenuId = id;
        }

        return true;
    }

}
