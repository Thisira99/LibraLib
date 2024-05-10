package com.example.lib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText edUsername, edEmail, edPassword, edConfirmPassword;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.editTextRegusername);
        edPassword = findViewById(R.id.editTextRegPassword);
        edEmail = findViewById(R.id.editTextRegEmail);
        edConfirmPassword = findViewById(R.id.editTextRegconfPassword);
        btn = findViewById(R.id.btnRegister);
        tv = findViewById(R.id.textViewExistingUser);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String confPassword = edConfirmPassword.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                Database db = new Database(getApplicationContext(),"libralib",null,1);

                if (username.length() == 0 || email.length() == 0 || password.length() == 0 || confPassword.length() == 0)
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                else if (!email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confPassword))
                    Toast.makeText(getApplicationContext(), "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
                else {
                    db.register(username, email, password);
                    Toast.makeText(getApplicationContext(), "Registration Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //redirect to the new activity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


    }
}