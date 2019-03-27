package com.example.palak.newsaggapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.palak.newsaggapplication.Model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button SignIn, SignUp;
    EditText email, password;

    FirebaseAuth firebaseAuth;
    user userInfo;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    void initViews(){

        SignIn = findViewById(R.id.btnSignIn);
        SignUp = findViewById(R.id.btnSignUp);
        email = findViewById(R.id.etxtEmail);
        password = findViewById(R.id.etxtPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        userInfo = new user();

        SignUp.setOnClickListener(this);
        SignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (R.id.btnSignIn ==v.getId()) {

            loginUser();
        }

        else
        {
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
        }

    }

    void loginUser(){

        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userInfo.email,userInfo.password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Login Successful!",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Email Id or Password doesn't match",Toast.LENGTH_LONG).show();
                        Log.i("User","Login Unsuccessful!! "+e.getMessage());
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
        );

    }
}
