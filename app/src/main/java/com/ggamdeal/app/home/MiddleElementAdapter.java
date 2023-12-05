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

public class MiddleElementAdapter extends RecyclerView.Adapter<MiddleElementAdapter.ViewHolder> {

    private String TAG = "FirebaseInfo";
    private FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();
    private Context mContext;
    private List<GameInfo> gameActionInfoList = new ArrayList<>();

    public MiddleElementAdapter(Context context) {
        mContext = context;
        getDataFromFirestore();
    }

    @NonNull
    @Override
    public MiddleElementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homepage_middlecardview, parent, false);
        return new MiddleElementAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MiddleElementAdapter.ViewHolder holder, int position) {
        GameInfo gameInfo = gameActionInfoList.get(position);

        Glide.with(mContext)
                .load(gameInfo.getImgUrl())
                .into(holder.imageView);

        holder.titleTextView.setText(gameInfo.getTitle());
        holder.discountRateView.setText(gameInfo.getDiscountRate());
        holder.originalPriceView.setText(gameInfo.getOriginalPrice());
        holder.discountPriceView.setText(gameInfo.getDiscountPrice());
    }

    @Override
    public int getItemCount() {
        return gameActionInfoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView discountRateView;
        TextView originalPriceView;
        TextView discountPriceView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_image);
            titleTextView = itemView.findViewById(R.id.card_title);
            originalPriceView = itemView.findViewById(R.id.card_original_price);
            discountPriceView = itemView.findViewById(R.id.card_discount_price);
            discountRateView = itemView.findViewById(R.id.card_discount_rate);
        }
    }

    private void getDataFromFirestore() {
        db.collection("steam_action")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // 'img' 필드에서 이미지 URL 가져오기
                            String imageUrl = document.getString("img");
                            String titleInfo = document.getString("title");
                            String link = document.getString("link");
                            String originalPrice = document.getString("original_price");
                            String discountPrice = document.getString("discount_price");
                            String discountRateInfo = document.getString("discount_pct");
                            GameInfo gameInfo = new GameInfo(imageUrl, titleInfo, link, discountPrice, originalPrice, discountRateInfo + " 할인");
                            gameActionInfoList.add(gameInfo);
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
