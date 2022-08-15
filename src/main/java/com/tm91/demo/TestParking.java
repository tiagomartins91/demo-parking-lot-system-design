package com.tm91.demo;

import com.tm91.demo.boundary.ParkingLot;
import com.tm91.demo.entity.Ticket;
import com.tm91.demo.entity.Vehicle;
import com.tm91.demo.entity.VehicleSize;
import com.tm91.demo.entity.charge.FourWheelerWeekDayChargeStrategy;
import com.tm91.demo.entity.charge.TwoWheelerWeekDayChargeStrategy;

public class TestParking {

    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.newParkingLot(5, 10);

        Vehicle vehicle = new Vehicle("Mh12", VehicleSize.TWO_WHEELER);

        Ticket ticket = parkingLot.park(vehicle);
        System.out.println(ticket);

        Vehicle vehicle2 = new Vehicle("Mh13", VehicleSize.FOUR_WHEELER);
        Ticket ticket2 = parkingLot.park(vehicle2);
        System.out.println(ticket2);

        parkingLot.displayParkingStatus();

        int cost1 = parkingLot.unPark(ticket, new TwoWheelerWeekDayChargeStrategy());
        System.out.println(cost1);

        int cost2 = parkingLot.unPark(ticket2, new FourWheelerWeekDayChargeStrategy());
        System.out.println(cost2);

        parkingLot.displayParkingStatus();
    }
}
