package com.kerberos.movietickets.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kerberos.movietickets.R;
import com.kerberos.movietickets.dbhandler.DbHandler;
import com.kerberos.movietickets.models.Bookings;
import com.kerberos.movietickets.models.Tickets;
import com.kerberos.movietickets.utils.FileUtil;
import com.kerberos.movietickets.utils.TinyDB;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChekoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chekout);
        String movieId = getIntent().getStringExtra("id");
        DbHandler dbHandler = new DbHandler(this);
        Tickets tickets = dbHandler.getTicket(movieId);
        TextView name = findViewById(R.id.textView2);
        TextView description = findViewById(R.id.textView4);
        TextView capacity = findViewById(R.id.textView3);
        ImageView poster = findViewById(R.id.imageView5);
        MaterialButton book = findViewById(R.id.book);
        TextInputLayout seats = findViewById(R.id.seats);
        TinyDB tinyDB = new TinyDB(this);

        //set the values to the UI
        name.setText(tickets.getMovie());
        description.setText(tickets.getDescription());
        capacity.setText(" Remaining seats: " + tickets.getCapacity());
        poster.setImageBitmap(FileUtil.getImage(this,tickets.getImage()));
        if(tickets.getCapacity() < 1){
            book.setEnabled(false);
        }

        book.setOnClickListener(view -> {
            if (TextUtils.isEmpty(seats.getEditText().getText())) {
                seats.setError("Enter the number of people attending");
            }else if(Integer.parseInt(seats.getEditText().getText().toString()) < 1){
                seats.setError("Cannot be 0");
            }else  if(tickets.getCapacity() < Integer.parseInt(seats.getEditText().getText().toString())){
                seats.setError("Not Enough Seats");
            }
            else{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = sdf.format(new Date());
                Bookings booking = new Bookings();
                booking.setDate(currentDate);
                booking.setMovie(String.valueOf(tickets.getId()));
                booking.setSeats(seats.getEditText().getText().toString());
                booking.setPrice(Integer.parseInt(seats.getEditText().getText().toString()) * tickets.getPrice());
                booking.setMovieImage(tickets.getImage());
                booking.setUserId(tinyDB.getInt("id"));
                dbHandler.addBooking(booking);
                dbHandler.updateCapacity(tickets.getCapacity() - Integer.parseInt(seats.getEditText().getText().toString()),movieId);
            }

        });

    }
}