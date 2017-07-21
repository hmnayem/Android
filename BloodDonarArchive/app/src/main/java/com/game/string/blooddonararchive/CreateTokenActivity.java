package com.game.string.blooddonararchive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTokenActivity extends AppCompatActivity {

    Toolbar mToolbar;

    private EditText mTitle;
    private EditText mDescription;
    private EditText mHospitalName;
    private EditText mLocation;
    private CheckBox mLocationCheckBox;
    private Spinner mBloodGroupSpinner;
    private Spinner mUrgencySpinner;

    private Button mCreateTokenButton;
    private ProgressDialog mProgressDialog;

    private String[] mBloodGroups;
    private String[] mUrgency;

    private String mSelectedBloodGroup;
    private String mSelectedUrgency;
    private String mTokenId;

    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mRootDb;
    private DatabaseReference mChildDb;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_token);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mRootDb = FirebaseDatabase.getInstance().getReference();
        mChildDb = mRootDb.child("tokens").push();
        mTokenId = mChildDb.getKey();

        mProgressDialog = new ProgressDialog(this);

        mToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setBackground(getDrawable(R.color.colorPrimaryDark));
        mToolbar.setTitleTextColor(Color.WHITE);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mBloodGroups = getResources().getStringArray(R.array.blood_group);
        mUrgency = getResources().getStringArray(R.array.urgency);

        mTitle = (EditText) findViewById(R.id.create_token_title_edit_text);
        mDescription = (EditText) findViewById(R.id.create_token_description_edit_text);
        mHospitalName = (EditText) findViewById(R.id.create_token_hospital_name);
        mLocation = (EditText) findViewById(R.id.create_token_add_location);

        mLocationCheckBox = (CheckBox) findViewById(R.id.create_token_location_check_box);
        mBloodGroupSpinner = (Spinner) findViewById(R.id.create_token_blood_group_spinner);
        mUrgencySpinner = (Spinner) findViewById(R.id.create_token_urgency_spinne);

        mCreateTokenButton = (Button) findViewById(R.id.create_token_subnmit_button);

        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mBloodGroups);
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> urgencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mUrgency);
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mBloodGroupSpinner.setAdapter(bloodAdapter);
        mBloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedBloodGroup = mBloodGroups[position];
                Toast.makeText(CreateTokenActivity.this, "Selected : " + mSelectedBloodGroup, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mUrgencySpinner.setAdapter(urgencyAdapter);
        mUrgencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedUrgency = mUrgency[position];
                Toast.makeText(CreateTokenActivity.this, "Selected : " + mSelectedUrgency, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCreateTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    private void update() {

        String title = mTitle.getText().toString().trim();
        String description = mDescription.getText().toString().trim();
        String hospitalName = mHospitalName.getText().toString().trim();
        String location = mLocation.getText().toString().trim();
        String blood = mSelectedBloodGroup;
        String urgency = mSelectedUrgency;
        String tokenId = mTokenId;
        final String userId = mFirebaseUser.getUid();

        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Creating A New Blood Request...");

        if (!isAnyFieldEmpty(title, description, hospitalName, location, blood, urgency, tokenId, userId)) {
            BloodToken token = new BloodToken(title, description, hospitalName, location, blood, urgency, userId, tokenId);
            mProgressDialog.show();
            mChildDb.child("token").setValue(token)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(CreateTokenActivity.this, "Token Created Successfully", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                            startActivity(new Intent(CreateTokenActivity.this, MainActivity.class));
                            finish();
                        }
                    });
        }

    }

    private boolean isAnyFieldEmpty(String title, String description, String hospitalName, String location,
                                    String blood, String urgency, String tokenId, String userId) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(hospitalName) || TextUtils.isEmpty(location)
                || TextUtils.isEmpty(blood) || TextUtils.isEmpty(urgency) || TextUtils.isEmpty(tokenId) || TextUtils.isEmpty(userId)) {
            return true;
        }

        return false;
    }


}

