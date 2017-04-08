package com.letsgoapp.Views;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.letsgoapp.R;
import com.letsgoapp.Services.INavigationService;
import com.letsgoapp.Services.NavigationService;
import com.letsgoapp.Utils.ContextUtill;
import com.letsgoapp.ViewModels.MainActivityViewModel;
import com.letsgoapp.Views.Fragments.ActionFragment;
import com.letsgoapp.Views.Fragments.AddMeetingFragment;
import com.letsgoapp.Views.Fragments.GMapFragment;
import com.letsgoapp.Views.Fragments.MyConfirmsFragment;
import com.letsgoapp.databinding.ActivityMainBinding;
import com.vk.sdk.util.VKUtil;

import static com.letsgoapp.Utils.ContextUtill.SetTopContext;

//import android.support.v4.app.FragmentManager;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String APP_PREFERENCES = "mySettings";
    public static final String APP_PREFERENCES_REGISTER = "register";
    public static final String APP_PREFERENCES_TOKEN = "token";
    public static final String APP_PREFERENCES_REF = "ref";
    private INavigationService navigationService;

    public FragmentManager fragmentManager;
    DrawerLayout drawer;
    Toolbar toolbar;
    public GMapFragment gMapFragment;
    MainActivityViewModel mainActivityViewModel;
    SharedPreferences sharedPreferences;
    TextView messages,confirms;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        navigationService = new NavigationService();
        mainActivityViewModel = new MainActivityViewModel("https://pp.userapi.com/c837426/v837426417/28dee/s-Rks5_j60I.jpg");
        SetTopContext(this);
        activityMainBinding.setMainVM(mainActivityViewModel);

        toolbar = activityMainBinding.toolbar.toolbar2;
        toolbar.setTitle("Актуальные события");
        button = activityMainBinding.toolbar.fab;
        button.setOnClickListener(v -> {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddMeetingFragment()).commit();
            toolbar.setTitle("Создание события");
        });
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getFragmentManager();

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (!sharedPreferences.contains(APP_PREFERENCES_REGISTER)) {
            navigationService.goLogin();
        } else {
            ContextUtill.GetContextApplication().setToken(sharedPreferences.getString(APP_PREFERENCES_TOKEN,null));
            ContextUtill.GetContextApplication().setHref(sharedPreferences.getString(APP_PREFERENCES_REF,null));
            gMapFragment = new GMapFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, gMapFragment).commit();
        }
//        requestPermissions();


        //
        //Create these objects above OnCreate()of your main activity
//These lines should be added in the OnCreate() of your main activity
        confirms=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_my_confirms));

        messages=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_messages));

//This method will initialize the count value
        initializeCountDrawer();
    }

    private void initializeCountDrawer(){

        //Gravity property aligns the text
        confirms.setGravity(Gravity.CENTER_VERTICAL);
        confirms.setTypeface(null, Typeface.BOLD);
        confirms.setTextColor(getResources().getColor(R.color.colorAccent));
        confirms.setText("99+");
        messages.setGravity(Gravity.CENTER_VERTICAL);
        messages.setTypeface(null,Typeface.BOLD);
        messages.setTextColor(getResources().getColor(R.color.colorAccent));
//count is added
        messages.setText("7");

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SetTopContext(this);
        if (resultCode == RESULT_OK) {
            if (data.getExtras().getBoolean("auth")) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(data.getStringExtra("token")!=null){
                    editor.putString(APP_PREFERENCES_TOKEN,data.getStringExtra("token"));
                    editor.putBoolean(APP_PREFERENCES_REGISTER, true);
                }
                if(data.getExtras().getString("href")!=null){
                    editor.putString(APP_PREFERENCES_REF,data.getExtras().getString("href"));
                }
                editor.apply();
                gMapFragment = new GMapFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, gMapFragment).commit();
                navigationService.goProfile(null);
            }
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        fragmentManager = getFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_map) {
            //fragmentManager.beginTransaction().replace(R.id.fragment_container, new GMapFragment()).commit();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, gMapFragment).commit();
            toolbar.setTitle("Актуальные события");
            button.show();
            item.setChecked(true);
        } else if (id == R.id.nav_my_confirms) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new MyConfirmsFragment()).commit();
            toolbar.setTitle("Мои Заявки");
            button.hide();
            item.setChecked(true);
        } else if (id == R.id.nav_my_actions) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new ActionFragment()).commit();
            toolbar.setTitle("Мои события");
            button.hide();
            item.setChecked(true);
        } else if (id == R.id.nav_messages) {
            button.hide();
            item.setChecked(true);
        } else if (id == R.id.nav_create_action) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddMeetingFragment()).commit();
            toolbar.setTitle("Создание события");
            button.hide();
            item.setChecked(true);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
