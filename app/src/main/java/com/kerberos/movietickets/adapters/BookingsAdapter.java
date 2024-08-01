package com.kerberos.movietickets.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kerberos.movietickets.R;
import com.kerberos.movietickets.activities.AdminMovieActivity;
import com.kerberos.movietickets.activities.ChekoutActivity;
import com.kerberos.movietickets.dbhandler.DbHandler;
import com.kerberos.movietickets.models.Bookings;
import com.kerberos.movietickets.models.Tickets;
import com.kerberos.movietickets.models.Users;
import com.kerberos.movietickets.utils.FileUtil;
import com.kerberos.movietickets.utils.TinyDB;

import java.util.ArrayList;
import java.util.Objects;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder> {
	private ArrayList<Bookings> BookingsList;
	private Context mContext;

	public BookingsAdapter(ArrayList<Bookings> BookingsList, Context mContext) {
		this.mContext = mContext;
		this.BookingsList = BookingsList;
	}

	// This method creates a new ViewHolder object for each item in the RecyclerView
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// Inflate the layout for each item and return a new ViewHolder object
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_list_item, parent, false);
		return new MyViewHolder(itemView);
	}

	// This method returns the total 
	// number of items in the data set
	@Override
	public int getItemCount() {
		return BookingsList.size();
	}

	// This method binds the data to the ViewHolder object 
	// for each item in the RecyclerView
	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		Bookings currentBooking = BookingsList.get(position);
		holder.name.setText(currentBooking.getMovie());
		DbHandler dbHandler = new DbHandler(mContext);
		Tickets tickets = dbHandler.getTicket(currentBooking.getMovie());
		//Users user = dbHandler.getUser(currentBooking.getUserId());
		holder.description.setText(tickets.getDescription());
		holder.date.setText(currentBooking.getDate());
		holder.seats.setText(currentBooking.getSeats() + " seats");
		TinyDB tinyDB = new TinyDB(mContext);
		holder.moviePoster.setImageBitmap(FileUtil.getImage(mContext,tickets.getImage()));

		holder.itemView.setOnClickListener(view -> {
			if(Objects.equals(tinyDB.getString("role"), "0")){
				//show dialog of which user movie was booked

			}
		});
	}

	// This class defines the ViewHolder object for each item in the RecyclerView
	public static class MyViewHolder extends RecyclerView.ViewHolder {
		private TextView name, seats, date, description;
		ImageView moviePoster;
		public MyViewHolder(View itemView) {
			super(itemView);
			name = itemView.findViewById(R.id.name);
			seats = itemView.findViewById(R.id.seats);
			date = itemView.findViewById(R.id.date);
			description = itemView.findViewById(R.id.description);
			moviePoster = itemView.findViewById(R.id.imageView4);
		}
	}
}
