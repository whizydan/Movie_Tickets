package com.kerberos.movietickets.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kerberos.movietickets.R;
import com.kerberos.movietickets.models.Users;
import java.util.ArrayList;
import java.util.Objects;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
	private ArrayList<Users> usersList;
	private Context mContext;

	public UsersAdapter(ArrayList<Users> usersList, Context mContext) {
		this.mContext = mContext;
		this.usersList = usersList;
	}

	// This method creates a new ViewHolder object for each item in the RecyclerView
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// Inflate the layout for each item and return a new ViewHolder object
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list_item, parent, false);
		return new MyViewHolder(itemView);
	}

	// This method returns the total 
	// number of items in the data set
	@Override
	public int getItemCount() {
		return usersList.size();
	}

	// This method binds the data to the ViewHolder object 
	// for each item in the RecyclerView
	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		Users currentUser = usersList.get(position);
		holder.name.setText(currentUser.getName());

		holder.itemView.setOnClickListener(view -> {

		});
	}

	// This class defines the ViewHolder object for each item in the RecyclerView
	public static class MyViewHolder extends RecyclerView.ViewHolder {
		private TextView name;
		ImageView eventStatusImage,eventImage;
		public MyViewHolder(View itemView) {
			super(itemView);
			name = itemView.findViewById(R.id.name);
		}
	}
}
