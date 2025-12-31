package com.example.myapplicationfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList;
    private FirebaseFirestore db;
    private FloatingActionButton fabAdd;
    private Button btnLogout, btnDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        bookList = new ArrayList<>();

        // Initialize Views
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(this, bookList);
        recyclerView.setAdapter(adapter);

        // Add Book Button
        fabAdd.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddEditBookActivity.class)));

        // Logout Button Logic
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        // Delete Account Button Logic
        btnDeleteAccount.setOnClickListener(v -> confirmDeleteAccount());

        loadBooks();
    }

    private void loadBooks() {
        db.collection("books").addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(this, "Error loading books", Toast.LENGTH_SHORT).show();
                return;
            }
            bookList.clear();
            if (value != null) {
                for (DocumentSnapshot doc : value.getDocuments()) {
                    Book book = doc.toObject(Book.class);
                    if (book != null) {
                        book.setId(doc.getId());
                        bookList.add(book);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void confirmDeleteAccount() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure? This cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(MainActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
