package com.example.demo2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class AddNewRecipeActivity extends AppCompatActivity
{
    private LinearLayout mLinearLayout;

    private EditText mRecipeName;
    private EditText mEnterRecipe;

    private int editTextCounter = 0;
    private static final int MAX_EDITTEXT_LIMIT = 20;

    private Button mAddIngredientBtn;
    private Button mBackBtn;
    private Button mClearAllBtn;
    private Button mSaveRecipeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);

        mLinearLayout = findViewById(R.id.parent_linear_layout);

        // UI elements
        mRecipeName = findViewById(R.id.edittxt_recipe_name);
        mEnterRecipe = findViewById(R.id.edittxt_recipe_description);

        // Instantiate Buttons
        mAddIngredientBtn = findViewById(R.id.btn_add_an_ingredient);
        mBackBtn = findViewById(R.id.btn_previous_from_add_new_recipe);
        mSaveRecipeBtn = findViewById(R.id.btn_save_recipe);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAddIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(editTextCounter >= MAX_EDITTEXT_LIMIT)
            {
                Toast toast = Toast.makeText(AddNewRecipeActivity.this, "You have reached max ingredients", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else
            {
                addNewEditTextField();
            }
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewRecipeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addNewEditTextField()
    {
        final Thread extraThread = new Thread(new Runnable() {
            @Override
            public void run() {

            try
            {
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View rowView = inflater.inflate(R.layout.newfield, null);

                assert mLinearLayout != null;
                mLinearLayout.post(new Runnable() {
                    @Override
                    public void run() {

                        mLinearLayout.addView(rowView, mLinearLayout.getChildCount());
                    }
                });
            }
            catch (NullPointerException ex)
            {
                ex.printStackTrace();
            }
            }
        });
        extraThread.start();
    }


    public ArrayList<String> getAllIngredients()
    {
        ArrayList<LinearLayout> linearList = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();

        // Adds all children linearlayouts to a list.
        for(int i = 0; i < mLinearLayout.getChildCount(); i++)
        {
            if(mLinearLayout.getChildAt(i) instanceof LinearLayout)
            {
                linearList.add((LinearLayout) mLinearLayout.getChildAt(i));
            }
        }

        // Targets the edittext fields within the list of linear layouts and get user input.
        for(int i = 0; i < linearList.size(); i++)
        {
            LinearLayout l = linearList.get(i);
            EditText et = (EditText) l.getChildAt(0);

            if(et.getText().toString() != null || !et.getText().toString().isEmpty())
            {
                list.add(et.getText().toString());
            }
        }
        return list;
    }

    public void onSaveData(View view)
    {
        ArrayList<String> ingredientsList = new ArrayList<>();
        ingredientsList = getAllIngredients();

        Toast.makeText(AddNewRecipeActivity.this, "Recipe Has Been Saved", Toast.LENGTH_SHORT).show();
    }

    public void onClearAll(View view)
    {
        mRecipeName.getText().clear();
        mEnterRecipe.getText().clear();

    }

    public void onDelete(View v)
    {
        mLinearLayout.removeView((View) v.getParent());
    }
}
