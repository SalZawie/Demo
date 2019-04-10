package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RecipePageActivity extends AppCompatActivity {
    private Button backButton;
    private Button logoutButton;
    private Button addRecipeButton;
    private ImageView[] imageViews;
    private TextView[] textViews;
    private static int SIZE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        // Get information from other page
        Intent intent = getIntent();
        final String[] ingredients = intent.getStringArrayExtra("Ingredients");
        final Set ingredientSet = new HashSet(Arrays.asList(ingredients));

        // Assign some constants
        final String IMAGE_VIEW_NAME = "foodImageView";
        final String TEXT_VIEW_NAME = "foodTextView";

        // Create arrays to store information
        imageViews = new ImageView[SIZE];
        textViews = new TextView[SIZE];

        // Assign values to arrays
        for (int nIndex = 0; nIndex < SIZE; nIndex++) {
            int nResID = getResources().getIdentifier(IMAGE_VIEW_NAME +
                    Integer.toString(nIndex), "id", getPackageName());
            imageViews[nIndex] = findViewById(nResID);
            nResID = getResources().getIdentifier(TEXT_VIEW_NAME +
                    Integer.toString(nIndex), "id", getPackageName());
            textViews[nIndex] = findViewById(nResID);
        }

        // Connect buttons to layout
        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);
        addRecipeButton = findViewById(R.id.addRecipeButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipePageActivity.this, SearchPageActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipePageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipePageActivity.this, AddNewRecipeActivity.class);
                startActivity(intent);
            }
        });
    }

}
