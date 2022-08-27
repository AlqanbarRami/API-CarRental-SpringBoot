package com.twrental.twrent.Service;

import com.twrental.twrent.Model.Bookings;

import java.util.List;

public interface BookingService {

    boolean checkUser(int bookingId, String userName);
    Bookings UpdateOrder(Bookings booking);
    boolean checkBooking(int bookingId);
    Bookings SaveBooking(Bookings bookings);
    List<Bookings> MyOrders(String customerUserName);
    List<Bookings> ShowOrders();
    Bookings NewOrder(Bookings bookings, int PriceDay, String userName);
    int getTotalPrice(String DateFrom, String DateTo, int PriceDay);
    Bookings GetTheBooking(int BookingId);
}
