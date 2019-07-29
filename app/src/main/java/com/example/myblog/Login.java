package com.example.myblog;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends Fragment implements View.OnClickListener {

    private Button btn_login,btn_signup;
    private EditText edt_email,edt_pass,edt_username;

    private Context context;
    private userAuthenticationListener listener;
    private FirebaseAuth auth;
    @Nullable private FirebaseUser currentUser;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        listener = (userAuthenticationListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        edt_email =view.findViewById(R.id.email_edt);
        edt_pass =view.findViewById(R.id.passEt);
        btn_login =view.findViewById(R.id.login_btn);
        btn_signup =view.findViewById(R.id.signup_btn);
        btn_login.setOnClickListener(this);
        btn_signup.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                 String email = edt_email.getText().toString();
                 String pass = edt_pass.getText().toString();

                 try{
                     if(email.isEmpty()&&pass.isEmpty()){
                         Toast.makeText(getActivity(), "please fillup email and pass field",Toast.LENGTH_SHORT).show();
                     }else if(edt_email.getText().toString().isEmpty()){
                         Toast.makeText(getActivity(), "please fillup email field",Toast.LENGTH_SHORT).show();
                     }else if(edt_pass.getText().toString().isEmpty()){
                         Toast.makeText(getActivity(), "please fillup pass field",Toast.LENGTH_SHORT).show();}
                     else{
                         auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if(task.isSuccessful()){
                                     Toast.makeText(getActivity(), "Login Successfull",Toast.LENGTH_SHORT).show();
                                     listener.onAuthComplete();
                                     edt_email.setText("Enter your email address");
                                     edt_pass.setText("Enter your password");
                                 }
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
                             }
                         });
                     }

                 }catch(Exception e){
                     Toast.makeText(getActivity(),"Error:" +e,Toast.LENGTH_SHORT).show();
            }
                 break;


            case R.id.signup_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                View mView = getLayoutInflater().inflate(R.layout.signuplayout,null);
                final EditText email_signup =(EditText) mView.findViewById(R.id.email_edt);
                final EditText password_signup =(EditText) mView.findViewById(R.id.passEt);
                Button btnReg = (Button) mView.findViewById(R.id.regBtn);


                btnReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!email_signup.getText().toString().isEmpty() && !password_signup.getText().toString().isEmpty()){

                            try {
                                String email = email_signup.getText().toString();
                                String pass = password_signup.getText().toString();
                                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                 @Override public void onComplete(@NonNull Task<AuthResult> task) {
                                     if(task.isSuccessful()){
                                         currentUser = auth.getCurrentUser();

                                     }
                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                  }
                              });
                            }catch (Exception ex){

                            }

                            email_signup.setText(" ");
                            password_signup.setText(" ");
                            Toast.makeText(getActivity(), R.string.Successfully,Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getActivity(), R.string.error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setView(mView);
                AlertDialog dialog = builder.create();
                builder.show();
                break;
        }
    }

    interface userAuthenticationListener{
        Void onAuthComplete();
    }
}