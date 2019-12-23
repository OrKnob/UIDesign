package com.example.uidesign.Fragments;



import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.uidesign.R;
import com.example.uidesign.ValidationManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {
    TextView btnSignUp,ForgotPassword;
    Button btnSignIn;
    TextInputLayout lLoginEmail,lLoginPassword;
    TextInputEditText etLoginEmail,etLoginPassword;
    Boolean isValidEmail = false , isValidPassword = false;
    FirebaseAuth mauth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v =  inflater.inflate(R.layout.fragment_login, container, false);
        mauth = FirebaseAuth.getInstance();
        if(mauth.getCurrentUser() != null){
            FragmentTransaction fr = getFragmentManager().beginTransaction();
            fr.replace(R.id.fragment_container,new WelcomeFragment());
            fr.commit();
        }
        initView(v);

        textWatcher();


        //Bringing Back the Views
        btnSignIn.setVisibility(v.VISIBLE);
        ForgotPassword.setVisibility(v.VISIBLE);
        lLoginEmail.setVisibility(v.VISIBLE);
        lLoginPassword.setVisibility(v.VISIBLE);


       btnSignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               GoToSignUp(v);

           }
       });

       btnSignIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SignInUser();
           }
       });

       ForgotPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(isValidEmail){
                   mauth.sendPasswordResetEmail(etLoginEmail.getText().toString())
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                              if(task.isSuccessful()){
                                  Toast.makeText(getActivity(), "An Email has been sent to you containing the password reset link", Toast.LENGTH_SHORT).show();
                              }
                              else
                                  Toast.makeText(getActivity(), "Please try again later", Toast.LENGTH_SHORT).show();
                               }
                           });

               }
               else Toast.makeText(getActivity(), "Enter A Valid Email", Toast.LENGTH_SHORT).show();
           }
       });



       return v;
    }

    private void SignInUser() {

        if(isValidEmail && isValidPassword){
            mauth.signInWithEmailAndPassword(etLoginEmail.getText().toString(),etLoginPassword.getText().toString())
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FragmentTransaction fr = getFragmentManager().beginTransaction();
                                fr.replace(R.id.fragment_container,new WelcomeFragment());
                                fr.commit();
                                Toast.makeText(getActivity(),"SignIn Successful",Toast.LENGTH_LONG);

                            }
                            else
                                Toast.makeText(getActivity(),"Please check your email and password and try again",Toast.LENGTH_SHORT).show();

                        }
                    });

        }
        else Toast.makeText(getActivity(),"One or more fields are empty",Toast.LENGTH_SHORT).show();

    }

    private void textWatcher() {

        etLoginEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ValidationManager.isFieldEmpty(etLoginEmail.getText().toString())){
                    etLoginEmail.setError("Field Cannot Be Empty");
                }
                else if(ValidationManager.isEmailValid(etLoginEmail.getText().toString())){
                    etLoginEmail.setError("Enter a valid Email Address");
                }
                else isValidEmail  = true;

            }
        });

        etLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ValidationManager.isFieldEmpty(etLoginPassword.getText().toString())){
                    etLoginPassword.setError("Field Cannot Be Empty");
                }
                else if(ValidationManager.isValidPassword(etLoginPassword.getText().toString())){
                    etLoginPassword.setError("Password must contain a letter and a number with length between 8-16 characters");
                }
                else isValidPassword = true;

            }
        });

    }

    private void initView(View v) {

        btnSignUp = v.findViewById(R.id.btnSignUp);
        btnSignIn = v.findViewById(R.id.btnLoginSignIn);
        ForgotPassword = v.findViewById(R.id.tvForgotPassword);
        lLoginEmail = v.findViewById(R.id.lLoginEmail);
        lLoginPassword = v.findViewById(R.id.lLoginPassword);
        etLoginEmail = v.findViewById(R.id.etLoginEmail);
        etLoginPassword = v.findViewById(R.id.etLoginPassword);

    }

    private void GoToSignUp(View v) {

        btnSignIn.setVisibility(v.INVISIBLE);
        ForgotPassword.setVisibility(v.INVISIBLE);
        lLoginEmail.setVisibility(v.INVISIBLE);
        lLoginPassword.setVisibility(v.INVISIBLE);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.fade_out);
        btnSignUp.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                btnSignUp.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new SignUpFragment());
                fr.commit();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


}
