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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/* Author: Dan St-Jean, dan.stj@outlook.com */

public class AddRecipeController
{
    private String strRecipeName;
    private String strRecipeDirections;
    private String strImageUrl;
    private boolean isFoodSelected;

    private String mUserUid;

    private final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private EditText mRecipeName;
    private EditText mRecipeDirections;
    private EditText mImageUrl;
    private RadioButton mFoodBtn;

    private int successfulPush;

    private LinearLayout mLinearLayout;

    public AddRecipeController()
    {

    }

    public AddRecipeController(LinearLayout linearLayout, EditText rName, EditText rDirections, EditText imageUrl, RadioButton foodBtn)
    {
        this.mLinearLayout = linearLayout;
        this.mRecipeName = rName;
        this.mRecipeDirections = rDirections;
        this.mImageUrl = imageUrl;
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

    private ArrayList<String> getAllIngredients()
    {
        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i < mLinearLayout.getChildCount(); i++)
        {
            if(mLinearLayout.getChildAt(i) instanceof LinearLayout)
            {
                LinearLayout l = (LinearLayout) mLinearLayout.getChildAt(i);
                EditText et = (EditText) l.getChildAt(0);

                if(!et.getText().toString().isEmpty() && !et.getText().toString().trim().equals(""))
                {
                    list.add(et.getText().toString().toLowerCase());
                }
            }
        }

        return list;
    }

    private void resetEditTextFields()
    {
        mRecipeName.setText("");
        mRecipeDirections.setText("");
        mImageUrl.setText("");

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
    private void setRecipeName(EditText recipeName)
    {
        this.strRecipeName = !recipeName.getText().toString().isEmpty() ? recipeName.getText().toString() : null;
    }

    private String getRecipeName() { return this.strRecipeName; }

    // Retrieves text values entered by the user for recipe directions
    private void setRecipeDirections(EditText recipeDirections)
    {
        this.strRecipeDirections = !recipeDirections.getText().toString().isEmpty() ? recipeDirections.getText().toString() : null;
    }

    private String getRecipeDirections() { return this.strRecipeDirections; }

    private void setIsFoodSelected(RadioButton rButton)
    {
        this.isFoodSelected = rButton.isChecked() ? true : false;
    }

    private Boolean getIsFoodSelected() { return this.isFoodSelected; }

    private void setImageUrl(EditText imageUrl) { this.strImageUrl = !imageUrl.getText().toString().isEmpty() ? imageUrl.getText().toString() : null; }

    private String getImageUrl() { return this.strImageUrl; }


    private String getUserUid()
    {
       this.mUserUid = currentUser.getUid();

       return mUserUid;
    }

    public int DbPush()
    {
        setRecipeName(mRecipeName);
        setRecipeDirections(mRecipeDirections);
        setImageUrl(mImageUrl);
        setIsFoodSelected(mFoodBtn);

        ArrayList<String> ingredientsList;
        ingredientsList = getAllIngredients();

        if(getRecipeName() != null && getRecipeDirections() != null
                && ingredientsList.size() > 2)
        {
            // Sets path
            DatabaseReference dataBaseRef = mDatabase.getReference("recipes/" + getUserUid());

            // Generate dynamic recipe ID
            String key = dataBaseRef.push().getKey();
            // Sets data to the database model
            DBRecipesModel post = new DBRecipesModel(getIsFoodSelected(), getImageUrl(),
                    getRecipeDirections(), ingredientsList);

            Map<String, Object> postValues = post.toRecipeMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/" + getRecipeName() + "/" + key, postValues);

            dataBaseRef.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    if (aVoid == null)
                    {
                        successfulPush = 1;
                        resetEditTextFields();
                    } else {
                        successfulPush = 2;
                    }
                }
            });
        }
        else
        {
            successfulPush = 3;
        }

        return successfulPush;
    }
}
