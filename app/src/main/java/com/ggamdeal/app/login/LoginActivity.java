package com.ggamdeal.app.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ggamdeal.app.R;
import com.ggamdeal.app.welcome.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    EditText emailInputField;
    EditText passwordInputField;
    TextView emailErrorMessage;
    AppCompatButton emailSignUpButton;
    AppCompatButton emailLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        // Initialize Firebase Auth
        initFirebaseAuth();

        emailInputField = findViewById(R.id.emailInputField);
        passwordInputField = findViewById(R.id.passwordInputField);
        emailErrorMessage = findViewById(R.id.emailErrorMessage);

        checkEmailFormat();

        emailLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInputField.getText().toString();
                String password = passwordInputField.getText().toString();

                signUp(email, password);
            }
        });

        emailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInputField.getText().toString();
                String password = passwordInputField.getText().toString();

                signIn(email, password);
            }
        });

        //Welcomepage로 돌아가기 위해 비밀번호 변경 버튼을 따로 설정해줌 (이후 삭제 예정)
        TextView button = findViewById(R.id.findPasswordTextButton);
        button.setOnClickListener(v -> {
            Intent returnToWelcomeIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(returnToWelcomeIntent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //로그인 된 상태면, Intent를 통해 LoginComplete.class로 넘어감
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    private void initFirebaseAuth() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Log.d(TAG, "createUserWithEmail:failure");
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Log.d(TAG, "signInWithEmail:failure");
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, LoginComplete.class);
            intent.putExtra("USER_PROFILE", "email: " + user.getEmail() + "\n" + "uid: " + user.getUid());

            startActivity(intent);
        }
    }

    void checkEmailFormat() {
        emailInputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    emailErrorMessage.setText("이메일 형식으로 입력해주세요.");    // 경고 메세지
                    emailInputField.setBackgroundResource(R.drawable.red_edittext);  // 적색 테두리 적용
                } else {
                    emailErrorMessage.setText("");         //에러 메세지 제거
                    emailInputField.setBackgroundResource(R.drawable.white_edittext);  //테투리 흰색으로 변경
                }
            }// afterTextChanged()..
        });
    }
}