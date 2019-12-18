package com.example.uidesign;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;


public class SignUpFragment extends Fragment {

    TextView btnLogin;
    TextInputLayout etSignUpEmail,etSignUpPassword,etSignUpName,etSignUpPhoneNumber;
    Button btnRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        btnLogin = v.findViewById(R.id.btnLogin);
        etSignUpEmail = v.findViewById(R.id.etSignUpEmail);
        etSignUpName = v.findViewById(R.id.etSignUpName);
        etSignUpPassword = v.findViewById(R.id.etSignUpPassword);
        etSignUpPhoneNumber = v.findViewById(R.id.etSignUpPhoneNumber);
        btnRegister = v.findViewById(R.id.btnSignUpRegister);


//Bringing back the views
        btnRegister.setVisibility(v.VISIBLE);
        etSignUpEmail.setVisibility(v.VISIBLE);
        etSignUpName.setVisibility(v.VISIBLE);
        etSignUpPassword.setVisibility(v.VISIBLE);
        etSignUpPhoneNumber.setVisibility(v.VISIBLE);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRegister.setVisibility(v.INVISIBLE);
                etSignUpEmail.setVisibility(v.INVISIBLE);
                etSignUpName.setVisibility(v.INVISIBLE);
                etSignUpPassword.setVisibility(v.INVISIBLE);
                etSignUpPhoneNumber.setVisibility(v.INVISIBLE);

                Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.fade_out_1);
                btnLogin.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        btnLogin.setText("");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment_container,new LoginFragment());
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
