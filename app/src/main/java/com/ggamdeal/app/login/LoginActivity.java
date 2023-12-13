package com.ggamdeal.app.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ggamdeal.app.R;
import com.ggamdeal.app.home.HomeActivity;
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

        initFirebaseAuth();

        emailInputField = findViewById(R.id.emailInputField);
        passwordInputField = findViewById(R.id.passwordInputField);
        emailErrorMessage = findViewById(R.id.emailErrorMessage);
        emailLoginButton = findViewById(R.id.emailLoginButton);
        emailSignUpButton = findViewById(R.id.emailSignUpButton);

        checkEmailFormat();

        emailLoginButton.setOnClickListener(v -> {
            String email = emailInputField.getText().toString();
            String password = passwordInputField.getText().toString();
            if(!email.isEmpty() && !password.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailLogin(email, password);
            }
            else{
                showAlertDialog("이메일 또는 비밀번호를 정확히 입력해주세요");
            }
        });

        emailSignUpButton.setOnClickListener(v -> {
            String email = emailInputField.getText().toString();
            String password = passwordInputField.getText().toString();
            if(!email.isEmpty() && !password.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailSignUp(email, password);
            }
            else{
                showAlertDialog("이메일 또는 비밀번호를 정확히 입력해주세요");
            }
        });

        TextView button = findViewById(R.id.findPasswordTextButton);
        button.setOnClickListener(v -> {
            Intent findpassword = new Intent(getApplicationContext(), FindPasswordActivity.class);
            startActivity(findpassword);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //로그인 된 상태면, Intent를 통해 LoginComplete.class로 넘어감
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void emailSignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        showAlertDialog("회원가입에 성공하였습니다.");
                    } else {
                        Log.d(TAG, "createUserWithEmail:failure");
                        Toast.makeText(getApplicationContext(), "서버 오류로 인해 회원가입을 실패하였습니다. 잠시 후 다시 시도해주세요",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void emailLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.d(TAG, "signInWithEmail:failure");
                        showAlertDialog("가입된 이메일이 아니거나, 비밀번호가 일치하지 않습니다.");
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
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
                    emailErrorMessage.setText("이메일 형식으로 입력해주세요.");
                    emailInputField.setBackgroundResource(R.drawable.red_edittext);
                } else {
                    emailErrorMessage.setText("");
                    emailInputField.setBackgroundResource(R.drawable.white_edittext);
                }
            }
        });
    }

    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage(message)
                .setPositiveButton("확인", (dialog, which) -> dialog.dismiss())
                .show();
    }
}