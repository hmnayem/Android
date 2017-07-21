package com.game.string.blooddonararchive;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    private EditText mRegisterName;
    private EditText mRegisterEmail;
    private EditText mRegisterAddress;
    private EditText mRegisterPhone;
    private Spinner mBloodGroupSpinner;
    private EditText mRegisterPassword;

    private Button mRegisterSubmitButton;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mRoot;
    private DatabaseReference mChild;

    private String[] mBloodGroups;
    private String mSelectedBloodGroup;


    public static RegisterFragment getInstance() {
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();
        mRoot = FirebaseDatabase.getInstance().getReference();
        mChild = mRoot.child("users");

        mBloodGroups = getResources().getStringArray(R.array.blood_group);
        mProgressDialog = new ProgressDialog(getContext());

        mRegisterName = (EditText) rootView.findViewById(R.id.register_fragment_name_edit_text);
        mRegisterEmail = (EditText) rootView.findViewById(R.id.register_fragment_email_edit_text);
        mRegisterAddress = (EditText) rootView.findViewById(R.id.register_fragment_address_edit_text);
        mRegisterPhone = (EditText) rootView.findViewById(R.id.register_fragment_phone_edit_text);
        mBloodGroupSpinner = (Spinner) rootView.findViewById(R.id.register_fragment_blood_spinner);
        mRegisterPassword = (EditText) rootView.findViewById(R.id.register_fragment_password_edit_text);
        mRegisterSubmitButton = (Button) rootView.findViewById(R.id.register_fragment_submit_button);

        mRegisterSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mBloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mBloodGroupSpinner.setAdapter(adapter);
        mBloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedBloodGroup = mBloodGroups[position];
                Toast.makeText(getContext(), "Selected : " + mSelectedBloodGroup, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    private void signUp() {

        final String name = mRegisterName.getText().toString().trim();
        final String email = mRegisterEmail.getText().toString().trim();
        final String address = mRegisterAddress.getText().toString().trim();
        final String phone = mRegisterPhone.getText().toString().trim();
        final String blood = mSelectedBloodGroup;
        String password = mRegisterPassword.getText().toString().trim();

        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Creating Account");


        if (!isAnyFieldEmpty(name, email, address, phone, blood, password)) {

            if (checkEmail(email) && checkPhone(phone)) {

                mProgressDialog.show();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                User user = new User(name, email, address, phone, blood, firebaseUser.getUid());
                                mChild.child(firebaseUser.getUid()).child("info").setValue(user);
                                Toast.makeText(getContext(), "User Creation Complete", Toast.LENGTH_SHORT).show();
                                mProgressDialog.dismiss();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "User Creation Failure", Toast.LENGTH_SHORT).show();
                                mProgressDialog.dismiss();
                            }
                        });

            }

        }


    }


    private boolean checkEmail(String email) {
        if (email == null) {
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean checkPhone(String phone) {
        if (phone == null) {
            return false;
        }

        return Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isAnyFieldEmpty(String name, String email, String address, String phone, String blood, String password) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(address)
                || TextUtils.isEmpty(phone) || TextUtils.isEmpty(blood) || TextUtils.isEmpty(password)) {
            return true;
        }

        return false;
    }
}



























