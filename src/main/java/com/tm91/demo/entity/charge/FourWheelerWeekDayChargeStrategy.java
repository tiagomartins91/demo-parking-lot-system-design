package com.tm91.demo.entity.charge;

import com.tm91.demo.control.ParkingChargeStrategy;

public class FourWheelerWeekDayChargeStrategy implements ParkingChargeStrategy {

    private static final int HOUR_VALUE = 20;

    @Override
    public int getCharge(int parkHours) {
        return parkHours > 1 ? HOUR_VALUE * parkHours : HOUR_VALUE;
    }
}
