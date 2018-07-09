package com.hannahpark.simplechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

public class ChatActivity extends AppCompatActivity {
    static final String TAG = ChatActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //User login
        if(ParseUser.getCurrentUser() != null) {    //start with existing user
            startWithCurrentUser();
        } else {    //if not logged in, login as new anonymous user
            login();
        }
    }

    //get userId from the cached currentUser object
    void startWithCurrentUser() {
        //TODO:
    }

    //Create an anonymous user using ParseAnonymousUtils and set UserId
    void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Anonymous login failed: ", e);
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }
}
