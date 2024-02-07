package org.poo.cb;

public class PremiumUserDecorator implements UserDecorator{
    private User user;
    private TransferFeeStrategy feeStrategy;

    public PremiumUserDecorator(User user) {
        this.user = user;
    }

    @Override
    public void buyPremiumOption(User foundUser) {
        BankAccount account = foundUser.findAccountByCurrency("USD");
        if (account.getValue() < 100) {
            System.out.println("Insufficient amount in account for buying premium option");
            return;
        }
        // Deduct $100 from the USD account
        account.subtractValue(100.0);
    }

    @Override
    public boolean isPremiumUser(String email) {
        return true;
    }
}
