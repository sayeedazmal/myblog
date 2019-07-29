package com.example.myblog;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myblog.Interface.OnBlogItemClick;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Viewblog extends Fragment{

    private RecyclerView recyclerView;
    private Myadapter myadapter;
    private List<Postnew> listofPost;
    private ProgressBar progressBar;
    private Myadapter adapter;
    private OnBlogItemClick onBlogItemClick;


    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference rootRef;
    DatabaseReference userRef;
    DatabaseReference userIdRef;
    DatabaseReference myRef;

    private  Context context;
    public Viewblog() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        onBlogItemClick = (OnBlogItemClick) context;

    }

    @Override
    public void onResume() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listofPost.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Postnew postnew = dataSnapshot1.getValue(Postnew.class);
                    listofPost.add(postnew);
                }
                myadapter  = new Myadapter(context,listofPost);
                recyclerView.clearOnChildAttachStateChangeListeners();
                recyclerView.setAdapter(myadapter);
                progressBar.setVisibility(View.INVISIBLE);

                myadapter.setOnclicklistener(new Myadapter.Onclicklistener() {
                    @Override
                    public void onclick(Postnew postnew) {
                        onBlogItemClick.onItemClick(postnew);

                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Error"+databaseError,Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_viewblog, container, false);
        recyclerView  = view.findViewById(R.id.reecylerViewId);
        recyclerView.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.prograssId);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listofPost = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        rootRef  = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("user");
        userIdRef = userRef.child(user.getUid());
        myRef = userIdRef.child("Post");

        return view;
    }


}
