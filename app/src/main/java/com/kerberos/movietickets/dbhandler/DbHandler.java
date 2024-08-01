package com.kerberos.movietickets.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kerberos.movietickets.dialogs.SuccessDialog;
import com.kerberos.movietickets.models.Bookings;
import com.kerberos.movietickets.models.Tickets;
import com.kerberos.movietickets.models.Users;

import java.util.ArrayList;

public class DbHandler {

    // Database and table details
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    // Tables
    private static final String USERS_TABLE = "users";
    private static final String TICKETS_TABLE = "tickets";
    private static final String BOOKINGS_TABLE = "bookings";
    // Common column names
    private static final String KEY_ID = "id";
    // Users table columns
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ROLE = "role";
    private static final String KEY_SECURITY_ANSWER = "answer";
    // Tickets table columns
    private static final String KEY_MOVIE = "movie";
    private static final String KEY_VALID_TO = "valid_to";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PRICE = "price";
    // Bookings table columns
    private static final String KEY_DATE = "date";
    private static final String KEY_SEATS = "seats";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_MOVIE_IMAGE = "movie_image";

    private static String createUserTable = "CREATE TABLE IF NOT EXISTS \"users\" (\n" +
            "\t\"id\"\tINTEGER,\n" +
            "\t\"name\"\tTEXT,\n" +
            "\t\"address\"\tTEXT,\n" +
            "\t\"email\"\tTEXT,\n" +
            "\t\"phone\"\tTEXT,\n" +
            "\t\"password\"\tTEXT,\n" +
            "\t\"role\"\tTEXT,\n" +
            "\t\"answer\"\tTEXT,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")";
    private static String createBookingTable = "CREATE TABLE IF NOT EXISTS \"bookings\" (\n" +
            "\t\"id\"\tINTEGER,\n" +
            "\t\"movie\"\tTEXT,\n" +
            "\t\"date\"\tTEXT,\n" +
            "\t\"seats\"\tTEXT,\n" +
            "\t\"price\"\tINTEGER,\n" +
            "\t\"userId\"\tINTEGER,\n" +
            "\t\"movie_image\"\tTEXT,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")";
    private static String createTicketsTable = "CREATE TABLE IF NOT EXISTS \"tickets\" (\n" +
            "\t\"id\"\tINTEGER,\n" +
            "\t\"movie\"\tTEXT,\n" +
            "\t\"valid_to\"\tTEXT,\n" +
            "\t\"image\"\tTEXT,\n" +
            "\t\"status\"\tTEXT,\n" +
            "\t\"description\"\tTEXT,\n" +
            "\t\"capacity\"\tINTEGER,\n" +
            "\t\"price\"\tINTEGER,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")";
    private static Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    public DbHandler(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }
    // Database open and close methods
    public DbHandler open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }
    // Helper class to manage the database
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Upgrade the database if needed
        }
    }
    public void createTables(){
        // Create tables here
        try{
            db.execSQL(createTicketsTable);
            db.execSQL(createUserTable);
            db.execSQL(createBookingTable);
        }catch (SQLException sqlException){
            showMessage("Failed creating tables",sqlException.getLocalizedMessage());
        }
    }
    public void addDummyData(){
        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESS,"640 20300");
        values.put(KEY_EMAIL,"doe@gmail.com");
        values.put(KEY_NAME,"John Doe");
        values.put(KEY_ROLE,"0");
        values.put(KEY_PASSWORD,"touchdown");
        values.put(KEY_PHONE,"07997255502");
        values.put(KEY_SECURITY_ANSWER,"turquoise");
        try{
            db.insert(USERS_TABLE, null, values);
        }catch (SQLException exception){
            showMessage("Could not Dummy Data", exception.getLocalizedMessage());
        }
    }
    public void addUser(Users user) {
        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESS,user.getAddress());
        values.put(KEY_EMAIL,user.getEmail());
        values.put(KEY_NAME,user.getName());
        values.put(KEY_ROLE,user.getRole());
        values.put(KEY_PASSWORD,user.getPassword());
        values.put(KEY_PHONE,user.getPhone());
        values.put(KEY_SECURITY_ANSWER,user.getSecurityAnswer());
        try{
            db.insert(USERS_TABLE, null, values);
        }catch (SQLException exception){
            showMessage("Could not Add user", exception.getLocalizedMessage());
        }
    }
    public void addBooking(Bookings booking) {
        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE,booking.getMovie());
        values.put(KEY_PRICE,booking.getPrice());
        values.put(KEY_SEATS,booking.getSeats());
        values.put(KEY_USER_ID,booking.getUserId());
        values.put(KEY_MOVIE_IMAGE,booking.getMovieImage());
        values.put(KEY_DATE,booking.getDate());
        try{
            db.insert(BOOKINGS_TABLE, null, values);
            SuccessDialog dialog = new SuccessDialog(context,booking);
            dialog.show();
        }catch (SQLException exception){
            showMessage("Could not Add booking", exception.getLocalizedMessage());
        }
    }
    public void addTicket(Tickets ticket) {
        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE,ticket.getMovie());
        values.put(KEY_PRICE,ticket.getPrice());
        values.put(KEY_DESCRIPTION,ticket.getDescription());
        values.put(KEY_IMAGE,ticket.getImage());
        values.put(KEY_STATUS,ticket.getStatus());
        values.put(KEY_VALID_TO,ticket.getValidTo());
        values.put("capacity",ticket.getCapacity());
        try{
            db.insert(TICKETS_TABLE, null, values);
            Toast.makeText(context, "Movie Added", Toast.LENGTH_SHORT).show();
        }catch (SQLException exception){
            showMessage("Could not Add Ticket", exception.getLocalizedMessage());
        }
    }
    public Users getUser(int userId) {
        Cursor cursor = db.rawQuery("select * from " + USERS_TABLE + " where id = " + userId,null);
        cursor.moveToFirst();
        Users user = new Users();
        user.setId(cursor.getInt(0));
        user.setAddress(cursor.getString(2));
        user.setEmail(cursor.getString(3));
        user.setName(cursor.getString(1));
        user.setPassword(cursor.getString(5));
        user.setPhone(cursor.getString(4));
        user.setRole(cursor.getString(6));
        user.setSecurityAnswer(cursor.getString(7));
        cursor.close();
        return user;
    }
    public Users login(String email, String password){
        Cursor cursor = db.rawQuery("select * from " + USERS_TABLE + " where " + KEY_EMAIL + " = '" + email + "' and password = '" + password + "'",null);
        if(cursor.moveToFirst()){
            Users user = new Users();
            user.setId(cursor.getInt(0));
            user.setAddress(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setName(cursor.getString(1));
            user.setPassword(cursor.getString(5));
            user.setPhone(cursor.getString(4));
            user.setRole(cursor.getString(6));
            user.setSecurityAnswer(cursor.getString(7));
            cursor.close();
            return user;
        }else{
            cursor.close();
            showMessage("Invalid","Wrong user credentials provided");
        }
        return null;
    }
    public Tickets getTicket(String ticketId) {
        Cursor cursor = db.rawQuery("select * from " + TICKETS_TABLE + " where id = '" + ticketId + "'",null);
        cursor.moveToFirst();
        Tickets tickets = new Tickets();
        tickets.setId(cursor.getInt(0));
        tickets.setDescription(cursor.getString(5));
        tickets.setMovie(cursor.getString(1));
        tickets.setImage(cursor.getString(3));
        tickets.setStatus(cursor.getString(4));
        tickets.setValidTo(cursor.getString(2));
        tickets.setPrice(cursor.getInt(7));
        tickets.setCapacity(cursor.getInt(6));
        cursor.close();
        return tickets;
    }
    public Tickets getTicketByMovie(String ticketId) {
        Cursor cursor = db.rawQuery("select * from " + TICKETS_TABLE + " where id = '" + ticketId + "'",null);
        cursor.moveToFirst();
        Tickets tickets = new Tickets();
        tickets.setId(cursor.getInt(0));
        tickets.setDescription(cursor.getString(5));
        tickets.setMovie(cursor.getString(1));
        tickets.setImage(cursor.getString(3));
        tickets.setStatus(cursor.getString(4));
        tickets.setValidTo(cursor.getString(2));
        tickets.setPrice(cursor.getInt(7));
        tickets.setCapacity(cursor.getInt(6));
        cursor.close();
        return tickets;
    }
    public Bookings getBooking(String movie) {
        Cursor cursor = db.rawQuery("select * from " + BOOKINGS_TABLE + " where movie = " + movie,null);
        cursor.moveToFirst();
        Bookings booking = new Bookings();
        booking.setId(cursor.getInt(0));
        booking.setMovie(cursor.getString(1));
        booking.setDate(cursor.getString(2));
        booking.setPrice(cursor.getInt(3));
        booking.setSeats(cursor.getString(4));
        booking.setMovieImage(cursor.getString(5));
        booking.setUserId(cursor.getInt(6));
        cursor.close();
        return booking;
    }
    public ArrayList<Users> getUsers() {
        Cursor cursor = db.rawQuery("select * from " + USERS_TABLE,null);
        ArrayList<Users> users = new ArrayList<>();
        while (cursor.moveToNext()){
            Users user = new Users();
            user.setId(cursor.getInt(0));
            user.setAddress(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setName(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            user.setPhone(cursor.getString(5));
            user.setRole(cursor.getString(6));

            users.add(user);
        }
        cursor.close();
        return users;
    }
    public ArrayList<Tickets> getTickets() {
        Cursor cursor = db.rawQuery("select * from " + TICKETS_TABLE,null);
        ArrayList<Tickets> tickets = new ArrayList<>();
        while(cursor.moveToNext()){
            Tickets ticket = new Tickets();
            ticket.setId(cursor.getInt(0));
            ticket.setDescription(cursor.getString(5));
            ticket.setMovie(cursor.getString(1));
            ticket.setImage(cursor.getString(3));
            ticket.setStatus(cursor.getString(4));
            ticket.setValidTo(cursor.getString(2));
            ticket.setPrice(cursor.getInt(7));
            ticket.setCapacity(cursor.getInt(6));
            tickets.add(ticket);
        }
        cursor.close();
        return tickets;
    }
    public ArrayList<Bookings> getBookings(String movieId) {
        Cursor cursor = db.rawQuery("select * from " + BOOKINGS_TABLE + " where movie = '" + movieId + "'",null);
        ArrayList<Bookings> bookings = new ArrayList<>();
        while (cursor.moveToNext()){
            Bookings booking = new Bookings();
            booking.setId(cursor.getInt(0));
            booking.setMovie(cursor.getString(1));
            booking.setDate(cursor.getString(2));
            booking.setPrice(cursor.getInt(3));
            booking.setSeats(cursor.getString(4));
            booking.setMovieImage(cursor.getString(5));
            booking.setUserId(cursor.getInt(6));

            bookings.add(booking);
        }
        cursor.close();
        return bookings;
    }
    public void updateUser(Users user) {
        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESS,user.getAddress());
        values.put(KEY_EMAIL,user.getEmail());
        values.put(KEY_NAME,user.getName());
        values.put(KEY_PASSWORD,user.getPassword());
        values.put(KEY_PHONE,user.getPhone());
        values.put(KEY_SECURITY_ANSWER,user.getSecurityAnswer());

        try{
            db.update(USERS_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(user.getId())});
            Toast.makeText(context,"Profile Updated",Toast.LENGTH_LONG).show();
        }catch (SQLException exception){
            showMessage("Could not Update user", exception.getLocalizedMessage());
        }
    }
    public void updateCapacity(int count,String movieId) {
        ContentValues values = new ContentValues();
        values.put("capacity",count);

        try{
            db.update(TICKETS_TABLE, values, KEY_ID + " = ?", new String[]{String.valueOf(movieId)});
        }catch (SQLException exception){
            showMessage("Could not Update user", exception.getLocalizedMessage());
        }
    }
    public void deleteUser(int userId) {
        // Delete a user from the USERS_TABLE based on their ID.
        try{
            db.delete(USERS_TABLE, KEY_ID + " = ?", new String[]{String.valueOf(userId)});
        }catch (SQLException exception){
            showMessage("Could not Delete user", exception.getLocalizedMessage());
        }
    }
    private static void showMessage(String title, String message){
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .show();
    }
    // Similar CRUD operations for tickets and bookings.

}