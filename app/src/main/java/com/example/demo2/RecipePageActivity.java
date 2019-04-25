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

public class RecipePageActivity extends BasicActivity
{
    private ImageView[] mImageViews;
    private TextView[] mTextViews;
    private LinearLayout[] mLinearLayouts;
    private String[] mRecipeName;
    private String[] mIngredientList;
    private String[] mStep;
    private String[] mImageURL;

    private int mPageLinkCounter = 0;
    private boolean mHasResults = false;

    private static String smPICTURE_NOT_AVAILABLE = "https://www.themezzaninegroup.com/wp-content/uploads/2017/12/photo-not-available.jpg";
    private static int smSIZE = 4; //TODO don't limit to only four

    // Database variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

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
        final String LINEAR_LAYOUT_NAME = "linearLayout";

        // Create arrays to store information
        mImageViews = new ImageView[smSIZE];
        mTextViews = new TextView[smSIZE];
        mLinearLayouts = new LinearLayout[smSIZE];

        mRecipeName = new String[smSIZE];
        mIngredientList = new String[smSIZE];
        mStep = new String[smSIZE];
        mImageURL = new String[smSIZE];

        // Assign values to arrays
        for (int nIndex = 0; nIndex < smSIZE; nIndex++)
        {
            int nResID = getResources().getIdentifier(IMAGE_VIEW_NAME + Integer.toString(nIndex), "id", getPackageName());
            mImageViews[nIndex] = findViewById(nResID);
            nResID = getResources().getIdentifier(TEXT_VIEW_NAME + Integer.toString(nIndex), "id", getPackageName());
            mTextViews[nIndex] = findViewById(nResID);
            nResID = getResources().getIdentifier(LINEAR_LAYOUT_NAME + Integer.toString(nIndex), "id", getPackageName());
            mLinearLayouts[nIndex] = findViewById(nResID);
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
                finish();
                Toast.makeText(RecipePageActivity.this, "Log out completed.", Toast.LENGTH_SHORT).show();
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
                        mRecipeName[mPageLinkCounter] = recipeName;
                        mIngredientList[mPageLinkCounter] = ingredientList;

                        try
                        {
                            Picasso.get().load(attributes.child("imageURL").getValue().toString()).fit().centerCrop().into(mImageViews[mPageLinkCounter]);
                            mImageURL[mPageLinkCounter] = attributes.child("imageURL").getValue().toString();
                        }
                        catch (Exception e)
                        {
                            Picasso.get().load(smPICTURE_NOT_AVAILABLE).fit().centerCrop().into(mImageViews[mPageLinkCounter]);
                            mImageURL[mPageLinkCounter] = smPICTURE_NOT_AVAILABLE;
                        }

                        mTextViews[mPageLinkCounter].setText(mRecipeName[mPageLinkCounter]);
                        mStep[mPageLinkCounter] = attributes.child("steps").getValue().toString();

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
        mLinearLayouts[counter].setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent onePageRecipeIntent = new Intent(RecipePageActivity.this, OneRecipePage.class);
                onePageRecipeIntent.putExtra("recipeName", mRecipeName[counter]);
                onePageRecipeIntent.putExtra("ingredients", mIngredientList[counter]);
                onePageRecipeIntent.putExtra("steps", mStep[counter]);
                onePageRecipeIntent.putExtra("imageURL", mImageURL[counter]);

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
