package com.example.assignment;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private String first, last, email, pass, confirm;
    private EditText regFirst, regLast, regEmail, regPass, regConf;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        regFirst = findViewById(R.id.regFirst);
        regLast = findViewById(R.id.regLast);
        regEmail = findViewById(R.id.regEmail);
        regPass = findViewById(R.id.regPwd);
        regConf = findViewById(R.id.regConf);

        submit = findViewById(R.id.btnRegister);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateText();
                if(validateInput()){
                    registerUser();
                }
            }
        });

    }

    private void updateText(){
        first = regFirst.getText().toString();
        last = regLast.getText().toString();
        email = regEmail.getText().toString();
        pass = regPass.getText().toString();
        confirm = regConf.getText().toString();
    }

    private boolean validateInput(){
        if(first.equals("")){
            regFirst.setError("Cannot be empty");
            regFirst.requestFocus();
            return false;
        }
        if(last.equals("")){
            regLast.setError("Cannot be empty");
            regLast.requestFocus();
            return false;
        }
        if(email.equals("")){
            regEmail.setError("Cannot be empty");
            regEmail.requestFocus();
            return false;
        }
        if(pass.equals("")){
            regPass.setError("Cannot be empty");
            regPass.requestFocus();
            return false;
        }
        if(confirm.equals("")){
            regConf.setError("Cannot be empty");
            regConf.requestFocus();
            return false;
        }
        if(!confirm.equals(pass)){
            regConf.setError("Should be the same as password");
            regConf.requestFocus();
            return false;
        }
        return true;
    }

    private void registerUser() {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.w(TAG, "createUserWithEmailAndPassword:success", task.getException());
                    Toast.makeText(RegisterActivity.this, "Register success.",
                    Toast.LENGTH_SHORT).show();
                    //TODO store user info, init user records
                    finishActivity(0);
                }else{
                    Log.w(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                    Toast.makeText(RegisterActivity.this, "Register failed.",
                    Toast.LENGTH_SHORT).show();
                    Exception e = task.getException();
                    if(e instanceof FirebaseAuthWeakPasswordException) {
                        regPass.setError("Weak password");
                        regPass.requestFocus();
                    }else if(e instanceof FirebaseAuthInvalidCredentialsException) {
                        regEmail.setError("Invalid email");
                        regEmail.requestFocus();
                    }else if(e instanceof FirebaseAuthUserCollisionException) {
                        regEmail.setError("User exist");
                        regEmail.requestFocus();
                    }
                }
            }
        });
    }
}
