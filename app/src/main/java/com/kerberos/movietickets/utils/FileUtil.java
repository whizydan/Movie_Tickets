package com.kerberos.movietickets.utils;

/**
 * Created with Android Studio
 *
 * @Author: Kerberos
 * @Date: 31/10/2023
 * @Project: Movie Tickets
 */
import android.content.Context;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    /*public static void copyImageToPrivateFolder(Context context, Uri sourceUri, String destinationFileName) throws IOException {
        // Get the content resolver
        ContentResolver contentResolver = context.getContentResolver();

        // Open an input stream from the source URI
        InputStream inputStream = contentResolver.openInputStream(sourceUri);

        // Get the app's private directory
        File privateDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Create the destination file in the private directory
        File destinationFile = new File(privateDir, destinationFileName);

        // Create an output stream to the destination file
        OutputStream outputStream = new FileOutputStream(destinationFile);

        // Copy the image data
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // Close the streams
        inputStream.close();
        outputStream.close();
    }*/
    public static void copyImageToPrivateFolder(Context context, Uri sourceUri, String destinationFileName) throws IOException {
        try (InputStream in = context.getContentResolver().openInputStream(sourceUri);
             OutputStream out = context.openFileOutput(destinationFileName, Context.MODE_PRIVATE)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
    // Load an image from the app's private storage
    public static Bitmap loadImageFromPrivateStorage(Context context, String filename) {
        try {
            // Get the app's private directory
            File privateDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            // Create a File object for the image file
            File imageFile = new File(privateDir, filename);

            // Check if the file exists
            if (imageFile.exists()) {
                // Decode the file into a Bitmap
                return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            } else {
                // Handle the case where the file doesn't exist
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
            return null;
        }
    }
    public static Bitmap getImage(Context context, String filename){
        String imagePath = context.getFilesDir() + "/" + filename;

        // Check if the image file exists
        if (fileExists(imagePath)) {
            // Load the image file into a Bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            // Set the Bitmap to the ImageView
            return bitmap;
        } else {
            return null;
        }
    }
    private static boolean fileExists(String filePath) {
        // Check if the file exists at the given path
        return new File(filePath).exists();
    }
}

