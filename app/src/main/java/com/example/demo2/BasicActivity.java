package com.example.demo2;

/**
 * This class is created by Oksana Miller
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class BasicActivity extends AppCompatActivity {

    FirebaseUser user;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        user = FirebaseAuth.getInstance().getCurrentUser();
        inflater.inflate(R.menu.account,menu);
        MenuItem logout_signup = menu.findItem(R.id.menu_logout_signup);
        if( user != null) {
            inflater.inflate(R.menu.menu, menu);
            //Set menu label to log out
            logout_signup.setTitle("Log out");
        }
        else{
            //Set menu label to log out
            logout_signup.setTitle("Sign up");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_addRecipe:
                intent = new Intent(BasicActivity.this, AddNewRecipeActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_search:
                intent = new Intent(BasicActivity.this, SearchPageActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_resetPsw:
                intent = new Intent(BasicActivity.this, AccountRecoveryActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_verification:
                intent = new Intent(BasicActivity.this, VerifyCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_logout_signup:
                if( user != null) //If user is LOGGED IN
                {
                    // DO Log OUT
                    FirebaseAuth.getInstance().signOut();
                    intent = new Intent(BasicActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(BasicActivity.this,"You are logged out",Toast.LENGTH_LONG).show();
                }
                else // If USER is NULL
                {
                    intent = new Intent(BasicActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
