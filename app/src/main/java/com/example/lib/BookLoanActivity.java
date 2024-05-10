package com.example.lib;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookLoanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_loan);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        String dynamicString = "Loan Book";
        toolbarTitle.setText(dynamicString);

        Button loanButton = findViewById(R.id.btnBook);
        loanButton.setOnClickListener(v -> {

        });
    }
}