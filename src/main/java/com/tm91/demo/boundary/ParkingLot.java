package com.tm91.demo.boundary;

import com.tm91.demo.control.Parking;
import com.tm91.demo.entity.Slot;
import com.tm91.demo.entity.Ticket;
import com.tm91.demo.entity.Vehicle;
import com.tm91.demo.entity.VehicleSize;
import com.tm91.demo.exception.ParkingFullException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingLot implements Parking {

    private static ParkingLot parkingLot;
    private final List<Slot> twoWheelerSlots;
    private final List<Slot> fourWheelerSlots;

    private ParkingLot() {
        this.twoWheelerSlots = new ArrayList<>();
        this.fourWheelerSlots = new ArrayList<>();
    }

    public static ParkingLot newParkingLot() {
        if (parkingLot == null) {
            parkingLot = new ParkingLot();
        }
        return parkingLot;
    }

    public static ParkingLot newParkingLot(int numberOfTwoWheelerParkingSlots, int numberOfFourWheelerParkingSlots) {
        if (parkingLot == null) {
            parkingLot = new ParkingLot();
            parkingLot.initializeParkingSlots(numberOfTwoWheelerParkingSlots, numberOfFourWheelerParkingSlots);
        }
        return parkingLot;
    }

    public boolean initializeParkingSlots(int numberOfTwoWheelerParkingSlots, int numberOfFourWheelerParkingSlots) {

        for (int i = 1; i <= numberOfTwoWheelerParkingSlots; i++) {
            twoWheelerSlots.add(new Slot(i));
        }
        System.out.printf("Created a two wheeler parking lot with %s slots %n", numberOfTwoWheelerParkingSlots);

        for (int i = 1; i <= numberOfFourWheelerParkingSlots; i++) {
            fourWheelerSlots.add(new Slot(i));
        }
        System.out.printf("Created a four wheeler parking lot with %s slots %n", numberOfFourWheelerParkingSlots);

        return true;
    }

    @Override
    public Ticket park(Vehicle vehicle) throws ParkingFullException {
        Slot nextAvailableSlot;

        if (vehicle.getVehicleSize().equals(VehicleSize.FOUR_WHEELER)) {
            nextAvailableSlot = getNextAvailableFourWheelerSlot();
        } else {
            nextAvailableSlot = getNextAvailableTwoWheelerSlot();
        }

        nextAvailableSlot.occupySlot(vehicle);
        System.out.printf("Allocated slot number: %d \n", nextAvailableSlot.getSlotNumber());

        return Ticket.of(nextAvailableSlot.getSlotNumber(), vehicle.getVehicleNumber(), new Date(), vehicle.getVehicleSize());
    }

    public void displayParkingStatus() {
        long availableFourWheelerSlots = fourWheelerSlots.stream()
                .filter(Slot::isEmpty)
                .count();

        long availableTwoWheelerSlots = twoWheelerSlots.stream()
                .filter(Slot::isEmpty)
                .count();

        System.out.printf("Available four wheeler slots: %d, Available two wheeler slots: %d", availableFourWheelerSlots, availableTwoWheelerSlots);
    }

    private Slot getNextAvailableFourWheelerSlot() throws ParkingFullException {
        return fourWheelerSlots.stream()
                .filter(Slot::isEmpty)
                .findFirst()
                .orElseThrow(ParkingFullException::noEmptySlotsAvailable);
    }

    private Slot getNextAvailableTwoWheelerSlot() throws ParkingFullException {
        return twoWheelerSlots.stream()
                .filter(Slot::isEmpty)
                .findFirst()
                .orElseThrow(ParkingFullException::noEmptySlotsAvailable);
    }
}
