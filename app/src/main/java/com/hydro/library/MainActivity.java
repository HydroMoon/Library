package com.hydro.library;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterParse {

    ViewPager myPager;
    String[] PERMISSION = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.setting_menu:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.about_menu:
                startActivity(new Intent(this, About_us.class));
                break;
        }

        return true;
    }

    @Override
    public void SendData(List<Object> bList) {
        String tag = "android:switcher:" + R.id.viewpager + ":" + 1;
        Fragment f = getSupportFragmentManager().findFragmentByTag(tag);
        LibraryFragment fragment = (LibraryFragment) f;
        fragment.FillAdapter(bList);
    }

    private void permission_check() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(PERMISSION, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != 1 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
            permission_check();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);


        myPager = (ViewPager) findViewById(R.id.viewpager);
        myPager.setOffscreenPageLimit(2);
        myPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(myPager);


        if (getResources().getBoolean(R.bool.is_right_to_left)) {
            ViewCompat.setLayoutDirection(tabLayout, ViewCompat.LAYOUT_DIRECTION_LTR);
        }

        permission_check();

        Log.i("Token", "Token: " + FirebaseInstanceId.getInstance().getToken());

        FirebaseMessaging.getInstance().subscribeToTopic("SoftwareEngineering");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean switchValue = sharedPref.getBoolean(SettingsActivity.PREFRENCE_KEY_SWITCH, false);

        Log.i("LOL", String.valueOf(switchValue));

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public ViewPager getPager() {
        return myPager;
    }


    //PagerAdapter class
    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private static int FRAG_NUMS = 2;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return FRAG_NUMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mainFrag.newInstance();
                case 1:
                    return LibraryFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Main";
                case 1:
                    return "My Library";
                default:
                    return null;
            }
        }
    }
}
