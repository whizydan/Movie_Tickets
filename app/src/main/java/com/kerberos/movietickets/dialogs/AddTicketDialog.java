package com.kerberos.movietickets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kerberos.movietickets.R;
import com.kerberos.movietickets.dbhandler.DbHandler;
import com.kerberos.movietickets.models.Tickets;
import com.kerberos.movietickets.utils.FileUtil;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created with Android Studio
 *
 * @Author: Kerberos
 * @Date: 01/11/2023
 * @Project: Movie Tickets
 */
public class AddTicketDialog extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private static final String IMAGE_FILE_NAME = null;
    Boolean imageSelected;
    String destinationName;

    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ticket);
        DbHandler dbHandler = new DbHandler(this);
        dbHandler.open();
        TextInputLayout name = findViewById(R.id.name);
        TextInputLayout validTo = findViewById(R.id.valid_to);
        TextInputLayout description = findViewById(R.id.description);
        TextInputLayout capacity = findViewById(R.id.capacity);
        TextInputLayout price = findViewById(R.id.price);
        RadioGroup status = findViewById(R.id.status);
        MaterialButton submit = findViewById(R.id.materialButton);
        FloatingActionButton image = findViewById(R.id.image);
        poster = findViewById(R.id.imageView3);
        final Boolean[] isLive = new Boolean[1];
        final Boolean[] selected = {false};
        imageSelected = false;

        image.setOnClickListener(view -> {
            //choose the poster image
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selected[0] = true;
                if(radioGroup.getCheckedRadioButtonId() == R.id.live){
                    isLive[0] = true;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.inactive) {
                    isLive[0] = false;
                }
            }
        });

        submit.setOnClickListener(view -> {
            if(selected[0] && imageSelected){
                if(TextUtils.isEmpty(name.getEditText().getText())){
                    name.setError("Enter the name of the show");
                }else if(TextUtils.isEmpty(validTo.getEditText().getText())){
                   validTo.setError("Select the Deadline for registration");
                }if(TextUtils.isEmpty(description.getEditText().getText())){
                    description.setError("Enter a synopsis of the show");
                }if(TextUtils.isEmpty(capacity.getEditText().getText())){
                    capacity.setError("Enter the Capacity for this booking");
                }if(TextUtils.isEmpty(price.getEditText().getText())){
                    price.setError("Enter the Price for one ticket");
                }else{
                    Tickets movie = new Tickets();
                    movie.setCapacity(Integer.parseInt(capacity.getEditText().getText().toString()));
                    movie.setMovie(name.getEditText().getText().toString());
                    movie.setDescription(description.getEditText().getText().toString());
                    movie.setPrice(Integer.parseInt(price.getEditText().getText().toString()));
                    movie.setStatus(isLive[0].toString());
                    movie.setValidTo(validTo.getEditText().getText().toString());
                    movie.setImage(destinationName);
                    dbHandler.addTicket(movie);
                }
            }else{
                Toast.makeText(this, "Choose Availability Status", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                imageSelected = true;
                Uri imageUri = data.getData();
                destinationName = getFileName(imageUri);
                try {
                    ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(imageUri, "r");
                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                    Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                    parcelFileDescriptor.close();
                    poster.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                try {
                    FileUtil.copyImageToPrivateFolder(AddTicketDialog.this, imageUri, destinationName);
                    Toast.makeText(AddTicketDialog.this, destinationName, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private String getFileName(Uri uri) {
        String fileName = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                fileName = cursor.getString(displayNameIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return fileName;
    }
}
