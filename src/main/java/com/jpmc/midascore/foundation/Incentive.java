package com.jpmc.midascore.foundation;

public class Incentive {
    private float amount;

    // Default constructor is required for JSON processing
    public Incentive() {
    }

    public Incentive(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Incentive{amount=" + amount + "}";
    }
}