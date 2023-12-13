package com.ggamdeal.app.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ggamdeal.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class FindPasswordActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailInput;
    Button sendMailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.emailEditText);
        sendMailButton = findViewById(R.id.resetPasswordButton);

        sendMailButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            if (!email.isEmpty()) {
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "비밀번호 재설정 이메일이 전송되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                showAlertDialog("비밀번호 재설정 이메일 전송을 실패하였습니다.\n이메일을 확인해주세요.");
                            }
                        });
            } else {
                showAlertDialog("이메일을 입력하세요");
            }
        });
    }

    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "비밀번호 재설정 이메일이 전송되었습니다.", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();

                        Intent newLoginActivity = new Intent(FindPasswordActivity.this, LoginActivity.class);
                        newLoginActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(newLoginActivity);
                        finish();
                    } else {
                        showAlertDialog("비밀번호 재설정 이메일 전송을 실패하였습니다.\n이메일을 확인해주세요.");
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