package org.poo.cb;

public class AddMoneyCommand implements UserCommand{
    User user;
    String currency;
    Double value;
    public AddMoneyCommand (User user, String currency, Double value) {
        this.user = user;
        this.currency = currency;
        this.value = value;
    }
    @Override
    public void execute() {
        user.addMoney(currency,value);
    }
}
