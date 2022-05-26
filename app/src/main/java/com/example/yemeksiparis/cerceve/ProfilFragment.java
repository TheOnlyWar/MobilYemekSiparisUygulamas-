package com.example.yemeksiparis.cerceve;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yemeksiparis.MainActivity;
import com.example.yemeksiparis.R;
import com.example.yemeksiparis.RestoranAnasayfaActivity;
import com.example.yemeksiparis.RestoranGirisActivity;
import com.example.yemeksiparis.model.Kullanici;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    private TextView kullaniciMail, kullaniciAd, kullaniciSoyad, kullaniciAdres, kullaniciTel;
    private Button btn_musteri_profil_cikis;

    private FirebaseAuth mYetki = FirebaseAuth.getInstance();
    private DatabaseReference oku;

    String mecvutKullaniciId;


    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        mYetki = FirebaseAuth.getInstance();
        oku = FirebaseDatabase.getInstance().getReference();
        mecvutKullaniciId = mYetki.getCurrentUser().getUid();

        oku = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child("0M2hn2TTK6NM6zs94hPuke4J65l1");


        kullaniciMail = view.findViewById(R.id.profil_mail);
        kullaniciAd = view.findViewById(R.id.profil_ad);
        kullaniciSoyad = view.findViewById(R.id.profil_soyad);
        kullaniciAdres = view.findViewById(R.id.profil_adres);
        kullaniciTel = view.findViewById(R.id.profil_tel);
        btn_musteri_profil_cikis = view.findViewById(R.id.btn_musteri_profil_cıkıs);

        btn_musteri_profil_cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYetki.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        oku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(getContext() == null)
                {
                    return;
                }
                Kullanici kullanici = snapshot.getValue(Kullanici.class);
                kullaniciMail.setText(kullanici.getKullanicimail());
                kullaniciAd.setText(kullanici.getKullaniciad());
                kullaniciSoyad.setText(kullanici.getKullanicisoyad());
                kullaniciAdres.setText(kullanici.getKullaniciadres());
                kullaniciTel.setText(kullanici.getKullanicitel());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

}