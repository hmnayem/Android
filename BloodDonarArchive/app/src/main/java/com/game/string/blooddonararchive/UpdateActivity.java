package com.game.string.blooddonararchive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity{

    private static final int SELECT_PICTURE = 5;
    private EditText mName;
    private EditText mEmail;
    private EditText mAddress;
    private EditText mPhone;
    private EditText mOccupation;
    private EditText mAge;

    private Spinner mBloodGroupSpinner;
    private Button mUpdateButton;
    private ImageButton mImageButton;

    private ProgressDialog mProgressDialog;

    private String[] mBloodGroups;
    private String mSelectedBloodGroup;

    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mBloodGroups = getResources().getStringArray(R.array.blood_group);
        mProgressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        mName = (EditText)findViewById(R.id.update_name_edit_text);
        mEmail = (EditText)findViewById(R.id.update_email_edit_text);
        mAddress = (EditText)findViewById(R.id.update_address_edit_text);
        mPhone = (EditText)findViewById(R.id.update_phone_edit_text);
        mOccupation = (EditText)findViewById(R.id.update_occupation_edit_text);
        mAge = (EditText)findViewById(R.id.update_age_edit_text);

        mBloodGroupSpinner = (Spinner)findViewById(R.id.update_blood_group__spinner);
        mUpdateButton = (Button)findViewById(R.id.update_submit_button);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mBloodGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mBloodGroupSpinner.setAdapter(adapter);
        mBloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedBloodGroup = mBloodGroups[position];
                Toast.makeText(UpdateActivity.this, "Selected : " + mBloodGroups[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setData();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void updateData() {

        final String name = mName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String address = mAddress.getText().toString().trim();
        final String phone = mPhone.getText().toString().trim();
        final String occupation = mOccupation.getText().toString().trim();
        final int age = Integer.parseInt(mAge.getText().toString());
        final String blood = mSelectedBloodGroup;
        Log.d("Blood", "One "+ blood + "");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Updating Information");

        if (!isAnyFieldEmpty(name, email, address, phone, occupation, blood)) {
            mProgressDialog.show();

            User user = new User(name, email, address, phone, blood, occupation, age, "N/A", mFirebaseUser.getUid());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseUser.getUid());
            Log.d("User ID", ref.getKey());

            ref.child("info").setValue(user);

            mProgressDialog.dismiss();
            startActivity(new Intent(UpdateActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Fill ALl The Field", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isAnyFieldEmpty(String name, String email, String address, String phone, String blood, String occupation) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(address) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(occupation) || TextUtils.isEmpty(blood)) {

            return true;
        }

        return false;
    }

    private void setData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(mFirebaseUser.getUid()).child("info");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    mName.setText(user.getName());
                    mEmail.setText(user.getEmail());
                    mAddress.setText(user.getAddress());
                    mPhone.setText(user.getPhone());
                    if (getPosition(user.getBloodGroup()) != -1) {
                        mBloodGroupSpinner.setSelection(getPosition(user.getBloodGroup()));
                    }
                    mOccupation.setText(user.getOccupation());
                    Integer age = Integer.valueOf(user.getAge());
                    mAge.setText(age + "");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getPosition(String value) {

        for (int i=0; i<mBloodGroups.length; i++) {
            if (mBloodGroups[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }
}
