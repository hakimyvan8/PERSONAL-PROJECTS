package com.igor.mamba.User;

public class Driver {

    private String DriverId;
    private String DriverFullname;
    private String DriverPhoneNumber;
    private String DriverLincence;
    private String DriverImage;
    public Driver(String driverId, String driverFullname, String driverPhoneNumber, String driverLincence, String driverImage, String driverEmail) {
        DriverId = driverId;
        DriverFullname = driverFullname;
        DriverPhoneNumber = driverPhoneNumber;
        DriverLincence = driverLincence;
        DriverImage = driverImage;
        DriverEmail = driverEmail;
    }
    public String getDriverId() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        DriverId = driverId;
    }

    public String getDriverFullname() {
        return DriverFullname;
    }

    public void setDriverFullname(String driverFullname) {
        DriverFullname = driverFullname;
    }

    public String getDriverPhoneNumber() {
        return DriverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        DriverPhoneNumber = driverPhoneNumber;
    }

    public String getDriverLincence() {
        return DriverLincence;
    }

    public void setDriverLincence(String driverLincence) {
        DriverLincence = driverLincence;
    }

    public String getDriverImage() {
        return DriverImage;
    }

    public void setDriverImage(String driverImage) {
        DriverImage = driverImage;
    }

    public String getDriverEmail() {
        return DriverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        DriverEmail = driverEmail;
    }

    private String DriverEmail;



}
