package com.example.choisangah.realtonymony;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import layout.AppInfoFragment;
import layout.HomeFragment;
import layout.ItemFragment;
import layout.TonyTestFragment;
import layout.UserPlatteFragment;

public class HomeActivity extends AppCompatActivity {

    private static final int REQ_WRITE_EXTERNAL_STORAGE = 1;
    private static final int REQ_CAMERA = 2;

    TonyTestFragment tonyTestFragment;
    HomeFragment homeFragment;
    ItemFragment itemFragment;
    UserPlatteFragment userPlatteFragment;
    AppInfoFragment appInfoFragment;

    private ActionBarDrawerToggle mainDrawerToggle;
    private DrawerLayout drawer;
    private NavigationView nav_Views;
    private Toolbar toolbar;
//    GridView gridView;
//    HomeGridAdapter gridAdapter;
//    ArrayList<Skin> skins = new ArrayList<>();
//    final static int imglist[] = {R.drawable.springwarm,R.drawable.summercool,R.drawable.autumnwarm,R.drawable.wintercool};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeFragment = new HomeFragment();
        itemFragment = new ItemFragment();
        tonyTestFragment = new TonyTestFragment();
        userPlatteFragment = new UserPlatteFragment();
        appInfoFragment = new AppInfoFragment();


        initViews();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQ_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    checkPermission();
                }
                break;

            case REQ_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    checkPermission();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void initViews() {

        initToolBar();
        initSlideMenu();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, homeFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        checkPermission();
    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQ_WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA},
                    REQ_WRITE_EXTERNAL_STORAGE);
        }
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TONY MONY");
        toolbar.setTitleMarginStart(120);
        toolbar.setTitleMarginEnd(40);
        toolbar.setTitleTextColor(Color.WHITE);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mainDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initSlideMenu() {
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        nav_Views = (NavigationView) findViewById(R.id.nav_Views);

        mainDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawer, R.string.drawer_open, R.string.drawer_close);
        // mainDrawerToggle.setHomeAsUpIndicator();
        drawer.addDrawerListener(mainDrawerToggle);

        nav_Views.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawer.closeDrawers();
                item.setChecked(true);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


                switch (item.getItemId()) {
                    case R.id.home:
                        transaction.replace(R.id.container, homeFragment);

                        break;
                    case R.id.findpc:
                        transaction.replace(R.id.container, tonyTestFragment);
                        break;
                    case R.id.item:
                        transaction.replace(R.id.container, itemFragment);
                        break;
                    case R.id.mypalette:
                        transaction.replace(R.id.container,userPlatteFragment);

                        break;
                    case R.id.appinfo:
                        transaction.replace(R.id.container,appInfoFragment);
                        break;

                }

//                transaction.addToBackStack(null);
                transaction.commit();

                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mainDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mainDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void selectNaviItem(int itemindex){
        nav_Views.getMenu().getItem(itemindex).setChecked(true);
    }
}
