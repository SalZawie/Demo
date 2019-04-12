package com.example.demo2;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import java.util.regex.Pattern;



import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity
{
    private static final Pattern PASSWORD_PATTERN =
                         Pattern.compile("^" +                //Start of Expression
                                         "(?=.*[0-9])" +      //at least 1 digit
                                         "(?=.*[a-z])" +      //at least 1 lower case letter
                                         "(?=.*[A-Z])" +      //at least 1 upper case letter
                                         "(?=.*[@#$%^&+=])" + //at least 1 special character
                                         "(?=\\S+$)" +        //no white spaces
                                         ".{6,}" +            //at least 6 characters
                                         "$");                //End of Expression

    private TextInputLayout userName ;
    private TextInputLayout userEmail;
    private TextInputLayout userPW   ;

    private FirebaseAuth user_auth   ;
    private FirebaseUser current_user;

    DatabaseReference firebaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName  = findViewById(R.id.txtSUName);
        userEmail = findViewById(R.id.txtSUEmail);
        userPW    = findViewById(R.id.txtSUPassword);

        user_auth = FirebaseAuth.getInstance();

        firebaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public void validateNameEmailPasswordInput(View v)
    {
        if (!validName() || !validEmail() || !validPassword())
        {
            return;
        }

        createFirebaseUser(v);
    }

    private boolean validName()
    {
        String nameInput = userName.getEditText().getText().toString().trim();

        if (nameInput.isEmpty())
        {
            userName.setError("Field can't be empty");
            return false;
        }
        else if (nameInput.length() > 20)
        {
            userName.setError("Name too long");
            return false;
        }
        else
        {
            userName.setError(null);
            return true;
        }
    }

    private boolean validEmail()
    {
        String emailInput = userEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty())
        {
            userEmail.setError("Field can't be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
        {
            userEmail.setError("Please enter a valid email address");
            return false;
        }
        else
        {
            userEmail.setError(null);
            return true;
        }
    }

    private boolean validPassword()
    {
        String passwordInput = userPW.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty())
        {
            userPW.setError("Field can't be empty");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches())
        {
            userPW.setError("Invalid password format .. \n At least 1 digit" +
                                                       "\n At least 1 lower case letter"  +
                                                       "\n At least 1 upper case letter"  +
                                                       "\n At least 1 special character " +
                                                       "\n ( @ # $ % ^ & + = )"   +
                                                       "\n At least 6 characters" +
                                                       "\n Spaces are not allowed");
            return false;
        }
        else
        {
            userPW.setError(null);
            return true;
        }
    }

    public void createFirebaseUser(View view){
        String name  = userName.getEditText().getText().toString().trim() ;
        String email = userEmail.getEditText().getText().toString().trim();
        String password = userPW.getEditText().getText().toString().trim();

        user_auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>()
        {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task)
             {
                  Toast.makeText(SignUpActivity.this,"createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                  if (!task.isSuccessful())
                  {
                     Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                  }
                  else
                  {
                     Toast.makeText(SignUpActivity.this, "You have successfully registered, Please login with your new credentials" + task.getException(), Toast.LENGTH_SHORT).show();
                     sendVerification();

                     startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                     finish();
                  }
             }
        });
    }

    public void sendVerification()
    {
        current_user = user_auth.getCurrentUser();
        current_user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(SignUpActivity.this, "Verification code send to" + current_user.getEmail(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener()
        {
           @Override
           public void onFailure(@NonNull Exception e)
           {
              Toast.makeText(SignUpActivity.this, "Verification FAILED to sent to" + current_user.getEmail(), Toast.LENGTH_SHORT).show();
           }
        });
    }

/*
    public void saveUserToDatabase(View view) // To Database
    {
        String name   = userName.getEditText().getText().toString() ;
        String email  = userEmail.getEditText().getText().toString();
        String password = userPW.getEditText().getText().toString() ;

        boolean verified = false;

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) )
        {
            String id = firebaseReference.push().getKey();
            User userObj = new User(id,name,password,email,verified);
            firebaseReference.child(id).setValue(userObj);

            //Clear the input fields
            userName.getEditText().setText("") ;
            userEmail.getEditText().setText("");
            userPW.getEditText().setText("")   ;
        }
        else
        {
            Toast.makeText(SignUpActivity.this,"Field(s) are empty",Toast.LENGTH_LONG).show();
        }
    }
*/
}
