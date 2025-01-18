package com.connexion.poo_project.model;

public class Room {
    private String name;
    private double temperature;
    private boolean isOn;
    private double targetTemperature;
    private double totalConsumption;
    private static final double POWER_RATE = 0.5; // kW per degree difference

    public Room(String name, double temperature, boolean isOn) {
        this.name = name;
        this.temperature = temperature;
        this.isOn = isOn;
        this.targetTemperature = temperature + 1;
        this.totalConsumption = 0;
    }

    public String getName() { return name; }
    public double getTemperature() { return temperature; }
    public boolean isOn() { return isOn; }
    public double getTargetTemperature() { return targetTemperature; }
    public double getTotalConsumption() { return totalConsumption; }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        calculatePowerConsumption();
    }

    public void togglePower() {
        isOn = !isOn;
        if (!isOn) {
            totalConsumption = 0;
        }
    }

    public void setTargetTemperature(double target) {
        this.targetTemperature = target;
        calculatePowerConsumption();
    }

    public void updateConsumption(double hours) {
        if (isOn) {
            double tempDiff = Math.abs(targetTemperature - temperature);
            totalConsumption += tempDiff * POWER_RATE * hours;
        }
    }

    private void calculatePowerConsumption() {
        if (!isOn) {
            totalConsumption = 0;
            return;
        }
    }

}