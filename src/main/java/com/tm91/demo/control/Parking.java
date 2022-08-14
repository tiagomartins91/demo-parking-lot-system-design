package com.tm91.demo.control;

import com.tm91.demo.entity.Ticket;
import com.tm91.demo.entity.Vehicle;
import com.tm91.demo.exception.ParkingFullException;

public interface Parking {

    Ticket park(Vehicle vehicle) throws ParkingFullException;

    //int unPark(Ticket ticket, )

}
