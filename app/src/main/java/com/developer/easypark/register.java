package com.developer.easypark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {
    Button to_login;
    EditText userName;
    EditText userSurname;
    EditText userEmail;
    EditText userPassword;
    EditText userPassword2;
    Button submit;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Creer votre compte");
        to_login = findViewById(R.id.to_login);
        mAuth = FirebaseAuth.getInstance();

        userEmail = (EditText) findViewById(R.id.register_email);
        userName = (EditText) findViewById(R.id.register_name);
        userSurname = (EditText) findViewById(R.id.register_surname);
        userPassword = (EditText) findViewById(R.id.register_password);
        userPassword2 = (EditText) findViewById(R.id.register_password2);
        submit = (Button) findViewById(R.id.register_btn);

        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                register.this.finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Veuillez patienter", Toast.LENGTH_LONG).show();
               /* if (userPassword.getText().toString() != userPassword2.getText().toString()){
                    System.out.println(userPassword.getText().toString() + " - " + userPassword2.getText().toString());
                    Toast.makeText(getApplicationContext(), "Veillez saisir les mêmes mots de passe", Toast.LENGTH_LONG).show();
                }
                else{*/
                    signUp(userEmail.getText().toString(),userPassword.getText().toString() );
                //}

            }
        });dai

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        }
    }
    private void signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information

                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(), "Enregistrement effedtué", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), login.class);
                    startActivity(intent);
                    finish();

                } else {
                    // If sign in fails, display a message to the user
                }
            }
        });
    }
}