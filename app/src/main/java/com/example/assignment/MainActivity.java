package com.example.assignment;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class MainActivity extends AppCompatActivity {

    Button login, register;
    TextView inputEmail, inputPass;
    String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);

        inputEmail = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateText();
                if (validateInput()) {
                    loginUser();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

    private void updateText() {
        email = inputEmail.getText().toString();
        pass = inputPass.getText().toString();
    }

    private boolean validateInput() {
        if(email.equals("")){
            inputEmail.setError("Cannot be empty");
            inputEmail.requestFocus();
            return false;
        }
        if(pass.equals("")){
            inputPass.setError("Cannot be empty");
            inputPass.requestFocus();
            return false;
        }
        return true;
    }

    private void loginUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            ((Global) getApplication()).setAuth(auth);
                            //startActivity(new Intent(MainActivity.this, MapFragment.class));
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            //startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                            //startActivity(new Intent(MainActivity.this, TestActivity.class));
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Exception e = task.getException();
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                inputPass.setError("Incorrect password");
                                inputPass.requestFocus();
                            } else if (e instanceof FirebaseAuthInvalidUserException) {
                                String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();

                                if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                                    inputEmail.setError("No matching account found");
                                } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                                    inputEmail.setError("User account has been disabled");
                                } else {
                                    inputEmail.setError(e.getLocalizedMessage());
                                }
                                inputEmail.requestFocus();
                            }
                        }
                    }
                });
    }
}