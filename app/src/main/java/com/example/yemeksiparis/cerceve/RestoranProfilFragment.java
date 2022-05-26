package com.example.yemeksiparis.cerceve;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yemeksiparis.R;
import com.example.yemeksiparis.RestoranGirisActivity;
import com.example.yemeksiparis.model.Kullanici;
import com.example.yemeksiparis.model.Restoran;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestoranProfilFragment extends Fragment {

    private TextView restoranMail, restoranAd, restoranAdres, restoranTel;
    private Button btn_restoran_profil_cikis;

    private FirebaseAuth rYetki = FirebaseAuth.getInstance();
    private DatabaseReference rOku;

    String restoranMecvutKullaniciId;

    public RestoranProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restoran_profil, container, false);

        rYetki = FirebaseAuth.getInstance();
        rOku = FirebaseDatabase.getInstance().getReference();
        restoranMecvutKullaniciId = rYetki.getCurrentUser().getUid();

        rOku = FirebaseDatabase.getInstance().getReference().child("Restoranlar").child("SOtoGavaRpeUfoBpPMY0GIFLH8s2");

        restoranMail = view.findViewById(R.id.restoran_profil_mail);
        restoranAd = view.findViewById(R.id.restoran_profil_ad);
        restoranAdres = view.findViewById(R.id.restoran_profil_adres);
        restoranTel = view.findViewById(R.id.restoran_profil_tel);
        btn_restoran_profil_cikis = view.findViewById(R.id.btn_restoran_profil_cıkıs);

        btn_restoran_profil_cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rYetki.signOut();
                Intent intent = new Intent(getActivity(), RestoranGirisActivity.class);
                startActivity(intent);
            }
        });

        rOku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(getContext() == null)
                {
                    return;
                }
                Restoran restoran = snapshot.getValue(Restoran.class);
                restoranMail.setText(restoran.getRestoranmail());
                restoranAd.setText(restoran.getRestoranad());
                restoranAdres.setText(restoran.getRestoranadres());
                restoranTel.setText(restoran.getRestorantel());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}