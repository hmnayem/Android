package com.game.string.blooddonararchive;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference mRoot;

    private String uid;
    private FirebaseUser firebaseUser;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        Fragment currentFragment = null;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentFragment = LandingPageFragment.getInstance();
                    break;
                case R.id.navigation_dashboard:
                    currentFragment = ActiveTokenFragment.getInstance();
                    break;
                case R.id.navigation_notifications:
                    currentFragment = FriendListFragment.getInstance();
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.landing_page_container, currentFragment);
            transaction.commit();

            return true;
        }

    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
            finish();
        }
        else {
            firebaseUser = mAuth.getCurrentUser();
            uid = firebaseUser.getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            Log.d("Current UID", uid);

            databaseReference.child("info").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user1 = dataSnapshot.getValue(User.class);

                    if (user1 == null) {
                        startActivity(new Intent(MainActivity.this, UpdateActivity.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            mToolbar = (Toolbar) findViewById(R.id.top_toolbar);
            setSupportActionBar(mToolbar);
            mToolbar.setBackground(getDrawable(R.color.colorPrimaryDark));
            mToolbar.setTitleTextColor(Color.WHITE);

            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.landing_page_container, LandingPageFragment.getInstance());
            transaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.create_new_token:
                startActivity(new Intent(MainActivity.this, CreateTokenActivity.class));
                break;

            case R.id.update_profile:
                startActivity(new Intent(MainActivity.this, UpdateActivity.class));
                break;

            case R.id.logout:
                mAuth.signOut();
                Toast.makeText(this, "Successfully Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
                finish();
                break;
        }

        return true;
    }
}
