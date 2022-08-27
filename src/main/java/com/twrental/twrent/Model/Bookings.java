package com.twrental.twrent.Model;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "Bookings")
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @Column(name = "carId")
    private int carId;
    @Nullable
    @Column(name = "customerUserName")
    private String customerUserName;
    @Column(name = "bookingDateFrom")
    private String bookingDateFrom;
    @Column(name = "bookingDateTo")
    private String bookingDateTo;
    @Nullable
    @Column(name = "priceDay")
    private int priceDay;
    @Nullable
    @Column(name = "totalPrice")
    private int totalPrice;
    @Nullable
    @Column(name = "bookingStatus")
    private String bookingStatus;

    public Bookings(int bookingId, int carId, @Nullable String customerUserName, String bookingDateFrom, String bookingDateTo, int priceDay, int totalPrice, @Nullable String bookingStatus) {
        this.bookingId = bookingId;
        this.carId = carId;
        this.customerUserName = customerUserName;
        this.bookingDateFrom = bookingDateFrom;
        this.bookingDateTo = bookingDateTo;
        this.priceDay = priceDay;
        this.totalPrice = totalPrice;
        this.bookingStatus = bookingStatus;
    }

    public Bookings(){

    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Nullable
    public String getCustomerUserName() {
        return customerUserName;
    }

    public void setCustomerUserName(@Nullable String customerUserName) {
        this.customerUserName = customerUserName;
    }

    public String getBookingDateFrom() {
        return bookingDateFrom;
    }

    public void setBookingDateFrom(String bookingDateFrom) {
        this.bookingDateFrom = bookingDateFrom;
    }

    public String getBookingDateTo() {
        return bookingDateTo;
    }

    public void setBookingDateTo(String bookingDateTo) {
        this.bookingDateTo = bookingDateTo;
    }

    public int getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(int priceDay) {
        this.priceDay = priceDay;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Nullable
    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(@Nullable String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}