package com.example.myapplicationfirebase;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddEditBookActivity extends AppCompatActivity {
    private EditText titleEt, authorEt, isbnEt, yearEt;
    private Button saveBtn;
    private FirebaseFirestore db;
    private String bookId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        db = FirebaseFirestore.getInstance();
        titleEt = findViewById(R.id.etTitle);
        authorEt = findViewById(R.id.etAuthor);
        isbnEt = findViewById(R.id.etIsbn);
        yearEt = findViewById(R.id.etYear);
        saveBtn = findViewById(R.id.btnSaveBook);

        if (getIntent().hasExtra("bookId")) {
            bookId = getIntent().getStringExtra("bookId");
            titleEt.setText(getIntent().getStringExtra("title"));
            authorEt.setText(getIntent().getStringExtra("author"));
            isbnEt.setText(getIntent().getStringExtra("isbn"));
            yearEt.setText(getIntent().getStringExtra("year"));
            saveBtn.setText("Update Book");
        }

        saveBtn.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        String title = titleEt.getText().toString().trim();
        String author = authorEt.getText().toString().trim();
        String isbn = isbnEt.getText().toString().trim();
        String year = yearEt.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(isbn) || TextUtils.isEmpty(year)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> book = new HashMap<>();
        book.put("title", title);
        book.put("author", author);
        book.put("isbn", isbn);
        book.put("year", year);

        if (bookId == null) {
            db.collection("books").add(book)
                    .addOnSuccessListener(docRef -> {
                        Toast.makeText(this, "Book Added", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            db.collection("books").document(bookId).update(book)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Book Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error updating: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}
