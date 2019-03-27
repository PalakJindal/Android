package com.example.palak.newsaggapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button SignIn, SignUp;
    EditText email, password, name, confirmpassword, phoneno;

    user userInfo;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }

    void initViews(){

        SignIn = findViewById(R.id.btnSignIn);
        SignUp = findViewById(R.id.btnSignUp);
        email = findViewById(R.id.etxtEmail);
        password = findViewById(R.id.etxtPassword);
        name = findViewById(R.id.etxtName);
        confirmpassword = findViewById(R.id.etxtCPassword);
        phoneno = findViewById(R.id.etxtPhoneNo);

        userInfo = new user();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        SignUp.setOnClickListener(this);
        SignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (R.id.btnSignUp ==v.getId())
        {

            if((!TextUtils.isEmpty(password.getText().toString())) && (!TextUtils.isEmpty(confirmpassword.getText().toString())) && (!TextUtils.isEmpty(phoneno.getText().toString())) && (!TextUtils.isEmpty(email.getText().toString())) && (!TextUtils.isEmpty(name.getText().toString()) ))
            {


                if(password.getText().toString().equals(confirmpassword.getText().toString()))
                {
                    Toast.makeText(this, name.getText().toString()+" Successfully Registered!", Toast.LENGTH_SHORT).show();
                    userInfo.email = email.getText().toString();
                    userInfo.password = password.getText().toString();
                    userInfo.name = name.getText().toString();
                    userInfo.phoneno = phoneno.getText().toString();


                    email.getText().clear();
                    confirmpassword.getText().clear();
                    password.getText().clear();
                    name.getText().clear();
                    phoneno.getText().clear();

                    registerUser();

                }
                else
                {
                    Toast.makeText(this,"Password doesn't match",Toast.LENGTH_LONG).show();
                }
            }

            else
            {
                Toast.makeText(this,"Please fill the complete details!",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    }

    void registerUser(){

        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userInfo.email,userInfo.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String uid = task.getResult().getUser().getUid();
                    Log.i("User","User's id "+uid);
                    progressDialog.dismiss();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("User","registration Unsuccessful!! "+e.getMessage());
                e.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }


}
