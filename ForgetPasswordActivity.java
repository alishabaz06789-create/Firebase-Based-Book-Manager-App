package com.example.myapplicationfirebase;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText emailEt;
    private Button resetBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.etResetEmail);
        resetBtn = findViewById(R.id.btnReset);

        resetBtn.setOnClickListener(v -> {
            String email = emailEt.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter your registered email", Toast.LENGTH_SHORT).show();
                return;
            }
            // Send Reset Email
            mAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(unused -> Toast.makeText(this, "Reset link sent to your email", Toast.LENGTH_LONG).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}
