package com.example.myblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myblog.Interface.OnBlogItemClick;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements Login.userAuthenticationListener,
    Homepage.Homelistener, OnBlogItemClick {

    private ImageView imgWelcome;
    private FragmentManager manager;
    private  FirebaseAuth auth;
    private FirebaseUser users;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Homepage homepage = new Homepage();
        ft.add(R.id.fragmentContainer,homepage);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_signout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.settings:

                break;
            case R.id.signout:
                auth = FirebaseAuth.getInstance();
                if(auth != null){
                    auth.signOut();
                    Toast.makeText(this,"Signout successfully",Toast.LENGTH_SHORT).show();
                    Login login = new Login();
                    manager.beginTransaction().replace(R.id.fragmentContainer,login).commit();
//                    Homepage homepage = new Homepage();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,homepage,fragment.getClass().getSimpleName()).addToBackStack(null);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Void onAuthComplete() {
        Blogwrite blog = new Blogwrite();
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,blog).addToBackStack("back").commit();
        manager.beginTransaction().replace(R.id.fragmentContainer,blog).commit();
        return null;
    }

    @Override
    public void homeAuth() {
         auth = FirebaseAuth.getInstance();
         users = auth.getCurrentUser();
         fragment = null;
        if(users != null){
            fragment = new Blogwrite();
        }else{
            fragment = new Login();
        }

        manager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
    }


    @Override
    public void onItemClick(Postnew postnew) {
        Bundle bundle = new Bundle();
        bundle.putString("Image",postnew.getImageUri());
        bundle.putString("title",postnew.getPostTitle());
        bundle.putString("description",postnew.getDescription());
        FragmentTransaction transaction = manager.beginTransaction();

        Read_post read_post = new Read_post();
        read_post.setArguments(bundle);
        transaction.replace(R.id.fragmentContainer,read_post).addToBackStack(null).commit();
    }

    @Override
    public void onBackPress() {
        manager.beginTransaction().replace(R.id.fragmentContainer,new Blogwrite()).commit();
    }
}
