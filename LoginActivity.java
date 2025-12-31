package com.example.myapplicationfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEt, passEt;
    private Button loginBtn;
    private TextView registerTv, forgotPassTv;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Check if user is already logged in
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        emailEt = findViewById(R.id.etLoginEmail);
        passEt = findViewById(R.id.etLoginPass);
        loginBtn = findViewById(R.id.btnLogin);
        registerTv = findViewById(R.id.tvRegisterLink);
        forgotPassTv = findViewById(R.id.tvForgotPassword);

        loginBtn.setOnClickListener(v -> loginUser());
        registerTv.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        forgotPassTv.setOnClickListener(v -> startActivity(new Intent(this, ForgotPasswordActivity.class)));
    }

    private void loginUser() {
        String email = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
