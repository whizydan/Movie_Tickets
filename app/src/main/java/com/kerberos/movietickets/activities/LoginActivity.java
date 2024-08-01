package com.kerberos.movietickets.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kerberos.movietickets.R;
import com.kerberos.movietickets.dbhandler.DbHandler;
import com.kerberos.movietickets.models.Users;
import com.kerberos.movietickets.utils.TinyDB;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout password, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MaterialButton signIn = findViewById(R.id.sign_in);
        TextView signUp = findViewById(R.id.sign_up);
        TextView resetPassword = findViewById(R.id.reset_password);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        TinyDB tinyDB = new TinyDB(this);
        DbHandler dbHandler = new DbHandler(this);

        if(tinyDB.getBoolean("loggedIn")){
            if(Objects.equals(tinyDB.getString("role"), "0")){
                startActivity(new Intent(this,AdminHomeActivity.class));
                finish();
            }else{
                startActivity(new Intent(this,UserHomeActivity.class));
                finish();
            }
        }
        signIn.setOnClickListener(view -> {
            if(TextUtils.isEmpty(password.getEditText().getText().toString())){
                password.setError("Enter Password");
            }else if(TextUtils.isEmpty(email.getEditText().getText().toString())){
                email.setError("Enter Email");
            }else{
                Users user = dbHandler.login(email.getEditText().getText().toString().trim(),password.getEditText().getText().toString().trim());
                if(user != null){
                    tinyDB.putString("role",user.getRole());
                    tinyDB.putInt("id",user.getId());
                    tinyDB.putBoolean("loggedIn",true);

                    if(Objects.equals(user.getRole(), "0")){
                        startActivity(new Intent(this,AdminHomeActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(this,UserHomeActivity.class));
                        finish();
                    }
                }
            }

        });
        signUp.setOnClickListener(view -> {
            startActivity(new Intent(this,RegisterActivity.class));
            finish();
        });
        resetPassword.setOnClickListener(view -> {
            startActivity(new Intent(this,ResetPasswordActivity.class));
            finish();
        });
    }
}