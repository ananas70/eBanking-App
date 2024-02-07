package org.poo.cb;

public class NormalFeeStrategy implements TransferFeeStrategy{
    @Override
    public double calculateFee(double sum) {
        return 0.1 * sum; //  1% tax
    }
}
