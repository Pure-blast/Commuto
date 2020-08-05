package com.sandeep.Commuto;

import com.sandeep.Commuto.Model.Location;
import com.sandeep.Commuto.Model.Trip;
import com.sandeep.Commuto.Model.User;
import com.sandeep.Commuto.Model.d_driver;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ZYX {

    private Connection connect() {
        String url = "jdbc:sqlite:/Users/batsandeep/Documents/Java Projects/Commuto™/details.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void updatePassword(String username, String password) {
        String sql = "UPDATE user_details SET password = (?) WHERE username = (?)";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, password);
            pstmt.setString(2, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRating(String car_id, double ratin) {
        String sql = "UPDATE driver_details SET rating = (?) WHERE car_id = (?)";
        String sql2 = "SELECT * FROM driver_details";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql2)) {

            d_driver d = new d_driver();
            DecimalFormat df = new DecimalFormat("0.00");

            while(rs.next()) {
                d.setCar_id(rs.getString("car_id"));
                d.setRating(rs.getDouble("rating"));
                if(d.getCar_id().equals(car_id))
                {
                    double f = (d.getRating()+ratin)/2.00;
                    pstmt.setDouble(1, Double.parseDouble(df.format(f)));
                    pstmt.setString(2, car_id);
                    pstmt.executeUpdate();
                }
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateWalletBalance(String username, Double amtAdd) {
        String sql = "UPDATE user_details SET wallet_balance = (?) WHERE username = (?)";
        String sql2 = "SELECT * FROM user_details";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql2)) {

            User user = new User();

            while(rs.next()) {
                user.setUsername(rs.getString("username"));
                user.setWallet_balance(rs.getDouble("wallet_balance"));
                if(user.getUsername().equals(username))
                {
                    pstmt.setDouble(1, amtAdd);
                    pstmt.setString(2, username);
                    pstmt.executeUpdate();
                }
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String setLabelasName(String username) {
        String sql = "SELECT * FROM user_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            User user = new User();
            while(rs.next()) {
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                if(user.getUsername().equals(username))
                {
                    return user.getName();
                }
            }

            return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public double getWalletBalance(String username) {
        String sql = "SELECT * FROM user_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            User user = new User();
            while(rs.next()) {
                user.setUsername(rs.getString("username"));
                user.setWallet_balance(rs.getDouble("wallet_balance"));
                if(user.getUsername().equals(username))
                {
                    DecimalFormat df2 = new DecimalFormat("###.##");
                    return Double.parseDouble(df2.format(user.getWallet_balance()));
                }
            }

            return 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public double getTripCost(String pickup, String destination) {
        String sql = "SELECT * FROM trip_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            Trip trip = new Trip();
            while(rs.next()) {
                trip.setPickup(rs.getString("pickup"));
                trip.setDestination(rs.getString("destination"));
                trip.setCost(rs.getDouble("cost"));
                if(trip.getPickup().equals(pickup) && trip.getDestination().equals(destination))
                {
                    return trip.getCost();
                }
            }

            return 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public String getTripETA(String pickup, String destination) {
        String sql = "SELECT * FROM trip_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            Trip trip = new Trip();
            while(rs.next()) {
                trip.setPickup(rs.getString("pickup"));
                trip.setDestination(rs.getString("destination"));
                trip.setEta(rs.getString("eta"));
                if(trip.getPickup().equals(pickup) && trip.getDestination().equals(destination))
                {
                    return trip.getEta();
                }
            }

            return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getDriverName(int driver_id) {
        String sql = "SELECT * FROM driver_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            d_driver d = new d_driver();
            while(rs.next()) {
                d.setDriver_id(rs.getInt("driver_id"));
                d.setName(rs.getString("name"));
                if(d.getDriver_id() == driver_id)
                {
                    return d.getName();
                }
            }

            return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public String getCarModel(int driver_id) {
        String sql = "SELECT * FROM driver_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            d_driver d = new d_driver();
            while(rs.next()) {
                d.setDriver_id(rs.getInt("driver_id"));
                d.setCar_model(rs.getString("car_model"));
                if(d.getDriver_id() == driver_id)
                {
                    return d.getCar_model();
                }
            }

            return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getDriverPhone(int driver_id) {
        String sql = "SELECT * FROM driver_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            d_driver d = new d_driver();
            while(rs.next()) {
                d.setDriver_id(rs.getInt("driver_id"));
                d.setPhone(rs.getString("phone"));
                if(d.getDriver_id() == driver_id)
                {
                    return d.getPhone();
                }
            }

            return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getCarID(int driver_id) {
        String sql = "SELECT * FROM driver_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            d_driver d = new d_driver();
            while(rs.next()) {
                d.setDriver_id(rs.getInt("driver_id"));
                d.setCar_id(rs.getString("car_id"));
                if(d.getDriver_id() == driver_id)
                {
                    return d.getCar_id();
                }
            }

            return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public double getDriverRating(int did) {
        String sql = "SELECT * FROM driver_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            d_driver d = new d_driver();
            while(rs.next()) {
                d.setDriver_id(rs.getInt("driver_id"));
                d.setRating(rs.getDouble("rating"));
                if(d.getDriver_id() == did)
                {
                    return d.getRating();
                }
            }

            return 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }


    public void insertNewUserPart1(String name, String email, String phone) {
        String sql = "INSERT INTO user_details(name,email,phone,wallet_balance, username, password) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setDouble(4,0.00);
            pstmt.setString(5, "new_user");
            pstmt.setString(6, "new_user");

            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertNewUserPart2(String username, String password, String email) {
        String sql = "UPDATE user_details SET username=(?), password=(?) WHERE email = (?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    pstmt.setString(3, email);
                    pstmt.executeUpdate();

        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public int getDriverforUser(String pickupLocation, String destination) {
        String sql = "SELECT * FROM driver_details ORDER BY rating DESC";
        String sql2 = "UPDATE driver_details SET (status, location) = (?, ?) WHERE driver_id = (?)";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql2);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            d_driver d = new d_driver();
            while(rs.next()) {
                d.setLocation(rs.getString("location"));
                d.setDriver_id(rs.getInt("driver_id"));
                d.setStatus(rs.getString("status"));
                if(d.getLocation().equals(pickupLocation) && d.getStatus().equals("FREE"))
                {
                    pstmt.setString(1,"BUSY" );
                    pstmt.setString(2, destination);
                    pstmt.setInt(3, d.getDriver_id());
                    pstmt.executeUpdate();
                    return d.getDriver_id();
                }
            }

            return 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public double getCostFromLocation(String location1, String location2) {

        String sql = "SELECT * FROM location_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            Location p = new Location();
            Location d = new Location();
            while(rs.next()) {
                p.setLocationName(rs.getString("location_name"));
                p.setLocationXcoordinate(rs.getDouble("locX_coordinate"));
                p.setLocationYcoordinate(rs.getDouble("locY_coordinate"));
                p.setLocationZcoordinate(rs.getDouble("locZ_coordinate"));
                if(p.getLocationName().equals(location1))
                    break;
            }
            while(rs.next()) {
                d.setLocationName(rs.getString("location_name"));
                d.setLocationXcoordinate(rs.getDouble("locX_coordinate"));
                d.setLocationYcoordinate(rs.getDouble("locY_coordinate"));
                d.setLocationZcoordinate(rs.getDouble("locZ_coordinate"));
                if(d.getLocationName().equals(location2))
                    break;
            }

            double distance = Math.sqrt(Math.pow((p.getLocationXcoordinate()-d.getLocationXcoordinate()), 2) + Math.pow(p.getLocationYcoordinate()-d.getLocationYcoordinate(), 2)+ Math.pow(p.getLocationZcoordinate()-d.getLocationZcoordinate(), 2));
            return Math.round((distance*4) + 15);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public double getETAFromLocation(String location1, String location2) {

        String sql = "SELECT * FROM location_details";

        try(Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            Location p = new Location();
            Location d = new Location();
            while(rs.next()) {
                p.setLocationName(rs.getString("location_name"));
                p.setLocationXcoordinate(rs.getDouble("locX_coordinate"));
                p.setLocationYcoordinate(rs.getDouble("locY_coordinate"));
                p.setLocationZcoordinate(rs.getDouble("locZ_coordinate"));
                if(p.getLocationName().equals(location1))
                    break;
            }
            while(rs.next()) {
                d.setLocationName(rs.getString("location_name"));
                d.setLocationXcoordinate(rs.getDouble("locX_coordinate"));
                d.setLocationYcoordinate(rs.getDouble("locY_coordinate"));
                d.setLocationZcoordinate(rs.getDouble("locZ_coordinate"));
                if(d.getLocationName().equals(location2))
                    break;
            }

            double distance = Math.sqrt(Math.pow((p.getLocationXcoordinate()-d.getLocationXcoordinate()), 2) + Math.pow(p.getLocationYcoordinate()-d.getLocationYcoordinate(), 2)+ Math.pow(p.getLocationZcoordinate()-d.getLocationZcoordinate(), 2));
            return Math.round(distance*60/80);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public void RandomizeLocations() {
        String sql = "SELECT * FROM driver_details";
        String sql2 = "UPDATE driver_details SET (status, location) = (?, ?) WHERE driver_id = (?)";

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql2);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql)) {

            d_driver d = new d_driver();
            Random rand = new Random();
            List<String> givenList = Arrays.asList("Irvington, Newark", "Central Park, Manhattan", "Crown Heights, Brooklyn", "Yankee Stadium, Upper Manhattan", "Washington Square Park", "Long Island City", "West New York");
            while(rs.next()) {
                d.setLocation(rs.getString("location"));
                d.setDriver_id(rs.getInt("driver_id"));
                d.setStatus(rs.getString("status"));


                int randomIndex = rand.nextInt(givenList.size());
                String randomElement = givenList.get(randomIndex);

                pstmt.setString(1,"FREE" );
                pstmt.setString(2, randomElement);
                pstmt.setInt(3, d.getDriver_id());
                pstmt.executeUpdate();

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
