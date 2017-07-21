package com.game.string.blooddonararchive;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class RequestListAdapter extends ArrayAdapter {

    private Context mContex;
    private int mResource;
    private LinkedList<BloodToken> mTokenList;

    private TextView mTitle;
    private TextView mUrgency;
    private TextView mOwnerName;
    private TextView mCreatingDate;

    public RequestListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull LinkedList<BloodToken> tokenList) {
        super(context, resource, tokenList);

        this.mContex = context;
        this.mResource = resource;
        this.mTokenList = tokenList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(mResource, parent, false);

        mTitle = (TextView) rootView.findViewById(R.id.request_row_title_text);
        mUrgency = (TextView) rootView.findViewById(R.id.request_row_urgency_text);
        mOwnerName = (TextView) rootView.findViewById(R.id.request_row_creator_text);
        mCreatingDate = (TextView) rootView.findViewById(R.id.request_row_date_text);

        updateData(position);

        return rootView;
    }

    private void updateData(int position) {

        mTitle.setText(mTokenList.get(position).getTitle());
        mUrgency.setText(mTokenList.get(position).getUrgency());
        getTokenOwnerName(position);
        mCreatingDate.setText(DateHelper.getDateString(mTokenList.get(position).getCreatingTime()));

    }

    private void getTokenOwnerName(int position) {


        String ownerId = mTokenList.get(position).getOwnerId();

        DatabaseReference ownerDB = FirebaseDatabase.getInstance().getReference().child("users").child(ownerId).child("info");
        ownerDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mOwnerName.setText("Created By " + user.getName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

