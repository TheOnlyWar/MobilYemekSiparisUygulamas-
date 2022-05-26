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

public class KayitActivity extends AppCompatActivity {

    private Button KayitOlusturmaButonu;
    private EditText KullaniciMail, KullaniciSifre, KullaniciSifre2,KullaniciAd, KullaniciSoyad, KullaniciAdres, KullaniciTel;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference yol;

    private ProgressDialog yukleniyorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        //Kontrol tanımlamaları

        KayitOlusturmaButonu = findViewById(R.id.musteri_kayıt_ol);

        KullaniciAd = findViewById(R.id.m_kayıt_name);
        KullaniciSoyad = findViewById(R.id.m_kayıt_surname);
        KullaniciAdres = findViewById(R.id.m_kayıt_adres);
        KullaniciTel = findViewById(R.id.m_kayıt_phone);
        KullaniciMail = findViewById(R.id.m_kayıt_mail);
        KullaniciSifre = findViewById(R.id.m_kayıt_password);
        KullaniciSifre2 = findViewById(R.id.m_kayıt_password2);

        KayitOlusturmaButonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                yukleniyorDialog = new ProgressDialog(KayitActivity.this);
                yukleniyorDialog.setTitle("Yeni hesap oluşturuluyor.");
                yukleniyorDialog.setMessage("Lütfen bekleyiniz...");
                yukleniyorDialog.setCanceledOnTouchOutside(true);
                yukleniyorDialog.show();

                String ad = KullaniciAd.getText().toString();
                String soyad = KullaniciSoyad.getText().toString();
                String adres = KullaniciAdres.getText().toString();
                String tel = KullaniciTel.getText().toString();
                String email = KullaniciMail.getText().toString();
                String sifre = KullaniciSifre.getText().toString();
                String sifre2 = KullaniciSifre2.getText().toString();

                if(TextUtils.isEmpty(ad)|TextUtils.isEmpty(soyad)|TextUtils.isEmpty(adres)|TextUtils.isEmpty(tel)|TextUtils.isEmpty(email)|TextUtils.isEmpty(sifre)|TextUtils.isEmpty(sifre2))
                {
                    yukleniyorDialog.dismiss();
                    Toast.makeText(KayitActivity.this, "Lütfen tüm alanları doldurunuz...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!sifre.equals(sifre2))
                    {
                        yukleniyorDialog.dismiss();
                        Toast.makeText(KayitActivity.this, "Şifre alanları uyuşmuyor...", Toast.LENGTH_SHORT).show();
                    }
                    YeniHesapOlustur(ad, soyad, adres, tel, email, sifre);
                }
            }
        });
    }

    private void YeniHesapOlustur(final String kullaniciadi, final String kullanicisoyad, final String kullaniciadres, final String kullanicitel, final String kullanicimail, final String kullanicisifre)
    {

        firebaseAuth.createUserWithEmailAndPassword(kullanicimail,kullanicisifre)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            String kullaniciId = firebaseUser.getUid();

                            yol = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(kullaniciId);

                            HashMap<String, Object> hashMap = new HashMap<>();

                            hashMap.put("id", kullaniciId);
                            hashMap.put("kullaniciad", kullaniciadi);
                            hashMap.put("kullanicisoyad", kullanicisoyad);
                            hashMap.put("kullaniciadres", kullaniciadres);
                            hashMap.put("kullanicitel", kullanicitel);
                            hashMap.put("kullanicimail", kullanicimail);
                            hashMap.put("kullanicisifre", kullanicisifre);

                            yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        yukleniyorDialog.dismiss();

                                        Toast.makeText(KayitActivity.this, "Kayıt Başarılı...", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(KayitActivity.this, AnasayfaActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else
                        {
                            yukleniyorDialog.dismiss();
                            Toast.makeText(KayitActivity.this, "Kayıt başarısız...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}