package com.ggamdeal.app.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ggamdeal.app.R;
import com.ggamdeal.app.home.GameInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopElementAdapter extends RecyclerView.Adapter<TopElementAdapter.ViewHolder> {

    private static final String TAG = "FirebaseInfo";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Fragment fragment;
    private List<GameInfo> gameInfoList = new ArrayList<>();

    public TopElementAdapter(Fragment fragment) {
        this.fragment = fragment;
        getActionDataFromFirestore();
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

        Glide.with(fragment)
                .load(gameInfo.getImageUrl())
                .into(holder.imageView);

        holder.titleView.setText(gameInfo.getTitle());
        holder.discountRateView.setText(gameInfo.getDiscountRate());
        holder.setGameUrl(gameInfo.getGameLink());
    }

    @Override
    public int getItemCount() {
        return gameInfoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private String gameUrl;
        ImageView imageView;
        TextView titleView;
        TextView discountRateView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.topImageView);
            titleView = itemView.findViewById(R.id.topImageTitle);
            discountRateView = itemView.findViewById(R.id.topImageDiscountRate);
            itemView.setOnClickListener(this);
        }

        public void setGameUrl(String gameUrl) {
            this.gameUrl = gameUrl;
        }

        @Override
        public void onClick(View v) {
            Log.d("Cardview_Event", "Cardview 클릭");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(gameUrl));
            itemView.getContext().startActivity(intent);
        }
    }

    private void getActionDataFromFirestore() {
        CollectionReference colref = db.collection("Game").document("Steam").collection("Action");
        colref.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String discountPrice = document.getString("discountPrice");
                        String discountRate = document.getString("discountRate");
                        String originalPrice = document.getString("originalPrice");
                        String imageUrl = document.getString("img");
                        String gameLink = document.getString("gameLink");
                        String titleInfo = document.getString("title");
                        GameInfo gameInfo = new GameInfo(imageUrl, originalPrice, gameLink, discountPrice, titleInfo, discountRate + " 할인 중");
                        gameInfoList.add(gameInfo);
                    }
                    notifyDataSetChanged();
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}