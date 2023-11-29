package com.ggamdeal.app.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ggamdeal.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {

    private static final String TAG = "EmailPassword";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context mContext;

    private List<String> mImageUrls = new ArrayList<>(); // 이미지 URL을 담을 리스트

    private int[] mImages = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3
    };

    public ImagePagerAdapter(Context context) {

        mContext = context;
        getDataFromFirestore();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mImageUrls.get(position))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.topImageView);
        }
    }

    private void getDataFromFirestore() {
        // 'steam' 컬렉션에서 데이터 가져오기
        db.collection("steam")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // 'img' 필드에서 이미지 URL 가져오기
                                String imageUrl = document.getString("img");
                                mImageUrls.add(imageUrl);
                            }
                            notifyDataSetChanged(); // 데이터가 변경되었음을 어댑터에 알림
                        } else {
                            // 오류 처리
                            // task.getException()를 통해 오류에 대한 자세한 정보를 얻을 수 있습니다.
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
