package com.ggamdeal.app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ImageView nextImageButton;
    private TextView welcomePageTextContents;
    private int currentPage = 1; // Track the current page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);

        nextImageButton = findViewById(R.id.nextImageButton);
        welcomePageTextContents = findViewById(R.id.welcomepagetextcontents);

        nextImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the next button click
                changeTextForNextPage();
            }
        });
    }

    private void changeTextForNextPage() {
        String[] PAGE_TEXTS = {
                "123123123",
                "54456456",
                "12364456456"
                // Add more texts for additional pages
        };

        // Check if there are more pages
        if (currentPage < PAGE_TEXTS.length) {
            // Change the text based on the current page
            welcomePageTextContents.setText(PAGE_TEXTS[currentPage]);

            // Increment the current page
            currentPage++;
        } else {
            // Handle the case when there are no more pages
            // You may choose to navigate to the next activity or perform another action
            navigateToNextActivity();
        }
    }

    private void navigateToNextActivity() {
        // Implement the logic to navigate to the next activity
    }
}