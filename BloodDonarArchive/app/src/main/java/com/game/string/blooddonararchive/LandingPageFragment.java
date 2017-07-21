package com.game.string.blooddonararchive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class LandingPageFragment extends Fragment{

    public static LandingPageFragment getInstance() {
        return new LandingPageFragment();
    }

    private ImageView mPropics;
    private TextView mName;
    private TextView mBloodGroup;
    private Switch mStatusSwith;

    private ListView mListView;
    private LinkedList<BloodToken> mTokenList;

    private DatabaseReference mRootDB;
    private FirebaseUser mCurrentUser;

    private String mUserId;
    private User mUser;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_landing_page, container, false);

        mPropics = (ImageView) rootView.findViewById(R.id.landing_page_profile_image);
        mName = (TextView) rootView.findViewById(R.id.landing_page_profile_name);
        mBloodGroup = (TextView) rootView.findViewById(R.id.landing_page_profile_blood_group);
        mStatusSwith = (Switch) rootView.findViewById(R.id.landing_page_profile_status_switch);
        mListView = (ListView) rootView.findViewById(R.id.request_token_list);

        mStatusSwith.setChecked(true);
        mTokenList = new LinkedList<>();

        RequestListAdapter adapter = new RequestListAdapter(getContext(), R.layout.row_request_token, mTokenList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(), "selected : "+ mTokenList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (mCurrentUser != null) {
            mUserId = mCurrentUser.getUid();

            mRootDB = FirebaseDatabase.getInstance().getReference().child("users").child(mUserId).child("info");

            mRootDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mUser = dataSnapshot.getValue(User.class);
                    setUserData(mUser);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            addData();

        }

        return rootView;
    }

    private void setUserData(User user) {
        if (user != null) {
            mName.setText(user.getName());
            mBloodGroup.setText("Blood Group : " + user.getBloodGroup());
        }

    }


    private void addData() {
        DatabaseReference tokenReference = FirebaseDatabase.getInstance().getReference().child("tokens");
        mTokenList = new LinkedList<>();
        tokenReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BloodToken tk = snapshot.getValue(BloodToken.class);

                    if (!tk.getOwnerId().equals(mCurrentUser.getUid()) && tk.getBlood().equals(mUser.getBloodGroup())) {
                        mTokenList.push(tk);
                    }
                }

                RequestListAdapter adapter = new RequestListAdapter(getContext(), R.layout.row_request_token, mTokenList);
                mListView.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

