package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SearchPageActivity extends BasicActivity {
    private EditText searchOne;
    private EditText searchTwo;
    private EditText searchThree;
    private CheckBox myRecipeCheckBox;
    private RadioButton foodRadioButtom;
    private RadioButton drinkRadioButtom;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private String firstIngredient;
    private String secondIngredient;
    private String thirdIngredient;

    private Button backButton;
    private Button logoutButton;
    private Button addRecipeButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        searchOne = findViewById(R.id.searchOne);
        searchTwo = findViewById(R.id.searchTwo);
        searchThree = findViewById(R.id.searchThree);
        searchButton = findViewById(R.id.searchButton);
        backButton = findViewById(R.id.backButton);
        logoutButton = findViewById(R.id.logoutButton);
        addRecipeButton = findViewById(R.id.addRecipeButton);
        foodRadioButtom = findViewById(R.id.foodRadioButtom);
        drinkRadioButtom = findViewById(R.id.drinkRadioButtom);
        myRecipeCheckBox = findViewById(R.id.myRecipeChechBox);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                firstIngredient = searchOne.getText().toString().toLowerCase();
                secondIngredient = searchTwo.getText().toString().toLowerCase();
                thirdIngredient = searchThree.getText().toString().toLowerCase();

                if (firstIngredient.matches("") || secondIngredient.matches("") || thirdIngredient.matches("")) {

                    Toast.makeText(SearchPageActivity.this, "Input Fields Cannot Be Blank", Toast.LENGTH_LONG).show();
                }
                else {
                    if (foodRadioButtom.isChecked()) {
                        Intent intent = new Intent(SearchPageActivity.this, RecipePageActivity.class);
                        intent.putExtra("Ingredients", new String[]{firstIngredient, secondIngredient, thirdIngredient});
                        intent.putExtra("Category", true);

                        if (myRecipeCheckBox.isChecked()){
                            intent.putExtra("User", firebaseUser.getUid());
                        }
                        else{

                            intent.putExtra("User", "null");
                        }

                        startActivity(intent);

                    }
                    else if (drinkRadioButtom.isChecked()) {
                        Intent intent = new Intent (SearchPageActivity.this, RecipePageActivity.class);
                        intent.putExtra("Ingredients", new String[]{firstIngredient, secondIngredient, thirdIngredient});
                        intent.putExtra("Category", false);

                        if (myRecipeCheckBox.isChecked()){
                            intent.putExtra("User", firebaseUser.getUid());
                        }
                        else{

                            intent.putExtra("User", "null");
                        }
                        startActivity(intent);
                    }

                }



            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPageActivity.this, AddNewRecipeActivity.class);
                startActivity(intent);
            }
        });




    }
}
