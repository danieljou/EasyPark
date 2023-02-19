package com.developer.easypark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developer.easypark.Modele.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class register extends AppCompatActivity {
    Button to_login;
    TextInputEditText userName;
    TextInputEditText userSurname;
    TextInputEditText userEmail;
    TextInputEditText userPassword;
    TextInputEditText userPassword2;
    Button submit;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Creer votre compte");
        to_login = findViewById(R.id.to_login);
        mAuth = FirebaseAuth.getInstance();

        userEmail = (TextInputEditText) findViewById(R.id.register_email);
        userName = (TextInputEditText) findViewById(R.id.register_name);
        userSurname = (TextInputEditText) findViewById(R.id.register_surname);
        userPassword = (TextInputEditText) findViewById(R.id.register_password);
        userPassword2 = (TextInputEditText) findViewById(R.id.register_password2);
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
                if(!validateFields()){
                    return;
                }
                    signUp(userEmail.getText().toString(),userPassword.getText().toString() );
                //}

            }
        });

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
                    User mUser = new User(userName.getText().toString(),userSurname.getText().toString(),
                            email,password,690784542);
                    FirebaseFirestore.getInstance().collection("user")
                                    .document(user.getUid())
                                            .set(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Enregistrement effedtué", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });


                } else {
                    // If sign in fails, display a message to the user
                }
            }
        });
    }
    public boolean validateFields(){
        // userName;
        // userSurname;
        // userEmail;
        // userPassword;
        // userPassword2;
        boolean state = true;
        if (userName.length() == 0){
            userName.setError("Ce champ es réquis");
            state = false;
        }
        if (userSurname.length() == 0){
            userSurname.setError("Ce champ es réquis");

            state = false;
        }
        if (userEmail.length() == 0){
            userEmail.setError("Ce champ es réquis");
            state = false;
        }
        if (userPassword.length() == 0){

            state = false;
        }
        return state;
    }

}