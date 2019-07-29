package com.example.myblog;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class Homepage extends Fragment {

    private ImageView wellcomeImg;

    private Context context;
    private Homelistener homelistener;

    public Homepage() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        homelistener = (Homelistener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        wellcomeImg = view.findViewById(R.id.welcome_img);
        wellcomeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homelistener.homeAuth();
            }
        });
        return view;
    }

    interface Homelistener{
        void homeAuth();
    }

}
