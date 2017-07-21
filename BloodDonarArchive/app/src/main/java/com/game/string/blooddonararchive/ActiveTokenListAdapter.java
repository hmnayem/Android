package com.game.string.blooddonararchive;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class ActiveTokenListAdapter extends ArrayAdapter {

    private Context mContext;
    private int mResource;
    private LinkedList<BloodToken> mTokenList;

    private TextView mTitle;
    private TextView mBloodGroup;
    private TextView mDate;

    public ActiveTokenListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull LinkedList<BloodToken> tokenList) {
        super(context, resource, tokenList);

        this.mContext = context;
        this.mResource = resource;
        this.mTokenList = tokenList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.row_active_token, parent, false);

        mTitle = (TextView) rootView.findViewById(R.id.active_row_title_text);
        mBloodGroup = (TextView) rootView.findViewById(R.id.active_row_blood_text);
        mDate = (TextView) rootView.findViewById(R.id.active_row_date_text);

        mTitle.setText(mTokenList.get(position).getTitle());
        mBloodGroup.setText(mTokenList.get(position).getBlood());

        String dates = DateHelper.getDateString((long) mTokenList.get(position).getCreatingTime());
        mDate.setText(dates);

        return rootView;
    }
}
