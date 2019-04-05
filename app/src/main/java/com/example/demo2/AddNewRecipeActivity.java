package com.example.demo2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddNewRecipeActivity extends AppCompatActivity
{

    private LinearLayout mLinearLayout;

    private EditText mRecipeName;
    private EditText mEnterRecipe;
    private List<EditText> mIngredientList;
    private int editTextCounter = 1;
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
        mIngredientList = new ArrayList<>();

        // Instantiate Buttons
        mAddIngredientBtn = findViewById(R.id.btn_add_an_ingredient);
        mBackBtn = findViewById(R.id.btn_previous_from_add_new_recipe);
        mClearAllBtn = findViewById(R.id.btn_clear_all_input);
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
                    Toast.makeText(AddNewRecipeActivity.this, "You have reached max ingredients", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    addNewEditTextField();
                    editTextCounter++;
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

                            mLinearLayout.addView(rowView, mLinearLayout.getChildCount() - 1);
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

    public void onDelete(View v) {
        mLinearLayout.removeView((View) v.getParent());
    }
}
