package com.example.demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BasicActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        inflater.inflate(R.menu.account,menu);
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
            case R.id.menu_signup:
                intent = new Intent(BasicActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_login:
                intent = new Intent(BasicActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
