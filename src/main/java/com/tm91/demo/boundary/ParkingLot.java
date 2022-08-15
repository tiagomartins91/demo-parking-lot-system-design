package com.tm91.demo.boundary;

import com.tm91.demo.control.Parking;
import com.tm91.demo.control.ParkingChargeStrategy;
import com.tm91.demo.entity.Slot;
import com.tm91.demo.entity.Ticket;
import com.tm91.demo.entity.Vehicle;
import com.tm91.demo.entity.VehicleSize;
import com.tm91.demo.exception.InvalidVehicleNumberException;
import com.tm91.demo.exception.ParkingFullException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingLot implements Parking {

    private static ParkingLot parkingLot;

    // TODO - Improve to use only one List<Slot> or just a class with capacity
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

    public int unPark(Ticket ticket, ParkingChargeStrategy parkingCostStrategy) throws InvalidVehicleNumberException {
        int costByHours;
        Slot slot;

        try {
            if (ticket.getVehicleSize().equals(VehicleSize.FOUR_WHEELER)) {
                slot = getFourWheelerSlotByVehicleNumber(ticket.getVehicleNumber());
            } else {
                slot = getTwoWheelerSlotByVehicleNumber(ticket.getVehicleNumber());
            }

            slot.vacateSlot();

            int hours = getHoursParked(ticket.getDate(), new Date());
            costByHours = parkingCostStrategy.getCharge(hours);

            System.out.println("Vehicle with registration " + ticket.getVehicleNumber() + " at slot number " + slot.getSlotNumber()
                    + " was parked for " + hours + " hours and the total charge is " + costByHours);
        } catch (InvalidVehicleNumberException invalidVehicleNumber) {
            System.out.println(invalidVehicleNumber.getMessage());
            throw invalidVehicleNumber;
        }

        return costByHours;
    }

    public void displayParkingStatus() {
        long availableFourWheelerSlots = fourWheelerSlots.stream()
                .filter(Slot::isEmpty)
                .count();

        long availableTwoWheelerSlots = twoWheelerSlots.stream()
                .filter(Slot::isEmpty)
                .count();

        System.out.printf("Parking status: Available four wheeler slots: %d, Available two wheeler slots: %d\n", availableFourWheelerSlots, availableTwoWheelerSlots);
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

    private Slot getFourWheelerSlotByVehicleNumber(String vehicleNumber) {
        return fourWheelerSlots.stream()
                .filter(slot -> slot.getVehicleNumber().equals(vehicleNumber))
                .findFirst()
                .orElseThrow(() -> new InvalidVehicleNumberException(vehicleNumber));
    }

    private Slot getTwoWheelerSlotByVehicleNumber(String vehicleNumber) {
        return twoWheelerSlots.stream()
                .filter(slot -> slot.getVehicleNumber().equals(vehicleNumber))
                .findFirst()
                .orElseThrow(() -> new InvalidVehicleNumberException(vehicleNumber));
    }

    private int getHoursParked(Date startDate, Date endDate) {
        long secs = (endDate.getTime() - startDate.getTime()) / 1000;
        return (int) (secs / 3600);
    }
}
