package com.game.string.blooddonararchive;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mLoginButton;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        mLoginButton = (Button) findViewById(R.id.login_fragment_button);
        mRegisterButton = (Button) findViewById(R.id.register_fragment_button);

        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, LoginFragment.getInstance());
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

        Fragment currentFragment = null;

        switch (v.getId()) {

            case R.id.login_fragment_button:
                currentFragment = LoginFragment.getInstance();
                break;

            case R.id.register_fragment_button:
                currentFragment = RegisterFragment.getInstance();
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, currentFragment);
        transaction.commit();
    }
}
