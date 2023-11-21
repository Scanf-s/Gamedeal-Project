package com.ggamdeal.app.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ggamdeal.app.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginComplete extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logincomplete);

        TextView textView = findViewById(R.id.textview);

        Intent intent = getIntent();
        String userProfile = intent.getStringExtra("USER_PROFILE");
        textView.setText(userProfile);

        Button signOutButton = findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                finish();
            }
        });
    }

    private void signOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        // Check if there is no current user.
        if (firebaseAuth.getCurrentUser() == null)
            Log.d(TAG, "signOut:success");
        else
            Log.d(TAG, "signOut:failure");
    }
}
