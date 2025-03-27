package com.example.newproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button loginbtn;
    EditText email,password;
    TextView signin;
    ProgressBar progress;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i=new Intent(getApplicationContext(), home.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn=findViewById(R.id.b1);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        signin=findViewById(R.id.textView1);
        progress=findViewById(R.id.progress1);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), register.class);
                startActivity(i);
                finish();

            }
        });
loginbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progress.setVisibility(View.VISIBLE);
        String mail,pass,mobile;
        mail=String.valueOf(email.getText());
        pass=String.valueOf(password.getText());


        if(TextUtils.isEmpty(mail)){
            Toast.makeText(MainActivity.this, "Please enter a email id", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(MainActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login successful.",
                                    Toast.LENGTH_SHORT).show();  // Sign in success, update UI with the signed-in user's information
                          Intent i=new Intent(getApplicationContext(), home.class);
                          startActivity(i);
                          finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(MainActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
});
    }
}