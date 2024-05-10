package com.example.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText edUseremail, edPassword;
    Button btn;
    TextView tv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUseremail = findViewById(R.id.editTextRegEmail);
        edPassword = findViewById(R.id.editTextRegPassword);
        btn = findViewById(R.id.btnRegister);
        tv = findViewById(R.id.textViewExistingUser);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("useremail")){
            startActivity(new Intent(LoginActivity.this, com.example.libralib.HomeActivity.class));
        }
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String useremail = edUseremail.getText().toString();
                String password = edPassword.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                com.example.libralib.Database db = new com.example.libralib.Database(getApplicationContext(),"libralib",null,1);

                if(useremail.length() == 0 || password.length() == 0)
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                else if (!useremail.matches(emailPattern)){
                    Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(db.login(useremail, password) == 1){
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("useremail", useremail);
                        // to save our data with key and value
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this, com.example.libralib.HomeActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        tv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                redirect to the new activity
                startActivity(new Intent(LoginActivity.this, com.example.libralib.RegisterActivity.class));
            }
        });
    }
}