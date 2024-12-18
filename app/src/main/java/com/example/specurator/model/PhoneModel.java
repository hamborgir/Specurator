package com.example.specurator.model;

import java.io.Serializable;

public class PhoneModel implements Serializable {
//    the database uses uppercase first letter as column name

    int id;
    String image;
    String brand;
    String name;
    String release_date;
    double weight;
    String os;
    int storage; //in gigabyte
    double screen_size;
    String screen_resolution;
    double ram;
    int battery;
    double camera;
    double price; // most likely in Rupee

    public PhoneModel(int id, String image, String brand, String name, String release_date, double weight, String os, int storage, double screen_size, String screen_resolution, double ram, int battery, double camera, double price) {
        this.id = id;
        this.image = image;
        this.brand = brand;
        this.name = name;
        this.release_date = release_date;
        this.weight = weight;
        this.os = os;
        this.storage = storage;
        this.screen_size = screen_size;
        this.screen_resolution = screen_resolution;
        this.ram = ram;
        this.battery = battery;
        this.camera = camera;
        this.price = price;
    }

    public PhoneModel(int id, String brand, String name, String image) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public double getScreen_size() {
        return screen_size;
    }

    public void setScreen_size(double screen_size) {
        this.screen_size = screen_size;
    }

    public String getScreen_resolution() {
        return screen_resolution;
    }

    public void setScreen_resolution(String screen_resolution) {
        this.screen_resolution = screen_resolution;
    }

    public double getRam() {
        return ram;
    }

    public void setRam(double ram) {
        this.ram = ram;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public double getCamera() {
        return camera;
    }

    public void setCamera(double camera) {
        this.camera = camera;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
