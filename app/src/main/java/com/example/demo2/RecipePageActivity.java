package com.example.demo2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class RecipePageActivity extends AppCompatActivity {
    private ImageView[] mImageViews;
    private TextView[] mTextViews;
    private Intent mOnePageRecipeIntent;
    private int mPageLinkCounter = 0;
    private static int SIZE = 4; //TODO don't limit to only four

    // Database variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        // Get information from other page
        Intent intent = getIntent();
        final String[] ingredients = intent.getStringArrayExtra("Ingredients");
        final boolean category = intent.getExtras().getBoolean("Category");
        final String user = intent.getStringExtra("User");

        Log.d("Debug", Arrays.toString(ingredients));

        // Assign some constants
        final String IMAGE_VIEW_NAME = "foodImageView";
        final String TEXT_VIEW_NAME = "foodTextView";

        // Create arrays to store information
        mImageViews = new ImageView[SIZE];
        mTextViews = new TextView[SIZE];

        // Assign values to arrays
        for (int nIndex = 0; nIndex < SIZE; nIndex++)
        {
            int nResID = getResources().getIdentifier(IMAGE_VIEW_NAME +
                    Integer.toString(nIndex), "id", getPackageName());
            mImageViews[nIndex] = findViewById(nResID);
            nResID = getResources().getIdentifier(TEXT_VIEW_NAME +
                    Integer.toString(nIndex), "id", getPackageName());
            mTextViews[nIndex] = findViewById(nResID);
        }

        // Database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("recipes");

        mDatabaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    for (DataSnapshot recipeID : snapshot.getChildren())
                    {
                        // All Attributes
                        String recipeName = recipeID.getKey();
                        for (DataSnapshot attributes : recipeID.getChildren())
                        {
                            // If it's in the Food Category
                            if (attributes.child("category").getValue().equals(category))
                            {
                                // Switch this to the retrieving part
                                String ingredientList = attributes.child("ingredients").getValue().toString();
                                ingredientList = ingredientList.substring(1, ingredientList.length() - 1);
                                Log.d("Debug-List", ingredientList);

                                // If all three ingredients are in the ingredient list
                                if (ingredientList.contains(ingredients[0]) && ingredientList.contains(ingredients[1]) && ingredientList.contains(ingredients[2]))
                                {
                                    mTextViews[mPageLinkCounter].setText(ingredientList);
                                    Picasso.get().load(attributes.child("imageURL").getValue().toString()).fit().centerCrop().into(mImageViews[mPageLinkCounter]);

                                    // Save this information and pass it to OneRecipePage
                                    mOnePageRecipeIntent = new Intent(RecipePageActivity.this, OneRecipePage.class);
                                    mOnePageRecipeIntent.putExtra("recipeName", recipeName);
                                    mOnePageRecipeIntent.putExtra("ingredients", ingredientList);
                                    mOnePageRecipeIntent.putExtra("steps", attributes.child("steps").getValue().toString());
                                    mOnePageRecipeIntent.putExtra("imageURL", attributes.child("imageURL").getValue().toString());

                                    // Debug message, erase after
                                    Log.d("Debug", recipeName);
                                    Log.d("Debug", ingredientList);
                                    Log.d("Debug", attributes.child("steps").getValue().toString());
                                    Log.d("Debug", attributes.child("imageURL").getValue().toString());

                                    // Click on either ImageView or TextView to go to OneRecipePage
                                    mImageViews[mPageLinkCounter].setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            startActivity(mOnePageRecipeIntent);
                                        }
                                    });
                                    mTextViews[mPageLinkCounter].setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            startActivity(mOnePageRecipeIntent);
                                        }
                                    });

                                    mPageLinkCounter++;
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
