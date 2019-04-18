package com.example.demo2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

public class RecipePageActivity extends BasicActivity {
    private ImageView[] mImageViews;
    private TextView[] mTextViews;
    private Intent mOnePageRecipeIntent;
    private int mPageLinkCounter = 0;
    private static int smSIZE = 4; //TODO don't limit to only four

    private String mRecipeName;
    private String mIngredientList;
    private String mStep;
    private String mImageURL;

    // Database variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Get information from other page
        Intent intent = getIntent();
        final String[] ingredients = intent.getStringArrayExtra("Ingredients");
        final boolean category = intent.getExtras().getBoolean("Category");
        final String user = intent.getStringExtra("User");

        // Assign some constants
        final String IMAGE_VIEW_NAME = "foodImageView";
        final String TEXT_VIEW_NAME = "foodTextView";

        // Create arrays to store information
        mImageViews = new ImageView[smSIZE];
        mTextViews = new TextView[smSIZE];

        // Assign values to arrays
        for (int nIndex = 0; nIndex < smSIZE; nIndex++)
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
        if (user.equals("null"))
        {
            mDatabaseReference = mFirebaseDatabase.getReference("recipes");
        }
        else
        {
            mDatabaseReference = mFirebaseDatabase.getReference("recipes").child(user);
        }

        mDatabaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (user.equals("null"))
                {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        searchDatabase(snapshot, category, ingredients);
                    }
                }

                else
                {
                    searchDatabase(dataSnapshot, category, ingredients);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.d("DatabaseError", databaseError.getDetails());
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

    public void searchDatabase(DataSnapshot snapshot, boolean category, String[] ingredients)
    {
        for (DataSnapshot recipeID : snapshot.getChildren())
        {

            mRecipeName = recipeID.getKey();

            for (final DataSnapshot attributes : recipeID.getChildren())
            {

                if (attributes.child("category").getValue().equals(category))
                {

                    mIngredientList = attributes.child("ingredients").getValue().toString();

                    // If all three ingredients are in the ingredient list
                    if (mIngredientList.contains(ingredients[0]) && mIngredientList.contains(ingredients[1]) && mIngredientList.contains(ingredients[2]))
                    {

                        Picasso.get().load(attributes.child("imageURL").getValue().toString()).fit().centerCrop().into(mImageViews[mPageLinkCounter]);
                        mTextViews[mPageLinkCounter].setText(mRecipeName);

                        mStep = attributes.child("steps").getValue().toString();
                        mImageURL = attributes.child("imageURL").getValue().toString();

                        mPageLinkCounter++;
                    }
                }
            }
        }
    }

    public void onImageOrTextClick(View view)
    {
        mOnePageRecipeIntent = new Intent(RecipePageActivity.this, OneRecipePage.class);
        mOnePageRecipeIntent.putExtra("recipeName", mRecipeName);
        mOnePageRecipeIntent.putExtra("ingredients", mIngredientList);
        mOnePageRecipeIntent.putExtra("steps", mStep);
        mOnePageRecipeIntent.putExtra("imageURL", mImageURL);
        startActivity(mOnePageRecipeIntent);
    }
}
