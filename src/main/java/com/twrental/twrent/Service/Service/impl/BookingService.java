package com.twrental.twrent.Service.Service.impl;

import com.twrental.twrent.Model.Bookings;
import com.twrental.twrent.Repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class BookingService implements com.twrental.twrent.Service.BookingService {

    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository){
        super();
        this.bookingRepository = bookingRepository;
    }


    @Override
    public boolean checkUser(int bookingId, String userName) {
        Bookings bookings = bookingRepository.findById(bookingId).get();
        if(userName.toLowerCase(Locale.ROOT).equals(bookings.getCustomerUserName().toLowerCase(Locale.ROOT))){
            return true;
        }
        return false;
    }

    @Override
    public Bookings UpdateOrder(Bookings booking) {
        String dateOfCanceled = String.valueOf(LocalDate.now());
        Bookings bookings = bookingRepository.findById(booking.getBookingId()).get();
        if(booking.getBookingDateTo() == null){
            bookings.setBookingDateTo(dateOfCanceled);
            bookings.setBookingStatus("canceled");
           int newTotalPrice =  getTotalPrice(bookings.getBookingDateFrom(), bookings.getBookingDateTo(), bookings.getPriceDay());
           bookings.setTotalPrice(newTotalPrice);
           bookingRepository.save(bookings);
           return bookings;
        }
        else{
            bookings.setBookingDateFrom(booking.getBookingDateFrom());
            bookings.setBookingDateTo(booking.getBookingDateTo());
            int newTotalPrice =  getTotalPrice(bookings.getBookingDateFrom(), bookings.getBookingDateTo(), bookings.getPriceDay());
            bookings.setTotalPrice(newTotalPrice);
            bookings.setBookingStatus("ongoing");
            bookingRepository.save(bookings);
            return bookings;
        }


    }

    @Override
    public boolean checkBooking(int bookingId) {
        Optional<Bookings> check = bookingRepository.findById(bookingId);
        if(check.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public Bookings SaveBooking(Bookings bookings) {
        return bookingRepository.save(bookings);
    }

    @Override
    public List<Bookings> MyOrders(String customerUserName) {
        List<Bookings> listOrders = bookingRepository.findByCustomerUserName(customerUserName);
        return listOrders;
    }

    @Override
    public List<Bookings> ShowOrders() {
        return bookingRepository.findAll();
    }

    @Override
    public Bookings NewOrder(Bookings bookings, int PriceDay, String userName) {
        Bookings newBooking = new Bookings();
        newBooking.setCarId(bookings.getCarId());
        newBooking.setBookingDateFrom(bookings.getBookingDateFrom());
        newBooking.setBookingDateTo(bookings.getBookingDateTo());
        newBooking.setCustomerUserName(userName);
        newBooking.setPriceDay(PriceDay);
        int totalPrice = getTotalPrice(bookings.getBookingDateFrom(), bookings.getBookingDateTo(), PriceDay);
        newBooking.setTotalPrice(totalPrice);
        newBooking.setBookingStatus("ongoing");
        SaveBooking(newBooking);
        return newBooking;
    }

    @Override
    public int getTotalPrice(String DateFrom, String DateTo, int PriceDay) {
        LocalDate dateBefore = LocalDate.parse(DateFrom);
        LocalDate dateAfter = LocalDate.parse(DateTo);
        long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        long price;
        if(noOfDaysBetween <1){
            price = PriceDay * (noOfDaysBetween + 1);
        }
        else{
            price = PriceDay * noOfDaysBetween;
        }
        return (int) price;
    }

    @Override
    public Bookings GetTheBooking(int BookingId) {
        Bookings bookings = bookingRepository.findById(BookingId).get();
        return bookings;
    }


}
