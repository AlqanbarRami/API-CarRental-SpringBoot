package com.twrental.twrent.Service.Service.impl;

import com.twrental.twrent.Model.Car;
import com.twrental.twrent.Repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarService implements com.twrental.twrent.Service.CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository){
        super();
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> GetAllCars() {
        List<Car> myCars = carRepository.findAll();
        if(myCars.isEmpty()){
            return null;
        }
        return myCars;

    }


    @Override
    public Car UpdateCar(Car car, int carId) {
        Car getTheCar = carRepository.findById(carId).get();
        if(getTheCar != null) {
            if(car.getCarModel()!=null) {
                getTheCar.setCarModel(car.getCarModel());
            }
            if(car.getCarName() != null) {
                getTheCar.setCarName(car.getCarName());
            }
            if(car.getCarPrice() != 0) {
                getTheCar.setCarPrice(car.getCarPrice());
            }
            if(car.getCarStatus() != null) {
                getTheCar.setCarStatus(car.getCarStatus());
            }
            Car newCarWithInfo = carRepository.save(getTheCar);
            return newCarWithInfo;
        }
        return null;

    }

    @Override
    public String DeleteCar(Car carFromBody) {
        Optional<Car> car = carRepository.findById(carFromBody.getCarId());
        if(car.isPresent()){
            carRepository.deleteById(carFromBody.getCarId());
            return "ok";
        }
        else
        {
           return null;
        }

    }

    @Override
    public Optional<Car> FindCar(int carId) {
        return carRepository.findById(carId);

    }

    @Override
    public int getPrice(int carId) {
        Car car = carRepository.findById(carId).get();
        if(car.getCarPrice() > 0) {
            return car.getCarPrice();
        }
        else {
            return 0;
        }

    }

    @Override
    public void MakeTheCarRented(boolean status, int carId) {
        Car car = carRepository.findById(carId).get();
        if (status) {
            car.setCarStatus("1");
        }
        else {
            car.setCarStatus("0");
        }
        carRepository.save(car);

    }

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public boolean CheckCarAvailable(int carId) {
        Car car = carRepository.findById(carId).get();
        if(Objects.equals(car.getCarStatus(), "1")){
            return true;
        }
        else {
            return false;
        }

    }
}
