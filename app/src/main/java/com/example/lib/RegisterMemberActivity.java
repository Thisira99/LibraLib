package com.example.lib;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member);

        com.example.libralib.Database db = new com.example.libralib.Database(getApplicationContext(),"libralib",null,1);


        Button btnRegister = findViewById(R.id.btnMemRegister);

        btnRegister.setOnClickListener(v -> {
            String memName = ((EditText) findViewById(R.id.editTextMemName)).getText().toString();
            String memAddr = ((EditText) findViewById(R.id.editTextMemAddr)).getText().toString();
            String memPhone = ((EditText) findViewById(R.id.editTextMemPhone)).getText().toString();

            int code = db.addMember(memName, memAddr, memPhone);
            if(code == 0){
                // Member added successfully
                Toast.makeText(getApplicationContext(), "Member added successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterMemberActivity.this, com.example.libralib.HomeActivity.class));
            } else{
                // Member not added
                Toast.makeText(getApplicationContext(), "Member not added (Error)", Toast.LENGTH_SHORT).show();
            }
        });

    }
}