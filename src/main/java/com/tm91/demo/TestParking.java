package com.tm91.demo;

import com.tm91.demo.boundary.ParkingLot;
import com.tm91.demo.entity.Ticket;
import com.tm91.demo.entity.Vehicle;
import com.tm91.demo.entity.VehicleSize;

public class TestParking {

    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.newParkingLot(5, 10);

        Vehicle vehicle = new Vehicle("Mh12", VehicleSize.TWO_WHEELER);

        Ticket ticket = parkingLot.park(vehicle);
        System.out.println(ticket);

        Vehicle vehicle2 = new Vehicle("Mh13", VehicleSize.TWO_WHEELER);
        Ticket ticket2 = parkingLot.park(vehicle2);
        System.out.println(ticket2);

        parkingLot.displayParkingStatus();
    }
}
