package com.tm91.demo.exception;

public class InvalidVehicleNumberException extends ParkingException {

    public InvalidVehicleNumberException(String msg) {
        super(msg);
    }

    public static InvalidVehicleNumberException vehicleNumberNotFound(String vehicleNumber) {
        throw new InvalidVehicleNumberException("Two wheeler with registration number " + vehicleNumber + " not found");
    }
}
