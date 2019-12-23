package com.example.uidesign.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.uidesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;



public class OtpFragment extends Fragment {



    String phone,verificationID;
    TextInputEditText et1,et2,et3,et4,et5,et6;
    Button Verify;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_otp, container, false);

        initView(v);
        textChangedListners();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SaveData", Context.MODE_PRIVATE);
        phone = sharedPreferences.getString("PhoneNumber","Data Not Found");
        sendVerificationCode(phone);


        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = (et1.getText().toString()+et2.getText().toString()+et3.getText().toString()+et4.getText().toString()+et5.getText().toString()+et6.getText().toString());
                if(otp.length()<6 || otp.isEmpty())

                    Toast.makeText(getActivity(), "OTP entered was not correct", Toast.LENGTH_SHORT).show();

            else verifyCode(otp);
            }

        });


        return v;

    }

    private void verifyCode(String code){

PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationID,code);
signInWithCredential(phoneAuthCredential);

    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {

        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         FragmentTransaction fr = getFragmentManager().beginTransaction();
                         fr.replace(R.id.fragment_container,new WelcomeFragment());
                         fr.commit();
                         Toast.makeText(getActivity(), "User Registration Successful", Toast.LENGTH_SHORT).show();
                     }
                     else
                         Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                try {
                    int a = Integer.parseInt(code);
                    int b = a / 100000;
                    int c = (a % 100000) / 10000;
                    int d = (a % 10000) / 1000;
                    int e = (a % 1000) / 100;
                    int f = (a % 100) / 10;
                    int g = a % 10;
                    et1.setText(String.valueOf(b));
                    et2.setText(String.valueOf(c));
                    et3.setText(String.valueOf(d));
                    et4.setText(String.valueOf(e));
                    et5.setText(String.valueOf(f));
                    et6.setText(String.valueOf(g));

                    verifyCode(code);
                }
                catch (NumberFormatException e){
                    Toast.makeText(getActivity(), "Not an Integer", Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };

    private void textChangedListners() {
        //for et1
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if(et1.getText().toString().length() == 1) {
                    et2.requestFocus();
                }

            }
        });

        //for et2
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(et2.getText().toString().length() == 1) {
                    et3.requestFocus();
                }

            }
        });

        //for et3
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(et3.getText().toString().length() == 1) {
                    et4.requestFocus();
                }

            }
        });

        //for et4
        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(et4.getText().toString().length() == 1) {
                    et5.requestFocus();
                }

            }
        });

        //for et5
        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(et5.getText().toString().length() == 1) {
                    et6.requestFocus();
                }

            }
        });
    }

    private void initView(View v) {
        Verify = v.findViewById(R.id.btnVerify);
        et1 = v.findViewById(R.id.etOtp1);
        et2 = v.findViewById(R.id.etOtp2);
        et3 = v.findViewById(R.id.etOtp3);
        et4 = v.findViewById(R.id.etOtp4);
        et5 = v.findViewById(R.id.etOtp5);
        et6 = v.findViewById(R.id.etOtp6);
    }


}
