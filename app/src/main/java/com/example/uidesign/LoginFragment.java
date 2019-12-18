package com.example.uidesign;



import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.textfield.TextInputLayout;


public class LoginFragment extends Fragment {
    TextView btnSignUp,tvLoginForgotPassword;
    Button btnSignIn;
    TextInputLayout etLoginEmail,etLoginPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v =  inflater.inflate(R.layout.fragment_login, container, false);

        btnSignUp = v.findViewById(R.id.btnSignUp);
        btnSignIn = v.findViewById(R.id.btnLoginSignIn);
        tvLoginForgotPassword = v.findViewById(R.id.tvForgotPassword);
        etLoginEmail = v.findViewById(R.id.etLoginEmail);
        etLoginPassword = v.findViewById(R.id.etLoginPassword);


        btnSignIn.setVisibility(v.VISIBLE);
        tvLoginForgotPassword.setVisibility(v.VISIBLE);
        etLoginEmail.setVisibility(v.VISIBLE);
        etLoginPassword.setVisibility(v.VISIBLE);

       btnSignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               btnSignIn.setVisibility(v.INVISIBLE);
               tvLoginForgotPassword.setVisibility(v.INVISIBLE);
               etLoginEmail.setVisibility(v.INVISIBLE);
               etLoginPassword.setVisibility(v.INVISIBLE);

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
       });



       return v;
    }


}
