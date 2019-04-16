package com.example.demo2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class AddRecipeController
{
    private String strRecipeName;
    private String strRecipeDirections;
    private boolean isFoodSeleted;
    private final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    private EditText mRecipeName;

    private EditText mRecipeDirections;

    private RadioButton mFoodBtn;
    private boolean isFoodSelected;
    boolean isPushSuccessful;

    private LinearLayout mLinearLayout;

    public AddRecipeController()
    {

    }

    public AddRecipeController(LinearLayout linearLayout, EditText rName, EditText rDirections, RadioButton foodBtn)
    {
        this.mLinearLayout = linearLayout;
        this.mRecipeName = rName;
        this.mRecipeDirections = rDirections;
        this.mFoodBtn = foodBtn;
    }

    public void addNewEditTextField(final Context context)
    {
        final Thread extraThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    final View rowView = inflater.inflate(R.layout.newfield, null);

                    assert mLinearLayout != null;
                    mLinearLayout.post(new Runnable() {
                        @Override
                        public void run() {

                            mLinearLayout.addView(rowView, mLinearLayout.getChildCount());
                        }
                    });
                }
                catch(NullPointerException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        extraThread.start();
    }

    public ArrayList<String> getAllIngredients()
    {
        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i < mLinearLayout.getChildCount(); i++)
        {
            if(mLinearLayout.getChildAt(i) instanceof LinearLayout)
            {
                LinearLayout l = (LinearLayout) mLinearLayout.getChildAt(i);
                EditText et = (EditText) l.getChildAt(0);

                if(et.getText().toString() != null && !et.getText().toString().isEmpty() &&
                        et.getText().toString().trim() != "")
                {
                    list.add(et.getText().toString().toLowerCase());
                }
            }
        }

        return list;
    }

    public void resetEditTextFields()
    {
        mRecipeName.setText("");
        mRecipeDirections.setText("");

        for(int i = 0; i < mLinearLayout.getChildCount(); i++)
        {
            if(mLinearLayout.getChildAt(i) instanceof LinearLayout)
            {
                LinearLayout l = (LinearLayout) mLinearLayout.getChildAt(i);
                EditText et = (EditText) l.getChildAt(0);

                et.setText("");
            }
        }
    }

    // Retrieves text values entered by the user for recipe name
    public void setRecipeName(EditText recipeName)
    {
        this.strRecipeName = !recipeName.getText().toString().isEmpty() ? recipeName.getText().toString() : null;
    }

    public String getRecipeName() { return this.strRecipeName; }

    // Retrieves text values entered by the user for recipe directions
    public void setRecipeDirections(EditText recipeDirections)
    {
        this.strRecipeDirections = !recipeDirections.getText().toString().isEmpty() ? recipeDirections.getText().toString() : null;
    }

    public String getRecipeDirections() { return this.strRecipeDirections; }

    public void setIsFoodSelected(RadioButton rButton)
    {
        this.isFoodSeleted = rButton.isChecked() ? true : false;
    }

    public Boolean getIsFoodSelected() { return this.isFoodSelected; }

    public boolean DbPush()
    {
        setRecipeName(mRecipeName);
        setRecipeDirections(mRecipeDirections);
        setIsFoodSelected(mFoodBtn);

        ArrayList<String> ingredientsList;
        ingredientsList = getAllIngredients();

        if(getRecipeName() != null && getRecipeDirections() != null
                && ingredientsList.size() > 0)
        {
            // Sets path
            DatabaseReference mDataBaseRef = mDatabase.getReference("recipes/" + "userID_Here");

            // Generate dynamic recipe ID
            String key = mDataBaseRef.push().getKey();
            // Sets data to the database model
            DBRecipesModel post = new DBRecipesModel(getIsFoodSelected(), "imgURLPlaceholder",
                    getRecipeDirections(), ingredientsList);

            Map<String, Object> postValues = post.toRecipeMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/" + getRecipeName() + "/" + key, postValues);

            mDataBaseRef.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                    if(databaseError == null)
                    {
                        /*
                        Toast toast = Toast.makeText(AddNewRecipeActivity.this, "Recipe Has Been Saved", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        */
                        isPushSuccessful = true;

                        resetEditTextFields();
                    }
                    else
                    {
                        /*
                        Toast toast = Toast.makeText(AddNewRecipeActivity.this, "There was a problem connecting to the Database", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        */
                        isPushSuccessful = false;
                    }
                }
            });
        }
        else
        {
            /*
            Toast toast = Toast.makeText(AddNewRecipeActivity.this, "Recipe Name and Direction can't be empty \n" +
                    "You must enter at least 1 ingredient", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            */
        }

        return isPushSuccessful;
    }
}
