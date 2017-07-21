package com.game.string.blooddonararchive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class ActiveTokenFragment extends Fragment{

    private ListView mListView;
    private LinkedList<BloodToken> mTokenList;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    public static ActiveTokenFragment getInstance() {
        return new ActiveTokenFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_active_token, container, false);

        mListView = (ListView) rootView.findViewById(R.id.active_token_list);

        mTokenList = new LinkedList<>();

        RequestListAdapter adapter = new RequestListAdapter(getContext(), R.layout.row_request_token, mTokenList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(), "selected : "+ mTokenList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        addData();

        return rootView;

    }


    private void addData() {
        DatabaseReference tokenReference = FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid());
        Log.d("Current Ref", tokenReference.getKey());
        mTokenList = new LinkedList<>();
        tokenReference.child("activeToken").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.d("On Chiled Added", dataSnapshot.toString());

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BloodToken tk = snapshot.getValue(BloodToken.class);

                    if (!tk.getOwnerId().equals(mUser.getUid()) && tk.getBlood().equals(tk.getBlood())) {
                        mTokenList.push(tk);
                    }
                }

                ActiveTokenListAdapter adapters = new ActiveTokenListAdapter(getContext(), R.layout.row_active_token, mTokenList);
                mListView.setAdapter(adapters);

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


