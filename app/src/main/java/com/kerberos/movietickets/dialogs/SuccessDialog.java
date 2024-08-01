package com.kerberos.movietickets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kerberos.movietickets.R;
import com.kerberos.movietickets.dbhandler.DbHandler;
import com.kerberos.movietickets.models.Bookings;
import com.kerberos.movietickets.models.Tickets;
import com.kerberos.movietickets.utils.FileUtil;

public class SuccessDialog extends Dialog {
    Bookings bookings;
    Context mContext;
    public SuccessDialog(@NonNull Context context, Bookings bookings) {
        super(context);
        setCancelable(true);
        this.bookings = bookings;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_dialog);
        TextView name = findViewById(R.id.name);
        DbHandler db = new DbHandler(mContext);
        ImageView imageView = findViewById(R.id.image);
        TextView seats = findViewById(R.id.textView6);
        TextView desc = findViewById(R.id.textView7);
        Tickets tickets = db.getTicket(bookings.getMovie());
        name.setText(tickets.getMovie());
        seats.setText("Booked " + bookings.getSeats() + " seats");
        desc.setText(tickets.getDescription());

        imageView.setImageBitmap(FileUtil.getImage(mContext,tickets.getImage()));
    }
}
