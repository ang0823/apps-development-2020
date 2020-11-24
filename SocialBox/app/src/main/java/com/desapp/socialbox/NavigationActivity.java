package com.desapp.socialbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.desapp.socialbox.fragment.FavoritesFragment;
import com.desapp.socialbox.fragment.HomeFragment;
import com.desapp.socialbox.fragment.NotficationsFragment;
import com.desapp.socialbox.fragment.OptionsFragment;
import com.desapp.socialbox.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Fragment fragment = null;
        fragment = new HomeFragment();
        startFragment(fragment);

        BottomNavigationView options = findViewById(R.id.navigationView);
        options.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.navBtn_home:
                        fragment = new HomeFragment();
                        break;
                    case  R.id.navBtn_profile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.navBtn_favorites:
                        fragment = new FavoritesFragment();
                        break;
                    case R.id.navBtn_notificacions:
                        fragment = new NotficationsFragment();
                        break;
                    case R.id.navBtn_otherOptions:
                        fragment = new OptionsFragment();
                        break;
                }
                if (fragment != null)
                    startFragment(fragment);

                return false;
            }
        });
    }

    public void startFragment(Fragment fragment){
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        transaction.replace(R.id.navigationBar, fragment);
        transaction.commit();
    }
}