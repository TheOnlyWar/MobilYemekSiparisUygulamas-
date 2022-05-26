package com.example.yemeksiparis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button m_btn_kayit, m_btn_giris, m_giris_buton, r_giris_buton;
    private EditText KullaniciMail, KullaniciSifre;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    ProgressDialog girisDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Kontrol tanımlamaları

        m_btn_giris = findViewById(R.id.m_btn_giris);
        m_btn_kayit = findViewById(R.id.m_btn_kayit);
        m_giris_buton = findViewById(R.id.m_giris_buton);
        r_giris_buton = findViewById(R.id.r_giris_buton);

        KullaniciMail = findViewById(R.id.giris_mail);
        KullaniciSifre = findViewById(R.id.giris_password);

        girisDialog = new ProgressDialog(this);

        m_giris_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent musteri = new Intent(MainActivity.this,MainActivity.class);
                startActivity(musteri);
            }
        });
        r_giris_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restoran = new Intent(MainActivity.this,RestoranGirisActivity.class);
                startActivity(restoran);
            }
        });
        m_btn_kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kayit = new Intent(MainActivity.this,KayitActivity.class);
                startActivity(kayit);
            }
        });
        m_btn_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KullaniciyeGirisIzniVer();
            }
        });
    }

    private void KullaniciyeGirisIzniVer() {
        String email = KullaniciMail.getText().toString();
        String sifre = KullaniciSifre.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "E-posta boş bırakılamaz.", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(sifre))
        {
            Toast.makeText(this, "Şifre boş bırakılamaz.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            girisDialog.setTitle("Giriş yapılıyor");
            girisDialog.setMessage("Lütfen Bekleyiniz...");
            girisDialog.setCanceledOnTouchOutside(true);
            girisDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,sifre)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                DatabaseReference yolGiris = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(firebaseAuth.getCurrentUser().getUid());

                                yolGiris.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        girisDialog.dismiss();

                                        Intent anaSayfa = new Intent(MainActivity.this,AnasayfaActivity.class);
                                        getIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(anaSayfa);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        girisDialog.dismiss();
                                    }
                                });
                            }
                            else
                            {
                                girisDialog.dismiss();
                                Toast.makeText(MainActivity.this, " Kullanıcı bulunamadı", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}