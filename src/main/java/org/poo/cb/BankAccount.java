package org.poo.cb;

public class BankAccount {
    final private String currency;
    private Double value;
    public BankAccount(String currency) {
        this.currency = currency;
    }
    public BankAccount(String currency, Double value) {
        this.currency = currency;
        this.value = value;
    }
    public String getCurrency() {
        return currency;
    }

    public Double getValue() {
        return value;
    }
    public void addValue(Double addedValue) {
        value += addedValue;
    }
    public void subtractValue(Double subtractedValue) {
        value -= subtractedValue;
    }
}
