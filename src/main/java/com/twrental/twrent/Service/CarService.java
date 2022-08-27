package com.twrental.twrent.Service;

import com.twrental.twrent.Model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> GetAllCars();
    Car UpdateCar(Car car, int carId);
    String DeleteCar(Car carFromBody);
    Optional<Car> FindCar(int carId);
    int getPrice(int carId);
    void MakeTheCarRented(boolean status, int carId);
    Car saveCar(Car car);
    boolean CheckCarAvailable(int carId);
}
