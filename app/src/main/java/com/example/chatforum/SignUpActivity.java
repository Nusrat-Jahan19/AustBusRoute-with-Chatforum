package com.example.chatforum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatforum.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity  {
    TextInputEditText textEmail, textPassword,textName;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        textEmail =(TextInputEditText)findViewById(R.id.emailRegister);
        textPassword =(TextInputEditText) findViewById(R.id.passwordRegister);
        progressBar=(ProgressBar)findViewById(R.id.progressbarRegister);
        textName=(TextInputEditText)findViewById(R.id.nameRegister);
        reference= FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void RegisterUser (View v)
    {
        progressBar.setVisibility(View.VISIBLE);

        final String email = textEmail.getText().toString();
        final String password = textPassword.getText().toString();
        final String name = textName.getText().toString();

        if(!email.equals("") && !password.equals("") && password.length()>6)
        {
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                User u = new User();
                                u.setName(name);
                                u.setEmail(email);
                                u.setUid(password);


                                reference.child(firebaseUser.getUid()).setValue(u)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(getApplicationContext(),"Register successful", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    Intent i = new Intent(SignUpActivity.this,GroupChatActivity.class);
                                                    startActivity(i);
                                                }
                                                else
                                                {

                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(getApplicationContext(),"Register is unsuccessful",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    });
        }
    }

    public void gotoLogin(View v)
    {
        Intent i = new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(i);
    }

}