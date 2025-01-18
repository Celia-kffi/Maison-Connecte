package com.connexion.poo_project.douche;

public class TowelWarmer {
    private String name;
    private double temperature;
    private boolean isOn;
    private double targetTemperature;
    private double totalConsumption;
    private static final double POWER_RATE = 0.3; // kW par degré de différence

    public TowelWarmer(String name) {
        this.name = name;
        this.temperature = 20.0;
        this.isOn = false;
        this.targetTemperature = temperature;
        this.totalConsumption = 0;
    }

    public String getName() { return name; }
    public double getTemperature() { return temperature; }
    public boolean isOn() { return isOn; }
    public double getTargetTemperature() { return targetTemperature; }
    public double getTotalConsumption() { return totalConsumption; }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void togglePower() {
        isOn = !isOn;
        if (!isOn) {
            totalConsumption = 0;
        }
    }

    public void setTargetTemperature(double target) {
        this.targetTemperature = target;
    }

    public void updateConsumption(double hours) {
        if (isOn) {
            double tempDiff = Math.abs(targetTemperature - temperature);
            totalConsumption += tempDiff * POWER_RATE * hours;
        }
    }
}