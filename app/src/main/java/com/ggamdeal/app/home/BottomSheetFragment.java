package com.ggamdeal.app.home;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.ggamdeal.app.R;
import com.ggamdeal.app.login.FindPasswordActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    int REQUEST_CODE_PICK_IMAGE = 1;
    ImageView profileImage;
    FirebaseUser currentUser;
    Button changeProfileButton;
    TextView emailView;
    TextView changePasswordButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImage = view.findViewById(R.id.profile_image);
        changePasswordButton = view.findViewById(R.id.change_password);
        emailView = view.findViewById(R.id.user_email);
        changeProfileButton = view.findViewById(R.id.change_profile_image);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            updateUserInfo(currentUser);
        }

        changeProfileButton.setOnClickListener(v -> {
            Intent getImagefromPhotos = new Intent(Intent.ACTION_PICK);
            getImagefromPhotos.setType("image/*");
            startActivityForResult(getImagefromPhotos, REQUEST_CODE_PICK_IMAGE);
        });

        changePasswordButton.setOnClickListener(v -> {
            Intent changePassword = new Intent(getActivity(), FindPasswordActivity.class);
            startActivity(changePassword);
            getActivity().finish();
        });
    }

    private void updateUserInfo(FirebaseUser user) {
        Glide.with(this).load(user.getPhotoUrl()).into(profileImage);
        emailView.setText(user.getEmail());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    profileImage.setImageURI(selectedImageUri);
                    uploadImageToFirebase(selectedImageUri);
                }
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference profileImagesRef = storageRef.child("profile_images/" + UUID.randomUUID().toString());

        profileImagesRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    profileImagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        updateUserProfile(uri);
                    });
                })
                .addOnFailureListener(e -> {
                    // 업로드 실패 처리
                    Toast.makeText(getActivity(), "프로필 이미지 업로드 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void updateUserProfile(Uri uri) {
        if (currentUser != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(uri)
                    .build();

            currentUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "프로필 이미지가 업데이트 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}