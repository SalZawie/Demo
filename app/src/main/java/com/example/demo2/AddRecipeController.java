package com.example.demo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;


public class AddRecipeController
{
    private String strRecipeName;
    private String strRecipeDirections;
    private boolean isFoodSeleted;

    public AddRecipeController()
    {

    }

    public void addNewEditTextField(final LinearLayout linearLayout, final Context context)
    {
        final Thread extraThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    final View rowView = inflater.inflate(R.layout.newfield, null);

                    assert linearLayout != null;
                    linearLayout.post(new Runnable() {
                        @Override
                        public void run() {

                            linearLayout.addView(rowView, linearLayout.getChildCount());
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

    public static ArrayList<String> getAllIngredients(LinearLayout linearLayout)
    {
        ArrayList<LinearLayout> linearList = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();

        // Adds all children linearlayouts to a list.
        for(int i = 0; i < linearLayout.getChildCount(); i++)
        {
            if(linearLayout.getChildAt(i) instanceof LinearLayout)
            {
                linearList.add((LinearLayout) linearLayout.getChildAt(i));
            }
        }

        // Targets the edittext fields within the list of linear layouts and get user input.
        for(int i = 0; i < linearList.size(); i++)
        {
            LinearLayout l = linearList.get(i);
            EditText et = (EditText) l.getChildAt(0);

            if(et.getText().toString() != null || !et.getText().toString().isEmpty())
            {
                list.add(et.getText().toString().toLowerCase().trim());
            }
        }
        return list;
    }

    // Retrieves text values entered by the user for recipe name
    public void setRecipeName(EditText recipeName)
    {

        this.strRecipeName = !recipeName.getText().toString().isEmpty() ? recipeName.getText().toString() : null;

    }

    public String getRecipeName() { return this.strRecipeName; }

    // Retrieves text values entered by the user for recipe directions
    public void setRecipeDescription(EditText recipeDirections)
    {
        this.strRecipeDirections = !recipeDirections.getText().toString().isEmpty() ? recipeDirections.getText().toString() : null;
    }

    public String getRecipeDirections() { return this.strRecipeDirections; }

    public void setIsFoodSelected(RadioButton rButton)
    {
        this.isFoodSeleted = rButton.isChecked() ? true : false;
    }

    public Boolean getIsFoodSelected() { return this.isFoodSeleted; }
}
