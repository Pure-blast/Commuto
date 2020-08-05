package com.sandeep.Commuto.Model;
import javafx.application.Platform;

import java.sql.*;

public class Datasource {

    public static final String DB_NAME = "details.db";

    public static final String CONNECTION_STRING = "jdbc:sqlite:/Users/batsandeep/Documents/Java Projects/Commuto™/"+DB_NAME;

    //Table of Users
    public static final String TABLE_USERS = "user_details";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PHONE = "phone";
    public static final String COL_WALLET = "wallet_balance";

    //Table of Drivers
    public static final String TABLE_DRIVERS = "driver_details";
    public static final String COL_d_ID = "driver_id";
    public static final String COL_d_NAME = "name";
    public static final String COL_d_PHONE = "phone";
    public static final String COL_d_RATING = "rating";
    public static final String COL_d_CARMODEL = "car_model";
    public static final String COL_d_CARID = "car_id";
    public static final String COL_d_LOCATION = "location";
    public static final String COL_d_STATUS = "status";

    //Table of Trips
    public static final String TABLE_TRIPS = "trip_details";
    public static final String COL_PICKUPS = "pickup";
    public static final String COL_DESTINATION = "destination";
    public static final String COL_ETA = "eta";
    public static final String COL_COST = "cost";


    private Connection conn;

    private static Datasource instance = new Datasource();


    private Datasource() {

    }

    public static Datasource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to Database: "+ e.getMessage());
            return false;
        }
    }


    public void close() {
        try {
            if(conn!=null)
            {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: "+ e.getMessage());
        }
    }




    public boolean lg_t(String usrnm, String pswrd) {

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_USERS)) {

            User user = new User();
            while(results.next()) {
                user.setUsername(results.getString(COL_USERNAME));
                user.setPassword(results.getString(COL_PASSWORD));
                if(user.getUsername().equals(usrnm))
                {
                    if(user.getPassword().equals(pswrd))
                    {
                        return true;
                    }
                }
            }

            return false;

        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            Platform.exit();
            return false;
        }

    }

    public boolean lg_t(String usrnm) {

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_USERS)) {

            User user = new User();
            while(results.next()) {
                user.setUsername(results.getString(COL_USERNAME));
                if(user.getUsername().equals(usrnm))
                {
                        return true;
                }
            }

            return false;

        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            Platform.exit();
            return false;
        }

    }

    public boolean mail(String usrnm, String email) {

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_USERS)) {

            User user = new User();
            while(results.next()) {
                user.setUsername(results.getString(COL_USERNAME));
                user.setEmail(results.getString(COL_EMAIL));
                if(user.getUsername().equals(usrnm) && user.getEmail().equals(email))
                {
                    return true;
                }
            }

            return false;

        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            Platform.exit();
            return false;
        }

    }

    public boolean mail(String email) {

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_USERS)) {

            User user = new User();
            while(results.next()) {
                user.setEmail(results.getString(COL_EMAIL));
                if(user.getEmail().equals(email))
                {
                    return false;
                }
            }

            return true;

        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            Platform.exit();
            return false;
        }

    }

    public boolean phone(String phone) {

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_USERS)) {

            User user = new User();
            while(results.next()) {
                user.setPhone(results.getString(COL_PHONE));
                if(user.getPhone().equals(phone))
                {
                    return false;
                }
            }

            return true;

        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            Platform.exit();
            return false;
        }

    }

    public String setnm(String usrnm) {

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_USERS)) {

            User user = new User();
            while(results.next()) {
                user.setUsername(results.getString(COL_USERNAME));
                user.setName(results.getString(COL_NAME));
                if(user.getUsername().equals(usrnm))
                {
                    return user.getName();
                }
            }

            return null;

        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            Platform.exit();
            return null;
        }

    }

    public double wallet(String usrnm) {

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_USERS)) {

            User user = new User();
            while(results.next()) {
                user.setUsername(results.getString(COL_USERNAME));
                user.setWallet_balance(results.getDouble(COL_WALLET));
                if(user.getUsername().equals(usrnm))
                {
                    return user.getWallet_balance();
                }
            }

            return 0;

        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            Platform.exit();
            return 0;
        }

    }


        



}
