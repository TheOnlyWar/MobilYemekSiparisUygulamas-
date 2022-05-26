package com.example.yemeksiparis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.yemeksiparis.cerceve.HomeFragment;
import com.example.yemeksiparis.cerceve.ProfilFragment;
import com.example.yemeksiparis.cerceve.RestoranAnasayfaFragment;
import com.example.yemeksiparis.cerceve.RestoranProfilFragment;
import com.example.yemeksiparis.cerceve.SepetFragment;
import com.example.yemeksiparis.cerceve.SiparisFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class RestoranAnasayfaActivity extends AppCompatActivity {

    BottomNavigationView RestoranBottomNavigationView;

    RestoranAnasayfaFragment restoranAnasayfaFragment = new RestoranAnasayfaFragment();
    SiparisFragment siparisFragment = new SiparisFragment();
    RestoranProfilFragment restoranProfilFragment = new RestoranProfilFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_anasayfa);

        RestoranBottomNavigationView = findViewById(R.id.restoran_bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.restoran_kapsayici_cerceve,restoranAnasayfaFragment).commit();

        RestoranBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.restoran_nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.restoran_kapsayici_cerceve, restoranAnasayfaFragment).commit();
                        return true;
                    case R.id.restoran_nav_sepet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.restoran_kapsayici_cerceve, siparisFragment).commit();
                        return true;
                    case R.id.restoran_nav_profil:
                        SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                        editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        editor.apply();

                        getSupportFragmentManager().beginTransaction().replace(R.id.restoran_kapsayici_cerceve, restoranProfilFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}