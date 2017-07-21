package com.game.string.blooddonararchive;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class FacebookFriendListAdapter extends ArrayAdapter {

    public FacebookFriendListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }
}
