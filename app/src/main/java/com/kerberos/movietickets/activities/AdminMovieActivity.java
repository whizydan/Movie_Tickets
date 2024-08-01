package com.kerberos.movietickets.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kerberos.movietickets.R;
import com.kerberos.movietickets.adapters.BookingsAdapter;
import com.kerberos.movietickets.dbhandler.DbHandler;
import com.kerberos.movietickets.models.Bookings;
import com.kerberos.movietickets.models.Tickets;
import com.kerberos.movietickets.utils.FileUtil;

import java.util.ArrayList;

public class AdminMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_movie);
        TextView movieName = findViewById(R.id.movie_name);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageView imageView = findViewById(R.id.imageView2);
        DbHandler dbHandler = new DbHandler(this);
        //bookings from a particular movie only
        String movieId = getIntent().getStringExtra("id");

        ArrayList<Bookings> bookings = dbHandler.getBookings(movieId);
        Tickets movies = dbHandler.getTicketByMovie(movieId);
        if(movies == null){
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }else{
            movieName.setText(movies.getMovie());
            imageView.setImageBitmap(FileUtil.getImage(this,movies.getImage()));
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(new BookingsAdapter(bookings,this));
        }


    }
}