package com.hannahpark.simplechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ChatActivity extends AppCompatActivity {
    static final String TAG = ChatActivity.class.getSimpleName();
    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";

    EditText etMessage;
    Button btSend;

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
        setUpMessagePosting();
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

    //setup button event handler which posts the entered message to Parse
    void setUpMessagePosting () {
        //find the text field and button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        //when the send button is clicked, create the message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String data = etMessage.getText().toString();
                ParseObject message = ParseObject.create("Message");
                message.put(USER_ID_KEY, ParseUser.getCurrentUser().getObjectId());
                message.put(BODY_KEY, data);
                message.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if(e==null) {
                            Toast.makeText(ChatActivity.this, "Successfully created a message on Parse",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "Failed to save the message", e);
                        }
                    }
                });
                etMessage.setText(null);
            }
        });
    }
}
