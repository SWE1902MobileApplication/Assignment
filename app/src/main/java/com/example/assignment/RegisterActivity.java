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
                    //TODO check success
                    finishActivity(0);
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
        return !(email.equals("") || pass.equals("") || first.equals("") || last.equals("") || confirm.equals(""))
                && pass.equals(confirm);
        //TODO error
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
                }else{
                    //TODO fail message
                    Log.w(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                    Toast.makeText(RegisterActivity.this, "Register failed.",
                    Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
