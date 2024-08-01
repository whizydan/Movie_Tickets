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

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout email, phone, password, address, name, answer;
    MaterialButton signUp;
    TextView signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);
        answer = findViewById(R.id.answer);
        TinyDB tinyDB = new TinyDB(this);
        DbHandler dbHandler = new DbHandler(this);

        signUp.setOnClickListener(view -> {
            if (TextUtils.isEmpty(email.getEditText().getText().toString())){
                email.setError("Enter Email");
            }else if (TextUtils.isEmpty(address.getEditText().getText().toString())){
                address.setError("Enter Address");
            }else if (TextUtils.isEmpty(phone.getEditText().getText().toString())){
                phone.setError("Enter Phone");
            }else if (TextUtils.isEmpty(password.getEditText().getText().toString())){
                password.setError("Enter Password");
            }else if (TextUtils.isEmpty(name.getEditText().getText().toString())){
                name.setError("Enter Name");
            }else if (TextUtils.isEmpty(answer.getEditText().getText().toString())){
                name.setError("Enter a security code you will use to reset password");
            }else{
                Users user = new Users();
                user.setSecurityAnswer(answer.getEditText().getText().toString());
                user.setAddress(address.getEditText().getText().toString());
                user.setEmail(email.getEditText().getText().toString());
                user.setName(name.getEditText().getText().toString());
                user.setPassword(password.getEditText().getText().toString());
                user.setRole("1");
                user.setPhone(phone.getEditText().getText().toString());
                dbHandler.addUser(user);
                tinyDB.putString("role",user.getRole());
                tinyDB.putInt("id",user.getId());
                tinyDB.putBoolean("loggedIn",true);

                startActivity(new Intent(this,UserHomeActivity.class));
                finish();
            }

        });
        signIn.setOnClickListener(view -> {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}