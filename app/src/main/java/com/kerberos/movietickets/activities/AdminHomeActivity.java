package com.kerberos.movietickets.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kerberos.movietickets.R;
import com.kerberos.movietickets.fragments.AdminAccountsFragment;
import com.kerberos.movietickets.fragments.AdminHomeFragment;
import com.kerberos.movietickets.utils.TinyDB;

public class AdminHomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final int REQUEST_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        ImageView logout = findViewById(R.id.logout);

        logout.setOnClickListener(view -> {
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.putString("role","");
            tinyDB.putInt("id",0);
            tinyDB.putBoolean("loggedIn",false);
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        });
        // Check if the permissions are already granted
        if (checkPermissions()) {
            // Permissions are already granted, you can proceed with your logic here.
        } else {
            // Request permissions if not granted
            requestPermissions();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, new AdminHomeFragment(this)).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, new AdminHomeFragment(AdminHomeActivity.this)).commit();
                }else if(item.getItemId() == R.id.accounts){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragHolder, new AdminAccountsFragment()).commit();
                }
                return false;
            }
        });
    }
    private boolean checkPermissions() {
        int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED;
    }
    // Request permissions
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        }
    }
    // Handle permission request results
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, you can proceed with your logic here.
            } else {
                // Permissions denied. Handle this as needed.
            }
        }
    }
}