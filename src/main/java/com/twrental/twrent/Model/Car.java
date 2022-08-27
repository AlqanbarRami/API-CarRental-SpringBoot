package com.twrental.twrent.Model;

import com.sun.istack.NotNull;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int carId;
    @Column(name = "carName")
    private String carName;
    @Column(name = "carModel")
    private String carModel;
    @Column(name = "carPrice")
    private int carPrice;
    @Column(name = "carStatus")
    private String carStatus = "1";

    public Car(int carId, String carName, String carModel, int carPrice, String carStatus) {
        this.carId = carId;
        this.carName = carName;
        this.carModel = carModel;
        this.carPrice = carPrice;
        this.carStatus = carStatus;
    }

    public Car(){

    }
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(int carPrice) {
        this.carPrice = carPrice;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }
}