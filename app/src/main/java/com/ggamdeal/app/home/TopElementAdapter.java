package com.ggamdeal.app.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ggamdeal.app.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopElementAdapter extends RecyclerView.Adapter<TopElementAdapter.ViewHolder> {

    private static final String TAG = "FirebaseInfo";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context mContext;

    private List<GameInfo> gameInfoList = new ArrayList<>();

    public TopElementAdapter(Context context) {
        mContext = context;
        getDataFromFirestore();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameInfo gameInfo = gameInfoList.get(position);

        Glide.with(mContext)
                .load(gameInfo.getImgUrl())
                .into(holder.imageView);

        holder.titleView.setText(gameInfo.getTitle());
        holder.discountRateView.setText(gameInfo.getDiscountRate());
    }

    @Override
    public int getItemCount() {
        return gameInfoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView discountRateView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.topImageView);
            titleView = itemView.findViewById(R.id.topImageTitle);
            discountRateView = itemView.findViewById(R.id.topImageDiscountRate);
        }
    }

    private void getDataFromFirestore() {
        db.collection("steam")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // 'img' 필드에서 이미지 URL 가져오기
                            String imageUrl = document.getString("img");
                            String titleInfo = document.getString("title");
                            String discountRateInfo = document.getString("discount_pct");
                            GameInfo gameInfo = new GameInfo(imageUrl, titleInfo, discountRateInfo + " 할인 중");
                            gameInfoList.add(gameInfo);
                        }
                        notifyDataSetChanged(); // 데이터가 변경되었음을 어댑터에 알림
                    } else {
                        // 오류 처리
                        // task.getException()를 통해 오류에 대한 자세한 정보를 얻을 수 있습니다.
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}