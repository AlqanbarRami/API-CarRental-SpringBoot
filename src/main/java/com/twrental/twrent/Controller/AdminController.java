package com.twrental.twrent.Controller;

import com.twrental.twrent.Model.Car;
import com.twrental.twrent.Service.CarService;
import com.twrental.twrent.Service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1")
public class AdminController {

    private final CarService carService;
    private final CustomerService customerService;
    private static final Logger logger = Logger.getLogger(AdminController.class);


    public AdminController(CarService carService, CustomerService customerService){
        this.carService = carService;
        this.customerService = customerService;
    }


    @PostMapping("/addcar")
    public ResponseEntity<Car> AddNewCar(@Validated @RequestBody Car car){
        logger.info("Admin added a car");
        return  new ResponseEntity<Car>(carService.saveCar(car), HttpStatus.CREATED);

    }

    @DeleteMapping("/deletecar")
    public ResponseEntity DeleteCar(@RequestBody Car car){
        if(carService.DeleteCar(car) != null){
            logger.info("Admin deleted a car, carId : " + car.getCarId());
            return new ResponseEntity("Car deleted",HttpStatus.ACCEPTED);
        }
        logger.info("Admin Tried to delete a car, carId is wrong");
        return new ResponseEntity("Car Not Found , Please Check carId And Try Again",HttpStatus.NOT_FOUND);

    }

    @PutMapping("/updatecar")
    public ResponseEntity UpdateCar(@RequestBody Car car){

        if(carService.FindCar(car.getCarId()).isPresent()){
            Car CheckCar = carService.UpdateCar(car, car.getCarId());
            logger.info("An Admin updated a car : " + "Car Id : " + car.getCarId());
            return new ResponseEntity(CheckCar,HttpStatus.ACCEPTED);
        }
        logger.info("Admin tried to update a car : " + "Car Id : " + car.getCarId() + " but carId is wrong");
        return new ResponseEntity("Car not Found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/customers")
    public ResponseEntity GetCustomers(){
        if(customerService.GetAllCustomers().isEmpty()){
            logger.info("Admin Display all customers, but the list is empty");
            return new ResponseEntity("No Customers Yet!", HttpStatus.OK);
        }
        logger.info("Admin Display all customers");
        return new ResponseEntity(customerService.GetAllCustomers(),HttpStatus.OK);
    }
}
