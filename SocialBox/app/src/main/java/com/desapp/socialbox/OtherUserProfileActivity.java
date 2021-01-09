package com.desapp.socialbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OtherUserProfileActivity extends AppCompatActivity {
    private ImageView friendPicture;
    private TextView friedName;
    private TextView friendStatus;
    private Button addFriendBtn;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        friendPicture = findViewById(R.id.friendProfilePic);
        friedName = findViewById(R.id.friendName);
        friendStatus = findViewById(R.id.friendStatus);
        addFriendBtn = findViewById(R.id.addFriendAction);
    }
}