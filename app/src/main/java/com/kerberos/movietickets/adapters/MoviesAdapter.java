package com.kerberos.movietickets.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kerberos.movietickets.R;
import com.kerberos.movietickets.activities.AdminMovieActivity;
import com.kerberos.movietickets.activities.ChekoutActivity;
import com.kerberos.movietickets.models.Bookings;
import com.kerberos.movietickets.models.Tickets;
import com.kerberos.movietickets.utils.FileUtil;
import com.kerberos.movietickets.utils.TinyDB;

import java.util.ArrayList;
import java.util.Objects;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
	private ArrayList<Tickets> moviesList;
	private Context mContext;
	TinyDB tinyDB;

	public MoviesAdapter(ArrayList<Tickets> moviesList, Context mContext) {
		this.mContext = mContext;
		this.moviesList = moviesList;
		tinyDB = new TinyDB(mContext);
	}

	// This method creates a new ViewHolder object for each item in the RecyclerView
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// Inflate the layout for each item and return a new ViewHolder object
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
		return new MyViewHolder(itemView);
	}

	// This method returns the total 
	// number of items in the data set
	@Override
	public int getItemCount() {
		return moviesList.size();
	}

	// This method binds the data to the ViewHolder object 
	// for each item in the RecyclerView
	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		Tickets currentMovie = moviesList.get(position);
		holder.name.setText(currentMovie.getMovie());
		holder.description.setText(currentMovie.getDescription());
		holder.validUpto.setText(currentMovie.getValidTo());
		holder.seats.setText("Seats Remaining: " + currentMovie.getCapacity());
		holder.price.setText("$ " + currentMovie.getPrice());
		if(currentMovie.getCapacity() > 100){
			holder.seats.setTextColor(mContext.getResources().getColor(R.color.green));
		}
		// Load the image from the app's private storage
		Bitmap image = FileUtil.getImage(mContext, currentMovie.getImage());
		if (image != null) {
			// Do something with the loaded image, like displaying it in an ImageView.
			// For example, if you have an ImageView with the id "imageView":
			holder.poster.setImageBitmap(image);
		}

		holder.itemView.setOnClickListener(view -> {
			if(Objects.equals(tinyDB.getString("role"), "0")){
				//redirect the admin to see bookings for this current movie
				Intent intent = new Intent(mContext, AdminMovieActivity.class);
				intent.putExtra("id",String.valueOf(currentMovie.getId()));
				mContext.startActivity(intent);
			}else{
				//redirect the user to book for this event
				Intent intent = new Intent(mContext, ChekoutActivity.class);
				intent.putExtra("id",String.valueOf(currentMovie.getId()));
				mContext.startActivity(intent);
			}
		});
	}

	// This class defines the ViewHolder object for each item in the RecyclerView
	public static class MyViewHolder extends RecyclerView.ViewHolder {
		private TextView name, price, validUpto, seats, description;
		ImageView poster;
		public MyViewHolder(View itemView) {
			super(itemView);
			name = itemView.findViewById(R.id.movie_name);
			poster = itemView.findViewById(R.id.imageView);
			price = itemView.findViewById(R.id.price);
			validUpto = itemView.findViewById(R.id.deadline);
			seats = itemView.findViewById(R.id.capacity);
			description = itemView.findViewById(R.id.description);
		}
	}
}
