package com.example.yemeksiparis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RestoranKayitActivity extends AppCompatActivity {

    private Button KayitOlusturmaButonu;
    private EditText RestoranMail, RestoranSifre, RestoranSifre2, RestoranAd, RestoranAdres, RestoranTel;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference yol;

    private ProgressDialog yukleniyorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_kayit);

        //Kontrol tanımlamaları

        KayitOlusturmaButonu = findViewById(R.id.restoran_kayıt_ol);

        RestoranMail = findViewById(R.id.restoran_kayıt_mail);
        RestoranSifre = findViewById(R.id.restoran_kayıt_password);
        RestoranSifre2 = findViewById(R.id.restoran_kayıt_password2);
        RestoranAd = findViewById(R.id.restoran_kayıt_name);
        RestoranAdres = findViewById(R.id.restoran_kayıt_adres);
        RestoranTel = findViewById(R.id.restoran_kayıt_phone);

        KayitOlusturmaButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                yukleniyorDialog = new ProgressDialog(RestoranKayitActivity.this);
                yukleniyorDialog.setTitle("Yeni hesap oluşturuluyor.");
                yukleniyorDialog.setMessage("Lütfen bekleyiniz...");
                yukleniyorDialog.setCanceledOnTouchOutside(true);
                yukleniyorDialog.show();


                String ad = RestoranAd.getText().toString();
                String adres = RestoranAdres.getText().toString();
                String tel = RestoranTel.getText().toString();
                String email = RestoranMail.getText().toString();
                String sifre = RestoranSifre.getText().toString();
                String sifre2 = RestoranSifre2.getText().toString();

                if(TextUtils.isEmpty(ad)|TextUtils.isEmpty(adres)|TextUtils.isEmpty(tel)|TextUtils.isEmpty(email)|TextUtils.isEmpty(sifre)|TextUtils.isEmpty(sifre2))
                {
                    yukleniyorDialog.dismiss();
                    Toast.makeText(RestoranKayitActivity.this, "Lütfen tüm alanları doldurunuz...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RestoranYeniHesapOlustur(ad, adres, tel, email, sifre);
                }
            }
        });

    }

    private void RestoranYeniHesapOlustur(final String restoranadi, final String restoranadres, final String restorantel, final String restoranmail, final String restoransifre)
    {

        firebaseAuth.createUserWithEmailAndPassword(restoranmail,restoransifre)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            String restoranId = firebaseUser.getUid();

                            yol = FirebaseDatabase.getInstance().getReference().child("Restoranlar").child(restoranId);

                            HashMap<String, Object> hashMap = new HashMap<>();


                            hashMap.put("id", restoranId);
                            hashMap.put("restoranad", restoranadi);
                            hashMap.put("restoranadres", restoranadres);
                            hashMap.put("restorantel", restorantel);
                            hashMap.put("restoranmail", restoranmail);
                            hashMap.put("restoransifre", restoransifre);

                            yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        yukleniyorDialog.dismiss();

                                        Toast.makeText(RestoranKayitActivity.this, "Kayıt Başarılı...", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RestoranKayitActivity.this, AnasayfaActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else
                        {
                            yukleniyorDialog.dismiss();
                            Toast.makeText(RestoranKayitActivity.this, "Kayıt başarısız...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}