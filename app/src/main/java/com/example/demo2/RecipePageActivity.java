package com.example.demo2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class RecipePageActivity extends AppCompatActivity {
    private ImageView[] imageViews;
    private TextView[] textViews;
    private Intent OnePageRecipeIntent;
    private int pageLinkCounter = 0;
    private static int SIZE = 4;

    // Database variables
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        // Get information from other page
        Intent intent = getIntent();
        final String[] ingredients = intent.getStringArrayExtra("Ingredients");
        Log.d("Debug", Arrays.toString(ingredients));

        // Assign some constants
        final String IMAGE_VIEW_NAME = "foodImageView";
        final String TEXT_VIEW_NAME = "foodTextView";

        // Create arrays to store information
        imageViews = new ImageView[SIZE];
        textViews = new TextView[SIZE];

        // Assign values to arrays
        for (int nIndex = 0; nIndex < SIZE; nIndex++)
        {
            int nResID = getResources().getIdentifier(IMAGE_VIEW_NAME +
                    Integer.toString(nIndex), "id", getPackageName());
            imageViews[nIndex] = findViewById(nResID);
            nResID = getResources().getIdentifier(TEXT_VIEW_NAME +
                    Integer.toString(nIndex), "id", getPackageName());
            textViews[nIndex] = findViewById(nResID);
        }

        // Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("recipes");

        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // All Users
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    // All Recipe IDs
                    for (DataSnapshot recipeID : snapshot.getChildren())
                    {
                        // All Attributes
                        String recipeName = recipeID.getKey();
                        for (DataSnapshot attributes : recipeID.getChildren())
                        {
                            // If it's in the Food Category
                            if (attributes.child("category").getValue().equals("true"))
                            {
                                final String ingredientList = attributes.child("ingredients").getValue().toString();

                                // If all three ingredients are in the ingredient list
                                if (ingredientList.contains(ingredients[0]) && ingredientList.contains(ingredients[1]) && ingredientList.contains(ingredients[2]))
                                {
                                    textViews[pageLinkCounter].setText(attributes.child("ingredients").getValue().toString());
                                    Picasso.get().load(attributes.child("imageURL").getValue().toString()).into(imageViews[pageLinkCounter]);

                                    // Save this information and pass it to OneRecipePage
                                    OnePageRecipeIntent = new Intent(RecipePageActivity.this, OneRecipePage.class);
                                    OnePageRecipeIntent.putExtra("recipeName", recipeName);
                                    OnePageRecipeIntent.putExtra("ingredients", attributes.child("ingredients").getValue().toString());
                                    OnePageRecipeIntent.putExtra("steps", attributes.child("steps").getValue().toString());
                                    OnePageRecipeIntent.putExtra("imageURL", attributes.child("imageURL").getValue().toString());

                                    // Debug message, erase after
                                    Log.d("Debug", recipeName);
                                    Log.d("Debug", attributes.child("ingredients").getValue().toString());
                                    Log.d("Debug", attributes.child("steps").getValue().toString());
                                    Log.d("Debug", attributes.child("imageURL").getValue().toString());

                                    // Click on either ImageView or TextView to go to OneRecipePage
                                    imageViews[pageLinkCounter].setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            startActivity(OnePageRecipeIntent);
                                        }
                                    });
                                    textViews[pageLinkCounter].setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            startActivity(OnePageRecipeIntent);
                                        }
                                    });

                                    pageLinkCounter++;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }

    public void onButtonClick(View view)
    {
        Intent intent;
        switch(view.getId())
        {
            case R.id.backButton:
                intent = new Intent(RecipePageActivity.this, SearchPageActivity.class);
                startActivity(intent);
                break;
            case R.id.logoutButton:
                intent = new Intent(RecipePageActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.addRecipeButton:
                intent = new Intent(RecipePageActivity.this, AddNewRecipeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
