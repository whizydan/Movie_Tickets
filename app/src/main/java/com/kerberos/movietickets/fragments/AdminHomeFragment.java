package com.kerberos.movietickets.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.kerberos.movietickets.R;
import com.kerberos.movietickets.activities.AdminHomeActivity;
import com.kerberos.movietickets.adapters.MoviesAdapter;
import com.kerberos.movietickets.dbhandler.DbHandler;
import com.kerberos.movietickets.dialogs.AddTicketDialog;
import com.kerberos.movietickets.models.Tickets;

import java.util.ArrayList;

public class AdminHomeFragment extends Fragment {

    MoviesAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Tickets> tickets = new ArrayList<>();
    DbHandler dbHandler;
    Context mContext;
    LottieAnimationView animation;
    public AdminHomeFragment(Context context) {
        // Required empty public constructor
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        dbHandler = new DbHandler(mContext);
        ExtendedFloatingActionButton addTicket = view.findViewById(R.id.add_ticket);
        animation = view.findViewById(R.id.animation);
        tickets = dbHandler.getTickets();
        adapter = new MoviesAdapter(tickets,mContext);

        if(tickets.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            animation.setVisibility(View.VISIBLE);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        //recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        addTicket.setOnClickListener(view1 -> {
            startActivity(new Intent(mContext, AddTicketDialog.class));
        });
    }
    void setAdapter(){
        tickets.clear();
        tickets = dbHandler.getTickets();
        adapter = new MoviesAdapter(tickets,mContext);
        recyclerView.setAdapter(adapter);
        if(tickets.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            animation.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            animation.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }
}