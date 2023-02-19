package com.developer.easypark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {
    Button to_register; // to register page button
    TextInputEditText email; // login email
    TextInputEditText password; // login password
    Button loginBtn; // login btn
    private FirebaseAuth mAuth; // firebase auth for authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Connexion");
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        email = (TextInputEditText) findViewById(R.id.email_connexion);
        password = (TextInputEditText) findViewById(R.id.password_connexion);
        loginBtn = (Button) findViewById(R.id.login);

        to_register = findViewById(R.id.to_register);
        to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),register.class);
                startActivity(intent);
                login.this.finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailV = email.getText().toString();
                String passwordV = password.getText().toString();
                signIn(emailV, passwordV);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        }
    }
    private void signIn(String email, String password){
        try {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                System.out.println(email);
                                FirebaseUser currentUser;
                                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                FirebaseFirestore.getInstance().collection("user")
                                        .document(currentUser.getUid())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                               check();
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(login.this, "Un erreur s'est produite", Toast.LENGTH_LONG).show();
                                System.out.println(" Erreur " + email);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(login.this, "Un erreur s'est produite", Toast.LENGTH_LONG).show();
                        }
                    });
        }
        catch (Exception e){

            Toast.makeText(getApplicationContext(), "Verfiez vos champs ", Toast.LENGTH_LONG).show();
        }

    }

    public  void check(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            Intent next = new Intent(getApplicationContext(),register.class);
            startActivity(next);

            return;
        }
        else {
            FirebaseFirestore.getInstance().collection("user")
                    .document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot user = task.getResult();
                                String is_admin = (String) user.get("is_admin");
                                Intent intent;
                                assert is_admin != null;
                                if (is_admin.equals("true")) {
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                } else {
                                    intent = new Intent(getApplicationContext(), profile.class);
                                }
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
    }
}