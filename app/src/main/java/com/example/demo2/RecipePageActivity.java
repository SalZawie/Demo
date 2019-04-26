package com.example.demo2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipePageActivity extends BasicActivity
{
    private ArrayList<ImageView> mImageViews;
    private ArrayList<TextView> mTextViews;
    private ArrayList<LinearLayout> mLinearLayouts;
    private ArrayList<String> mRecipeName;
    private ArrayList<String> mIngredientList;
    private ArrayList<String> mStep;
    private ArrayList<String> mImageURL;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    private int mPageLinkCounter;
    private boolean mHasResults;

    private static String smPICTURE_NOT_AVAILABLE = "https://www.themezzaninegroup.com/wp-content/uploads/2017/12/photo-not-available.jpg";
    private static int smSIZE = 4; //TODO don't limit to only four

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialize variables
        mImageViews = new ArrayList<>();
        mTextViews = new ArrayList<>();
        mLinearLayouts = new ArrayList<>();
        mRecipeName = new ArrayList<>();
        mIngredientList = new ArrayList<>();
        mStep = new ArrayList<>();
        mImageURL = new ArrayList<>();
        mPageLinkCounter = 0;
        mHasResults = false;

        // Get information from other page
        Intent intent = getIntent();
        final String[] ingredients = intent.getStringArrayExtra("Ingredients");
        final boolean category = intent.getExtras().getBoolean("Category");
        final String user = intent.getStringExtra("User");

        // Assign some constants
        final String IMAGE_VIEW_NAME = "foodImageView";
        final String TEXT_VIEW_NAME = "foodTextView";
        final String LINEAR_LAYOUT_NAME = "linearLayout";

        // Assign values to arrays
        for (int nIndex = 0; nIndex < smSIZE; nIndex++)
        {
            int nResID = getResources().getIdentifier(IMAGE_VIEW_NAME + nIndex, "id", getPackageName());
            mImageViews.add((ImageView)findViewById(nResID));
            nResID = getResources().getIdentifier(TEXT_VIEW_NAME + nIndex, "id", getPackageName());
            mTextViews.add((TextView)findViewById(nResID));
            nResID = getResources().getIdentifier(LINEAR_LAYOUT_NAME + nIndex, "id", getPackageName());
            mLinearLayouts.add((LinearLayout)findViewById(nResID));
        }

        // Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

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
                if (mPageLinkCounter < smSIZE)
                {
                    if (user.equals("null"))
                    {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            searchDatabase(snapshot, category, ingredients);
                            if (mPageLinkCounter >= smSIZE)
                            {
                                break;
                            }
                        }
                        hasResultsMessage();
                    }
                    else
                    {
                        searchDatabase(dataSnapshot, category, ingredients);
                        hasResultsMessage();
                    }
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
                finish();
                break;
            case R.id.logoutButton:
                mFirebaseAuth.signOut();
                Toast.makeText(RecipePageActivity.this, "You are logged out", Toast.LENGTH_SHORT).show();
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
        Search:
        for (DataSnapshot recipeID : snapshot.getChildren())
        {
            String recipeName = recipeID.getKey();

            for (final DataSnapshot attributes : recipeID.getChildren())
            {

                if (attributes.child("category").getValue().equals(category))
                {
                    String ingredientList = attributes.child("ingredients").getValue().toString();

                    // If all three ingredients are in the ingredient list
                    if (ingredientList.contains(ingredients[0]) && ingredientList.contains(ingredients[1]) && ingredientList.contains(ingredients[2]))
                    {
                        mRecipeName.add(recipeName);
                        mIngredientList.add(ingredientList);

                        try
                        {
                            Picasso.get().load(attributes.child("imageURL").getValue().toString()).fit().centerCrop().into(mImageViews.get(mPageLinkCounter));
                            mImageURL.add(attributes.child("imageURL").getValue().toString());
                        }
                        catch (Exception e)
                        {
                            Picasso.get().load(smPICTURE_NOT_AVAILABLE).fit().centerCrop().into(mImageViews.get(mPageLinkCounter));
                            mImageURL.add(smPICTURE_NOT_AVAILABLE);
                        }

                        mTextViews.get(mPageLinkCounter).setText(mRecipeName.get(mPageLinkCounter));
                        mStep.add(attributes.child("steps").getValue().toString());

                        clickToGoToOnePageRecipe(mPageLinkCounter);

                        mPageLinkCounter++;

                        if (mPageLinkCounter >= smSIZE)
                        {
                            break Search;
                        }

                        mHasResults = true;

                    }
                }
            }
        }
    }

    public void clickToGoToOnePageRecipe(final int counter)
    {
        mLinearLayouts.get(counter).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent onePageRecipeIntent = new Intent(RecipePageActivity.this, OneRecipePage.class);

                onePageRecipeIntent.putExtra("recipeName", mRecipeName.get(counter));
                onePageRecipeIntent.putExtra("ingredients", mIngredientList.get(counter));
                onePageRecipeIntent.putExtra("steps", mStep.get(counter));
                onePageRecipeIntent.putExtra("imageURL", mImageURL.get(counter));

                startActivity(onePageRecipeIntent);
            }
        });
    }

    public void hasResultsMessage()
    {
        if (!mHasResults)
        {
            Toast.makeText(RecipePageActivity.this, "No results, try searching other ingredients!", Toast.LENGTH_LONG).show();
        }
    }

}
