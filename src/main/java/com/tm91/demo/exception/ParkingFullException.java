package com.tm91.demo.exception;

public class ParkingFullException extends ParkingException {

    public ParkingFullException(String msg) {
        super(msg);
    }

    public static ParkingFullException noEmptySlotsAvailable() {
        throw new ParkingFullException("No Empty Slot available");
    }
}
