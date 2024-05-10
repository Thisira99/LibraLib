package com.example.lib;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        spinner = findViewById(R.id.pubSpinner);
        Button addBook = findViewById(R.id.btnAddBook);

        Database db = new Database(getApplicationContext(),"libralib",null,1);
//
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, db.getPublishers());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addBook.setOnClickListener(v -> {
            String title = ((EditText) findViewById(R.id.editTextBookName)).getText().toString();
            String authorName = ((EditText) findViewById(R.id.editTextAuthorName)).getText().toString();
            String publisherName = spinner.getSelectedItem().toString();
            int code = db.addBook(title, authorName, publisherName);
            if(code == 0){
                // Book added successfully
                Toast.makeText(getApplicationContext(), "Book added successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddBookActivity.this, HomeActivity.class));
            } else{
                // Book not added
                Toast.makeText(getApplicationContext(), "Book not added (Error)", Toast.LENGTH_SHORT).show();
            }
        });

    }
}