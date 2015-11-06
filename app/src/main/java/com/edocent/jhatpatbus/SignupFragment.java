package com.edocent.jhatpatbus;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.auth.signup.SignUpScreenlet;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment implements View.OnClickListener{

    TextView loginTextId;
    SignUpScreenlet mSignUpScreenlet;


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        loginTextId = (TextView) view.findViewById(R.id.loginTextId);
        loginTextId.setOnClickListener(this);

        mSignUpScreenlet = (SignUpScreenlet) view.findViewById(R.id.signup_screenlet);
        mSignUpScreenlet.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){

        }
    }
}
