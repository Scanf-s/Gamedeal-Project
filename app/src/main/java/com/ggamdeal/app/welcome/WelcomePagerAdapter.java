package com.ggamdeal.app.welcome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ggamdeal.app.R;

import java.util.List;

public class WelcomePagerAdapter extends RecyclerView.Adapter<WelcomePagerAdapter.WelcomeViewHolder> {

    private List<String> pageTexts;

    public WelcomePagerAdapter(List<String> pageTexts) {
        this.pageTexts = pageTexts;
    }

    @NonNull
    @Override
    public WelcomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.welcome_page_layout, parent, false);
        return new WelcomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomeViewHolder holder, int position) {
        holder.bind(pageTexts.get(position));
    }

    @Override
    public int getItemCount() {
        return pageTexts.size();
    }

    static class WelcomeViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public WelcomeViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.welcomePageTextContents);
        }

        public void bind(String text) {
            textView.setText(text);
        }
    }
}
