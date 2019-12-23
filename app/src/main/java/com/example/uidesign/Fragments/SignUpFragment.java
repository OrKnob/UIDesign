package com.example.uidesign.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import com.example.uidesign.R;
import com.example.uidesign.ValidationManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class SignUpFragment extends Fragment {

    TextView btnLogin;
    TextInputLayout lSignUpEmail,lSignUpPassword,lSignUpName,lSignUpPhoneNumber;
    TextInputEditText etSignUpName,etSignUpEmail,etSignUpPhoneNumber,etSignUpPassword;
    Button btnRegister;
    String name,phone,password,email;
    SharedPreferences.Editor editor;
    Boolean isValidName = false , isValidEmail = false , isValidPassword = false , isValidMobileNumber = false;
    FirebaseAuth firebaseAuth;



    public SignUpFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v =  inflater.inflate(R.layout.fragment_sign_up, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        initView(v);
        textWatcher();



//Bringing back the views
        btnRegister.setVisibility(v.VISIBLE);
        lSignUpEmail.setVisibility(v.VISIBLE);
        lSignUpName.setVisibility(v.VISIBLE);
        lSignUpPassword.setVisibility(v.VISIBLE);
        lSignUpPhoneNumber.setVisibility(v.VISIBLE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFromUser();
                passDataAndRegister();


            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GotoLogin(v);

            }
        });

        return v;
    }

    private void textWatcher() {
        etSignUpName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
if(ValidationManager.isFieldEmpty(Objects.requireNonNull(etSignUpName.getText().toString()))){
    etSignUpName.setError("Field Cannot Be Empty");
    isValidName = false;
}
else isValidName = true;

            }
        });

        etSignUpEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ValidationManager.isFieldEmpty(etSignUpEmail.getText().toString())){
                    etSignUpEmail.setError("Field Cannot Be Empty");
                }
                else if(ValidationManager.isEmailValid(etSignUpEmail.getText().toString())){
                    etSignUpEmail.setError("Enter a valid Email Address");
                }
                else isValidEmail = true;

            }
        });

        etSignUpPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ValidationManager.isFieldEmpty(etSignUpPassword.getText().toString())){
                    etSignUpPassword.setError("Field Cannot Be Empty");
                }
                else if (ValidationManager.isValidPassword(etSignUpPassword.getText().toString())){
                    etSignUpPassword.setError("Password must contain a letter and a number with length between 8-16 characters");
                }
                else  isValidPassword = true;

            }
        });

        etSignUpPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ValidationManager.isFieldEmpty(etSignUpPhoneNumber.getText().toString())){
                    etSignUpPhoneNumber.setError("Field Cannot Be Empty");
                }
                else if (ValidationManager.isValidMobileNumber(etSignUpPhoneNumber.getText().toString())){
                    etSignUpPhoneNumber.setError("Enter Valid Mobile Number");
                }
                else isValidMobileNumber = true;

            }
        });
    }

    private void passDataAndRegister() {
        phone = ("+91" + etSignUpPhoneNumber.getText().toString());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SaveData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("PhoneNumber",phone);
        editor.commit();

        if(isValidEmail && isValidPassword && isValidMobileNumber && isValidName){
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            ShowOtpFragment();


                        }
                    });
        }
        else Toast.makeText(getActivity(),"One or more fields are empty",Toast.LENGTH_SHORT).show();



    }

    public void getFromUser(){
        name = etSignUpName.getText().toString();
        email = etSignUpEmail.getText().toString();
        password = etSignUpPassword.getText().toString();


    }

    private void ShowOtpFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new OtpFragment());
        fragmentTransaction.commit();

    }

    private void GotoLogin(View v) {

        btnRegister.setVisibility(v.INVISIBLE);
        lSignUpEmail.setVisibility(v.INVISIBLE);
        lSignUpName.setVisibility(v.INVISIBLE);
        lSignUpPassword.setVisibility(v.INVISIBLE);
        lSignUpPhoneNumber.setVisibility(v.INVISIBLE);

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

    private void initView(View v) {
        btnLogin = v.findViewById(R.id.btnLogin);
        lSignUpEmail = v.findViewById(R.id.lSignUpEmail);
        lSignUpName = v.findViewById(R.id.lSignUpName);
        lSignUpPassword = v.findViewById(R.id.lSignUpPassword);
        lSignUpPhoneNumber = v.findViewById(R.id.lSignUpPhoneNumber);
        btnRegister = v.findViewById(R.id.btnSignUpRegister);


        etSignUpName = v.findViewById(R.id.etSignUpName);
        etSignUpPhoneNumber = v.findViewById(R.id.etSignUpPhoneNumber);
        etSignUpEmail = v.findViewById(R.id.etSignUpEmail);
        etSignUpPassword = v.findViewById(R.id.etSignUpPassword);
    }



}
