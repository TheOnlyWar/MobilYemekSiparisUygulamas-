package com.example.yemeksiparis.cerceve;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.yemeksiparis.AnasayfaActivity;
import com.example.yemeksiparis.KayitActivity;
import com.example.yemeksiparis.MainActivity;
import com.example.yemeksiparis.PizzaActivity;
import com.example.yemeksiparis.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    LinearLayout pizza;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        pizza = view.findViewById(R.id.pizza);

        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PizzaActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}