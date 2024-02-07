package org.poo.cb;

public class PremiumFeeStrategy implements TransferFeeStrategy{
    @Override
    public double calculateFee(double sum) {
        return 0; // No fee for premium
    }
}
