package com.kerberos.movietickets;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import com.kerberos.movietickets.dbhandler.DbHandler;
import com.kerberos.movietickets.utils.TinyDB;

import java.util.Objects;
import java.util.Random;

public class Starter extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TinyDB tinyDB = new TinyDB(this);
        DbHandler dbHandler = new DbHandler(this);
        if(Objects.equals(tinyDB.getString("init"), "")){
            dbHandler.createTables();
            dbHandler.addDummyData();
            tinyDB.putString("init","1");
        }
        /*
        // Zoom in animation
        ScaleAnimation zoomIn = new ScaleAnimation(1f, 2f, 1f, 2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        zoomIn.setDuration(2000); // Adjust the duration as needed
        zoomIn.setRepeatMode(Animation.REVERSE);
        zoomIn.setRepeatCount(Animation.INFINITE);

        // Zoom out animation
        ScaleAnimation zoomOut = new ScaleAnimation(2f, 1f, 2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        zoomOut.setDuration(2000); // Adjust the duration as needed
        zoomOut.setRepeatMode(Animation.REVERSE);
        zoomOut.setRepeatCount(Animation.INFINITE);

        // Create an AnimationSet to combine both animations
        AnimationSet zoomInOut = new AnimationSet(true);
        zoomInOut.addAnimation(zoomIn);
        zoomInOut.addAnimation(zoomOut);
        zoomInOut.setInterpolator(new LinearInterpolator());

        imageView.startAnimation(zoomInOut);
        */
    }
}