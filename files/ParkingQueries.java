/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leftq
 */
public class ParkingQueries {

    private static final String URL = "jdbc:mysql://localhost/parking?characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection connection = null;
    private PreparedStatement showParkingHistory = null;
    private PreparedStatement showTodaysCarHistory = null;
    private PreparedStatement showParkingLoad = null;
    private PreparedStatement showDayIncome = null;
    private PreparedStatement searchCarByPlate = null;
    private PreparedStatement searchCarByArrival = null;    
    private PreparedStatement insertNewCar = null;
    private PreparedStatement insertNewCarNow = null;
    private PreparedStatement departCar = null;
    private PreparedStatement departCarNow = null;
    private PreparedStatement newDay = null;
    private PreparedStatement showDate = null;
    private PreparedStatement showTime = null;
    
    public ParkingQueries() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            showParkingHistory = connection.prepareStatement("SELECT * FROM parking.cars");
//            showTodaysHistory = connection.prepareStatement("SELECT * FROM parking.cars WHERE arrival >= CURDATE()");
            showTodaysCarHistory = connection.prepareStatement("SELECT * FROM parking.cars WHERE departure IS NULL and plate = ?");
            showParkingLoad = connection.prepareStatement("SELECT COUNT(*) FROM parking.cars WHERE departure IS NULL");
            showDayIncome = connection.prepareStatement("SELECT SUM(cost) FROM parking.cars WHERE departure >= CURDATE() and departure <= NOW()");
            searchCarByPlate = connection.prepareStatement("SELECT * FROM parking.cars WHERE plate = ?");
            searchCarByArrival = connection.prepareStatement("SELECT * FROM parking.cars WHERE arrival >= ? AND "
                    + "arrival < ? + INTERVAL 1 DAY");            
            insertNewCar = connection.prepareStatement("INSERT INTO parking.cars(plate,arrival) VALUES (?,?)");
            insertNewCarNow = connection.prepareStatement("INSERT INTO parking.cars(plate,arrival) VALUES (?,now())");
            departCar = connection.prepareStatement("UPDATE parking.cars SET departure = ? WHERE plate = ? AND departure IS NULL");
            departCarNow = connection.prepareStatement("UPDATE parking.cars SET departure = now() WHERE plate = ? AND departure IS NULL");
            newDay = connection.prepareStatement("UPDATE parking.cars SET departure = now() WHERE departure IS NULL");
            showDate = connection.prepareStatement("SELECT date_format(now(), \"%a, %d %b %Y\") AS DATE");
            showTime = connection.prepareStatement("SELECT date_format(now(), \"%H:%i\") AS TIME");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Υπάρχει πρόβλημα με τη βάση...");
            System.exit(1);
        }//try-catch
    }//ParkingQueries
    
    public List<Cars> getAllCars() {
        List<Cars> results = null;
        ResultSet resultSet = null;
        try {            
            resultSet = showParkingHistory.executeQuery();
            results = new ArrayList<Cars>();
            while (resultSet.next()) {
                Cars car = new Cars();
                car.setIdCars(resultSet.getInt("IdCars"));
                car.setPlate(resultSet.getString("Plate"));
                car.setArrival(resultSet.getTimestamp("Arrival"));
                car.setDeparture(resultSet.getTimestamp("Departure"));
                car.setCost(resultSet.getFloat("Cost"));
                results.add(car);
            }//while
        } catch (SQLException ex) {
            ex.printStackTrace();
        }//try-catch
        finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }
        }//finally       
        return results;
    }//getAllCars

    public List<Cars> getTodaysCar(String plate) {
        List<Cars> results = null;
        ResultSet resultSet = null;
        try {   
            showTodaysCarHistory.setString(1, plate);
            resultSet = showTodaysCarHistory.executeQuery();
            results = new ArrayList<Cars>();
            while (resultSet.next()) {
                Cars car = new Cars();
                car.setIdCars(resultSet.getInt("IdCars"));
                car.setPlate(resultSet.getString("Plate"));
                car.setArrival(resultSet.getTimestamp("Arrival"));
                car.setDeparture(resultSet.getTimestamp("Departure"));
                car.setCost(resultSet.getFloat("Cost"));
                results.add(car);
            }//while
        } catch (SQLException ex) {
            ex.printStackTrace();
        }//try-catch
        finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }
        }//finally       
        return results;
    }//getTodaysCar    
    
    public int getParkedCars() {
        int result = 0;
        ResultSet resultSet = null;        
        try {
            resultSet = showParkingLoad.executeQuery();
            while (resultSet.next()) 
                result = resultSet.getInt("COUNT(*)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }//try-catch
        finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }
        }//finally       
        return result;
    }//getParkedCars
    
    public float getCurrentEarnings() {
        float result = 0;
        ResultSet resultSet = null;        
        try {
            resultSet = showDayIncome.executeQuery();
            while (resultSet.next())             
                result = resultSet.getFloat("SUM(cost)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }//try-catch
        finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }
        }//finally       
        return result;
    }//getCurrentEarnings
    
    public List<Cars> getCarByPlate(String plate) {
        List<Cars> results = null;
        ResultSet resultSet = null;
        try {
            searchCarByPlate.setString(1, plate);
            resultSet = searchCarByPlate.executeQuery();
            results = new ArrayList<Cars>();
            while (resultSet.next()) {
                Cars car = new Cars();
                car.setIdCars(resultSet.getInt("IdCars"));
                car.setPlate(resultSet.getString("Plate"));
                car.setArrival(resultSet.getTimestamp("Arrival"));
                car.setDeparture(resultSet.getTimestamp("Departure"));
                car.setCost(resultSet.getFloat("Cost"));
                results.add(car);
            }//while
        } catch (SQLException ex) {
            ex.printStackTrace();
        }//try-catch
        finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }
        }//finally       
        return results;
    }//getCarByPlate
    
    public List<Cars> getCarByArrival(Date date) {
        List<Cars> results = null;
        ResultSet resultSet = null;
        try {
            searchCarByArrival.setDate(1, date);
            searchCarByArrival.setDate(2, date);
            resultSet = searchCarByArrival.executeQuery();
            results = new ArrayList<Cars>();
            while (resultSet.next()) {
                Cars car = new Cars();
                car.setIdCars(resultSet.getInt("IdCars"));
                car.setPlate(resultSet.getString("Plate"));
                car.setArrival(resultSet.getTimestamp("Arrival"));
                car.setDeparture(resultSet.getTimestamp("Departure"));
                car.setCost(resultSet.getFloat("Cost"));
                results.add(car);
            }//while
        } catch (SQLException ex) {
            ex.printStackTrace();
        }//try-catch
        finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }
        }//finally       
        return results;
    }//getCarByArrival

    public int addCar(String plate, int hour, int minutes) {
        int result = 0;
        LocalDate date = java.time.LocalDate.now();
        Timestamp arrival = Timestamp.valueOf(date.atTime(hour, minutes));
        try {
            insertNewCar.setString(1, plate);
            insertNewCar.setTimestamp(2, arrival);
            result = insertNewCar.executeUpdate();
        } //addPerson
        catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }
        return result;
    }//addCar

    public int addCarNow(String plate) {
        int result = 0;
        try {
            insertNewCarNow.setString(1, plate);
            result = insertNewCarNow.executeUpdate();
        } //addPerson
        catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }
        return result;
    }//addCarNow  
    
    public int exitCar(String plate, int hour, int minutes) {
        int result = 0;
        LocalDate date = java.time.LocalDate.now();
        Timestamp departure = Timestamp.valueOf(date.atTime(hour, minutes));
        try {
            departCar.setTimestamp(1, departure);
            departCar.setString(2, plate);
            result = departCar.executeUpdate();
        } //addPerson
        catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }
        return result;
    }//exitCar

    public int exitCarNow(String plate) {
        int result = 0;
        try {
            departCarNow.setString(1, plate);
            result = departCarNow.executeUpdate();
        } //addPerson
        catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }
        return result;
    }//exitCarNow    

    public int startNewDay() {
        int result = 0;
        try {
            result = newDay.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }//try-catch      
        return result;
    }//startNewDay
    
    public String getDate() {
        String result = "";
        ResultSet resultSet = null;        
        try {
            resultSet = showDate.executeQuery();
            while (resultSet.next())
                result = resultSet.getString("DATE");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }//try-catch
        finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }
        }//finally       
        return result;
    }//getDate

    public String getTime() {
        String result = "";
        ResultSet resultSet = null;        
        try {
            resultSet = showTime.executeQuery();
            while (resultSet.next())
                result = resultSet.getString("TIME");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }//try-catch
        finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }
        }//finally       
        return result;
    }//getTime
    
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//close
    
}
