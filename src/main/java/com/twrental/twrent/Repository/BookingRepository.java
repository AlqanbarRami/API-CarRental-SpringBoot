package com.twrental.twrent.Repository;

import com.twrental.twrent.Model.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Bookings,Integer> {

    List<Bookings> findByCustomerUserName(String userName);

}
