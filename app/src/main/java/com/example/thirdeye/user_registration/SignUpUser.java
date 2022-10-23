 package com.example.thirdeye.user_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thirdeye.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;

 public class SignUpUser extends AppCompatActivity implements View.OnClickListener {
    private Button go_back_user,registerUser;
    private FirebaseAuth mAuth;
    private TextInputLayout editName, editEmail, editPass;
    FirebaseDatabase db;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_up_user);
        go_back_user=findViewById(R.id.go_back_user);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        registerUser= findViewById(R.id.sign_up_user);
        registerUser.setOnClickListener(this);
        editName= findViewById(R.id.name);
        editEmail = findViewById(R.id.email);
        editPass = findViewById(R.id.password);


//        go_back_user.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
    }
    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.sign_up_user:
               registerUser();
               break;
       }
    }
    private void registerUser(){
        String name = editName.getEditText().getText().toString().trim();
        String email = editEmail.getEditText().getText().toString().trim();
        String password = editPass.getEditText().getText().toString().trim();


        if(name.isEmpty()){
            editName.setError("Please provide us your name!");
            editName.requestFocus();
            return ;
        }
        if(email.isEmpty()){
            editEmail.setError("email is required.");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide us a valid email.");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPass.setError("Password is required.");
            editPass.requestFocus();
            return;
        }
        if(password.length()<6){
            editPass.setError("Minimum required password length is 6.");
            editPass.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(name,"user", email);
                    db= FirebaseDatabase.getInstance();
                    reference= db.getReference("Users");
                    reference.push().setValue(user);

                    Toast.makeText(SignUpUser.this,"user registered succesfully",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(SignUpUser.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}