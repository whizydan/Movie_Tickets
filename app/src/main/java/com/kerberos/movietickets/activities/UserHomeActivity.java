package com.kerberos.movietickets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.kerberos.movietickets.R;
import com.kerberos.movietickets.adapters.MoviesAdapter;
import com.kerberos.movietickets.dbhandler.DbHandler;
import com.kerberos.movietickets.models.Tickets;
import com.kerberos.movietickets.utils.ImagePagerAdapter;
import com.kerberos.movietickets.utils.TinyDB;

import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ImagePagerAdapter imagePagerAdapter;
    private int[] images = {R.drawable.download, R.drawable.best, R.drawable.joey};
    private int currentPage = 0;
    private static final long AUTO_SWIPE_DELAY = 3000; // Auto-swipe interval in milliseconds
    private Handler handler;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        recyclerView = findViewById(R.id.recyclerView);
        viewPager = findViewById(R.id.viewPager);
        imagePagerAdapter = new ImagePagerAdapter(this, images);
        viewPager.setAdapter(imagePagerAdapter);
        ImageView logout = findViewById(R.id.logout);

        logout.setOnClickListener(view -> {
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.putString("role","");
            tinyDB.putInt("id",0);
            tinyDB.putBoolean("loggedIn",false);
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        });

        handler = new Handler();
        startAutoSwipe();

        DbHandler dbHandler = new DbHandler(this);
        ArrayList<Tickets> movies;
        movies = dbHandler.getTickets();

        MoviesAdapter adapter = new MoviesAdapter(movies,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }
    private void startAutoSwipe() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPage == images.length - 1) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                viewPager.setCurrentItem(currentPage, true);
                startAutoSwipe(); // Continue auto-swipe
            }
        }, AUTO_SWIPE_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove handler callbacks to prevent memory leaks
        handler.removeCallbacksAndMessages(null);
    }
}