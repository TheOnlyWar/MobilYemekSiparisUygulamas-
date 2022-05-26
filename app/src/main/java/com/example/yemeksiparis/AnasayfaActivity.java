package com.example.yemeksiparis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.yemeksiparis.cerceve.HomeFragment;
import com.example.yemeksiparis.cerceve.ProfilFragment;
import com.example.yemeksiparis.cerceve.SepetFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class AnasayfaActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    SepetFragment sepetFragment = new SepetFragment();
    ProfilFragment profilFragment = new ProfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.kapsayici_cerceve,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.kapsayici_cerceve, homeFragment).commit();
                        return true;
                    case R.id.nav_sepet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.kapsayici_cerceve, sepetFragment).commit();
                        return true;
                    case R.id.nav_profil:
                        SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                        editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        editor.apply();

                        getSupportFragmentManager().beginTransaction().replace(R.id.kapsayici_cerceve, profilFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}