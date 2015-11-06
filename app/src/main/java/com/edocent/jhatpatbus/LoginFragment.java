package com.edocent.jhatpatbus;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements  View.OnClickListener, LoginListener{

    static String TAG = LoginFragment.class.getSimpleName();

    LoginListener mLoginListener;

    TextView signupTextId;
    LoginScreenlet mLoginScreenlet;

    static interface LoginListener{
        void callSignup();
        void callHome();
    }


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginScreenlet = (LoginScreenlet) view.findViewById(R.id.loginScreenletId);
        mLoginScreenlet.setListener(this);

        signupTextId = (TextView) view.findViewById(R.id.signupTextId);
        signupTextId.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            /*
            case R.id.loginScreenletId:
                Log.v(TAG, "Logged in. Now redirect to Home");
                break;
            */
            case R.id.signupTextId:
                Log.v(TAG, "Signup Text clicked");
                if(mLoginListener != null){
                    mLoginListener.callSignup();
                }
                break;
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mLoginListener = (LoginListener) activity;
    }

    @Override
    public void onLoginSuccess(User user) {
        Log.v(TAG, "User logged in "+user.getEmail());
        //Redirect to Home Fragment
        mLoginListener.callHome();
    }

    @Override
    public void onLoginFailure(Exception e) {

    }
}
