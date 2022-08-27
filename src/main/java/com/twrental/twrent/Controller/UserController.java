package com.twrental.twrent.Controller;
import com.twrental.twrent.Model.Bookings;
import com.twrental.twrent.Model.Car;
import com.twrental.twrent.Service.BookingService;
import com.twrental.twrent.Service.CarService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private final CarService carService;
    private final BookingService bookingService;
    private static final Logger logger = Logger.getLogger(UserController.class);



    public UserController(CarService carService, BookingService bookingService){
        this.carService = carService;
        this.bookingService = bookingService;

    }


    // "0" if the car not available and "1" for available
    // There is no clarification on the meaning of the available cars, the requirement is ambiguous anyway
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> GetCars() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (carService.GetAllCars() != null) {
            logger.info("Someone with UserName : " + auth.getName() + " Display the cars");
            return new ResponseEntity<List<Car>>(carService.GetAllCars(), HttpStatus.OK);
        } else {
            logger.info("Someone with UserName : " + auth.getName() + " Display the car , The list is empty");
            return new ResponseEntity("No Cars Yet!", HttpStatus.OK);
        }
    }

    @PostMapping("/ordercar")
    public ResponseEntity NewBooking(@RequestBody Bookings bookings){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Check if the id is correct and find the car
        Optional<Car> findMyCar = carService.FindCar(bookings.getCarId());
        if(findMyCar.isPresent()){
            // Getting the price to calculate the total price
            int getPriceDay = carService.getPrice(bookings.getCarId());
            //Check if the car is available
            if(!carService.CheckCarAvailable(bookings.getCarId())){
                logger.info("Someone with UserName : " + auth.getName() + " tried to rent a car, the carId is : " + bookings.getCarId() + " the car is not available!");
                return new ResponseEntity("Car Found but not available, please rent another car!",HttpStatus.FOUND);
            }
            // Send another request if any date was empty
            if(bookings.getBookingDateFrom() == null || bookings.getBookingDateTo() == null){
                logger.info("Someone with UserName : " + auth.getName() + " tried to rent a car, the carId is : " + bookings.getCarId() + ", with bad request, Date empty!");
                return new ResponseEntity("Please select the rental date from and to",HttpStatus.BAD_REQUEST);
            }
            else {
                Bookings newBooking = bookingService.NewOrder(bookings,getPriceDay,auth.getName());
                carService.MakeTheCarRented(false,bookings.getCarId()); // make the car not available!
                logger.info("Someone with UserName : " + auth.getName() + "  rented a car, the carId is : " + bookings.getCarId() );
                return new ResponseEntity(newBooking, HttpStatus.OK);
            }

        }
        else {
            logger.info("Someone with UserName : " + auth.getName() + " tried to rent a car, the carId is : " + bookings.getCarId() + " , carId is WRONG!");
            return new ResponseEntity("Car Not Found, Try with another carId", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/updateorder")
    public ResponseEntity UpdateMyOrder(@RequestBody Bookings bookings){
        //............Get the current user , Check if the request from an admin..........//
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_Admin")); //
        //----------------------------------------------------------------//

        if(bookingService.checkBooking(bookings.getBookingId())){ // check bookingId
            if(bookingService.checkUser(bookings.getBookingId(),auth.getName()) || hasUserRole){ // If from an admin is ok to update no need to be same user
                Bookings updateBooking = bookingService.UpdateOrder(bookings);
                Bookings GetCarIdInBooking = bookingService.GetTheBooking(bookings.getBookingId());
                carService.MakeTheCarRented(false,GetCarIdInBooking.getCarId()); // Make car rented
                if(bookings.getBookingDateTo() == null) {
                    carService.MakeTheCarRented(true,GetCarIdInBooking.getCarId()); // Make the car available again if user canceled any booking
                    logger.info("Someone with UserName : " + auth.getName() + " canceled booking with Id : " + bookings.getBookingId());
                }
                logger.info("Someone with UserName : " + auth.getName() + " updated booking with Id : " + bookings.getBookingId());
            return new ResponseEntity(updateBooking,HttpStatus.OK);
            }
            else{
                // The username must be the same in bookings table if the request not coming from an admin
                logger.info("Someone with UserName : " + auth.getName() + " tried to update booking with Id : " + bookings.getBookingId()+ " ,no permission!");
                return new ResponseEntity("You do not have permission", HttpStatus.FORBIDDEN);
            }
        }
        else {
            logger.info("Someone with UserName : " + auth.getName() + " tried to update booking with Id : " + bookings.getBookingId()+ " , Booking not found!");
            return new ResponseEntity("Booking Not Found, check booking id", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/myorders")
    public ResponseEntity GetMyOrders(){
        //....................//
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_Admin")); // Check if the request from an admin to send all bookings
        if(!hasUserRole) {
            //.................//

            List<Bookings> userBooking = bookingService.MyOrders(auth.getName());
            if (userBooking.isEmpty() ) {
                logger.info("Someone with UserName : " + auth.getName() + " display own orders, No Booking response!");
                return new ResponseEntity("No Booking!", HttpStatus.OK);
            }
            logger.info("Someone with UserName : " + auth.getName() + " display own orders");
            return new ResponseEntity(userBooking, HttpStatus.OK);
        }
        if(bookingService.ShowOrders().isEmpty()) {
            logger.info("An admin with UserName: " + auth.getName() + " display alla orders, No Booking response!");
            return new ResponseEntity("No Booking Yet!", HttpStatus.OK);
        }
        else {
            logger.info("An admin with UserName: " + auth.getName() + " display alla orders");
            return new ResponseEntity(bookingService.ShowOrders(), HttpStatus.OK);
        }
    }


}
