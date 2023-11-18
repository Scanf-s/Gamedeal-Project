package com.ggamdeal.app.welcome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ggamdeal.app.R;

public class WelcomePagerAdapter extends RecyclerView.Adapter<WelcomePagerAdapter.WelcomePageViewHolder> {

    private static final String[] PAGE_TEXTS = {
            "123123123",
            "54456456",
            "12364456456"
            // Add more texts for additional pages
    };

    @NonNull
    @Override
    public WelcomePageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.welcome_page_layout, parent, false);
        return new WelcomePageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomePageViewHolder holder, int position) {
        holder.bind(PAGE_TEXTS[position]);
    }

    @Override
    public int getItemCount() {
        return PAGE_TEXTS.length;
    }

    static class WelcomePageViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        WelcomePageViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.welcomepagetextcontents);
        }

        void bind(String text) {
            textView.setText(text);
        }
    }
}